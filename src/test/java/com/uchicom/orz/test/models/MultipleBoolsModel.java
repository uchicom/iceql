/*
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

import com.uchicom.orz.Iciql.IQColumn;
import com.uchicom.orz.Iciql.IQTable;

import java.util.Arrays;
import java.util.List;

/**
 * Model class to test the runtime exception of too many primitive boolean
 * fields in the model.
 *
 * @author James Moger
 */
@IQTable
public class MultipleBoolsModel {

    @IQColumn(autoIncrement = true, primaryKey = true)
    public int id;

    @IQColumn
    public boolean a;

    @IQColumn
    public boolean b;

    public MultipleBoolsModel() {
    }

    public MultipleBoolsModel(boolean a, boolean b) {
        this.a = a;
        this.b = b;
    }

    public static List<MultipleBoolsModel> getList() {
        return Arrays.asList(new MultipleBoolsModel(true, true), new MultipleBoolsModel(true, false),
                new MultipleBoolsModel(true, false), new MultipleBoolsModel(false, false));
    }
}
