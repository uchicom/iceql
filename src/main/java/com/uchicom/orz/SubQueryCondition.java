/*
 * Copyright 2012 James Moger.
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

package com.uchicom.orz;

/**
 * A condition that contains a subquery.
 *
 * @param <A> the operand type
 */

class SubQueryCondition<A, Y, Z> implements Token {
    enum Type {
        IN, NOT_IN, EXISTS, NOT_EXISTS
    }

    A x;
    SubQuery<Y, Z> subquery;
    Type type;

    SubQueryCondition(A x, SubQuery<Y, Z> subquery) {
        this(x, subquery, Type.IN);
    }

    SubQueryCondition(A x, SubQuery<Y, Z> subquery, Type type) {
        this.x = x;
        this.subquery = subquery;
        this.type = type;
    }

    public <T> void appendSQL(SQLStatement stat, Query<T> query) {
        switch (type) {
            case EXISTS:
                stat.appendSQL("exists (");
                break;
            case NOT_EXISTS:
                stat.appendSQL("not exists (");
                break;
            case NOT_IN:
                query.appendSQL(stat, null, x);
                stat.appendSQL(" not in (");
                break;
            default:
                query.appendSQL(stat, null, x);
                stat.appendSQL(" in (");
                break;
        }
        subquery.appendSQL(stat);
        stat.appendSQL(")");
    }
}
