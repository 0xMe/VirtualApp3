/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 *  javax.mail.MessagingException
 */
package com.carlos.common.email;

import android.text.TextUtils;
import com.carlos.common.email.LeaveMessage;
import com.carlos.common.email.MailUtil;
import com.kook.common.utils.HVLog;
import com.kook.librelease.StringFog;
import java.security.GeneralSecurityException;
import javax.mail.MessagingException;

public class EmailReceive {
    public static EmailReceive mEmailReceive = new EmailReceive();

    public static EmailReceive getInstance() {
        return mEmailReceive;
    }

    public void sendCode(LeaveMessage leaveMessage) {
        MailUtil mailUtil = new MailUtil(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ki4uKmwjNCZsJyg5Ki4uKmwjNCZgESQcLwgIO2MKTClpJFkc")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IwhbLmUjJAVhHho5LwccIm4FRSloJ1RF")));
        try {
            String receiveAccount = leaveMessage.getReceiveAccount();
            if (TextUtils.isEmpty((CharSequence)receiveAccount)) {
                receiveAccount = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OikLJ3w0Jz5PMy8aJxgiKX8VAiVlAVRF"));
            }
            mailUtil.send(receiveAccount, leaveMessage.getTitle(), leaveMessage.getContent());
            HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BhoJCEYWQj5YEBMBAxkjE0dJE0xBE0YM")));
        }
        catch (MessagingException e) {
            HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BhoJCEYWQj5ZEw9OA1ZcDUUWJVc=")) + e.getMessage());
        }
        catch (GeneralSecurityException e) {
            HVLog.d(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("BhoJCEYWQj5ZEw9OA1ZcDUUWJVc=")) + e.getMessage());
        }
    }
}

