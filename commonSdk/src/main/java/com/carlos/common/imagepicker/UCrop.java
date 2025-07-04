/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.Activity
 *  android.app.Fragment
 *  android.content.Context
 *  android.content.Intent
 *  android.graphics.Bitmap$CompressFormat
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.util.Log
 *  androidx.annotation.ColorInt
 *  androidx.annotation.DrawableRes
 *  androidx.annotation.FloatRange
 *  androidx.annotation.IntRange
 *  androidx.annotation.NonNull
 *  androidx.annotation.Nullable
 */
package com.carlos.common.imagepicker;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.carlos.common.imagepicker.UCropActivity;
import com.carlos.common.imagepicker.UCropFragment;
import com.carlos.common.imagepicker.entity.AspectRatio;
import com.kook.librelease.StringFog;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class UCrop {
    public static final int REQUEST_CROP = 69;
    public static final int RESULT_ERROR = 96;
    public static final int MIN_SIZE = 10;
    private static final String EXTRA_PREFIX = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQhSVg=="));
    public static final String EXTRA_INPUT_URI = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcBWAzODBqHwo7Ki5SVg=="));
    public static final String EXTRA_OUTPUT_URI = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcA2YFFjNqATAULBg2Vg=="));
    public static final String EXTRA_OUTPUT_CROP_ASPECT_RATIO = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcH2EwAjNhASw5Ly4YCmQzJCBlEQZF"));
    public static final String EXTRA_OUTPUT_IMAGE_WIDTH = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcBWAKPCJuDzwiLz0cLA=="));
    public static final String EXTRA_OUTPUT_IMAGE_HEIGHT = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcBWAKPCJuDAYuKi4ILG8VSFo="));
    public static final String EXTRA_OUTPUT_OFFSET_X = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcA2IwIDZuATAX"));
    public static final String EXTRA_OUTPUT_OFFSET_Y = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcA2IwIDZuATAI"));
    public static final String EXTRA_ERROR = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcGWEzMCplN1RF"));
    public static final String EXTRA_ASPECT_RATIO_X = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcHWEjOCBpJzARKC0cI2UmRVo="));
    public static final String EXTRA_ASPECT_RATIO_Y = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcHWEjOCBpJzARKC0cI2UmAlo="));
    public static final String EXTRA_MAX_SIZE_X = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcAX0FBhBvAQ4uJj5SVg=="));
    public static final String EXTRA_MAX_SIZE_Y = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcAX0FBhBvAQ4uJi5SVg=="));
    private Intent mCropIntent;
    private Bundle mCropOptionsBundle;

    public static UCrop of(Intent intent, @NonNull Uri source, @NonNull Uri destination) {
        return new UCrop(intent, source, destination);
    }

    private UCrop(Intent intent, @NonNull Uri source, @NonNull Uri destination) {
        if (intent == null) {
            this.mCropIntent = new Intent();
        } else {
            this.mCropIntent = intent;
            String packageName = intent.getStringExtra(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Khg+OWUzJC1iDFk7KgcMVg==")));
            int userId = intent.getIntExtra(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Lgc6KGwaLCthNAYw")), -1);
            Log.d((String)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JBUhDQ==")), (String)(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IQY2KmowIBF9JwozLD0cLmgnTChsETgqIz4+IWIIRSRsDg0z")) + packageName + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Pl85OHsKNANiASwJKF8IVg==")) + userId));
        }
        this.mCropOptionsBundle = new Bundle();
        this.mCropOptionsBundle.putParcelable(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcBWAzODBqHwo7Ki5SVg==")), (Parcelable)source);
        this.mCropOptionsBundle.putParcelable(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcA2YFFjNqATAULBg2Vg==")), (Parcelable)destination);
        this.mCropOptionsBundle.putParcelable(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcBWAzODBqHwo7Ki5SVg==")), (Parcelable)source);
    }

    public UCrop withAspectRatio(float x, float y) {
        this.mCropOptionsBundle.putFloat(EXTRA_ASPECT_RATIO_X, x);
        this.mCropOptionsBundle.putFloat(EXTRA_ASPECT_RATIO_Y, y);
        return this;
    }

    public UCrop useSourceImageAspectRatio() {
        this.mCropOptionsBundle.putFloat(EXTRA_ASPECT_RATIO_X, 0.0f);
        this.mCropOptionsBundle.putFloat(EXTRA_ASPECT_RATIO_Y, 0.0f);
        return this;
    }

    public UCrop withMaxResultSize(@IntRange(from=10L) int width, @IntRange(from=10L) int height) {
        if (width < 10) {
            width = 10;
        }
        if (height < 10) {
            height = 10;
        }
        this.mCropOptionsBundle.putInt(EXTRA_MAX_SIZE_X, width);
        this.mCropOptionsBundle.putInt(EXTRA_MAX_SIZE_Y, height);
        return this;
    }

    public UCrop withOptions(@NonNull Options options) {
        this.mCropOptionsBundle.putAll(options.getOptionBundle());
        return this;
    }

    public void start(@NonNull Activity activity) {
        this.start(activity, 69);
    }

    public void start(@NonNull Activity activity, int requestCode) {
        activity.startActivityForResult(this.getIntent((Context)activity), requestCode);
    }

    public void start(@NonNull Context context, @NonNull Fragment fragment) {
        this.start(context, fragment, 69);
    }

    @TargetApi(value=11)
    public void start(@NonNull Context context, @NonNull Fragment fragment, int requestCode) {
        fragment.startActivityForResult(this.getIntent(context), requestCode);
    }

    public Intent getIntent(@NonNull Context context) {
        this.mCropIntent.setClass(context, UCropActivity.class);
        this.mCropIntent.putExtras(this.mCropOptionsBundle);
        return this.mCropIntent;
    }

    public UCropFragment getFragment() {
        return UCropFragment.newInstance(this.mCropOptionsBundle);
    }

    public UCropFragment getFragment(Bundle bundle) {
        this.mCropOptionsBundle = bundle;
        return this.getFragment();
    }

    @Nullable
    public static Uri getOutput(@NonNull Intent intent) {
        return (Uri)intent.getParcelableExtra(EXTRA_OUTPUT_URI);
    }

    public static int getOutputImageWidth(@NonNull Intent intent) {
        return intent.getIntExtra(EXTRA_OUTPUT_IMAGE_WIDTH, -1);
    }

    public static int getOutputImageHeight(@NonNull Intent intent) {
        return intent.getIntExtra(EXTRA_OUTPUT_IMAGE_HEIGHT, -1);
    }

    public static float getOutputCropAspectRatio(@NonNull Intent intent) {
        return (Float) intent.getFloatExtra(EXTRA_OUTPUT_CROP_ASPECT_RATIO, 0);
    }

    @Nullable
    public static Throwable getError(@NonNull Intent result) {
        return (Throwable)result.getSerializableExtra(EXTRA_ERROR);
    }

    public static class Options {
        public static final String EXTRA_COMPRESSION_FORMAT_NAME = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcH2AgQTNlNAo8LAg2KWUxOANvJw4oIBVfKGwKFlo="));
        public static final String EXTRA_COMPRESSION_QUALITY = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcH2AgQTNlNAo8LAg2KWU2JC9rEQIwIBgAVg=="));
        public static final String EXTRA_ALLOWED_GESTURES = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcHWAaTSpqJAotJwgAD28aNCZoHjBF"));
        public static final String EXTRA_MAX_BITMAP_SIZE = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcAX0FBg9vATAcKC1XUmoKTT8="));
        public static final String EXTRA_MAX_SCALE_MULTIPLIER = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcAX0FBhBpJCQbLywmCWUaMAVvAQIwJAgqVg=="));
        public static final String EXTRA_IMAGE_TO_CROP_BOUNDS_ANIM_DURATION = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcBWAKPCJuDzAeIAdfKWwbFgNsERozJysmJW8KTVdlJFkoKRgYD2ojSFo="));
        public static final String EXTRA_DIMMED_LAYER_COLOR = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcGmMKQShuDjBAKC02J2wxLANqAQYb"));
        public static final String EXTRA_CIRCLE_DIMMED_LAYER = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcH2MFMCZsHgpIKi4mL2sFMExrHh40Jz5SVg=="));
        public static final String EXTRA_SHOW_CROP_FRAME = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcUmMaAjJhJygeLDwECGgFEj8="));
        public static final String EXTRA_CROP_FRAME_COLOR = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcH2EwAjNmNygqKS4AH2UjHgNvJ1RF"));
        public static final String EXTRA_CROP_FRAME_STROKE_WIDTH = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcH2EwAjNmNygqKS4AUm8aFgNlNyhNJQcMM28VSFo="));
        public static final String EXTRA_SHOW_CROP_GRID = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcUmMaAjJhJygeLDwICGoFMFo="));
        public static final String EXTRA_CROP_GRID_ROW_COUNT = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcH2EwAjNmJygiLztfKW8hLANsERoZ"));
        public static final String EXTRA_CROP_GRID_COLUMN_COUNT = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcH2EwAjNmJygiLzwYKWUaNAFqJTA6IAdfMw=="));
        public static final String EXTRA_CROP_GRID_COLOR = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcH2EwAjNmJygiLzwYKWUVGiY="));
        public static final String EXTRA_CROP_GRID_STROKE_WIDTH = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcH2EwAjNmJygiLzsYCmwzGj1oHCAwJBgMJw=="));
        public static final String EXTRA_TOOL_BAR_COLOR = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcXWAgAjdpNCQ7IAguKGUgFlo="));
        public static final String EXTRA_STATUS_BAR_COLOR = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcUmYaPD9qASwBKC1fH2UjHgNvJ1RF"));
        public static final String EXTRA_UCROP_COLOR_WIDGET_ACTIVE = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcXH0jMCplHCweKT4uCGcjAjBoNygZIwcuM28FJDE="));
        public static final String EXTRA_UCROP_WIDGET_COLOR_TOOLBAR = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcXH0jMCplHzAeKQgiJmgKFgtlESwyJAgMDGwgQQFqEVRF"));
        public static final String EXTRA_UCROP_TITLE_TEXT_TOOLBAR = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcXH0jMCplHzAeKQgiJmgKFgplHiw7JAYMLGsVLFo="));
        public static final String EXTRA_UCROP_WIDGET_CANCEL_DRAWABLE = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcXH0jMCplHzAeKQgiJmgKFh9rERo2JAdXBWUwOC9uJ1k7LQhSVg=="));
        public static final String EXTRA_UCROP_WIDGET_CROP_DRAWABLE = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcXH0jMCplHzAeKQgiJmgKFh9vJwYdLBgqKGogODRvNwZF"));
        public static final String EXTRA_UCROP_LOGO_COLOR = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcXH0jMCplHF0eLwguH2UjHgNvJ1RF"));
        public static final String EXTRA_HIDE_BOTTOM_CONTROLS = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcBmMKFiBhNFk9Iz4uL2AjGgRsDgo6JhguVg=="));
        public static final String EXTRA_FREE_STYLE_CROP = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcBGEwLCB9JzAyKT4AH2wzGiQ="));
        public static final String EXTRA_ASPECT_RATIO_SELECTED_BY_DEFAULT = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcHWEjOCBpJzARKC0cI2UmLD9qASg2IBc2I2EzBldpJwIoKQgELA=="));
        public static final String EXTRA_ASPECT_RATIO_OPTIONS = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcHWEjOCBpJzARKC0cI2UhGiRsAR46Jj4uVg=="));
        public static final String EXTRA_UCROP_ROOT_VIEW_BACKGROUND_COLOR = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcXH0jMCplHygeKQccX2oFNCFjJzg2JS0+OWwjFgJpNR46IxgAKg=="));
        private final Bundle mOptionBundle = new Bundle();

        @NonNull
        public Bundle getOptionBundle() {
            return this.mOptionBundle;
        }

        public void setCompressionFormat(@NonNull Bitmap.CompressFormat format) {
            this.mOptionBundle.putString(EXTRA_COMPRESSION_FORMAT_NAME, format.name());
        }

        public void setCompressionQuality(@IntRange(from=0L) int compressQuality) {
            this.mOptionBundle.putInt(EXTRA_COMPRESSION_QUALITY, compressQuality);
        }

        public void setAllowedGestures(int tabScale, int tabRotate, int tabAspectRatio) {
            this.mOptionBundle.putIntArray(EXTRA_ALLOWED_GESTURES, new int[]{tabScale, tabRotate, tabAspectRatio});
        }

        public void setMaxScaleMultiplier(@FloatRange(from=1.0, fromInclusive=false) float maxScaleMultiplier) {
            this.mOptionBundle.putFloat(EXTRA_MAX_SCALE_MULTIPLIER, maxScaleMultiplier);
        }

        public void setImageToCropBoundsAnimDuration(@IntRange(from=10L) int durationMillis) {
            this.mOptionBundle.putInt(EXTRA_IMAGE_TO_CROP_BOUNDS_ANIM_DURATION, durationMillis);
        }

        public void setMaxBitmapSize(@IntRange(from=10L) int maxBitmapSize) {
            this.mOptionBundle.putInt(EXTRA_MAX_BITMAP_SIZE, maxBitmapSize);
        }

        public void setDimmedLayerColor(@ColorInt int color2) {
            this.mOptionBundle.putInt(EXTRA_DIMMED_LAYER_COLOR, color2);
        }

        public void setCircleDimmedLayer(boolean isCircle) {
            this.mOptionBundle.putBoolean(EXTRA_CIRCLE_DIMMED_LAYER, isCircle);
        }

        public void setShowCropFrame(boolean show) {
            this.mOptionBundle.putBoolean(EXTRA_SHOW_CROP_FRAME, show);
        }

        public void setCropFrameColor(@ColorInt int color2) {
            this.mOptionBundle.putInt(EXTRA_CROP_FRAME_COLOR, color2);
        }

        public void setCropFrameStrokeWidth(@IntRange(from=0L) int width) {
            this.mOptionBundle.putInt(EXTRA_CROP_FRAME_STROKE_WIDTH, width);
        }

        public void setShowCropGrid(boolean show) {
            this.mOptionBundle.putBoolean(EXTRA_SHOW_CROP_GRID, show);
        }

        public void setCropGridRowCount(@IntRange(from=0L) int count) {
            this.mOptionBundle.putInt(EXTRA_CROP_GRID_ROW_COUNT, count);
        }

        public void setCropGridColumnCount(@IntRange(from=0L) int count) {
            this.mOptionBundle.putInt(EXTRA_CROP_GRID_COLUMN_COUNT, count);
        }

        public void setCropGridColor(@ColorInt int color2) {
            this.mOptionBundle.putInt(EXTRA_CROP_GRID_COLOR, color2);
        }

        public void setCropGridStrokeWidth(@IntRange(from=0L) int width) {
            this.mOptionBundle.putInt(EXTRA_CROP_GRID_STROKE_WIDTH, width);
        }

        public void setToolbarColor(@ColorInt int color2) {
            this.mOptionBundle.putInt(EXTRA_TOOL_BAR_COLOR, color2);
        }

        public void setStatusBarColor(@ColorInt int color2) {
            this.mOptionBundle.putInt(EXTRA_STATUS_BAR_COLOR, color2);
        }

        public void setActiveWidgetColor(@ColorInt int color2) {
            this.mOptionBundle.putInt(EXTRA_UCROP_COLOR_WIDGET_ACTIVE, color2);
        }

        public void setToolbarWidgetColor(@ColorInt int color2) {
            this.mOptionBundle.putInt(EXTRA_UCROP_WIDGET_COLOR_TOOLBAR, color2);
        }

        public void setToolbarTitle(@Nullable String text) {
            this.mOptionBundle.putString(EXTRA_UCROP_TITLE_TEXT_TOOLBAR, text);
        }

        public void setToolbarCancelDrawable(@DrawableRes int drawable2) {
            this.mOptionBundle.putInt(EXTRA_UCROP_WIDGET_CANCEL_DRAWABLE, drawable2);
        }

        public void setToolbarCropDrawable(@DrawableRes int drawable2) {
            this.mOptionBundle.putInt(EXTRA_UCROP_WIDGET_CROP_DRAWABLE, drawable2);
        }

        public void setLogoColor(@ColorInt int color2) {
            this.mOptionBundle.putInt(EXTRA_UCROP_LOGO_COLOR, color2);
        }

        public void setHideBottomControls(boolean hide) {
            this.mOptionBundle.putBoolean(EXTRA_HIDE_BOTTOM_CONTROLS, hide);
        }

        public void setFreeStyleCropEnabled(boolean enabled) {
            this.mOptionBundle.putBoolean(EXTRA_FREE_STYLE_CROP, enabled);
        }

        public void setAspectRatioOptions(int selectedByDefault, AspectRatio ... aspectRatio) {
            if (selectedByDefault > aspectRatio.length) {
                throw new IllegalArgumentException(String.format(Locale.US, com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JAgcPGgaRChvJyg/KhcMP2UzGixgNB5KLhgiO2YKTT95VlApPy4cEXgVLDNqJxo6IF4iKW4OPD5sJw4/LQcLOGwFRTdgMCQ7Iy4mPW4KAShsNzg/IxgfJGAjOD9vDlkdLARXJWUgNARsDTxJOy0YPGwzLzZ7I1A0LRUHCA==")), selectedByDefault, aspectRatio.length));
            }
            this.mOptionBundle.putInt(EXTRA_ASPECT_RATIO_SELECTED_BY_DEFAULT, selectedByDefault);
            this.mOptionBundle.putParcelableArrayList(EXTRA_ASPECT_RATIO_OPTIONS, new ArrayList<AspectRatio>(Arrays.asList(aspectRatio)));
        }

        public void setRootViewBackgroundColor(@ColorInt int color2) {
            this.mOptionBundle.putInt(EXTRA_UCROP_ROOT_VIEW_BACKGROUND_COLOR, color2);
        }

        public void withAspectRatio(float x, float y) {
            this.mOptionBundle.putFloat(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcHWEjOCBpJzARKC0cI2UmRVo=")), x);
            this.mOptionBundle.putFloat(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcHWEjOCBpJzARKC0cI2UmAlo=")), y);
        }

        public void useSourceImageAspectRatio() {
            this.mOptionBundle.putFloat(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcHWEjOCBpJzARKC0cI2UmRVo=")), 0.0f);
            this.mOptionBundle.putFloat(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcHWEjOCBpJzARKC0cI2UmAlo=")), 0.0f);
        }

        public void withMaxResultSize(@IntRange(from=10L) int width, @IntRange(from=10L) int height) {
            this.mOptionBundle.putInt(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcAX0FBhBvAQ4uJj5SVg==")), width);
            this.mOptionBundle.putInt(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojLDdhNFE1IykYD2UjOAZqATg7KQQcAX0FBhBvAQ4uJi5SVg==")), height);
        }
    }
}

