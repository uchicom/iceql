/*
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
import com.iciql.QueryWhere;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests of Joins.
 */
public class JoinTest {

    Db db;

    @Before
    public void setup() {
        db = IciqlSuite.openNewDb();

        db.insertAll(UserId.getList());
        db.insertAll(UserNote.getList());
    }

    @After
    public void tearDown() {
        db.close();
    }

    @Test
    public void testPrimitiveJoin() throws Exception {
        final UserId u = new UserId();
        final UserNote n = new UserNote();

        List<UserNote> notes = db.from(u).innerJoin(n).on(u.id).is(n.userId).where(u.id).is(2)
                .select(new UserNote() {
                    {
                        userId = n.userId;
                        id = n.id;
                        text = n.text;
                    }
                });
        assertEquals(3, notes.size());
    }

    @Test
    public void testPrimitiveJoinAndSimpleSelect() throws Exception {
        final UserId u = new UserId();
        final UserNote n = new UserNote();

        List<UserId> userIds = db.from(u).innerJoin(n).on(u.id).is(n.userId).where(n.text).is("D")
                .select();
        assertEquals(1, userIds.size());
        assertEquals(1, userIds.get(0).id);
    }

    @Test
    public void testJoin() throws Exception {
        final UserId u = new UserId();
        final UserNote n = new UserNote();

        // this query returns 1 UserId if the user has a note
        // it's purpose is to confirm fluency/type-safety on a very simple
        // join case where the main table is filtered/reduced by hits in a
        // related table

        List<UserId> users = db.from(u).innerJoin(n).on(u.id).is(n.userId).where(u.id).is(2).selectDistinct();

        assertEquals(1, users.size());
        assertEquals(2, users.get(0).id);
    }

    @Test
    public void testLeftJoin() throws Exception {
        final UserId u = new UserId();
        final UserNote n = new UserNote();

        List<UserId> notes = db.from(u).leftJoin(n).on(u.id).is(n.userId).where(u.id).is(4).select();
        assertEquals(1, notes.size());
        assertEquals(4, notes.get(0).id);
    }

    @Test
    public void testCompoundJoin() throws Exception {
        final UserId u = new UserId();
        final UserNote n = new UserNote();

        QueryWhere<UserId> q = db.from(u)
                .innerJoin(n)
                .on(u.id).isAnd(n.userId)
                .on(u.name).is("Dick")
                .where(u.id).is(2).or(u.id).is(1);

        List<UserId> users = q.selectDistinct();
        assertEquals(1, users.size());
        assertEquals(2, users.get(0).id);
        assertTrue(q.toSQL().contains("AND"));
    }

    @Test
    public void testJoinOneOf() throws Exception {
        final UserId u = new UserId();
        final UserNote n = new UserNote();

        // only user 1 has notes A, D, G
        QueryWhere<UserId> q = db.from(u)
                .innerJoin(n)
                .on(u.id).isAnd(n.userId)
                .on(n.text).oneOf("A", "D", "G")
                .where(u.id).exceeds(0);
        List<UserId> users = q.selectDistinct();
        assertEquals(1, users.size());
        assertEquals(1, users.get(0).id);
        assertTrue(q.toSQL().contains("IN('A', 'D', 'G')"));

        // Iterable and chained variants
        List<UserId> users2 = db.from(u)
                .innerJoin(n)
                .on(u.id).isAnd(n.userId)
                .on(n.text).oneOfAnd(Arrays.asList("A", "B", "D", "E"))
                .on(n.text).oneOf(Arrays.asList("A", "D"))
                .where(u.id).exceeds(0).selectDistinct();
        assertEquals(1, users2.size());
        assertEquals(1, users2.get(0).id);
    }

    @Test
    public void testJoinIsNullIsNotNull() throws Exception {
        final UserId u = new UserId();
        final UserNote n = new UserNote();

        // no note text is null: nothing joins
        QueryWhere<UserId> q = db.from(u)
                .innerJoin(n)
                .on(u.id).isAnd(n.userId)
                .on(n.text).isNull()
                .where(u.id).exceeds(0);
        assertEquals(0, q.selectDistinct().size());
        assertTrue(q.toSQL().contains("IS NULL"));

        // all note texts are not null: all users join
        QueryWhere<UserId> q2 = db.from(u)
                .innerJoin(n)
                .on(u.id).isAnd(n.userId)
                .on(n.text).isNotNull()
                .where(u.id).exceeds(0);
        assertEquals(3, q2.selectDistinct().size());
        assertTrue(q2.toSQL().contains("IS NOT NULL"));

        // chained variants
        List<UserId> users3 = db.from(u)
                .innerJoin(n)
                .on(u.id).isAnd(n.userId)
                .on(n.text).isNotNullAnd()
                .on(n.text).isNull()
                .where(u.id).exceeds(0).selectDistinct();
        assertEquals(0, users3.size());

        List<UserId> users4 = db.from(u)
                .innerJoin(n)
                .on(u.id).isAnd(n.userId)
                .on(n.text).isNullAnd()
                .on(n.text).isNotNull()
                .where(u.id).exceeds(0).selectDistinct();
        assertEquals(0, users4.size());
    }

