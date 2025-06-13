/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.accounts.AccountManagerCallback
 *  android.accounts.AccountManagerFuture
 *  android.accounts.AuthenticatorDescription
 *  android.accounts.AuthenticatorException
 *  android.accounts.OperationCanceledException
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.text.TextUtils
 *  android.util.Log
 *  android.view.View
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.ArrayAdapter
 *  android.widget.Button
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.TextView
 *  com.kook.librelease.R$id
 *  com.kook.librelease.R$layout
 *  com.kook.librelease.R$string
 */
package com.lody.virtual.client.stub;

import android.accounts.Account;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorDescription;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.kook.librelease.R;
import com.lody.virtual.StringFog;
import com.lody.virtual.client.ipc.VAccountManager;
import com.lody.virtual.client.stub.ChooseAccountTypeActivity;
import com.lody.virtual.helper.utils.VLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ChooseTypeAndAccountActivity
extends Activity
implements AccountManagerCallback<Bundle> {
    private static final String TAG = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Jgg2OWowNCZmHCg0Ki1fL2kgRVo="));
    private static final boolean DEBUG = false;
    public static final String EXTRA_ALLOWABLE_ACCOUNTS_ARRAYLIST = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggEDmowPDd9NFE/JwcqP28KGiZvHjBF"));
    public static final String EXTRA_ALLOWABLE_ACCOUNT_TYPES_STRING_ARRAY = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggEDmowPDd9NFE/JwcqP28KGiZvHCw0KQguDw=="));
    public static final String EXTRA_ADD_ACCOUNT_OPTIONS_BUNDLE = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggqPGMVLClgJzA2LBVfKmUzLCVlNDBF"));
    public static final String EXTRA_ADD_ACCOUNT_REQUIRED_FEATURES_STRING_ARRAY = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggqPGMVLClgJzA2LBYuPWogGi9sNygvJi4uO2YVLDVuASxF"));
    public static final String EXTRA_ADD_ACCOUNT_AUTH_TOKEN_TYPE_STRING = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LgcuLGUIMCVjJDA2JBgcKmkjSFo="));
    public static final String EXTRA_SELECTED_ACCOUNT = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uDmgVLAZiDgoRLy0qDWUjMAY="));
    @Deprecated
    public static final String EXTRA_ALWAYS_PROMPT_FOR_ACCOUNT = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggELWsaAgNpESw1KggmLmEVNARgATAqLD0uKmYVSFo="));
    public static final String EXTRA_DESCRIPTION_TEXT_OVERRIDE = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LRguKWswFi9hEQozKi0YAGkgFgZ9JCQgKS0MI2IaLFo="));
    public static final int REQUEST_NULL = 0;
    public static final int REQUEST_CHOOSE_TYPE = 1;
    public static final int REQUEST_ADD_ACCOUNT = 2;
    private static final String KEY_INSTANCE_STATE_PENDING_REQUEST = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KhguCGgFAiZiJSw/IwgMPWoKBlo="));
    private static final String KEY_INSTANCE_STATE_EXISTING_ACCOUNTS = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LQdfCW8wMC9gNDgRLy0qDWUjMAZsJ1RF"));
    private static final String KEY_INSTANCE_STATE_SELECTED_ACCOUNT_NAME = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uDmgVLAZiDgoRLy0qDWUjMAZ9NzgeLhhSVg=="));
    private static final String KEY_INSTANCE_STATE_SELECTED_ADD_ACCOUNT = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uDmgVLAZiDgoRKBc2E24FAiVvARo/"));
    private static final String KEY_INSTANCE_STATE_ACCOUNT_LIST = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmHFEzIy42Vg=="));
    public static final String KEY_USER_ID = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KQc2M28hAiw="));
    private static final int SELECTED_ITEM_NONE = -1;
    private Set<Account> mSetOfAllowableAccounts;
    private Set<String> mSetOfRelevantAccountTypes;
    private String mSelectedAccountName = null;
    private boolean mSelectedAddNewAccount = false;
    private String mDescriptionOverride;
    private ArrayList<Account> mAccounts;
    private int mPendingRequest = 0;
    private Parcelable[] mExistingAccounts = null;
    private int mSelectedItemIndex;
    private Button mOkButton;
    private int mCallingUserId;
    private boolean mDontShowPicker;

    public void onCreate(Bundle savedInstanceState) {
        Intent intent = this.getIntent();
        if (savedInstanceState != null) {
            this.mPendingRequest = savedInstanceState.getInt(KEY_INSTANCE_STATE_PENDING_REQUEST);
            this.mExistingAccounts = savedInstanceState.getParcelableArray(KEY_INSTANCE_STATE_EXISTING_ACCOUNTS);
            this.mSelectedAccountName = savedInstanceState.getString(KEY_INSTANCE_STATE_SELECTED_ACCOUNT_NAME);
            this.mSelectedAddNewAccount = savedInstanceState.getBoolean(KEY_INSTANCE_STATE_SELECTED_ADD_ACCOUNT, false);
            this.mAccounts = savedInstanceState.getParcelableArrayList(KEY_INSTANCE_STATE_ACCOUNT_LIST);
            this.mCallingUserId = savedInstanceState.getInt(KEY_USER_ID);
        } else {
            this.mPendingRequest = 0;
            this.mExistingAccounts = null;
            this.mCallingUserId = intent.getIntExtra(KEY_USER_ID, -1);
            Account selectedAccount = (Account)intent.getParcelableExtra(EXTRA_SELECTED_ACCOUNT);
            if (selectedAccount != null) {
                this.mSelectedAccountName = selectedAccount.name;
            }
        }
        VLog.v(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uDmgVLAZiDg08LwcqP28KGiZvVjwbLRgIJ0saGjZ5EVRF")) + this.mSelectedAccountName);
        this.mSetOfAllowableAccounts = this.getAllowableAccountSet(intent);
        this.mSetOfRelevantAccountTypes = this.getReleventAccountTypes(intent);
        this.mDescriptionOverride = intent.getStringExtra(EXTRA_DESCRIPTION_TEXT_OVERRIDE);
        this.mAccounts = this.getAcceptableAccountChoices(VAccountManager.get());
        if (this.mDontShowPicker) {
            super.onCreate(savedInstanceState);
            return;
        }
        if (this.mPendingRequest == 0 && this.mAccounts.isEmpty()) {
            this.setNonLabelThemeAndCallSuperCreate(savedInstanceState);
            if (this.mSetOfRelevantAccountTypes.size() == 1) {
                this.runAddAccountForAuthenticator(this.mSetOfRelevantAccountTypes.iterator().next());
            } else {
                this.startChooseAccountTypeActivity();
            }
        }
        String[] listItems = this.getListOfDisplayableOptions(this.mAccounts);
        this.mSelectedItemIndex = this.getItemIndexToSelect(this.mAccounts, this.mSelectedAccountName, this.mSelectedAddNewAccount);
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.choose_type_and_account);
        this.overrideDescriptionIfSupplied(this.mDescriptionOverride);
        this.populateUIAccountList(listItems);
        this.mOkButton = (Button)this.findViewById(16908314);
        this.mOkButton.setEnabled(this.mSelectedItemIndex != -1);
    }

    protected void onDestroy() {
        if (Log.isLoggable((String)TAG, (int)2)) {
            Log.v((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ji5fD2owLCtuEQYsKAUiDmkxQSloJwYwLC0qHX0jFixqNBo9LioqKWUxMD9vNCwbJi4fJ38FSFo=")));
        }
        super.onDestroy();
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_INSTANCE_STATE_PENDING_REQUEST, this.mPendingRequest);
        if (this.mPendingRequest == 2) {
            outState.putParcelableArray(KEY_INSTANCE_STATE_EXISTING_ACCOUNTS, this.mExistingAccounts);
        }
        if (this.mSelectedItemIndex != -1) {
            if (this.mSelectedItemIndex == this.mAccounts.size()) {
                outState.putBoolean(KEY_INSTANCE_STATE_SELECTED_ADD_ACCOUNT, true);
            } else {
                outState.putBoolean(KEY_INSTANCE_STATE_SELECTED_ADD_ACCOUNT, false);
                outState.putString(KEY_INSTANCE_STATE_SELECTED_ACCOUNT_NAME, this.mAccounts.get((int)this.mSelectedItemIndex).name);
            }
        }
        outState.putParcelableArrayList(KEY_INSTANCE_STATE_ACCOUNT_LIST, this.mAccounts);
    }

    public void onCancelButtonClicked(View view) {
        this.onBackPressed();
    }

    public void onOkButtonClicked(View view) {
        if (this.mSelectedItemIndex == this.mAccounts.size()) {
            this.startChooseAccountTypeActivity();
        } else if (this.mSelectedItemIndex != -1) {
            this.onAccountSelected(this.mAccounts.get(this.mSelectedItemIndex));
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Log.isLoggable((String)TAG, (int)2)) {
            if (data != null && data.getExtras() != null) {
                data.getExtras().keySet();
            }
            Bundle extras = data != null ? data.getExtras() : null;
            Log.v((String)TAG, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ji5fD2owLCtuEQYsKAUiDmkxQSloJwYwLC0qHX0jFixqNBo9LioqKWUxJDVsAR4hJQgMCn0wFiNlJyQZPBcMM28bLCViHjMd")) + requestCode + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186KmgaLBNgJAo/PghSVg==")) + resultCode + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186M2kKMAR9ASsd")) + extras + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PAhSVg=="))));
        }
        this.mPendingRequest = 0;
        if (resultCode == 0) {
            if (this.mAccounts.isEmpty()) {
                this.setResult(0);
                this.finish();
            }
            return;
        }
        if (resultCode == -1) {
            if (requestCode == 1) {
                String accountType;
                if (data != null && (accountType = data.getStringExtra(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmHwoZIxcMVg==")))) != null) {
                    this.runAddAccountForAuthenticator(accountType);
                    return;
                }
                Log.d((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ji5fD2owLCtuEQYsKAUiDmkxQSloJwYwLC0qHX0jFixqNBo9LioqKWUxJDVsAR4hJQgMCn0wFiNlJyQZODo6I2ojJCpgHjM8LBdeOmkVLCZrVjwsLT42KWYKRT95ETAyLD4fKHgaICZoHiw0Jj0MIGwwIzZlNwo0PhcMM28aNCthJw08LC0iL34zAjdlNzAgLAguIA==")));
            } else if (requestCode == 2) {
                String accountName = null;
                String accountType = null;
                if (data != null) {
                    accountName = data.getStringExtra(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LgcuLGULJCl9JB4vKj42Vg==")));
                    accountType = data.getStringExtra(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmHwoZIxcMVg==")));
                }
                if (accountName == null || accountType == null) {
                    Account[] currentAccounts = VAccountManager.get().getAccounts(this.mCallingUserId, null);
                    HashSet<Account> preExistingAccounts = new HashSet<Account>();
                    for (Parcelable parcelable : this.mExistingAccounts) {
                        preExistingAccounts.add((Account)parcelable);
                    }
                    for (Parcelable parcelable : currentAccounts) {
                        if (preExistingAccounts.contains(parcelable)) continue;
                        accountName = parcelable.name;
                        accountType = parcelable.type;
                        break;
                    }
                }
                if (accountName != null || accountType != null) {
                    this.setResultAndFinish(accountName, accountType);
                    return;
                }
            }
            Log.d((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ji5fD2owLCtuEQYsKAUiDmkxQSloJwYwLC0qHX0jFixqNBo9LioqKWUxJDVsAR4hJQgMCn0wFiNlJyQZODo6I2ojJCpgHjM8LBdeOmkVLCZrVjwsLggqJ2JTOCRpJCweIy4qCnVSICRvJygZJAdfI28KAj9+NBo/LQQ6KmgaJAViASggPxg6OWoJTSloARoqLhgEJ2IVSFo=")));
        }
        if (Log.isLoggable((String)TAG, (int)2)) {
            Log.v((String)TAG, (String)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ji5fD2owLCtuEQYsKAUiDmkxQSloJwYwLC0qHX0jFixqNBo9LioqKWUxJDVsAR4hJQgMCn0wFiNlJyQZODo6OWsVBiliDlE/KBhSVg==")));
        }
        this.setResult(0);
        this.finish();
    }

    protected void runAddAccountForAuthenticator(String type) {
        if (Log.isLoggable((String)TAG, (int)2)) {
            Log.v((String)TAG, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Kj0uCGMVMCxlDig5Ki4MDmUxHiVsNTgwKghfJ2AzFixpJCQ9KQdePngVSFo=")) + type));
        }
        Bundle options = this.getIntent().getBundleExtra(EXTRA_ADD_ACCOUNT_OPTIONS_BUNDLE);
        String[] requiredFeatures = this.getIntent().getStringArrayExtra(EXTRA_ADD_ACCOUNT_REQUIRED_FEATURES_STRING_ARRAY);
        String authTokenType = this.getIntent().getStringExtra(EXTRA_ADD_ACCOUNT_AUTH_TOKEN_TYPE_STRING);
        VAccountManager.get().addAccount(this.mCallingUserId, type, authTokenType, requiredFeatures, options, null, this, null);
    }

    public void run(AccountManagerFuture<Bundle> accountManagerFuture) {
        try {
            Bundle accountManagerResult = (Bundle)accountManagerFuture.getResult();
            Intent intent = (Intent)accountManagerResult.getParcelable(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAgcLGgVBgY=")));
            if (intent != null) {
                this.mPendingRequest = 2;
                this.mExistingAccounts = VAccountManager.get().getAccounts(this.mCallingUserId, null);
                intent.setFlags(intent.getFlags() & 0xEFFFFFFF);
                this.startActivityForResult(intent, 2);
                return;
            }
        }
        catch (OperationCanceledException e) {
            this.setResult(0);
            this.finish();
            return;
        }
        catch (AuthenticatorException | IOException e) {
            e.printStackTrace();
        }
        Bundle bundle = new Bundle();
        bundle.putString(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LQcMKmowFg1iASgpLwc6PQ==")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LQcMKmowESh9JB43KggMDmwjAjdvER4bLjo6MWMFFit5ESwuLBcEJ2wzSFo=")));
        this.setResult(-1, new Intent().putExtras(bundle));
        this.finish();
    }

    private void setNonLabelThemeAndCallSuperCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 21) {
            this.setTheme(16974396);
        } else {
            this.setTheme(16973941);
        }
        super.onCreate(savedInstanceState);
    }

    private void onAccountSelected(Account account) {
        Log.d((String)TAG, (String)(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uDmgVLAZiDg08LwcqP28KGiZvVjxF")) + account));
        this.setResultAndFinish(account.name, account.type);
    }

    private void setResultAndFinish(String accountName, String accountType) {
        Bundle bundle = new Bundle();
        bundle.putString(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LgcuLGULJCl9JB4vKj42Vg==")), accountName);
        bundle.putString(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lgg2OWowNCZmHwoZIxcMVg==")), accountType);
        this.setResult(-1, new Intent().putExtras(bundle));
        VLog.v(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ji5fD2owLCtuEQYsKAUiDmkxQSloJwYwLC0qHX0jFixqNBo9LioqD2sKMABoHjAaJhgMAmwwLFVsJywwKi5eInsKLCtgHjA5LBcMPn4zQSloJwYwLC0pJA==")) + accountName + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("M186Vg==")) + accountType);
        this.finish();
    }

    private void startChooseAccountTypeActivity() {
        Intent intent = new Intent((Context)this, ChooseAccountTypeActivity.class);
        intent.setFlags(524288);
        intent.putExtra(EXTRA_ALLOWABLE_ACCOUNT_TYPES_STRING_ARRAY, this.getIntent().getStringArrayExtra(EXTRA_ALLOWABLE_ACCOUNT_TYPES_STRING_ARRAY));
        intent.putExtra(EXTRA_ADD_ACCOUNT_OPTIONS_BUNDLE, this.getIntent().getBundleExtra(EXTRA_ADD_ACCOUNT_OPTIONS_BUNDLE));
        intent.putExtra(EXTRA_ADD_ACCOUNT_REQUIRED_FEATURES_STRING_ARRAY, this.getIntent().getStringArrayExtra(EXTRA_ADD_ACCOUNT_REQUIRED_FEATURES_STRING_ARRAY));
        intent.putExtra(EXTRA_ADD_ACCOUNT_AUTH_TOKEN_TYPE_STRING, this.getIntent().getStringExtra(EXTRA_ADD_ACCOUNT_AUTH_TOKEN_TYPE_STRING));
        intent.putExtra(KEY_USER_ID, this.mCallingUserId);
        this.startActivityForResult(intent, 1);
        this.mPendingRequest = 1;
    }

    private int getItemIndexToSelect(ArrayList<Account> accounts, String selectedAccountName, boolean selectedAddNewAccount) {
        if (selectedAddNewAccount) {
            return accounts.size();
        }
        for (int i = 0; i < accounts.size(); ++i) {
            if (!accounts.get((int)i).name.equals(selectedAccountName)) continue;
            return i;
        }
        return -1;
    }

    private String[] getListOfDisplayableOptions(ArrayList<Account> accounts) {
        String[] listItems = new String[accounts.size() + 1];
        for (int i = 0; i < accounts.size(); ++i) {
            listItems[i] = accounts.get((int)i).name;
        }
        listItems[accounts.size()] = this.getResources().getString(R.string.add_account_button_label);
        return listItems;
    }

    private ArrayList<Account> getAcceptableAccountChoices(VAccountManager accountManager) {
        Account[] accounts = accountManager.getAccounts(this.mCallingUserId, null);
        ArrayList<Account> accountsToPopulate = new ArrayList<Account>(accounts.length);
        for (Account account : accounts) {
            if (this.mSetOfAllowableAccounts != null && !this.mSetOfAllowableAccounts.contains(account) || this.mSetOfRelevantAccountTypes != null && !this.mSetOfRelevantAccountTypes.contains(account.type)) continue;
            accountsToPopulate.add(account);
        }
        return accountsToPopulate;
    }

    private Set<String> getReleventAccountTypes(Intent intent) {
        HashSet<String> setOfRelevantAccountTypes;
        String[] allowedAccountTypes = intent.getStringArrayExtra(EXTRA_ALLOWABLE_ACCOUNT_TYPES_STRING_ARRAY);
        AuthenticatorDescription[] descs = VAccountManager.get().getAuthenticatorTypes(this.mCallingUserId);
        HashSet<String> supportedAccountTypes = new HashSet<String>(descs.length);
        for (AuthenticatorDescription desc : descs) {
            supportedAccountTypes.add(desc.type);
        }
        if (allowedAccountTypes != null) {
            setOfRelevantAccountTypes = new HashSet();
            Collections.addAll(setOfRelevantAccountTypes, allowedAccountTypes);
            setOfRelevantAccountTypes.retainAll(supportedAccountTypes);
        } else {
            setOfRelevantAccountTypes = supportedAccountTypes;
        }
        return setOfRelevantAccountTypes;
    }

    private Set<Account> getAllowableAccountSet(Intent intent) {
        HashSet<Account> setOfAllowableAccounts = null;
        ArrayList validAccounts = intent.getParcelableArrayListExtra(EXTRA_ALLOWABLE_ACCOUNTS_ARRAYLIST);
        if (validAccounts != null) {
            setOfAllowableAccounts = new HashSet<Account>(validAccounts.size());
            for (Parcelable parcelable : validAccounts) {
                setOfAllowableAccounts.add((Account)parcelable);
            }
        }
        return setOfAllowableAccounts;
    }

    private void overrideDescriptionIfSupplied(String descriptionOverride) {
        TextView descriptionView = (TextView)this.findViewById(R.id.description);
        if (!TextUtils.isEmpty((CharSequence)descriptionOverride)) {
            descriptionView.setText((CharSequence)descriptionOverride);
        } else {
            descriptionView.setVisibility(8);
        }
    }

    private void populateUIAccountList(String[] listItems) {
        ListView list = (ListView)this.findViewById(16908298);
        list.setAdapter((ListAdapter)new ArrayAdapter((Context)this, 17367055, (Object[])listItems));
        list.setChoiceMode(1);
        list.setItemsCanFocus(false);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View v, int position, long id2) {
                ChooseTypeAndAccountActivity.this.mSelectedItemIndex = position;
                ChooseTypeAndAccountActivity.this.mOkButton.setEnabled(true);
            }
        });
        if (this.mSelectedItemIndex != -1) {
            list.setItemChecked(this.mSelectedItemIndex, true);
        }
    }
}

