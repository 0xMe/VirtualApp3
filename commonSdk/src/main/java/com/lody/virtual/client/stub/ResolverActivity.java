/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.annotation.TargetApi
 *  android.app.Activity
 *  android.app.ActivityManager
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.IntentFilter$AuthorityEntry
 *  android.content.IntentFilter$MalformedMimeTypeException
 *  android.content.pm.ActivityInfo
 *  android.content.pm.LabeledIntent
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.pm.ResolveInfo
 *  android.content.pm.ResolveInfo$DisplayNameComparator
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.graphics.drawable.Drawable
 *  android.net.Uri
 *  android.os.AsyncTask
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.PatternMatcher
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.AdapterView$OnItemLongClickListener
 *  android.widget.BaseAdapter
 *  android.widget.Button
 *  android.widget.ImageView
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.TextView
 *  com.kook.librelease.R$id
 *  com.kook.librelease.R$integer
 *  com.kook.librelease.R$layout
 *  com.kook.librelease.R$string
 */
package com.lody.virtual.client.stub;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PatternMatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.kook.librelease.R;
import com.lody.virtual.StringFog;
import com.lody.virtual.client.VClient;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.ipc.VActivityManager;
import com.lody.virtual.helper.utils.ComponentUtils;
import com.lody.virtual.helper.utils.Reflect;
import com.lody.virtual.helper.utils.VLog;
import com.lody.virtual.os.VUserHandle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ResolverActivity
extends Activity
implements AdapterView.OnItemClickListener {
    private static final String TAG = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ij4uKWozHj5iASwRLy42MWUVLAZuAVRF"));
    private static final boolean DEBUG = false;
    protected Bundle mOptions;
    protected String mResultWho;
    protected IBinder mResultTo;
    protected int mRequestCode;
    private int mLaunchedFromUid;
    private ResolveListAdapter mAdapter;
    private PackageManager mPm;
    private boolean mAlwaysUseOption;
    private boolean mShowExtended;
    private ListView mListView;
    private Button mAlwaysButton;
    private Button mOnceButton;
    private int mIconDpi;
    private int mIconSize;
    private int mMaxColumns;
    private int mLastSelected = -1;
    private AlertDialog dialog;
    private boolean mRegistered;

    private Intent makeMyIntent() {
        Intent intent = new Intent(this.getIntent());
        intent.setComponent(null);
        intent.setFlags(intent.getFlags() & 0xFF7FFFFF);
        return intent;
    }

    @SuppressLint(value={"MissingSuperCall"})
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = this.makeMyIntent();
        Set categories = intent.getCategories();
        int titleResource = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1kzKj42PW8aASZoATA/IxgAKk42QQ5nDB5F")).equals(intent.getAction()) && categories != null && categories.size() == 1 && categories.contains(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1kzKj42PW8aASZoJzg/LhgmKWEzBSlnHFlBJy5SVg=="))) ? R.string.choose : R.string.choose;
        int userId = intent.getIntExtra(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1kzKj42PW8aASZrDlk/KS49KmYFNCBlNVkhKC4qIGUVNFo=")), VUserHandle.getCallingUserId());
        this.onCreate(savedInstanceState, intent, this.getResources().getText(titleResource), null, null, true, userId);
    }

    protected void onCreate(Bundle savedInstanceState, Intent intent, CharSequence title, Intent[] initialIntents, List<ResolveInfo> rList, boolean alwaysUseOption, int userid) {
        super.onCreate(savedInstanceState);
        this.mLaunchedFromUid = userid;
        this.mPm = this.getPackageManager();
        this.mAlwaysUseOption = alwaysUseOption;
        this.mMaxColumns = this.getResources().getInteger(R.integer.config_maxResolverActivityColumns);
        this.mRegistered = true;
        ActivityManager am = (ActivityManager)this.getSystemService(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2LGUaOC9mEQZF")));
        this.mIconDpi = am.getLauncherLargeIconDensity();
        this.mIconSize = am.getLauncherLargeIconSize();
        this.mAdapter = new ResolveListAdapter((Context)this, intent, initialIntents, rList, this.mLaunchedFromUid);
        int count = this.mAdapter.getCount();
        if (Build.VERSION.SDK_INT >= 17 && this.mLaunchedFromUid < 0) {
            this.finish();
            return;
        }
        if (count == 1) {
            this.startSelected(0, false);
            this.mRegistered = false;
            this.finish();
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
        builder.setTitle(title);
        if (count > 1) {
            this.mListView = new ListView((Context)this);
            this.mListView.setAdapter((ListAdapter)this.mAdapter);
            this.mListView.setOnItemClickListener((AdapterView.OnItemClickListener)this);
            this.mListView.setOnItemLongClickListener((AdapterView.OnItemLongClickListener)new ItemLongClickListener());
            builder.setView((View)this.mListView);
            if (alwaysUseOption) {
                this.mListView.setChoiceMode(1);
            }
        } else {
            builder.setMessage(R.string.noApplications);
        }
        builder.setOnCancelListener(new DialogInterface.OnCancelListener(){

            public void onCancel(DialogInterface dialog) {
                ResolverActivity.this.finish();
            }
        });
        this.dialog = builder.show();
    }

    protected void onDestroy() {
        if (this.dialog != null && this.dialog.isShowing()) {
            this.dialog.dismiss();
        }
        super.onDestroy();
    }

    @TargetApi(value=15)
    Drawable getIcon(Resources res, int resId) {
        Drawable result;
        try {
            result = res.getDrawableForDensity(resId, this.mIconDpi);
        }
        catch (Resources.NotFoundException e) {
            result = null;
        }
        return result;
    }

    Drawable loadIconForResolveInfo(ResolveInfo ri) {
        try {
            Drawable dr;
            if (ri.resolvePackageName != null && ri.icon != 0 && (dr = this.getIcon(this.mPm.getResourcesForApplication(ri.resolvePackageName), ri.icon)) != null) {
                return dr;
            }
            int iconRes = ri.getIconResource();
            if (iconRes != 0 && (dr = this.getIcon(this.mPm.getResourcesForApplication(ri.activityInfo.packageName), iconRes)) != null) {
                return dr;
            }
        }
        catch (PackageManager.NameNotFoundException e) {
            VLog.e(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ji4AI2oFMCZIJw08KD0cDmk3TQRrDjAcKhcMJWIFNyNuNFk7OD1XO2gjQTNoNysT")) + VLog.getStackTraceString(e));
        }
        return ri.loadIcon(this.mPm);
    }

    protected void onRestart() {
        super.onRestart();
        if (!this.mRegistered) {
            this.mRegistered = true;
        }
        this.mAdapter.handlePackagesChanged();
    }

    protected void onStop() {
        super.onStop();
        if (this.mRegistered) {
            this.mRegistered = false;
        }
        if ((this.getIntent().getFlags() & 0x10000000) != 0 && !this.isChangingConfigurations()) {
            this.finish();
        }
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (this.mAlwaysUseOption) {
            int checkedPos = this.mListView.getCheckedItemPosition();
            boolean enabled = checkedPos != -1;
            this.mLastSelected = checkedPos;
            this.mAlwaysButton.setEnabled(enabled);
            this.mOnceButton.setEnabled(enabled);
            if (enabled) {
                this.mListView.setSelection(checkedPos);
            }
        }
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id2) {
        boolean hasValidSelection;
        int checkedPos = this.mListView.getCheckedItemPosition();
        boolean bl = hasValidSelection = checkedPos != -1;
        if (this.mAlwaysUseOption && (!hasValidSelection || this.mLastSelected != checkedPos)) {
            this.mAlwaysButton.setEnabled(hasValidSelection);
            this.mOnceButton.setEnabled(hasValidSelection);
            if (hasValidSelection) {
                this.mListView.smoothScrollToPosition(checkedPos);
            }
            this.mLastSelected = checkedPos;
        } else {
            this.startSelected(position, false);
        }
    }

    void startSelected(int which, boolean always) {
        if (this.isFinishing()) {
            return;
        }
        ResolveInfo ri = this.mAdapter.resolveInfoForPosition(which);
        Intent intent = this.mAdapter.intentForPosition(which);
        this.onIntentSelected(ri, intent, always);
        this.finish();
    }

    protected void onIntentSelected(ResolveInfo ri, Intent intent, boolean alwaysCheck) {
        if (this.mAlwaysUseOption && this.mAdapter.mOrigResolveList != null) {
            String mimeType;
            Set categories;
            IntentFilter filter = new IntentFilter();
            if (intent.getAction() != null) {
                filter.addAction(intent.getAction());
            }
            if ((categories = intent.getCategories()) != null) {
                for (String cat : categories) {
                    filter.addCategory(cat);
                }
            }
            filter.addCategory(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1kzKj42PW8aASZoJzg/LhgmKWEzBSlmHApKICsAAmcVSFo=")));
            int cat = ri.match & 0xFFF0000;
            Uri data = intent.getData();
            if (cat == 0x600000 && (mimeType = intent.resolveType((Context)this)) != null) {
                try {
                    filter.addDataType(mimeType);
                }
                catch (IntentFilter.MalformedMimeTypeException e) {
                    VLog.w(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IwgYDWgYMD9hHjMI")) + VLog.getStackTraceString(e), new Object[0]);
                    filter = null;
                }
            }
            if (data != null && data.getScheme() != null && (cat != 0x600000 || !StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LT4YDmgVSFo=")).equals(data.getScheme()) && !StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ACGwFNCZmEVRF")).equals(data.getScheme()))) {
                filter.addDataScheme(data.getScheme());
                if (Build.VERSION.SDK_INT >= 19) {
                    Iterator aIt;
                    Iterator pIt = ri.filter.schemeSpecificPartsIterator();
                    if (pIt != null) {
                        String ssp = data.getSchemeSpecificPart();
                        while (ssp != null && pIt.hasNext()) {
                            PatternMatcher p = (PatternMatcher)pIt.next();
                            if (!p.match(ssp)) continue;
                            filter.addDataSchemeSpecificPart(p.getPath(), p.getType());
                            break;
                        }
                    }
                    if ((aIt = ri.filter.authoritiesIterator()) != null) {
                        while (aIt.hasNext()) {
                            IntentFilter.AuthorityEntry a = (IntentFilter.AuthorityEntry)aIt.next();
                            if (a.match(data) < 0) continue;
                            int port = a.getPort();
                            filter.addDataAuthority(a.getHost(), port >= 0 ? Integer.toString(port) : null);
                            break;
                        }
                    }
                    if ((pIt = ri.filter.pathsIterator()) != null) {
                        String path = data.getPath();
                        while (path != null && pIt.hasNext()) {
                            PatternMatcher p = (PatternMatcher)pIt.next();
                            if (!p.match(path)) continue;
                            filter.addDataPath(p.getPath(), p.getType());
                            break;
                        }
                    }
                }
            }
            if (filter != null) {
                int N = this.mAdapter.mOrigResolveList.size();
                ComponentName[] set = new ComponentName[N];
                int bestMatch = 0;
                for (int i = 0; i < N; ++i) {
                    ResolveInfo r = this.mAdapter.mOrigResolveList.get(i);
                    set[i] = new ComponentName(r.activityInfo.packageName, r.activityInfo.name);
                    if (r.match <= bestMatch) continue;
                    bestMatch = r.match;
                }
                if (alwaysCheck) {
                    this.getPackageManager().addPreferredActivity(filter, bestMatch, set, intent.getComponent());
                } else {
                    try {
                        Reflect.on(VClient.get().getCurrentApplication().getPackageManager()).call(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uLGIFJANmHCg0Ki4qPW8bQSlvER49IxcqMw==")), intent, intent.resolveTypeIfNeeded(this.getContentResolver()), 65536, filter, bestMatch, intent.getComponent());
                    }
                    catch (Exception re) {
                        VLog.d(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JQcMKmowESh9JCAoKhccDmkJTQNrDixTLRc2CmUgBiplJAodIC4YCmoKOAVsDhET")) + VLog.getStackTraceString(re), new Object[0]);
                    }
                }
            }
        }
        if (intent != null) {
            intent = ComponentUtils.processOutsideIntent(this.mLaunchedFromUid, VirtualCore.get().isExtPackage(), new Intent(intent));
            ActivityInfo info = VirtualCore.get().resolveActivityInfo(intent, this.mLaunchedFromUid);
            if (info == null) {
                this.startActivity(intent);
            } else {
                intent.addFlags(0x2000000);
                int res = VActivityManager.get().startActivity(intent, info, this.mResultTo, this.mOptions, this.mResultWho, this.mRequestCode, null, this.mLaunchedFromUid);
                if (res != 0 && this.mResultTo != null && this.mRequestCode > 0) {
                    VActivityManager.get().sendCancelActivityResult(this.mResultTo, this.mResultWho, this.mRequestCode);
                }
            }
        }
    }

    void showAppDetails(ResolveInfo ri) {
        Intent in = new Intent().setAction(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1kpKAg2LmwjMC1sIxoCIQU6AmsINA5iHBpXIRUuGmMIMB19HwJBKiwuBmIbLFJnHw5B"))).setData(Uri.fromParts((String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Khg+OWUzJC1iAVRF")), (String)ri.activityInfo.packageName, null)).addFlags(524288);
        this.startActivity(in);
    }

    class LoadIconTask
    extends AsyncTask<DisplayResolveInfo, Void, DisplayResolveInfo> {
        LoadIconTask() {
        }

        protected DisplayResolveInfo doInBackground(DisplayResolveInfo ... params) {
            DisplayResolveInfo info = params[0];
            if (info.displayIcon == null) {
                info.displayIcon = ResolverActivity.this.loadIconForResolveInfo(info.ri);
            }
            return info;
        }

        protected void onPostExecute(DisplayResolveInfo info) {
            ResolverActivity.this.mAdapter.notifyDataSetChanged();
        }
    }

    class ItemLongClickListener
    implements AdapterView.OnItemLongClickListener {
        ItemLongClickListener() {
        }

        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id2) {
            ResolveInfo ri = ResolverActivity.this.mAdapter.resolveInfoForPosition(position);
            ResolverActivity.this.showAppDetails(ri);
            return true;
        }
    }

    private final class ResolveListAdapter
    extends BaseAdapter {
        private final Intent[] mInitialIntents;
        private final List<ResolveInfo> mBaseResolveList;
        private final Intent mIntent;
        private final int mLaunchedFromUid;
        private final LayoutInflater mInflater;
        List<DisplayResolveInfo> mList;
        List<ResolveInfo> mOrigResolveList;
        private ResolveInfo mLastChosen;
        private int mInitialHighlight = -1;

        public ResolveListAdapter(Context context, Intent intent, Intent[] initialIntents, List<ResolveInfo> rList, int launchedFromUid) {
            this.mIntent = new Intent(intent);
            this.mInitialIntents = initialIntents;
            this.mBaseResolveList = rList;
            this.mLaunchedFromUid = launchedFromUid;
            this.mInflater = (LayoutInflater)context.getSystemService(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ixg+J2owNAZsJAY2KD1bOWUzGgQ=")));
            this.mList = new ArrayList<DisplayResolveInfo>();
            this.rebuildList();
        }

        public void handlePackagesChanged() {
            int oldItemCount = this.getCount();
            this.rebuildList();
            this.notifyDataSetChanged();
            int newItemCount = this.getCount();
            if (newItemCount == 0) {
                ResolverActivity.this.finish();
            }
        }

        public int getInitialHighlight() {
            return this.mInitialHighlight;
        }

        private void rebuildList() {
            int N;
            List currentResolveList;
            this.mList.clear();
            if (this.mBaseResolveList != null) {
                currentResolveList = this.mBaseResolveList;
                this.mOrigResolveList = null;
            } else {
                currentResolveList = this.mOrigResolveList = ResolverActivity.this.mPm.queryIntentActivities(this.mIntent, 0x10000 | (ResolverActivity.this.mAlwaysUseOption ? 64 : 0));
            }
            if (currentResolveList != null && (N = currentResolveList.size()) > 0) {
                ResolveInfo ri;
                int i;
                ResolveInfo r0 = currentResolveList.get(0);
                for (i = 1; i < N; ++i) {
                    ResolveInfo ri2 = (ResolveInfo)currentResolveList.get(i);
                    if (r0.priority == ri2.priority && r0.isDefault == ri2.isDefault) continue;
                    while (i < N) {
                        if (this.mOrigResolveList == currentResolveList) {
                            this.mOrigResolveList = new ArrayList<ResolveInfo>(this.mOrigResolveList);
                        }
                        currentResolveList.remove(i);
                        --N;
                    }
                }
                if (N > 1) {
                    ResolveInfo.DisplayNameComparator rComparator = new ResolveInfo.DisplayNameComparator(ResolverActivity.this.mPm);
                    Collections.sort(currentResolveList, rComparator);
                }
                if (this.mInitialIntents != null) {
                    for (i = 0; i < this.mInitialIntents.length; ++i) {
                        Intent ii = this.mInitialIntents[i];
                        if (ii == null) continue;
                        ActivityInfo ai = ii.resolveActivityInfo(ResolverActivity.this.getPackageManager(), 0);
                        if (ai == null) {
                            VLog.w(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ij4uKWozHj5iASwRLy42MWUVLAZuAVRF")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Oz4fOGsVLAZjATwzLBgbOmkVNAVlNy8rLi4ACEsVSFo=")) + ii, new Object[0]);
                            continue;
                        }
                        ri = new ResolveInfo();
                        ri.activityInfo = ai;
                        if (ii instanceof LabeledIntent) {
                            LabeledIntent li = (LabeledIntent)ii;
                            ri.resolvePackageName = li.getSourcePackage();
                            ri.labelRes = li.getLabelResource();
                            ri.nonLocalizedLabel = li.getNonLocalizedLabel();
                            ri.icon = li.getIconResource();
                        }
                        this.mList.add(new DisplayResolveInfo(ri, ri.loadLabel(ResolverActivity.this.getPackageManager()), null, ii));
                    }
                }
                r0 = (ResolveInfo)currentResolveList.get(0);
                int start = 0;
                CharSequence r0Label = r0.loadLabel(ResolverActivity.this.mPm);
                ResolverActivity.this.mShowExtended = false;
                for (int i2 = 1; i2 < N; ++i2) {
                    CharSequence riLabel;
                    if (r0Label == null) {
                        r0Label = r0.activityInfo.packageName;
                    }
                    if ((riLabel = (ri = (ResolveInfo)currentResolveList.get(i2)).loadLabel(ResolverActivity.this.mPm)) == null) {
                        riLabel = ri.activityInfo.packageName;
                    }
                    if (riLabel.equals(r0Label)) continue;
                    this.processGroup(currentResolveList, start, i2 - 1, r0, r0Label);
                    r0 = ri;
                    r0Label = riLabel;
                    start = i2;
                }
                this.processGroup(currentResolveList, start, N - 1, r0, r0Label);
            }
        }

        private void processGroup(List<ResolveInfo> rList, int start, int end, ResolveInfo ro, CharSequence roLabel) {
            int num = end - start + 1;
            if (num == 1) {
                if (this.mLastChosen != null && this.mLastChosen.activityInfo.packageName.equals(ro.activityInfo.packageName) && this.mLastChosen.activityInfo.name.equals(ro.activityInfo.name)) {
                    this.mInitialHighlight = this.mList.size();
                }
                this.mList.add(new DisplayResolveInfo(ro, roLabel, null, null));
            } else {
                ResolverActivity.this.mShowExtended = true;
                boolean usePkg = false;
                CharSequence startApp = ro.activityInfo.applicationInfo.loadLabel(ResolverActivity.this.mPm);
                if (startApp == null) {
                    usePkg = true;
                }
                if (!usePkg) {
                    HashSet<CharSequence> duplicates = new HashSet<CharSequence>();
                    duplicates.add(startApp);
                    for (int j = start + 1; j <= end; ++j) {
                        ResolveInfo jRi = rList.get(j);
                        CharSequence jApp = jRi.activityInfo.applicationInfo.loadLabel(ResolverActivity.this.mPm);
                        if (jApp == null || duplicates.contains(jApp)) {
                            usePkg = true;
                            break;
                        }
                        duplicates.add(jApp);
                    }
                    duplicates.clear();
                }
                for (int k = start; k <= end; ++k) {
                    ResolveInfo add = rList.get(k);
                    if (this.mLastChosen != null && this.mLastChosen.activityInfo.packageName.equals(add.activityInfo.packageName) && this.mLastChosen.activityInfo.name.equals(add.activityInfo.name)) {
                        this.mInitialHighlight = this.mList.size();
                    }
                    if (usePkg) {
                        this.mList.add(new DisplayResolveInfo(add, roLabel, add.activityInfo.packageName, null));
                        continue;
                    }
                    this.mList.add(new DisplayResolveInfo(add, roLabel, add.activityInfo.applicationInfo.loadLabel(ResolverActivity.this.mPm), null));
                }
            }
        }

        public ResolveInfo resolveInfoForPosition(int position) {
            return this.mList.get((int)position).ri;
        }

        public Intent intentForPosition(int position) {
            DisplayResolveInfo dri = this.mList.get(position);
            Intent intent = new Intent(dri.origIntent != null ? dri.origIntent : this.mIntent);
            intent.addFlags(0x3000000);
            ActivityInfo ai = dri.ri.activityInfo;
            intent.setComponent(new ComponentName(ai.applicationInfo.packageName, ai.name));
            return intent;
        }

        public int getCount() {
            return this.mList.size();
        }

        public Object getItem(int position) {
            return this.mList.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = this.mInflater.inflate(R.layout.resolve_list_item, parent, false);
                ViewHolder holder = new ViewHolder(view);
                view.setTag((Object)holder);
                ViewGroup.LayoutParams lp = holder.icon.getLayoutParams();
                lp.width = lp.height = ResolverActivity.this.mIconSize;
            } else {
                view = convertView;
            }
            this.bindView(view, this.mList.get(position));
            return view;
        }

        private final void bindView(View view, DisplayResolveInfo info) {
            ViewHolder holder = (ViewHolder)view.getTag();
            holder.text.setText(info.displayLabel);
            if (ResolverActivity.this.mShowExtended) {
                holder.text2.setVisibility(0);
                holder.text2.setText(info.extendedInfo);
            } else {
                holder.text2.setVisibility(8);
            }
            if (info.displayIcon == null) {
                new LoadIconTask().execute(new DisplayResolveInfo[]{info});
            }
            holder.icon.setImageDrawable(info.displayIcon);
        }
    }

    private final class DisplayResolveInfo {
        ResolveInfo ri;
        CharSequence displayLabel;
        Drawable displayIcon;
        CharSequence extendedInfo;
        Intent origIntent;

        DisplayResolveInfo(ResolveInfo pri, CharSequence pLabel, CharSequence pInfo, Intent pOrigIntent) {
            this.ri = pri;
            this.displayLabel = pLabel;
            this.extendedInfo = pInfo;
            this.origIntent = pOrigIntent;
        }
    }

    static class ViewHolder {
        public TextView text;
        public TextView text2;
        public ImageView icon;

        public ViewHolder(View view) {
            this.text = (TextView)view.findViewById(R.id.text1);
            this.text2 = (TextView)view.findViewById(R.id.text2);
            this.icon = (ImageView)view.findViewById(R.id.icon);
        }
    }
}

