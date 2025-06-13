/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.TextView
 *  com.kook.librelease.R$drawable
 *  com.kook.librelease.R$id
 *  com.kook.librelease.R$layout
 *  com.kook.librelease.R$string
 */
package com.carlos.common.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.carlos.common.ui.activity.abs.ui.BaseAdapterPlus;
import com.carlos.common.ui.adapter.bean.DeviceData;
import com.kook.librelease.R;

public class DeviceAdapter
extends BaseAdapterPlus<DeviceData> {
    public DeviceAdapter(Context context) {
        super(context);
    }

    @Override
    protected View createView(int position, ViewGroup parent) {
        Object view = this.inflate(R.layout.item_location_app, parent, false);
        ViewHolder viewHolder = new ViewHolder((View)view);
        view.setTag((Object)viewHolder);
        return view;
    }

    @Override
    protected void attach(View view, DeviceData item, int position) {
        ViewHolder viewHolder = (ViewHolder)view.getTag();
        if (item.icon == null) {
            viewHolder.icon.setImageResource(R.drawable.ic_about);
        } else {
            viewHolder.icon.setVisibility(0);
            viewHolder.icon.setImageDrawable(item.icon);
        }
        viewHolder.label.setText((CharSequence)item.name);
        if (item.isMocking()) {
            viewHolder.location.setText(R.string.mock_device);
        } else {
            viewHolder.location.setText(R.string.mock_none);
        }
    }

    static class ViewHolder
    extends BaseAdapterPlus.BaseViewHolder {
        final ImageView icon = (ImageView)this.$(R.id.item_app_icon);
        final TextView label = (TextView)this.$(R.id.item_app_name);
        final TextView location = (TextView)this.$(R.id.item_location);

        public ViewHolder(View view) {
            super(view);
        }
    }
}

