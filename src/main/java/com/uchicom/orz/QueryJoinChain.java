/*
 * Copyright 2004-2011 H2 Group.
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

package com.uchicom.orz;

/**
 * A query returned after a join condition was completed. It behaves as a
 * normal {@link Query}, and additionally allows further join conditions to be
 * appended with {@code and(...)}. Chained conditions are combined with AND in
 * the ON clause (<code>ON a = b AND c = d</code>).
 */
public class QueryJoinChain<T> extends Query<T> {

    private final QueryJoin<T> queryJoin;

    QueryJoinChain(Query<T> query, QueryJoin<T> queryJoin) {
        super(query);
        this.queryJoin = queryJoin;
    }

    public QueryJoinCondition<T, Boolean> and(boolean x) {
        return queryJoin.on(x);
    }

    public QueryJoinCondition<T, Byte> and(byte x) {
        return queryJoin.on(x);
    }

    public QueryJoinCondition<T, Short> and(short x) {
        return queryJoin.on(x);
    }

    public QueryJoinCondition<T, Integer> and(int x) {
        return queryJoin.on(x);
    }

    public QueryJoinCondition<T, Long> and(long x) {
        return queryJoin.on(x);
    }

    public QueryJoinCondition<T, Float> and(float x) {
        return queryJoin.on(x);
    }

    public QueryJoinCondition<T, Double> and(double x) {
        return queryJoin.on(x);
    }

    public <A> QueryJoinCondition<T, A> and(A x) {
        return queryJoin.on(x);
    }
}
