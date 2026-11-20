/*
 * Copyright 2011 James Moger.
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
package com.uchicom.orz.test.models;

import com.uchicom.orz.Iciql.EnumType;
import com.uchicom.orz.Iciql.IQColumn;
import com.uchicom.orz.Iciql.IQEnum;
import com.uchicom.orz.Iciql.IQTable;
import com.uchicom.orz.test.models.EnumModels.Tree;

import java.sql.Timestamp;

/**
 * Static query models.
 */
public class StaticQueries {

    @IQTable(name = "StaticQueryTest1")
    public static class StaticModel1 {

        @IQColumn(primaryKey = true, autoIncrement = true)
        public Integer id;

        @IQColumn
        @IQEnum(EnumType.NAME)
        public Tree myTree;

        @IQColumn
        public String myString;

        @IQColumn
        public Boolean myBool;

        @IQColumn
        public Timestamp myTimestamp;

        @IQColumn
        public java.sql.Date myDate;

        @IQColumn
        public java.sql.Time myTime;

        public StaticModel1() {
        }
    }

    @IQTable(name = "StaticQueryTest2")
    public static class StaticModel2 {

        @IQColumn(primaryKey = true, autoIncrement = true)
        public Integer id;

        @IQColumn
        @IQEnum(EnumType.ENUMID)
        public Tree myTree;

        public StaticModel2() {
        }
    }

    @IQTable(name = "StaticQueryTest3")
    public static class StaticModel3 {

        @IQColumn(primaryKey = true, autoIncrement = true)
        public Integer id;

        @IQColumn
        @IQEnum(EnumType.ORDINAL)
        public Tree myTree;

        public StaticModel3() {
        }
    }
}
