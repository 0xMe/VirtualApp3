/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.graphics.Rect
 *  android.util.Log
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewParent
 *  android.widget.ImageView
 *  android.widget.RemoteViews
 *  android.widget.TextView
 */
package com.lody.virtual.server.notification;

import android.app.PendingIntent;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;
import com.lody.virtual.StringFog;
import com.lody.virtual.helper.utils.Reflect;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

class PendIntentCompat {
    private RemoteViews mRemoteViews;
    private Map<Integer, PendingIntent> clickIntents;

    PendIntentCompat(RemoteViews mRemoteViews) {
        this.mRemoteViews = mRemoteViews;
    }

    public int findPendIntents() {
        if (this.clickIntents == null) {
            this.clickIntents = this.getClickIntents(this.mRemoteViews);
        }
        return this.clickIntents.size();
    }

    public void setPendIntent(RemoteViews remoteViews, View remoteview, View oldRemoteView) {
        if (this.findPendIntents() > 0) {
            Iterator<Map.Entry<Integer, PendingIntent>> set = this.clickIntents.entrySet().iterator();
            ArrayList<RectInfo> list = new ArrayList<RectInfo>();
            int index = 0;
            while (set.hasNext()) {
                Map.Entry<Integer, PendingIntent> e = set.next();
                View view = oldRemoteView.findViewById(e.getKey().intValue());
                if (view == null) continue;
                Rect rect = this.getRect(view);
                list.add(new RectInfo(rect, e.getValue(), index));
                ++index;
            }
            if (remoteview instanceof ViewGroup) {
                this.setIntentByViewGroup(remoteViews, (ViewGroup)remoteview, list);
            }
        }
    }

    private Rect getRect(View view) {
        Rect rect = new Rect();
        rect.top = view.getTop();
        rect.left = view.getLeft();
        rect.right = view.getRight();
        rect.bottom = view.getBottom();
        ViewParent viewParent = view.getParent();
        if (viewParent != null && viewParent instanceof ViewGroup) {
            Rect prect = this.getRect((View)((ViewGroup)viewParent));
            rect.top += prect.top;
            rect.left += prect.left;
            rect.right += prect.left;
            rect.bottom += prect.top;
        }
        return rect;
    }

    private void setIntentByViewGroup(RemoteViews remoteViews, ViewGroup viewGroup, List<RectInfo> list) {
        int count = viewGroup.getChildCount();
        Rect p = new Rect();
        viewGroup.getHitRect(p);
        for (int i = 0; i < count; ++i) {
            Rect rect;
            RectInfo next;
            View v = viewGroup.getChildAt(i);
            if (v instanceof ViewGroup) {
                this.setIntentByViewGroup(remoteViews, (ViewGroup)v, list);
                continue;
            }
            if (!(v instanceof TextView) && !(v instanceof ImageView) || (next = this.findIntent(rect = this.getRect(v), list)) == null) continue;
            remoteViews.setOnClickPendingIntent(v.getId(), next.mPendingIntent);
        }
    }

    private RectInfo findIntent(Rect rect, List<RectInfo> list) {
        int maxArea = 0;
        RectInfo next = null;
        for (RectInfo rectInfo : list) {
            int size = this.getOverlapArea(rect, rectInfo.rect);
            if (size <= maxArea) continue;
            if (size == 0) {
                Log.w((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IhguCGgFAiZiIgY2LBcMDmUxAiVlDjwsKghSVg==")), (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LT4YCGgJIAZmJBEi")) + rectInfo.rect));
            }
            maxArea = size;
            next = rectInfo;
        }
        return next;
    }

    private int getOverlapArea(Rect rect1, Rect rect2) {
        Rect rect = new Rect();
        rect.left = Math.max(rect1.left, rect2.left);
        rect.top = Math.max(rect1.top, rect2.top);
        rect.right = Math.min(rect1.right, rect2.right);
        rect.bottom = Math.min(rect1.bottom, rect2.bottom);
        if (rect.left < rect.right && rect.top < rect.bottom) {
            return (rect.right - rect.left) * (rect.bottom - rect.top);
        }
        return 0;
    }

    private Map<Integer, PendingIntent> getClickIntents(RemoteViews remoteViews) {
        HashMap<Integer, PendingIntent> map = new HashMap<Integer, PendingIntent>();
        if (remoteViews == null) {
            return map;
        }
        Object mActionsObj = null;
        try {
            mActionsObj = Reflect.on(remoteViews).get(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IwY+OWwFAiVgNyhF")));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if (mActionsObj == null) {
            return map;
        }
        if (mActionsObj instanceof Collection) {
            Collection mActions = mActionsObj;
            for (Object one : mActions) {
                String action;
                if (one == null) continue;
                try {
                    action = (String)Reflect.on(one).call(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGMVLAZjDh42Ij0iD2kjSFo="))).get();
                }
                catch (Exception e) {
                    action = one.getClass().getSimpleName();
                }
                if (!StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii4uLGIzBhNgHgY5KSwmPW8VBi9lNyBPLC0qJ2AzFlo=")).equalsIgnoreCase(action)) continue;
                int id2 = (Integer)Reflect.on(one).get(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KT4YM2wxAiw=")));
                PendingIntent intent = (PendingIntent)Reflect.on(one).get(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KhguCGgFAiZiIgY2LBcMDmUzSFo=")));
                map.put(id2, intent);
            }
        }
        return map;
    }

    class RectInfo {
        Rect rect;
        PendingIntent mPendingIntent;
        int index;

        public RectInfo(Rect rect, PendingIntent pendingIntent, int index) {
            this.rect = rect;
            this.mPendingIntent = pendingIntent;
            this.index = index;
        }

        public String toString() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ij4uOWwLAiZiNB4hIz0MP2U0PFo=")) + this.rect + '}';
        }
    }
}

