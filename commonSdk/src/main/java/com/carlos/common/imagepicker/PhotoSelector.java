/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.net.Uri
 *  android.os.Bundle
 *  androidx.annotation.ColorInt
 *  androidx.annotation.NonNull
 */
package com.carlos.common.imagepicker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import com.carlos.common.imagepicker.ImageSelectorActivity;
import com.carlos.common.imagepicker.UCrop;
import com.carlos.common.imagepicker.utils.PermissionsUtils;
import com.kook.librelease.StringFog;
import java.io.Serializable;
import java.util.ArrayList;

public class PhotoSelector {
    public static final int CROP_RECTANG = 1;
    public static final int CROP_CIRCLE = 2;
    public static final int DEFAULT_MAX_SELECTED_COUNT = 9;
    public static final int DEFAULT_GRID_COLUMN = 3;
    public static final int DEFAULT_REQUEST_CODE = 999;
    public static final int RESULT_CODE = 1000;
    public static final int TAKE_PHOTO_CROP_REQUESTCODE = 1001;
    public static final int TAKE_PHOTO_REQUESTCODE = 1002;
    public static final int CROP_REQUESTCODE = 1003;
    public static final String SELECT_RESULT = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki4uDmgVLAZsJyw/Iy4MCGUzSFo="));
    public static final String EXTRA_MAX_SELECTED_COUNT = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Iwg+IGYwLCtgHjA5LBcMPmMFAiVvARo/"));
    public static final String EXTRA_GRID_COLUMN = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADmwVEiY="));
    public static final String EXTRA_SHOW_CAMERA = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki5fD2w2Gil9Dl0/Iz0iVg=="));
    public static final String EXTRA_SELECTED_IMAGES = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki4uDmgVLAZiDgpAKQdXOWkFGgM="));
    public static final String EXTRA_SINGLE = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki4YCGgzHis="));
    public static final String EXTRA_CROP = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LAc2H2swFiVhEVRF"));
    public static final String EXTRA_CROP_MODE = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li0MD28IGiNgJAo/"));
    public static final String EXTRA_MATERIAL_DESIGN = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Iwg+LGgaFi99DlFAKBcML2wjEiY="));
    public static final String EXTRA_TOOLBARCOLOR = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KRgAD2oLFjdhMig1KhdfKA=="));
    public static final String EXTRA_BOTTOMBARCOLOR = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Lj4ALGwFGiNlNCAqJy1fCG8KRVo="));
    public static final String EXTRA_STATUSBARCOLOR = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki0qP2wKNANlNCAqJy1fCG8KRVo="));
    public static final String EXTRA_POSITION = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KhgAKWUaMC9gJFlF"));
    public static final String EXTRA_ISPREVIEW = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LAc2Am8jND5jDjAt"));
    public static final String IS_CONFIRM = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LAc2H2szGiZiNAYqKghSVg=="));

    public static Uri getCropImageUri(@NonNull Intent intent) {
        return UCrop.getOutput(intent);
    }

    public static PhotoSelectorBuilder builder() {
        return new PhotoSelectorBuilder();
    }

    public static class PhotoSelectorBuilder {
        private Bundle mPickerOptionsBundle = new Bundle();
        private Intent mPickerIntent = new Intent();

        PhotoSelectorBuilder() {
        }

        public void start(@NonNull Activity activity, int requestCode) {
            if (PermissionsUtils.checkReadStoragePermission(activity)) {
                activity.startActivityForResult(this.getIntent((Context)activity), requestCode);
            }
        }

        public Intent getIntent(@NonNull Context context) {
            this.mPickerIntent.setClass(context, ImageSelectorActivity.class);
            this.mPickerIntent.putExtras(this.mPickerOptionsBundle);
            return this.mPickerIntent;
        }

        public PhotoSelectorBuilder setIntentExtras(String extrasKey, Serializable values) {
            this.mPickerIntent.putExtra(extrasKey, values);
            return this;
        }

        public void start(@NonNull Activity activity) {
            this.start(activity, 999);
        }

        public PhotoSelectorBuilder setMaxSelectCount(int maxSelectCount) {
            this.mPickerOptionsBundle.putInt(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Iwg+IGYwLCtgHjA5LBcMPmMFAiVvARo/")), maxSelectCount);
            return this;
        }

        public PhotoSelectorBuilder setSingle(boolean isSingle) {
            this.mPickerOptionsBundle.putBoolean(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki4YCGgzHis=")), isSingle);
            return this;
        }

        public PhotoSelectorBuilder setGridColumnCount(int columnCount) {
            this.mPickerOptionsBundle.putInt(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADmwVEiY=")), columnCount);
            return this;
        }

        public PhotoSelectorBuilder setShowCamera(boolean showCamera) {
            this.mPickerOptionsBundle.putBoolean(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki5fD2w2Gil9Dl0/Iz0iVg==")), showCamera);
            return this;
        }

        public PhotoSelectorBuilder setSelected(ArrayList<String> selected) {
            this.mPickerOptionsBundle.putStringArrayList(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki4uDmgVLAZiDgpAKQdXOWkFGgM=")), selected);
            return this;
        }

        public PhotoSelectorBuilder setToolBarColor(@ColorInt int toolBarColor) {
            this.mPickerOptionsBundle.putInt(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("KRgAD2oLFjdhMig1KhdfKA==")), toolBarColor);
            return this;
        }

        public PhotoSelectorBuilder setBottomBarColor(@ColorInt int bottomBarColor) {
            this.mPickerOptionsBundle.putInt(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Lj4ALGwFGiNlNCAqJy1fCG8KRVo=")), bottomBarColor);
            return this;
        }

        public PhotoSelectorBuilder setStatusBarColor(@ColorInt int statusBarColor) {
            this.mPickerOptionsBundle.putInt(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki0qP2wKNANlNCAqJy1fCG8KRVo=")), statusBarColor);
            return this;
        }

        public PhotoSelectorBuilder setMaterialDesign(boolean materialDesign) {
            this.mPickerOptionsBundle.putBoolean(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Iwg+LGgaFi99DlFAKBcML2wjEiY=")), materialDesign);
            return this;
        }

        public PhotoSelectorBuilder setCrop(boolean isCrop) {
            this.mPickerOptionsBundle.putBoolean(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LAc2H2swFiVhEVRF")), isCrop);
            return this;
        }

        public PhotoSelectorBuilder setCropMode(int mode) {
            this.mPickerOptionsBundle.putInt(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li0MD28IGiNgJAo/")), mode);
            return this;
        }
    }
}

