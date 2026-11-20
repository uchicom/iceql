/*
 * Copyright 2017 James Moger.
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
 * @author James Moger
 */
public interface Bitwise<A, T> {

    QueryWhere<T> exceeds(A y);

    QueryWhere<T> atLeast(A y);

    QueryWhere<T> lessThan(A y);

    QueryWhere<T> atMost(A y);
}
