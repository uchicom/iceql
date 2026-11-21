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

import com.uchicom.orz.util.Utils;

/**
 * This class represents a query with join and an incomplete condition.
 * Conditions are combined with AND; use {@code is(..).and(..)} to add more.
 *
 * @param <A> the incomplete condition data type
 */

public class QueryJoinCondition<T, A> {

    private Query<T> query;
    private SelectTable<T> join;
    private A x;

    QueryJoinCondition(Query<T> query, SelectTable<T> join, A x) {
        this.query = query;
        this.join = join;
        this.x = x;
    }

    private QueryJoinChain<T> chain() {
        return new QueryJoinChain<T>(query, new QueryJoin<T>(query, join));
    }

    public QueryJoinChain<T> is(boolean y) {
        return addPrimitive(y);
    }

    public QueryJoinChain<T> is(byte y) {
        return addPrimitive(y);
    }

    public QueryJoinChain<T> is(short y) {
        return addPrimitive(y);
    }

    public QueryJoinChain<T> is(int y) {
        return addPrimitive(y);
    }

    public QueryJoinChain<T> is(long y) {
        return addPrimitive(y);
    }

    public QueryJoinChain<T> is(float y) {
        return addPrimitive(y);
    }

    public QueryJoinChain<T> is(double y) {
        return addPrimitive(y);
    }

    @SuppressWarnings("unchecked")
    private QueryJoinChain<T> addPrimitive(Object o) {
        A alias = query.getPrimitiveAliasByValue((A) o);
        if (alias == null) {
            join.addConditionToken(new Condition<A>(x, (A) o, CompareType.EQUAL));
        } else {
            join.addConditionToken(new Condition<A>(x, alias, CompareType.EQUAL));
        }
        return chain();
    }

    public QueryJoinChain<T> is(A y) {
        join.addConditionToken(new Condition<A>(x, y, CompareType.EQUAL));
        return chain();
    }

    public QueryJoinChain<T> isNull() {
        join.addConditionToken(new Condition<A>(x, CompareType.IS_NULL));
        return chain();
    }

    public QueryJoinChain<T> isNotNull() {
        join.addConditionToken(new Condition<A>(x, CompareType.IS_NOT_NULL));
        return chain();
    }

    @SafeVarargs
    public final QueryJoinChain<T> oneOf(A... a) {
        return oneOf(Utils.newArrayIterable(a));
    }

    public QueryJoinChain<T> oneOf(Iterable<A> i) {
        join.addConditionToken(new Condition<A>(x, i, CompareType.IN));
        return chain();
    }

    @SafeVarargs
    public final QueryJoinChain<T> noneOf(A... a) {
        return noneOf(Utils.newArrayIterable(a));
    }

    public QueryJoinChain<T> noneOf(Iterable<A> i) {
        join.addConditionToken(new Condition<A>(x, i, CompareType.NOT_IN));
        return chain();
    }

    public QueryJoinChain<T> isNot(boolean y) {
        return addPrimitiveNot(y);
    }

    public QueryJoinChain<T> isNot(byte y) {
        return addPrimitiveNot(y);
    }

    public QueryJoinChain<T> isNot(short y) {
        return addPrimitiveNot(y);
    }

    public QueryJoinChain<T> isNot(int y) {
        return addPrimitiveNot(y);
    }

    public QueryJoinChain<T> isNot(long y) {
        return addPrimitiveNot(y);
    }

    public QueryJoinChain<T> isNot(float y) {
        return addPrimitiveNot(y);
    }

    public QueryJoinChain<T> isNot(double y) {
        return addPrimitiveNot(y);
    }

    @SuppressWarnings("unchecked")
    private QueryJoinChain<T> addPrimitiveNot(Object o) {
        A alias = query.getPrimitiveAliasByValue((A) o);
        if (alias == null) {
            join.addConditionToken(new Condition<A>(x, (A) o, CompareType.NOT_EQUAL));
        } else {
            join.addConditionToken(new Condition<A>(x, alias, CompareType.NOT_EQUAL));
        }
        return chain();
    }

    public QueryJoinChain<T> isNot(A y) {
        join.addConditionToken(new Condition<A>(x, y, CompareType.NOT_EQUAL));
        return chain();
    }
}
