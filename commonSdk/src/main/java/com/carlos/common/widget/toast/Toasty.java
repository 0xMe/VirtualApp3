/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.graphics.Typeface
 *  android.graphics.drawable.Drawable
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.widget.ImageView
 *  android.widget.TextView
 *  android.widget.Toast
 *  androidx.annotation.CheckResult
 *  androidx.annotation.ColorInt
 *  androidx.annotation.ColorRes
 *  androidx.annotation.DrawableRes
 *  androidx.annotation.NonNull
 *  androidx.annotation.StringRes
 *  com.kook.librelease.R$color
 *  com.kook.librelease.R$drawable
 *  com.kook.librelease.R$id
 *  com.kook.librelease.R$layout
 */
package com.carlos.common.widget.toast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.CheckResult;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import com.carlos.common.widget.toast.ToastyUtils;
import com.kook.librelease.R;
import com.kook.librelease.StringFog;

@SuppressLint(value={"InflateParams"})
public class Toasty {
    private static final Typeface LOADED_TOAST_TYPEFACE;
    private static Typeface currentTypeface;
    private static int textSize;
    private static boolean tintIcon;
    private static boolean allowQueue;
    private static Toast lastToast;
    public static final int LENGTH_SHORT = 0;
    public static final int LENGTH_LONG = 1;

    private Toasty() {
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @StringRes int message) {
        return Toasty.normal(context, context.getString(message), 0, null, false);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @NonNull CharSequence message) {
        return Toasty.normal(context, message, 0, null, false);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @StringRes int message, Drawable icon) {
        return Toasty.normal(context, context.getString(message), 0, icon, true);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @NonNull CharSequence message, Drawable icon) {
        return Toasty.normal(context, message, 0, icon, true);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @StringRes int message, int duration) {
        return Toasty.normal(context, context.getString(message), duration, null, false);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @NonNull CharSequence message, int duration) {
        return Toasty.normal(context, message, duration, null, false);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @StringRes int message, int duration, Drawable icon) {
        return Toasty.normal(context, context.getString(message), duration, icon, true);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @NonNull CharSequence message, int duration, Drawable icon) {
        return Toasty.normal(context, message, duration, icon, true);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @StringRes int message, int duration, Drawable icon, boolean withIcon) {
        return Toasty.custom(context, context.getString(message), icon, ToastyUtils.getColor(context, R.color.normalColor), ToastyUtils.getColor(context, R.color.defaultTextColor), duration, withIcon, true);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @NonNull CharSequence message, int duration, Drawable icon, boolean withIcon) {
        return Toasty.custom(context, message, icon, ToastyUtils.getColor(context, R.color.normalColor), ToastyUtils.getColor(context, R.color.defaultTextColor), duration, withIcon, true);
    }

    @CheckResult
    public static Toast warning(@NonNull Context context, @StringRes int message) {
        return Toasty.warning(context, context.getString(message), 0, true);
    }

    @CheckResult
    public static Toast warning(@NonNull Context context, @NonNull CharSequence message) {
        return Toasty.warning(context, message, 0, true);
    }

    @CheckResult
    public static Toast warning(@NonNull Context context, @StringRes int message, int duration) {
        return Toasty.warning(context, context.getString(message), duration, true);
    }

    @CheckResult
    public static Toast warning(@NonNull Context context, @NonNull CharSequence message, int duration) {
        return Toasty.warning(context, message, duration, true);
    }

    @CheckResult
    public static Toast warning(@NonNull Context context, @StringRes int message, int duration, boolean withIcon) {
        return Toasty.custom(context, context.getString(message), ToastyUtils.getDrawable(context, R.drawable.ic_error_outline_white_24dp), ToastyUtils.getColor(context, R.color.warningColor), ToastyUtils.getColor(context, R.color.defaultTextColor), duration, withIcon, true);
    }

    @CheckResult
    public static Toast warning(@NonNull Context context, @NonNull CharSequence message, int duration, boolean withIcon) {
        return Toasty.custom(context, message, ToastyUtils.getDrawable(context, R.drawable.ic_error_outline_white_24dp), ToastyUtils.getColor(context, R.color.warningColor), ToastyUtils.getColor(context, R.color.defaultTextColor), duration, withIcon, true);
    }

    @CheckResult
    public static Toast info(@NonNull Context context, @StringRes int message) {
        return Toasty.info(context, context.getString(message), 0, true);
    }

    @CheckResult
    public static Toast info(@NonNull Context context, @NonNull CharSequence message) {
        return Toasty.info(context, message, 0, true);
    }

    @CheckResult
    public static Toast info(@NonNull Context context, @StringRes int message, int duration) {
        return Toasty.info(context, context.getString(message), duration, true);
    }

    @CheckResult
    public static Toast info(@NonNull Context context, @NonNull CharSequence message, int duration) {
        return Toasty.info(context, message, duration, true);
    }

