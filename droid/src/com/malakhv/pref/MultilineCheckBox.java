/**
 * Copyright (C) 2013 Mikhail Malakhov <malakhv@live.ru>
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

package com.malakhv.pref;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Class implements {@link CheckBoxPreference} without limiting the number of lines for
 * preference's title and summary.
 *
 * @author Mikhail.Malakhov [malakhv@live.ru|https://github.com/malakhv]
 * */
@SuppressWarnings("unused")
public class MultilineCheckBox extends CheckBoxPreference {

    /**
     * Simple constructor to use when creating a preference from code. Just call super(), in this
     * implementation.
     * */
    public MultilineCheckBox(Context context) { super(context);}

    /**
     * Constructor that is called when inflating a preference from XML. Just call super(), in this
     * implementation.
     * */
    public MultilineCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Perform inflation from XML and apply a class-specific base style from a theme attribute.
     * Just call super(), in this implementation.
     * */
    public MultilineCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Perform inflation from XML and apply a class-specific base style from a theme attribute or
     * style resource. Just call super(), in this implementation.
     * */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MultilineCheckBox(Context context, AttributeSet attrs, int defStyleAttr, int
            defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * Binds the created View to the data for this Preference. In this implementation, disabled the
     * single line limitation for a preference's title and the number of lines limitation for a
     * preference's summary.
     * */
    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        MultilinePreference.setMultilineTitle(view);
        MultilinePreference.setMultilineSummary(view);
    }
}