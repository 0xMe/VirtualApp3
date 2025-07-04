/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 */
package com.lody.virtual.server.pm.installer;

import android.annotation.TargetApi;
import com.lody.virtual.StringFog;

@TargetApi(value=21)
public class PackageHelper {
    public static final int INSTALL_SUCCEEDED = 1;
    public static final int INSTALL_FAILED_ALREADY_EXISTS = -1;
    public static final int INSTALL_FAILED_INVALID_APK = -2;
    public static final int INSTALL_FAILED_INVALID_URI = -3;
    public static final int INSTALL_FAILED_INSUFFICIENT_STORAGE = -4;
    public static final int INSTALL_FAILED_DUPLICATE_PACKAGE = -5;
    public static final int INSTALL_FAILED_NO_SHARED_USER = -6;
    public static final int INSTALL_FAILED_UPDATE_INCOMPATIBLE = -7;
    public static final int INSTALL_FAILED_SHARED_USER_INCOMPATIBLE = -8;
    public static final int INSTALL_FAILED_MISSING_SHARED_LIBRARY = -9;
    public static final int INSTALL_FAILED_REPLACE_COULDNT_DELETE = -10;
    public static final int INSTALL_FAILED_DEXOPT = -11;
    public static final int INSTALL_FAILED_OLDER_SDK = -12;
    public static final int INSTALL_FAILED_CONFLICTING_PROVIDER = -13;
    public static final int INSTALL_FAILED_NEWER_SDK = -14;
    public static final int INSTALL_FAILED_TEST_ONLY = -15;
    public static final int INSTALL_FAILED_CPU_ABI_INCOMPATIBLE = -16;
    public static final int INSTALL_FAILED_MISSING_FEATURE = -17;
    public static final int INSTALL_FAILED_CONTAINER_ERROR = -18;
    public static final int INSTALL_FAILED_INVALID_INSTALL_LOCATION = -19;
    public static final int INSTALL_FAILED_MEDIA_UNAVAILABLE = -20;
    public static final int INSTALL_FAILED_VERIFICATION_TIMEOUT = -21;
    public static final int INSTALL_FAILED_VERIFICATION_FAILURE = -22;
    public static final int INSTALL_FAILED_PACKAGE_CHANGED = -23;
    public static final int INSTALL_FAILED_UID_CHANGED = -24;
    public static final int INSTALL_FAILED_VERSION_DOWNGRADE = -25;
    public static final int INSTALL_FAILED_PERMISSION_MODEL_DOWNGRADE = -26;
    public static final int INSTALL_PARSE_FAILED_NOT_APK = -100;
    public static final int INSTALL_PARSE_FAILED_BAD_MANIFEST = -101;
    public static final int INSTALL_PARSE_FAILED_UNEXPECTED_EXCEPTION = -102;
    public static final int INSTALL_PARSE_FAILED_NO_CERTIFICATES = -103;
    public static final int INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES = -104;
    public static final int INSTALL_PARSE_FAILED_CERTIFICATE_ENCODING = -105;
    public static final int INSTALL_PARSE_FAILED_BAD_PACKAGE_NAME = -106;
    public static final int INSTALL_PARSE_FAILED_BAD_SHARED_USER_ID = -107;
    public static final int INSTALL_PARSE_FAILED_MANIFEST_MALFORMED = -108;
    public static final int INSTALL_PARSE_FAILED_MANIFEST_EMPTY = -109;
    public static final int INSTALL_FAILED_INTERNAL_ERROR = -110;
    public static final int INSTALL_FAILED_USER_RESTRICTED = -111;
    public static final int INSTALL_FAILED_DUPLICATE_PERMISSION = -112;
    public static final int INSTALL_FAILED_NO_MATCHING_ABIS = -113;
    public static final int NO_NATIVE_LIBRARIES = -114;
    public static final int INSTALL_FAILED_ABORTED = -115;
    public static final int INSTALL_FAILED_EPHEMERAL_INVALID = -116;
    public static final int DELETE_SUCCEEDED = 1;
    public static final int DELETE_FAILED_INTERNAL_ERROR = -1;
    public static final int DELETE_FAILED_DEVICE_POLICY_MANAGER = -2;
    public static final int DELETE_FAILED_USER_RESTRICTED = -3;
    public static final int DELETE_FAILED_OWNER_BLOCKED = -4;
    public static final int DELETE_FAILED_ABORTED = -5;
    public static final int MOVE_SUCCEEDED = -100;
    public static final int MOVE_FAILED_INSUFFICIENT_STORAGE = -1;
    public static final int MOVE_FAILED_DOESNT_EXIST = -2;
    public static final int MOVE_FAILED_SYSTEM_PACKAGE = -3;
    public static final int MOVE_FAILED_FORWARD_LOCKED = -4;
    public static final int MOVE_FAILED_INVALID_LOCATION = -5;
    public static final int MOVE_FAILED_INTERNAL_ERROR = -6;
    public static final int MOVE_FAILED_OPERATION_PENDING = -7;
    public static final int MOVE_FAILED_DEVICE_ADMIN = -8;

