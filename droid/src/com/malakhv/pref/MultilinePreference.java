/* *
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
import android.content.res.TypedArray;
import android.os.Build;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devlear.droid.R;

/**
 * Class implements {@link Preference} without limiting the number of lines for preference's title
 * and summary.
 * @author <a href="https://github.com/malakhv">Mikhail.Malakhov</a>
 * */
@SuppressWarnings("unused")
public class MultilinePreference extends Preference {

    /** The gravity of preference's title and summary. */
    //TODO Right now, you can specify this parameter only in xml
    private int mGravity = Gravity.NO_GRAVITY;

    /**
     * Simple constructor to use when creating a preference from code. Just call super(), in this
     * implementation.
     * */
    public MultilinePreference(Context context) { super(context);}

    /**
     * Constructor that is called when inflating a preference from XML. Just call super(), in this
     * implementation.
     * */
    public MultilinePreference(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.preferenceStyle);
    }

    /**
     * Perform inflation from XML and apply a class-specific base style from a theme attribute.
     * Just call super(), in this implementation.
     * */
    public MultilinePreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFromAttr(context, attrs, defStyleAttr, 0);
    }

    /**
     * Perform inflation from XML and apply a class-specific base style from a theme attribute or
     * style resource. Just call super(), in this implementation.
     * */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MultilinePreference(Context context, AttributeSet attrs, int defStyleAttr, int
            defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initFromAttr(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * Initialize this {@code preference} object.
     * */
    protected void initFromAttr(Context context, AttributeSet attrs, int defStyleAttr, int
            defStyleRes) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Preference, defStyleAttr,
                defStyleRes);
        mGravity = a.getInt(R.styleable.Preference_android_gravity, mGravity);
        a.recycle();
    }

    /**
     * Binds the created View to the data for this Preference. In this implementation, disabled the
     * single line limitation for a preference's title and the number of lines limitation for a
     * preference's summary.
     * */
    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        setMultilineTitle(view);
        setMultilineSummary(view);
        if (mGravity != Gravity.NO_GRAVITY) {
            setTitleGravity(view, mGravity);
            setSummaryGravity(view, mGravity);
        }
    }

    /**
     * Disable the single line limitation for a preference's title.
     * */
    static void setMultilineTitle(View view) { // package access
        if (view == null) return;
        final TextView v = (TextView) view.findViewById(android.R.id.title);
        if (v != null) {
            v.setSingleLine(false);
        }
    }

    /**
     * Disable the number of lines limitation for a preference's summary.
     * */
    static void setMultilineSummary(View view) {
        if (view == null) return;
        final TextView v = (TextView) view.findViewById(android.R.id.summary);
        if (v != null) {
            v.setMaxLines(Integer.MAX_VALUE);
        }
    }

    /**
     * Sets the gravity for a preference's title.
     * */
    static void setTitleGravity(View view, int gravity) {
        final TextView v = (TextView) view.findViewById(android.R.id.title);
        if (v != null) {
            setTextViewGravity(v, gravity);
        }
    }

    /**
     * Sets the gravity for a preference's summary.
     * */
    static void setSummaryGravity(View view, int gravity) {
        final TextView v = (TextView) view.findViewById(android.R.id.summary);
        if (v != null) {
            setTextViewGravity(v, gravity);
        }
    }

    /**
     * Sets the gravity of the {@code TextView} into {@code Preference} layout.
     * */
    static void setTextViewGravity(TextView view, int gravity) {
        if (view == null || view.getGravity() == gravity) return;
        view.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        view.setGravity(gravity);
    }

}