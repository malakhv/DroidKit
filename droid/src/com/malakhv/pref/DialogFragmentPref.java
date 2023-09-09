/* *
 * Copyright (C) 1996-2013 Mikhail Malakhov <malakhv@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied.
 *
 * See the License for the specific language governing permissions
 * and limitations under the License.
 * */

package com.malakhv.pref;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.Preference;
import android.util.AttributeSet;

/**
 * Class implements {@link Preference} with {@link DialogFragment}.
 * @author <a href="https://github.com/malakhv">Mikhail.Malakhov</a>
 * */
//TODO Need to add more JavaDoc comments about preference key and dialog lifecycle
@SuppressWarnings("unused")
public abstract class DialogFragmentPref extends Preference {

    /** The link for quick access to the {@link FragmentManager}. */
    private FragmentManager mFragmentManager = null;

    /**
     * Simple constructor to use when creating a preference from code.
     * */
    public DialogFragmentPref(Context context) {
        this(context, null);
    }

    /**
     * Constructor that is called when inflating a preference from XML.
     * */
    public DialogFragmentPref(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.dialogPreferenceStyle);
    }

    /**
     * Perform inflation from XML and apply a class-specific base style from a theme attribute.
     * */
    public DialogFragmentPref(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr); init(context);
    }

    /**
     * Perform inflation from XML and apply a class-specific base style from a theme attribute or
     * style resource.
     * */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DialogFragmentPref(Context context, AttributeSet attrs, int defStyleAttr,
            int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes); init(context);
    }

    /**
     * Initialize this {@code preference} object.
     * */
    protected void init(Context context) {
        try {
            final Activity activity = (Activity) context;
            mFragmentManager = activity.getFragmentManager();
        } catch (ClassCastException e) {
            e.printStackTrace();
            mFragmentManager = null;
        }
    }

    /**
     * Processes a click on the preference.
     * */
    @Override
    protected void onClick() { showDialog(); }

    /**
     * Return the FragmentManager for interacting with fragments associated with this activity.
     * */
    protected FragmentManager getFragmentManager() { return mFragmentManager; }

    /**
     * Returns the string tag that use for {@link DialogFragment} into this {@link Preference}. By
     * default, the preference's key used.
     * @see #getKey()
     * */
    protected String getFragmentTag() { return getKey(); }

    /**
     * Check this preference has any string tag for its {@link DialogFragment}, or not.
     * */
    protected boolean hasFragmentTag() {
        final String tag = getFragmentTag();
        return tag != null && !tag.isEmpty();
    }

    /**
     * Returns the current {@link DialogFragment} for this {@link Preference}, if it exists.
     * */
    protected DialogFragment getDialogFragment() {
        final String tag = getFragmentTag();
        if (mFragmentManager == null || tag == null || tag.isEmpty()) return null;
        return (DialogFragment) mFragmentManager.findFragmentByTag(tag);
    }

    /**
     * Shows the {@link DialogFragment}.
     * */
    public void showDialog() {
        if (isDialogShowing() && !hasFragmentTag()) return;
        final InnerDialogFragment fragment = new InnerDialogFragment();
        fragment.setPreference(this);
        fragment.show(mFragmentManager, getFragmentTag());
    }

    /**
     * @return Whether the dialog is currently showing.
     * */
    public boolean isDialogShowing() {
        final DialogFragment fragment = getDialogFragment();
        return fragment != null && fragment.getDialog() != null && fragment.getDialog().isShowing();
    }

    /**
     * Override to build your own custom Dialog container.
     *
     * @param savedInstanceState The last saved instance state of the Fragment,
     * or null if this is a freshly created Fragment.
     *
     * @return Return a new Dialog instance to be displayed by the Fragment.
     */
    public abstract Dialog onCreateDialog(Bundle savedInstanceState);

    /**
     * Called when the dialog is canceled.
     * <p>This will only be invoked when the dialog is canceled. Cancel events alone will not
     * capture all ways that the dialog might be dismissed. If the creator needs to know when a
     * dialog is dismissed in general, use {@link #onDismissDialog}.</p>
     * */
    protected void onCancelDialog(DialogInterface dialog) {}

    /**
     * Called when dialog was dismissed.
     * */
    protected void onDismissDialog(DialogInterface dialog) {}

    /**
     * Hook allowing a Preference to re-apply a representation of its internal state that had
     * previously been generated by onSaveInstanceState().
     * */
    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
        if (state != null) {
            InnerDialogFragment fragment = (InnerDialogFragment) getDialogFragment();
            if (fragment != null) fragment.setPreference(this);
        }
    }

    /**
     * Dialog fragment that use for show a dialog.
     * */
    public static class InnerDialogFragment extends DialogFragment {

        /** The own preference object for this {@link InnerDialogFragment}. */
        private DialogFragmentPref ownPreference = null;

        /**
         * Set the new own preference object for this {@link InnerDialogFragment}.
         * */
        public void setPreference(DialogFragmentPref preference) {
            ownPreference = preference;
        }

        /**
         * Builds dialog for own preference.
         * */
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            if (ownPreference != null) {
                return ownPreference.onCreateDialog(savedInstanceState);
            } else {
                this.dismiss();
                return super.onCreateDialog(savedInstanceState);
            }
        }

        /**
         * Called when the dialog is canceled.
         * */
        @Override
        public void onCancel(DialogInterface dialog) {
            super.onCancel(dialog);
            if (ownPreference != null) ownPreference.onCancelDialog(dialog);
        }

        /**
         * Called when dialog was dismissed.
         * */
        @Override
        public void onDismiss(DialogInterface dialog) {
            super.onDismiss(dialog);
            if (ownPreference != null) ownPreference.onDismissDialog(dialog);
        }
    }
}
