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
 * A method call.
 */
class Function implements Token {

    private final String name;
    private final Token expr;

    Function(String name, Token expr) {
        this.name = name;
        this.expr = expr;
    }

    public String toString() {
        return name + "(" + expr + ")";
    }

    public <T> void appendSQL(SQLStatement stat, Query<T> query) {
        // untested
        stat.appendSQL(name + "(");
        expr.appendSQL(stat, query);
        stat.appendSQL(")");
    }
}