    @Test
    public void testJoinIsNot() throws Exception {
        final UserId u = new UserId();
        final UserNote n = new UserNote();

        QueryWhere<UserId> q = db.from(u)
                .innerJoin(n)
                .on(u.id).isAnd(n.userId)
                .on(n.text).isNot("A")
                .where(u.id).exceeds(0);
        assertTrue(q.toSQL().contains("<>"));
        assertEquals(3, q.selectDistinct().size());

        // chained variant: user 1 only has A, D, G so it disappears
        List<UserId> users = db.from(u)
                .innerJoin(n)
                .on(u.id).isAnd(n.userId)
                .on(n.text).isNotAnd("A")
                .on(n.text).isNotAnd("D")
                .on(n.text).isNot("G")
                .where(u.id).exceeds(0).selectDistinct();
        assertEquals(2, users.size());

        // primitive variant
        List<UserId> users2 = db.from(u)
                .innerJoin(n)
                .on(u.id).isAnd(n.userId)
                .on(u.id).isNot(1)
                .where(u.id).exceeds(0).selectDistinct();
        assertEquals(2, users2.size());
    }

    @Test
    public void testJoinNoneOf() throws Exception {
        final UserId u = new UserId();
        final UserNote n = new UserNote();

        // user 1 only has notes A, D, G so it must not be joined
        QueryWhere<UserId> q = db.from(u)
                .innerJoin(n)
                .on(u.id).isAnd(n.userId)
                .on(n.text).noneOf("A", "D", "G")
                .where(u.id).exceeds(0);
        List<UserId> users = q.selectDistinct();
        assertEquals(2, users.size());
        assertTrue(q.toSQL().contains("NOT IN"));

        // Iterable and chained variants
        List<UserId> users2 = db.from(u)
                .innerJoin(n)
                .on(u.id).isAnd(n.userId)
                .on(n.text).noneOfAnd(Arrays.asList("A", "D", "G"))
                .on(n.text).noneOf(Arrays.asList("B", "E", "H"))
                .where(u.id).exceeds(0).selectDistinct();
        assertEquals(1, users2.size());
        assertEquals(3, users2.get(0).id);
    }

    @Test
    public void testSubQuery() throws Exception {
        final UserId u = new UserId();
        final UserNote n = new UserNote();

        QueryWhere<UserId> q = db.from(u).where(u.id).in(db.from(n).where(n.userId).exceeds(0).subQuery(n.userId));
        List<UserId> notes = q.select();
        assertEquals(3, notes.size());

        // do not test MySQL on this statement because the databases
        if (IciqlSuite.isMySQL(db)) {
            assertEquals("SELECT * FROM UserId WHERE `id` in (SELECT `userId` FROM UserNote WHERE `userId` > 0 )", q.toSQL());
        } else {
            assertEquals("SELECT * FROM UserId WHERE id in (SELECT userId FROM UserNote WHERE userId > 0 )", q.toSQL());
        }
    }

    @IQTable
    public static class UserId {

        @IQColumn(primaryKey = true)
        public int id;

        @IQColumn(length = 10)
        public String name;

        public UserId() {
            // public constructor
        }

        public UserId(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public String toString() {
            return name + " (" + id + ")";
        }

        public static List<UserId> getList() {
            UserId[] list = {new UserId(1, "Tom"), new UserId(2, "Dick"), new UserId(3, "Harry"), new UserId(4, "Jack")};
            return Arrays.asList(list);
        }
    }

    @IQTable
    public static class UserNote {

        @IQColumn(autoIncrement = true, primaryKey = true)
        public int id;

        @IQColumn
        public int userId;

        @IQColumn(length = 10)
        public String text;

        public UserNote() {
            // public constructor
        }

        public UserNote(int userId, String text) {
            this.userId = userId;
            this.text = text;
        }

        public String toString() {
            return text;
        }

        public static List<UserNote> getList() {
            UserNote[] list = {new UserNote(1, "A"), new UserNote(2, "B"), new UserNote(3, "C"),
                    new UserNote(1, "D"), new UserNote(2, "E"), new UserNote(3, "F"), new UserNote(1, "G"),
                    new UserNote(2, "H"), new UserNote(3, "I"),};
            return Arrays.asList(list);
        }
    }
}
