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
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Build;
import android.preference.DialogPreference;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.devlear.droid.R;

/**
 * A {@link Preference} that displays a {@link SeekBar} as a dialog.
 * @author Mikhail.Malakhov [malakhv@live.ru|https://github.com/malakhv]
 */
@SuppressWarnings("unused")
public class SeekBarDialog extends DialogPreference {

    /** The default value of progress for {@link SeekBar} in the dialog. */
    public static final int DEFAULT_VALUE = 0;

    /** The default maximum value of progress for {@link SeekBar} in the dialog. */
    public static final int DEFAULT_MAX_VALUE = 100;

    /** The default dialog message formatted text. */
    public static final String DEFAULT_DIALOG_MESSAGE = "%s %%";

    /** The maximum value of progress for {@link SeekBar} in the dialog. */
    private int mMax = DEFAULT_MAX_VALUE;

    /** The current progress value of {@link SeekBar} in the dialog. */
    private int mValue;

    /** The summary of this Preference. */
    private String mSummary = null;

    /** The dialog message of this Preference.  */
    private String mDialogMessage = null;

    /** The {@link SeekBar} shown in the dialog. */
    private SeekBar mSeekBar = null;

    /** The {@link TextView} shown in the dialog as a message. */
    private TextView mDialogMessageView = null;

    /** The internal listener for {@link SeekBar} shown in the dialog. */
    private OnSeekBarChangeListener mOnSeekBarChangeListener = new OnSeekBarChangeListener();

    /**
     * Simple constructor to use when creating a preference from code.
     * */
    public SeekBarDialog(Context context) { this(context, null); }

    /**
     * Constructor that is called when inflating a preference from XML.
     * */
    public SeekBarDialog(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.dialogPreferenceStyle);
    }

    /**
     * Perform inflation from XML and apply a class-specific base style from a theme attribute.
     * */
    public SeekBarDialog(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    /**
     * Perform inflation from XML and apply a class-specific base style from a theme attribute or
     * style resource.
     * */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SeekBarDialog(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        // Retrieve the SeekBar attributes
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SeekBarDialog,
                defStyleAttr, defStyleRes);
        mMax = a.getInt(R.styleable.SeekBarDialog_android_max, DEFAULT_MAX_VALUE);
        mValue = a.getInt(R.styleable.SeekBarDialog_android_progress, DEFAULT_VALUE);
        mDialogMessage = a.getString(R.styleable.SeekBarDialog_android_dialogMessage);
        if (mDialogMessage == null) mDialogMessage = DEFAULT_DIALOG_MESSAGE;

        // Retrieve the Preference summary attribute since it's private in the Preference class
        mSummary = a.getString(R.styleable.SeekBarDialog_android_summary);
        a.recycle();

        // Set custom layout with SeekBar for the dialog
        this.setDialogLayoutResource(R.layout.pref_seek_bar_dialog);
    }

    /**
     * Gets the value from  this {@link Preference}.
     * */
    public int getValue() { return mValue; }

    /**
     * Binds views in the content View of the dialog to data. <p>Make sure to call through to the
     * superclass implementation.</p>
     * @param view The content View of the dialog, if it is custom.
     * */
    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);

        // Configure SeekBar
        mSeekBar = (SeekBar) view.findViewById(R.id.seek_bar);
        mSeekBar.setProgress(mValue);
        mSeekBar.setMax(mMax);
        mSeekBar.setOnSeekBarChangeListener(mOnSeekBarChangeListener);

        // Configure dialog message view
        mDialogMessageView = (TextView) view.findViewById(android.R.id.message);
    }

    /**
     * Called when the dialog is dismissed and should be used to save data to
     * the {@link SharedPreferences}.
     * @param positiveResult Whether the positive button was clicked (true), or the negative button
     *      was clicked or the dialog was canceled (false).
     * */
    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        if (positiveResult) { // Need to persist value
            final int value = mSeekBar.getProgress();
            if (callChangeListener(value)) setValue(value);
        }
        mSeekBar.setOnSeekBarChangeListener(null);
    }

    /**
     * Set the current progress to the specified value.
     * */
    public void setValue(int value) {
        final boolean wasBlocking = shouldDisableDependents();

        // Persist value
        if (value < 0) value = 0;
        if (value > mMax) value = mMax;
        boolean changed = mValue != value;
        mValue = value;
        persistInt(mValue);

        // Notify about value was changed
        if (changed) notifyChanged();

        // Resolve dependencies
        final boolean isBlocking = shouldDisableDependents();
        if (isBlocking != wasBlocking) notifyDependencyChange(isBlocking);
    }

    /**
     * Returns the summary of this {@link SeekBarDialog}. If the summary has a
     * {@link String#format String formatting} marker in it (i.e. "%s" or "%1$s"), then
     * the current value will be substituted in its place.
     * */
    @Override
    public CharSequence getSummary() {
        return mSummary != null ? String.format(mSummary, mValue) : super.getSummary();
    }

    /**
     * Sets the summary for this {@link SeekBarDialog} with a CharSequence. If the summary has a
     * {@link String#format String formatting} marker in it (i.e. "%s" or "%1$s"), then
     * the current value will be substituted in its place when it's retrieved.
     */
    @Override
    public void setSummary(CharSequence summary) {
        super.setSummary(summary);
        mSummary = (summary != null ? summary.toString() : null);
    }

    /**
     * Returns the message to be shown on subsequent dialogs. If the message has a
     * {@link String#format String formatting} marker in it (i.e. "%s" or "%1$s"), then
     * the current value will be substituted in its place.
     * */
    @Override
    public CharSequence getDialogMessage() { return getDialogMessage(mValue); }

    /**
     * Returns the message to be shown on subsequent dialogs for specified value. If the message
     * has a {@link String#format String formatting} marker in it (i.e. "%s" or "%1$s"),
     * then the current value will be substituted in its place.
     * */
    public CharSequence getDialogMessage(int value) {
        return mDialogMessage != null ? String.format(mDialogMessage, value)
                : super.getDialogMessage();
    }

    /**
     * Called when a Preference is being inflated and the default value attribute needs to be read.
     * */
    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getInt(index, DEFAULT_VALUE);
    }

    /**
     * Implement this to set the initial value of the Preference. For more details, please, see
     * method from super class.
     * */
    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        setValue(restoreValue ? getPersistedInt(mValue) : (int) defaultValue);
    }

    /**
     * A callback that notifies clients when the progress level has been changed. For more details,
     * please, see {@link OnSeekBarChangeListener}.
     *
     * */
    private class OnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        /**
         * Notification that the progress level has changed.
         * */
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (mDialogMessageView != null) mDialogMessageView.setText(getDialogMessage(progress));
        }

        /** Notification that the user has started a touch gesture. */
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) { /* do nothing */}

        /**
         * Notification that the user has finished a touch gesture.
         */
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) { /* do nothing */ }
    }

}