/*
 * Copyright 2004-2011 H2 Group.
 * Copyright 2011 James Moger.
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

package com.iciql.test;

import com.iciql.Db;
import com.iciql.Iciql.IQColumn;
import com.iciql.Iciql.IQTable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests of YearMonth type.
 */
public class YearMonthTest {

    Db db;

    @Before
    public void setup() {
        db = IciqlSuite.openNewDb();
    }

    @After
    public void tearDown() {
        db.close();
    }

    @Test
    public void testYearMonths() {
        List<YearMonthRecord> originals = YearMonthRecord.getList();
        db.insertAll(originals);

        YearMonthRecord y = new YearMonthRecord();
        List<YearMonthRecord> retrieved = db.from(y).orderBy(y.id).select();
        assertEquals(originals.size(), retrieved.size());
        for (int i = 0; i < originals.size(); i++) {
            assertEquals(originals.get(i).id, retrieved.get(i).id);
            assertEquals(originals.get(i).yearMonth, retrieved.get(i).yearMonth);
        }

        YearMonth target = originals.get(3).yearMonth;
        YearMonthRecord found = db.from(y).where(y.yearMonth).is(target).selectFirst();
        assertEquals(originals.get(3).id, found.id);
        assertEquals(target, found.yearMonth);

        db.dropTable(YearMonthRecord.class);
    }

    /**
     * A simple class used in this test.
     */
    @IQTable(name = "YEAR_MONTH_TEST")
    public static class YearMonthRecord {

        @IQColumn(primaryKey = true)
        public Integer id;

        @IQColumn()
        public YearMonth yearMonth;

        public YearMonthRecord() {
            // public constructor
        }

        private YearMonthRecord(int id, YearMonth yearMonth) {
            this.id = id;
            this.yearMonth = yearMonth;
        }

        public static List<YearMonthRecord> getList() {
            List<YearMonthRecord> list = new ArrayList<YearMonthRecord>();
            YearMonth base = YearMonth.of(2024, 11);
            for (int i = 0; i < 10; i++) {
                list.add(new YearMonthRecord(i + 1, base.plusMonths(i)));
            }
            return list;
        }
    }
}
