/*
 * Copyright 2014 James Moger.
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

package com.uchicom.orz.adapter.postgresql;

import com.uchicom.orz.adapter.XStreamTypeAdapter;

/**
 * Postgres XML data type adapter maps an XML column to a domain object using
 * XStream.
 *
 * @author James Moger
 */
public abstract class XmlObjectAdapter extends XStreamTypeAdapter {

    @Override
    public String getDataType() {
        return "xml";
    }

}
