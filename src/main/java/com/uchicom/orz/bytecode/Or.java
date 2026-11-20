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

package com.uchicom.orz.bytecode;

import com.uchicom.orz.Query;
import com.uchicom.orz.SQLStatement;
import com.uchicom.orz.Token;

/**
 * An OR expression.
 */
public class Or implements Token {

    private final Token left, right;

    private Or(Token left, Token right) {
        this.left = left;
        this.right = right;
    }

    static Or get(Token left, Token right) {
        return new Or(left, right);
    }

    public <T> void appendSQL(SQLStatement stat, Query<T> query) {
        // untested
        left.appendSQL(stat, query);
        stat.appendSQL(" OR ");
        right.appendSQL(stat, query);
    }

}
