/*
 * Copyright 2026 uchicom.
 * Modified by uchicom: package renamed from com.iciql to com.uchicom.orz.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.uchicom.orz.test;

import com.uchicom.orz.Db;
import com.uchicom.orz.Iciql.IQColumn;
import com.uchicom.orz.Iciql.IQTable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Round-trip tests of the java.time types.
 */
public class JavaTimeTest {

    Db db;

    @Before
    public void setup() {
        db = IciqlSuite.openNewDb();
    }

    @After
    public void tearDown() {
        try {
            db.dropTable(TimeRecord.class);
        } catch (Exception e) {
            // table may not exist
        }
        db.close();
    }

    private List<TimeRecord> insert() {
        List<TimeRecord> originals = TimeRecord.getList();
        db.insertAll(originals);
        return originals;
    }

    private void assertSame(TimeRecord expected, TimeRecord actual) {
        assertNotNull(actual);
        assertEquals(expected.id, actual.id);
        assertEquals(expected.dateOnly, actual.dateOnly);
        assertEquals(expected.timeOfDay, actual.timeOfDay);
        assertEquals(expected.dateTimeLocal, actual.dateTimeLocal);
        assertEquals(expected.zonedDateTime.toInstant(), actual.zonedDateTime.toInstant());
        assertEquals(expected.instant, actual.instant);
        assertEquals(expected.offsetDateTime.toInstant(), actual.offsetDateTime.toInstant());
        assertEquals(expected.offsetTime, actual.offsetTime);
        assertEquals(expected.yearValue, actual.yearValue);
        assertEquals(expected.monthDay, actual.monthDay);
        assertEquals(expected.duration, actual.duration);
    }

    @Test
    public void testRoundTrip() {
        List<TimeRecord> originals = insert();
        TimeRecord t = new TimeRecord();
        List<TimeRecord> retrieved = db.from(t).orderBy(t.id).select();
        assertEquals(originals.size(), retrieved.size());
        for (int i = 0; i < originals.size(); i++) {
            assertSame(originals.get(i), retrieved.get(i));
        }
        db.dropTable(TimeRecord.class);
    }

    @Test
    public void testWhere() {
        List<TimeRecord> originals = insert();
        TimeRecord t = new TimeRecord();
        TimeRecord expected = originals.get(3);

        assertSame(expected, db.from(t).where(t.dateOnly).is(expected.dateOnly).selectFirst());
        assertSame(expected, db.from(t).where(t.timeOfDay).is(expected.timeOfDay).selectFirst());
        assertSame(expected, db.from(t).where(t.dateTimeLocal).is(expected.dateTimeLocal).selectFirst());
        assertSame(expected, db.from(t).where(t.zonedDateTime).is(expected.zonedDateTime).selectFirst());
        assertSame(expected, db.from(t).where(t.instant).is(expected.instant).selectFirst());
        assertSame(expected, db.from(t).where(t.offsetDateTime).is(expected.offsetDateTime).selectFirst());
        assertSame(expected, db.from(t).where(t.offsetTime).is(expected.offsetTime).selectFirst());
        assertSame(expected, db.from(t).where(t.yearValue).is(expected.yearValue).selectFirst());
        assertSame(expected, db.from(t).where(t.monthDay).is(expected.monthDay).selectFirst());
        assertSame(expected, db.from(t).where(t.duration).is(expected.duration).selectFirst());

        // range comparison
        assertEquals(3, db.from(t).where(t.instant).exceeds(originals.get(6).instant).select().size());
        db.dropTable(TimeRecord.class);
    }

    /**
     * Instant must be stored as the UTC wall-clock time.
     */
    @Test
    public void testInstantIsUtc() {
        insert();
        TimeRecord t = new TimeRecord();
        TimeRecord first = db.from(t).orderBy(t.id).selectFirst();
        // compare with the UTC literal, independent of the JVM default time zone
        LocalDateTime utc = LocalDateTime.of(2024, 11, 1, 12, 34, 56, 123_000_000);
        assertEquals(utc.toInstant(ZoneOffset.UTC), first.instant);
        TimeRecord found = db.from(t).where(t.instant).is(utc.toInstant(ZoneOffset.UTC)).selectFirst();
        assertEquals(first.id, found.id);
        db.dropTable(TimeRecord.class);
    }

    /**
     * A simple class used in this test.
     */
    @IQTable(name = "JAVA_TIME_TEST")
    public static class TimeRecord {

        @IQColumn(primaryKey = true)
        public Integer id;

        @IQColumn()
        public LocalDate dateOnly;

        @IQColumn()
        public LocalTime timeOfDay;

        @IQColumn()
        public LocalDateTime dateTimeLocal;

        @IQColumn()
        public ZonedDateTime zonedDateTime;

        @IQColumn()
        public Instant instant;

        @IQColumn()
        public OffsetDateTime offsetDateTime;

        @IQColumn()
        public OffsetTime offsetTime;

        @IQColumn()
        public Year yearValue;

        @IQColumn()
        public MonthDay monthDay;

        @IQColumn()
        public Duration duration;

        public TimeRecord() {
            // public constructor
        }

        public static List<TimeRecord> getList() {
            List<TimeRecord> list = new ArrayList<TimeRecord>();
            LocalDateTime base = LocalDateTime.of(2024, 11, 1, 12, 34, 56, 123_000_000);
            for (int i = 0; i < 10; i++) {
                TimeRecord r = new TimeRecord();
                r.id = i + 1;
                LocalDateTime ldt = base.plusDays(i).plusMinutes(i);
                r.dateOnly = ldt.toLocalDate();
                r.timeOfDay = ldt.toLocalTime().withNano(0).plusSeconds(i);
                r.dateTimeLocal = ldt;
                r.zonedDateTime = ldt.atZone(ZoneId.systemDefault());
                r.instant = ldt.toInstant(ZoneOffset.UTC);
                r.offsetDateTime = ldt.atOffset(ZoneOffset.ofHours(9));
                r.offsetTime = OffsetTime.of(ldt.toLocalTime(), ZoneOffset.ofHours(9));
                r.yearValue = Year.of(2000 + i);
                r.monthDay = MonthDay.of(1 + i, 1 + i);
                r.duration = Duration.ofMinutes(90 + i).plusMillis(i);
                list.add(r);
            }
            return list;
        }
    }
}
