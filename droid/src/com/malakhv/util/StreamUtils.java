/* *
 * Copyright (C) 2013 Mikhail Malakhov <malakhv@gmail.com>
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Class contains some methods for working with {@link InputStream}.
 * @author Mikhail.Malakhov
 * */
public final class StreamUtils {

    /**
     * Convert {@link InputStream} to {@link String}.
     * */
    public static String toStringOrThrow(InputStream is, boolean doClose) throws IOException {
        if (is == null) return null;
        if (is.available() <= 0 ) return "";
        StringBuilder sBuilder = new StringBuilder();
        BufferedReader bReader = new BufferedReader(new InputStreamReader(is));
        try {
            String str;
            while ((str = bReader.readLine()) != null) {
                sBuilder.append(str).append(StrUtils.CHAR_NEW_LINE);
            }
        } finally {
            bReader.close();
            if (doClose) is.close();
        }
        return sBuilder.toString();
    }

    /**
     * Convert {@link InputStream} to {@link String}.
     * */
    public static String toString(InputStream is, boolean doClose) {
        try {
            return toStringOrThrow(is, doClose);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * This class has only static data, not need to create instance.
     * */
    private StreamUtils() { /* Empty */ }

}