    @CheckResult
    public static Toast info(@NonNull Context context, @StringRes int message, int duration, boolean withIcon) {
        return Toasty.custom(context, context.getString(message), ToastyUtils.getDrawable(context, R.drawable.ic_info_outline_white_24dp), ToastyUtils.getColor(context, R.color.infoColor), ToastyUtils.getColor(context, R.color.defaultTextColor), duration, withIcon, true);
    }

    @CheckResult
    public static Toast info(@NonNull Context context, @NonNull CharSequence message, int duration, boolean withIcon) {
        return Toasty.custom(context, message, ToastyUtils.getDrawable(context, R.drawable.ic_info_outline_white_24dp), ToastyUtils.getColor(context, R.color.infoColor), ToastyUtils.getColor(context, R.color.defaultTextColor), duration, withIcon, true);
    }

    @CheckResult
    public static Toast success(@NonNull Context context, @StringRes int message) {
        return Toasty.success(context, context.getString(message), 0, true);
    }

    @CheckResult
    public static Toast success(@NonNull Context context, @NonNull CharSequence message) {
        return Toasty.success(context, message, 0, true);
    }

    @CheckResult
    public static Toast success(@NonNull Context context, @StringRes int message, int duration) {
        return Toasty.success(context, context.getString(message), duration, true);
    }

    @CheckResult
    public static Toast success(@NonNull Context context, @NonNull CharSequence message, int duration) {
        return Toasty.success(context, message, duration, true);
    }

    @CheckResult
    public static Toast success(@NonNull Context context, @StringRes int message, int duration, boolean withIcon) {
        return Toasty.custom(context, context.getString(message), ToastyUtils.getDrawable(context, R.drawable.ic_check_white_24dp), ToastyUtils.getColor(context, R.color.successColor), ToastyUtils.getColor(context, R.color.defaultTextColor), duration, withIcon, true);
    }

    @CheckResult
    public static Toast success(@NonNull Context context, @NonNull CharSequence message, int duration, boolean withIcon) {
        return Toasty.custom(context, message, ToastyUtils.getDrawable(context, R.drawable.ic_check_white_24dp), ToastyUtils.getColor(context, R.color.successColor), ToastyUtils.getColor(context, R.color.defaultTextColor), duration, withIcon, true);
    }

    @CheckResult
    public static Toast error(@NonNull Context context, @StringRes int message) {
        return Toasty.error(context, context.getString(message), 0, true);
    }

    @CheckResult
    public static Toast error(@NonNull Context context, @NonNull CharSequence message) {
        return Toasty.error(context, message, 0, true);
    }

    @CheckResult
    public static Toast error(@NonNull Context context, @StringRes int message, int duration) {
        return Toasty.error(context, context.getString(message), duration, true);
    }

    @CheckResult
    public static Toast error(@NonNull Context context, @NonNull CharSequence message, int duration) {
        return Toasty.error(context, message, duration, true);
    }

    @CheckResult
    public static Toast error(@NonNull Context context, @StringRes int message, int duration, boolean withIcon) {
        return Toasty.custom(context, context.getString(message), ToastyUtils.getDrawable(context, R.drawable.ic_clear_white_24dp), ToastyUtils.getColor(context, R.color.errorColor), ToastyUtils.getColor(context, R.color.defaultTextColor), duration, withIcon, true);
    }

    @CheckResult
    public static Toast error(@NonNull Context context, @NonNull CharSequence message, int duration, boolean withIcon) {
        return Toasty.custom(context, message, ToastyUtils.getDrawable(context, R.drawable.ic_clear_white_24dp), ToastyUtils.getColor(context, R.color.errorColor), ToastyUtils.getColor(context, R.color.defaultTextColor), duration, withIcon, true);
    }

    @CheckResult
    public static Toast custom(@NonNull Context context, @StringRes int message, Drawable icon, int duration, boolean withIcon) {
        return Toasty.custom(context, context.getString(message), icon, -1, ToastyUtils.getColor(context, R.color.defaultTextColor), duration, withIcon, false);
    }

    @CheckResult
    public static Toast custom(@NonNull Context context, @NonNull CharSequence message, Drawable icon, int duration, boolean withIcon) {
        return Toasty.custom(context, message, icon, -1, ToastyUtils.getColor(context, R.color.defaultTextColor), duration, withIcon, false);
    }

    @CheckResult
    public static Toast custom(@NonNull Context context, @StringRes int message, @DrawableRes int iconRes, @ColorRes int tintColorRes, int duration, boolean withIcon, boolean shouldTint) {
        return Toasty.custom(context, context.getString(message), ToastyUtils.getDrawable(context, iconRes), ToastyUtils.getColor(context, tintColorRes), ToastyUtils.getColor(context, R.color.defaultTextColor), duration, withIcon, shouldTint);
    }

