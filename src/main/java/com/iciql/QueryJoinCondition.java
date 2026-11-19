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

package com.iciql;

import com.iciql.util.Utils;

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

    @Deprecated
    public QueryJoin<T> isNullAnd() {
        join.addConditionToken(new Condition<A>(x, CompareType.IS_NULL));
        return new QueryJoin<T>(query, join);
    }

    public QueryJoinChain<T> isNotNull() {
        join.addConditionToken(new Condition<A>(x, CompareType.IS_NOT_NULL));
        return chain();
    }

    @Deprecated
    public QueryJoin<T> isNotNullAnd() {
        join.addConditionToken(new Condition<A>(x, CompareType.IS_NOT_NULL));
        return new QueryJoin<T>(query, join);
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
    @Deprecated
    public final QueryJoin<T> oneOfAnd(A... a) {
        return oneOfAnd(Utils.newArrayIterable(a));
    }

    @Deprecated
    public QueryJoin<T> oneOfAnd(Iterable<A> i) {
        join.addConditionToken(new Condition<A>(x, i, CompareType.IN));
        return new QueryJoin<T>(query, join);
    }

    @SafeVarargs
    public final QueryJoinChain<T> noneOf(A... a) {
        return noneOf(Utils.newArrayIterable(a));
    }

    public QueryJoinChain<T> noneOf(Iterable<A> i) {
        join.addConditionToken(new Condition<A>(x, i, CompareType.NOT_IN));
        return chain();
    }

    @SafeVarargs
    @Deprecated
    public final QueryJoin<T> noneOfAnd(A... a) {
        return noneOfAnd(Utils.newArrayIterable(a));
    }

    @Deprecated
    public QueryJoin<T> noneOfAnd(Iterable<A> i) {
        join.addConditionToken(new Condition<A>(x, i, CompareType.NOT_IN));
        return new QueryJoin<T>(query, join);
    }

    @Deprecated
    public QueryJoin<T> isAnd(boolean y) {
        return addPrimitiveAnd(y);
    }

    @Deprecated
    public QueryJoin<T> isAnd(byte y) {
        return addPrimitiveAnd(y);
    }

    @Deprecated
    public QueryJoin<T> isAnd(short y) {
        return addPrimitiveAnd(y);
    }

    @Deprecated
    public QueryJoin<T> isAnd(int y) {
        return addPrimitiveAnd(y);
    }

    @Deprecated
    public QueryJoin<T> isAnd(long y) {
        return addPrimitiveAnd(y);
    }

    @Deprecated
    public QueryJoin<T> isAnd(float y) {
        return addPrimitiveAnd(y);
    }

    @Deprecated
    public QueryJoin<T> isAnd(double y) {
        return addPrimitiveAnd(y);
    }

    @SuppressWarnings("unchecked")
    private QueryJoin<T> addPrimitiveAnd(Object o) {
        A alias = query.getPrimitiveAliasByValue((A) o);
        if (alias == null) {
            join.addConditionToken(new Condition<A>(x, (A) o, CompareType.EQUAL));
        } else {
            join.addConditionToken(new Condition<A>(x, alias, CompareType.EQUAL));
        }
        return new QueryJoin<T>(query, join);
    }

    @Deprecated
    public QueryJoin<T> isAnd(A y) {
        join.addConditionToken(new Condition<A>(x, y, CompareType.EQUAL));
        return new QueryJoin<T>(query, join);
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

    @Deprecated
    public QueryJoin<T> isNotAnd(boolean y) {
        return addPrimitiveNotAnd(y);
    }

    @Deprecated
    public QueryJoin<T> isNotAnd(byte y) {
        return addPrimitiveNotAnd(y);
    }

    @Deprecated
    public QueryJoin<T> isNotAnd(short y) {
        return addPrimitiveNotAnd(y);
    }

    @Deprecated
    public QueryJoin<T> isNotAnd(int y) {
        return addPrimitiveNotAnd(y);
    }

    @Deprecated
    public QueryJoin<T> isNotAnd(long y) {
        return addPrimitiveNotAnd(y);
    }

    @Deprecated
    public QueryJoin<T> isNotAnd(float y) {
        return addPrimitiveNotAnd(y);
    }

    @Deprecated
    public QueryJoin<T> isNotAnd(double y) {
        return addPrimitiveNotAnd(y);
    }

    @SuppressWarnings("unchecked")
    private QueryJoin<T> addPrimitiveNotAnd(Object o) {
        A alias = query.getPrimitiveAliasByValue((A) o);
        if (alias == null) {
            join.addConditionToken(new Condition<A>(x, (A) o, CompareType.NOT_EQUAL));
        } else {
            join.addConditionToken(new Condition<A>(x, alias, CompareType.NOT_EQUAL));
        }
        return new QueryJoin<T>(query, join);
    }

    @Deprecated
    public QueryJoin<T> isNotAnd(A y) {
        join.addConditionToken(new Condition<A>(x, y, CompareType.NOT_EQUAL));
        return new QueryJoin<T>(query, join);
    }
}
