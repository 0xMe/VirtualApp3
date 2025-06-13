/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.Uri
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  androidx.annotation.NonNull
 *  androidx.recyclerview.widget.RecyclerView$Adapter
 *  androidx.recyclerview.widget.RecyclerView$ViewHolder
 *  com.bumptech.glide.Glide
 *  com.bumptech.glide.request.RequestOptions
 *  com.kook.librelease.R$drawable
 *  com.kook.librelease.R$id
 *  com.kook.librelease.R$layout
 */
package com.carlos.common.imagepicker.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.carlos.common.imagepicker.entity.Image;
import com.kook.librelease.R;
import com.kook.librelease.StringFog;
import java.io.File;
import java.util.List;

public class PreviewImageAdapter
extends RecyclerView.Adapter<ImageHolder> {
    private Context mContext;
    private List<Image> mImgList;
    public OnItemClcikLitener onItemClcikLitener;

    public void setOnItemClcikLitener(OnItemClcikLitener onItemClcikLitener) {
        this.onItemClcikLitener = onItemClcikLitener;
    }

    public PreviewImageAdapter(Context mContext, List<Image> mImgList) {
        this.mContext = mContext;
        this.mImgList = mImgList;
    }

    public List<Image> getData() {
        return this.mImgList;
    }

    @NonNull
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final ImageHolder imageHolder = new ImageHolder(LayoutInflater.from((Context)this.mContext).inflate(R.layout.preview_item, parent, false));
        imageHolder.itemView.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                if (PreviewImageAdapter.this.onItemClcikLitener != null) {
                    PreviewImageAdapter.this.onItemClcikLitener.OnItemClcik(PreviewImageAdapter.this, imageHolder.itemView, imageHolder.getLayoutPosition());
                }
            }
        });
        return imageHolder;
    }

    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {
        String path = this.mImgList.get(position).getPath();
        Uri uri = path.startsWith(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LBcqLG8FSFo="))) ? Uri.parse((String)path) : Uri.fromFile((File)new File(path));
        Glide.with((Context)this.mContext).setDefaultRequestOptions(new RequestOptions().dontTransform().placeholder(R.drawable.ic_image).error(R.drawable.ic_img_load_fail).override(800, 1200)).load(uri).into(holder.imageView);
    }

    public int getItemCount() {
        return this.mImgList.size();
    }

    class ImageHolder
    extends RecyclerView.ViewHolder {
        private ImageView imageView;

        ImageHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView)itemView.findViewById(R.id.iv_itemimg);
        }
    }

    public static interface OnItemClcikLitener {
        public void OnItemClcik(PreviewImageAdapter var1, View var2, int var3);
    }
}