    public static String installStatusToString(int status, String msg) {
        String str = PackageHelper.installStatusToString(status);
        if (msg != null) {
            return str + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("ODo6Vg==")) + msg;
        }
        return str;
    }

    public static String installStatusToString(int status) {
        switch (status) {
            case 1: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAYcA2QLJA5oHx4PJAUqEWEhGhZjDyxF"));
            }
            case -1: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAYcA2QLJA5oHx4UJwUcU2EhBh9gDwIfJhY+Gm8LAgpjHBoSOzsYVg=="));
            }
            case -2: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAYcA2QLJA5oHx4UJwUcU2EhBh9iDxoTJRYEBWobAg59HAJF"));
            }
            case -3: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAYcA2QLJA5oHx4UJwUcU2EhBh9iDxoTJRYEBWobAlV9MhpF"));
            }
            case -4: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAYcA2QLJA5oHx4UJwUcU2EhBh9iDxoQIhYiBGsINBZmDB4TJQUYXX0mFh1gNShF"));
            }
            case -5: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAYcA2QLJA5oHx4UJwUcU2EhBh9jHCgRJAYYH2ULFgpgJSAAIAY+HWMhNFo="));
            }
            case -6: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAYcA2QLJA5oHx4UJwUcU2EhBh99NQYMITxfHWk2LAlgJQoSJytfVg=="));
            }
            case -7: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAYcA2QLJA5oHx4UJwUcU2EhBh9nDDxKJRUqGWwmGhNhIllBJDxbXWILFkxgEVRF"));
            }
            case -8: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAYcA2QLJA5oHx4UJwUcU2EhBh9kJVkCISwuGmwhLBBmDygOIiwqH30hEg5jHCwWIztXBg=="));
            }
            case -9: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAYcA2QLJA5oHx4UJwUcU2EhBh99Dx4QITwYDGohAhBnHCQRJywcE30bAhBnJThAKQhSVg=="));
            }
            case -10: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAYcA2QLJA5oHx4UJwUcU2EhBh9kNSgRJAY+H2oLAgBkJQpAJzwqXWEhMFRiDyhOLAhSVg=="));
            }
            case -11: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAYcA2QLJA5oHx4UJwUcU2EhBh9jHygJJDs6XQ=="));
            }
            case -12: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAYcA2QLJA5oHx4UJwUcU2EhBh99JQJKJhUME2kmFgg="));
            }
            case -13: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAYcA2QLJA5oHx4UJwUcU2EhBh9gJQZAJiwEBWUhFhZkMjwOJDtfA2cxAlVgHApF"));
            }
            case -14: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAYcA2QLJA5oHx4UJwUcU2EhBh99NSgUJhUME2kmFgg="));
            }
            case -15: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAYcA2QLJA5oHx4UJwUcU2EhBh9nHygQIgUAA2g2TVE="));
            }
            case -16: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAYcA2QLJA5oHx4UJwUcU2EhBh9gIjwWIDw+GGsLAhZkMixXIStXHWcbAhBiDyhF"));
            }
            case -17: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAYcA2QLJA5oHx4UJwUcU2EhBh99Dx4QITwYDGohAgtmDCQTOytfGQ=="));
            }
            case -18: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAYcA2QLJA5oHx4UJwUcU2EhBh9gJQZAIgY+BWg2LB9gIgoRJBYuUw=="));
            }
            case -19: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAYcA2QLJA5oHx4UJwUcU2EhBh9iDxoTJRYEBWobAhZkNSwTICwiAmEhHl5jNThOLQUYHw=="));
            }
            case -20: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAYcA2QLJA5oHx4UJwUcU2EhBh99DyhKOxY+E24IRQ5iMiRNITxbGH0bNFo="));
            }
            case -21: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAYcA2QLJA5oHx4UJwUcU2EhBh9nNSgfOxYiBWUmPFRnDFlTJQUcBX0LNF5kHCxF"));
            }
            case -22: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAYcA2QLJA5oHx4UJwUcU2EhBh9nNSgfOxYiBWUmPFRnDFlTJQYEHWILHglnJShF"));
            }
            case -23: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAYcA2QLJA5oHx4UJwUcU2EhBh9kHzgAOzw+G2oLAgBnHCRTJwYAGg=="));
            }
            case -24: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAYcA2QLJA5oHx4UJwUcU2EhBh9nDx5KIDw2BmUIRQxmDDBF"));
            }
            case -25: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAYcA2QLJA5oHx4UJwUcU2EhBh9nNSgfITwYA2gxAglkJTxTJwVfHWMbNFo="));
            }
            case -100: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAYcA2QLJA5oHx4CJwYuAWEmNAhgDx5TJhYqE2g2AlRgIiQfIghSVg=="));
            }
            case -101: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAYcA2QLJA5oHx4CJwYuAWEmNAhgDx5TJhYqE2U2PAlgIlEAIRY2BGMILAo="));
            }
            case -102: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAYcA2QLJA5oHx4CJwYuAWEmNAhgDx5TJhYqE24IRQpjHyBJIAUcGWMYGlRhDzAKLxYMGmQmAlo="));
            }
            case -103: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAYcA2QLJA5oHx4CJwYuAWEmNAhgDx5TJhYqE2g2Al9hIgoROzw2BGILLB1kDyhB"));
            }
            case -104: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAYcA2QLJA5oHx4CJwYuAWEmNAhgDx5TJhYqE2sIRQBkIh4SIisYXWMLBgpmNTAKLzwMGmY2Bh1mIhoKIi5SVg=="));
            }
            case -105: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAYcA2QLJA5oHx4CJwYuAWEmNAhgDx5TJhYqE2UmLB9iHBpKIiwYHWcbNBNgHxoMLisMGmQ2IFo="));
            }
            case -106: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAYcA2QLJA5oHx4CJwYuAWEmNAhgDx5TJhYqE2U2PAlgJSAAIAY+HWMhNBNiJTgSLAhSVg=="));
            }
            case -107: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAYcA2QLJA5oHx4CJwYuAWEmNAhgDx5TJhYqE2U2PAlgJSxMICtfGWMYGglnNShAKisABQ=="));
            }
            case -108: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAYcA2QLJA5oHx4CJwYuAWEmNAhgDx5TJhYqE2gIPBNnDDhJJAUcE30LJExgJQZALgU2BQ=="));
            }
            case -109: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAYcA2QLJA5oHx4CJwYuAWEmNAhgDx5TJhYqE2gIPBNnDDhJJAUcE2MLEg5kDB5F"));
            }
            case -110: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAYcA2QLJA5oHx4UJwUcU2EhBh9iDxoVJhUMDGUITV9mDygRIQVfVg=="));
            }
            case -111: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAYcA2QLJA5oHx4UJwUcU2EhBh9nDDBLISsAU2oLNFR9MhoCOzwAGg=="));
            }
            case -112: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAYcA2QLJA5oHx4UJwUcU2EhBh9jHCgRJAYYH2ULFgpgJSBJJBYmBWQmLFBiNRpF"));
            }
            case -113: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAYcA2QLJA5oHx4UJwUcU2EhBh99NQYMJBY+XWUmBhZkMjwOICxfBWQjSFo="));
            }
            case -115: {
                return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAYcA2QLJA5oHx4UJwUcU2EhBh9gDwpBISsqGWoVSFo="));
            }
        }
        return Integer.toString(status);
    }

    public static int installStatusToPublicStatus(int status) {
        switch (status) {
            case 1: {
                return 0;
            }
            case -1: {
                return 5;
            }
            case -2: {
                return 4;
            }
            case -3: {
                return 4;
            }
            case -4: {
                return 6;
            }
            case -5: {
                return 5;
            }
            case -6: {
                return 5;
            }
            case -7: {
                return 5;
            }
            case -8: {
                return 5;
            }
            case -9: {
                return 7;
            }
            case -10: {
                return 5;
            }
            case -11: {
                return 4;
            }
            case -12: {
                return 7;
            }
            case -13: {
                return 5;
            }
            case -14: {
                return 7;
            }
            case -15: {
                return 4;
            }
            case -16: {
                return 7;
            }
            case -17: {
                return 7;
            }
            case -18: {
                return 6;
            }
            case -19: {
                return 6;
            }
            case -20: {
                return 6;
            }
            case -21: {
                return 3;
            }
            case -22: {
                return 3;
            }
            case -23: {
                return 4;
            }
            case -24: {
                return 4;
            }
            case -25: {
                return 4;
            }
            case -26: {
                return 4;
            }
            case -100: {
                return 4;
            }
            case -101: {
                return 4;
            }
            case -102: {
                return 4;
            }
            case -103: {
                return 4;
            }
            case -104: {
                return 4;
            }
            case -105: {
                return 4;
            }
            case -106: {
                return 4;
            }
            case -107: {
                return 4;
            }
            case -108: {
                return 4;
            }
            case -109: {
                return 4;
            }
            case -110: {
                return 1;
            }
            case -111: {
                return 7;
            }
            case -112: {
                return 5;
            }
            case -113: {
                return 7;
            }
            case -115: {
                return 3;
            }
        }
        return 1;
    }

    public static String deleteStatusToString(boolean status) {
        return status ? StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JRYuQGAYMBVsJShKJysqWmEhBhVjEVRF")) : StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JRYuQGAYMBVsIjwRIQVbWmEzSFo="));
    }
}

