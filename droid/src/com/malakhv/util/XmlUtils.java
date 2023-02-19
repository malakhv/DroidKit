/* *
 * Copyright (C) 2022 Mikhail Malakhov <malakhv@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * */

package com.malakhv.util;

/**
 * @author <a href="https://github.com/malakhv">Mikhail.Malakhov</a>
 */
public final class XmlUtils {

    public final static String XML_DOC_HEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";

    public static final String XML_TAG_START = "<";
    public static final String XML_TAG_END = ">";
    public static final String XML_TAG_CLOSE = "/>";

    public static String makeNameValuePair(String name, String value) {
        //TODO Need to check input parameters
        return name + "=" + "\"" + value + "\"";
    }

}
