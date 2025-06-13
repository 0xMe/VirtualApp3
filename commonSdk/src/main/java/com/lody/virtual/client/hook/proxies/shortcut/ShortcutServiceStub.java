/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageManager
 *  android.content.pm.ShortcutInfo
 *  android.content.pm.ShortcutInfo$Builder
 *  android.graphics.Bitmap
 *  android.graphics.drawable.Icon
 *  android.os.Parcelable
 *  android.text.TextUtils
 *  android.util.ArraySet
 */
package com.lody.virtual.client.hook.proxies.shortcut;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.ArraySet;
import com.lody.virtual.StringFog;
import com.lody.virtual.client.VClient;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.ReplaceCallingPkgMethodProxy;
import com.lody.virtual.client.hook.base.ReplaceFirstUserIdMethodProxy;
import com.lody.virtual.helper.compat.BuildCompat;
import com.lody.virtual.helper.compat.ParceledListSliceCompat;
import com.lody.virtual.helper.utils.BitmapUtils;
import com.lody.virtual.helper.utils.Reflect;
import com.lody.virtual.helper.utils.VLog;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import mirror.android.content.pm.IShortcutService;
import mirror.android.content.pm.ParceledListSlice;
import mirror.com.android.internal.infra.AndroidFuture;

@TargetApi(value=25)
public class ShortcutServiceStub
extends BinderInvocationProxy {
    public ShortcutServiceStub() {
        super(IShortcutService.Stub.TYPE, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki5fD28gMClmAQpF")));
    }

    @Override
    protected void onBindMethods() {
        super.onBindMethods();
        this.addMethodProxy(new ReplaceCallingPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LRgYKWsVFiRiDyg0Ki4uLm4KGgZsJ1RF"))));
        this.addMethodProxy(new ReplaceCallingPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LQgcP2sjHitpJBo1Iz42P2UgBgM="))));
        this.addMethodProxy(new ReplaceCallingPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGcjNCN9DgY2KQcYM2YFQSRlHzAcKhgcCg=="))));
        this.addMethodProxy(new ReplaceCallingPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGcjJAZiDFEzKgccLmIVGgNrDiwVIxgIJw=="))));
        this.addMethodProxy(new ReplaceCallingPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLH0VLCVgMl07LRU2MW8jGiZsJx4cLC02Vg=="))));
        this.addMethodProxy(new ReplaceCallingPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGIVJDBpJBo1Iz42P2UgBhNlJCgbKgU6J2E2PCZqHho/Ki0cMw=="))));
        this.addMethodProxy(new ReplaceCallingPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uKGowFgZpJBo1Iz42P2UgBlBsJygv"))));
        this.addMethodProxy(new ReplaceCallingPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iy4cEW8KICRjDig7LBccDW8bQSlvER49LhhSVg=="))));
        this.addMethodProxy(new ReplaceCallingPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LBg+KWczRSVhNwo5LAg2X28KAgZkESg5LBgYD2EgGipsN1RF"))));
        this.addMethodProxy(new ReplaceCallingPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uDWowOCtlDlEoIBgcDm4jPC9oIjAZLD0MCn0jLD9lJ1RF"))));
        this.addMethodProxy(new ReplaceCallingPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uDWowOCtqEQY2LwdXMW4IAiBlJAo/LT0uCmEjSFo="))));
        this.addMethodProxy(new ReplaceCallingPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGczRSVhNwo5LAg2Lw=="))));
        this.addMethodProxy(new ReplaceCallingPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uDWowOCtoHh42KCtbMWUVGixkJ1kcKS0qJWYFFjY="))));
        this.addMethodProxy(new WrapperShortcutInfo(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KhcuKWULMD9gNCA3KQcqAWwzNARvETAwKghSVg==")), 1, null));
        this.addMethodProxy(new WrapperShortcutInfo(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj4uL2wVNANmHyQzKjwqMm8KRQZoJCg/")), 1, false));
        this.addMethodProxy(new UnWrapperShortcutInfo(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGcFAiZgNDAwOy0ADWoaBilvDiw6"))));
        this.addMethodProxy(new WrapperShortcutInfo(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggqPGAKAiZ9Dl0zLywqMm8KRQZoJCg/KT5SVg==")), 1, false));
        this.addMethodProxy(new WrapperShortcutInfo(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uLGAKAiZ9Dl0zLywqMm8KRQZoJCg/KT5SVg==")), 1, false));
        this.addMethodProxy(new UnWrapperShortcutInfo(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGAKAiZ9Dl0zLywqMm8KRQZoJCg/KT5SVg=="))));
        this.addMethodProxy(new WrapperShortcutInfo(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li0MM2saMCtpJBo1Iz42P2UgBl9rDjAwLAcqBWAzFiBsNzBF")), 1, null));
        this.addMethodProxy(new WrapperShortcutInfo(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KQc6PGsaMCtpJBo1Iz42P2UgBgM=")), 1, false));
        this.addMethodProxy(new ReplaceCallingPkgMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGIVJCZjDjw/Iy42AWwzNARvETAwKgc2Vg=="))){

            @Override
            public Object call(Object who, Method method, Object ... args) {
                return ParceledListSliceCompat.create(new ArrayList());
            }
        });
        this.addMethodProxy(new ReplaceFirstUserIdMethodProxy(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAc2DGgaJAViASggOxccDmQgBitlDDAwKQc6KWEzFiBuEVRF"))));
    }

    static ShortcutInfo wrapper(Context appContext, ShortcutInfo shortcutInfo, String pkg, int userId) {
        Bitmap bmp;
        Icon icon = (Icon)Reflect.on(shortcutInfo).opt(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IwYYOWozBlo=")));
        if (icon != null) {
            bmp = BitmapUtils.drawableToBitmap(icon.loadDrawable(appContext));
        } else {
            PackageManager pm = VirtualCore.get().getPackageManager();
            bmp = BitmapUtils.drawableToBitmap(appContext.getApplicationInfo().loadIcon(pm));
        }
        Intent proxyIntent = VirtualCore.get().wrapperShortcutIntent(shortcutInfo.getIntent(), null, pkg, userId);
        proxyIntent.putExtra(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JysiEWYwHil9AQo/KC1fKGwjGgM=")), ShortcutServiceStub.setToString(shortcutInfo.getCategories()));
        proxyIntent.putExtra(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JysiEWYwHjd9JwozLD0cLmgjSFo=")), (Parcelable)shortcutInfo.getActivity());
        ShortcutInfo.Builder builder = new ShortcutInfo.Builder(VirtualCore.get().getContext(), pkg + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JhhSVg==")) + userId + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("My5SVg==")) + shortcutInfo.getId());
        if (shortcutInfo.getLongLabel() != null) {
            builder.setLongLabel(shortcutInfo.getLongLabel());
        }
        if (shortcutInfo.getShortLabel() != null) {
            builder.setShortLabel(shortcutInfo.getShortLabel());
        }
        builder.setIcon(Icon.createWithBitmap((Bitmap)bmp));
        builder.setIntent(proxyIntent);
        return builder.build();
    }

    static ShortcutInfo unWrapper(Context appContext, ShortcutInfo shortcutInfo, String _pkg, int _userId) throws URISyntaxException {
        Intent intent = shortcutInfo.getIntent();
        if (intent == null) {
            return null;
        }
        String pkg = intent.getStringExtra(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JysiEWYwHh9hHg49Ji5SVg==")));
        int userId = intent.getIntExtra(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JysiEWYwHh9mASg/IzxfMWk2NFo=")), 0);
        if (TextUtils.equals((CharSequence)pkg, (CharSequence)_pkg) && userId == _userId) {
            Set<String> cs;
            String _id = shortcutInfo.getId();
            String id2 = _id.substring(_id.indexOf(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("My5SVg=="))) + 1);
            Icon icon = (Icon)Reflect.on(shortcutInfo).opt(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IwYYOWozBlo=")));
            String uri = intent.getStringExtra(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JysiEWYwHh9mASwzJi5SVg==")));
            Intent targetIntent = null;
            if (!TextUtils.isEmpty((CharSequence)uri)) {
                targetIntent = Intent.parseUri((String)uri, (int)0);
            }
            ComponentName componentName = (ComponentName)intent.getParcelableExtra(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JysiEWYwHjd9JwozLD0cLmgjSFo=")));
            String categories = intent.getStringExtra(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JysiEWYwHil9AQo/KC1fKGwjGgM=")));
            ShortcutInfo.Builder builder = new ShortcutInfo.Builder(appContext, id2);
            if (icon != null) {
                builder.setIcon(icon);
            }
            if (shortcutInfo.getLongLabel() != null) {
                builder.setLongLabel(shortcutInfo.getLongLabel());
            }
            if (shortcutInfo.getShortLabel() != null) {
                builder.setShortLabel(shortcutInfo.getShortLabel());
            }
            if (componentName != null) {
                builder.setActivity(componentName);
            }
            if (targetIntent != null) {
                builder.setIntent(targetIntent);
            }
            if ((cs = ShortcutServiceStub.toSet(categories)) != null) {
                builder.setCategories(cs);
            }
            return builder.build();
        }
        return null;
    }

    private static <T> String setToString(Set<T> sets) {
        if (sets == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<T> iterator = sets.iterator();
        boolean first = true;
        while (iterator.hasNext()) {
            if (first) {
                first = false;
            } else {
                stringBuilder.append(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("MxhSVg==")));
            }
            stringBuilder.append(iterator.next());
        }
        return stringBuilder.toString();
    }

    @TargetApi(value=23)
    private static Set<String> toSet(String allStr) {
        if (allStr == null) {
            return null;
        }
        String[] strs = allStr.split(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("MxhSVg==")));
        ArraySet sets = new ArraySet();
        for (String str : strs) {
            sets.add(str);
        }
        return sets;
    }

    static class UnWrapperShortcutInfo
    extends ReplaceCallingPkgMethodProxy {
        public UnWrapperShortcutInfo(String name) {
            super(name);
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            Object parceledListSlice = super.call(who, method, args);
            if (parceledListSlice != null) {
                ArrayList<ShortcutInfo> result = new ArrayList<ShortcutInfo>();
                if (!UnWrapperShortcutInfo.getConfig().isAllowCreateShortcut()) {
                    return ParceledListSliceCompat.create(result);
                }
                List<?> list = ParceledListSlice.getList.call(parceledListSlice, new Object[0]);
                if (list != null) {
                    for (int i = list.size() - 1; i >= 0; --i) {
                        Object obj = list.get(i);
                        if (!(obj instanceof ShortcutInfo)) continue;
                        ShortcutInfo info = (ShortcutInfo)obj;
                        ShortcutInfo target = ShortcutServiceStub.unWrapper((Context)VClient.get().getCurrentApplication(), info, UnWrapperShortcutInfo.getAppPkg(), UnWrapperShortcutInfo.getAppUserId());
                        if (target == null) continue;
                        result.add(target);
                    }
                }
                return ParceledListSliceCompat.create(result);
            }
            return null;
        }
    }

    static class WrapperShortcutInfo
    extends ReplaceCallingPkgMethodProxy {
        private int infoIndex;
        private Object defValue;

        public WrapperShortcutInfo(String name, int index, Object defValue) {
            super(name);
            this.infoIndex = index;
            this.defValue = defValue;
        }

        private Object wrapperResult_(Object result) {
            if (!BuildCompat.isS()) {
                return result;
            }
            Object ret = AndroidFuture.ctor.newInstance();
            AndroidFuture.complete.call(ret, result);
            return ret;
        }

        private Object wrapperResult(Method method, Object result) {
            VLog.i(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KS0MP28KICthNSw/Iy4MCGUzSFo=")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IwguLGUFGixOClw3")) + method.toString(), new Object[0]);
            if (!method.toString().contains(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JggcPG8jGi9iHDwvLBgMKGkjSFo=")))) {
                return result;
            }
            Object ret = AndroidFuture.ctor.newInstance();
            AndroidFuture.complete.call(ret, result);
            return ret;
        }

        @Override
        public Object call(Object who, Method method, Object ... args) throws Throwable {
            if (!WrapperShortcutInfo.getConfig().isAllowCreateShortcut()) {
                VLog.i(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii5fD28gMClmAQoPKAguLGwjAitkJCwwLS5SVg==")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("BwkjOEZJRlZYFQMiAhxcCUdJOT1BADk0AFYBA1gHQgBFEx8f")), new Object[0]);
                return this.wrapperResult(method, this.defValue);
            }
            Object paramValue = args[this.infoIndex];
            if (paramValue != null) {
                if (paramValue instanceof ShortcutInfo) {
                    ShortcutInfo shortcutInfo = (ShortcutInfo)paramValue;
                    args[this.infoIndex] = ShortcutServiceStub.wrapper((Context)VClient.get().getCurrentApplication(), shortcutInfo, WrapperShortcutInfo.getAppPkg(), WrapperShortcutInfo.getAppUserId());
                } else {
                    List<?> list;
                    ArrayList<ShortcutInfo> result = new ArrayList<ShortcutInfo>();
                    try {
                        list = ParceledListSlice.getList.call(paramValue, new Object[0]);
                    }
                    catch (Throwable e) {
                        return this.wrapperResult(method, this.defValue);
                    }
                    if (list != null) {
                        for (int i = list.size() - 1; i >= 0; --i) {
                            Object obj = list.get(i);
                            if (!(obj instanceof ShortcutInfo)) continue;
                            ShortcutInfo info = (ShortcutInfo)obj;
                            ShortcutInfo target = ShortcutServiceStub.unWrapper((Context)VClient.get().getCurrentApplication(), info, WrapperShortcutInfo.getAppPkg(), WrapperShortcutInfo.getAppUserId());
                            if (target == null) continue;
                            result.add(target);
                        }
                    }
                    args[this.infoIndex] = ParceledListSliceCompat.create(result);
                }
                return method.invoke(who, args);
            }
            return this.wrapperResult(method, this.defValue);
        }
    }
}