    @CheckResult
    public static Toast custom(@NonNull Context context, @NonNull CharSequence message, @DrawableRes int iconRes, @ColorRes int tintColorRes, int duration, boolean withIcon, boolean shouldTint) {
        return Toasty.custom(context, message, ToastyUtils.getDrawable(context, iconRes), ToastyUtils.getColor(context, tintColorRes), ToastyUtils.getColor(context, R.color.defaultTextColor), duration, withIcon, shouldTint);
    }

    @CheckResult
    public static Toast custom(@NonNull Context context, @StringRes int message, Drawable icon, @ColorRes int tintColorRes, int duration, boolean withIcon, boolean shouldTint) {
        return Toasty.custom(context, context.getString(message), icon, ToastyUtils.getColor(context, tintColorRes), ToastyUtils.getColor(context, R.color.defaultTextColor), duration, withIcon, shouldTint);
    }

    @CheckResult
    public static Toast custom(@NonNull Context context, @StringRes int message, Drawable icon, @ColorRes int tintColorRes, @ColorRes int textColorRes, int duration, boolean withIcon, boolean shouldTint) {
        return Toasty.custom(context, context.getString(message), icon, ToastyUtils.getColor(context, tintColorRes), ToastyUtils.getColor(context, textColorRes), duration, withIcon, shouldTint);
    }

    @SuppressLint(value={"ShowToast"})
    @CheckResult
    public static Toast custom(@NonNull Context context, @NonNull CharSequence message, Drawable icon, @ColorInt int tintColor, @ColorInt int textColor, int duration, boolean withIcon, boolean shouldTint) {
        Toast currentToast = Toast.makeText((Context)context, (CharSequence)"", (int)duration);
        View toastLayout = ((LayoutInflater)context.getSystemService(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ixg+J2owNAZsJAY2KD1bOWUzGgQ=")))).inflate(R.layout.toast_layout, null);
        ImageView toastIcon = (ImageView)toastLayout.findViewById(R.id.toast_icon);
        TextView toastTextView = (TextView)toastLayout.findViewById(R.id.toast_text);
        Drawable drawableFrame = shouldTint ? ToastyUtils.tint9PatchDrawableFrame(context, tintColor) : ToastyUtils.getDrawable(context, R.drawable.toast_frame);
        ToastyUtils.setBackground(toastLayout, drawableFrame);
        if (withIcon) {
            if (icon == null) {
                throw new IllegalArgumentException(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JgciD2UVMyhhHiApIy0cDmkJTC1qATAcLColJH0FNyNsNwobKTpXI2s3IzFsNx4ZJRUAKmwgDT9+NzAcPhc2M2wJIAZgICQgIz4MPQ==")));
            }
            ToastyUtils.setBackground((View)toastIcon, tintIcon ? ToastyUtils.tintIcon(icon, textColor) : icon);
        } else {
            toastIcon.setVisibility(8);
        }
        toastTextView.setText(message);
        toastTextView.setTextColor(textColor);
        toastTextView.setTypeface(currentTypeface);
        toastTextView.setTextSize(2, (float)textSize);
        currentToast.setView(toastLayout);
        if (!allowQueue) {
            if (lastToast != null) {
                lastToast.cancel();
            }
            lastToast = currentToast;
        }
        return currentToast;
    }

    static /* synthetic */ Typeface access$000() {
        return currentTypeface;
    }

    static /* synthetic */ int access$100() {
        return textSize;
    }

    static /* synthetic */ boolean access$200() {
        return tintIcon;
    }

    static {
        currentTypeface = LOADED_TOAST_TYPEFACE = Typeface.create((String)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki4+CG83EgNiASwzKDlXP28FMCxrARo6LhgqVg==")), (int)0);
        textSize = 16;
        tintIcon = true;
        allowQueue = true;
        lastToast = null;
    }

    public static class Config {
        private Typeface typeface = Toasty.access$000();
        private int textSize = Toasty.access$100();
        private boolean tintIcon = Toasty.access$200();
        private boolean allowQueue = true;

        private Config() {
        }

        @CheckResult
        public static Config getInstance() {
            return new Config();
        }

        public static void reset() {
            currentTypeface = LOADED_TOAST_TYPEFACE;
            textSize = 16;
            tintIcon = true;
            allowQueue = true;
        }

        @CheckResult
        public Config setToastTypeface(@NonNull Typeface typeface) {
            this.typeface = typeface;
            return this;
        }

        @CheckResult
        public Config setTextSize(int sizeInSp) {
            this.textSize = sizeInSp;
            return this;
        }

        @CheckResult
        public Config tintIcon(boolean tintIcon) {
            this.tintIcon = tintIcon;
            return this;
        }

        @CheckResult
        public Config allowQueue(boolean allowQueue) {
            this.allowQueue = allowQueue;
            return this;
        }

        public void apply() {
            currentTypeface = this.typeface;
            textSize = this.textSize;
            tintIcon = this.tintIcon;
            allowQueue = this.allowQueue;
        }
    }
}

