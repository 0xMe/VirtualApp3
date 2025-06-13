/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package android.accounts;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.lody.virtual.StringFog;

public interface IAccountAuthenticatorResponse
extends IInterface {
    public void onResult(Bundle var1) throws RemoteException;

    public void onRequestContinued() throws RemoteException;

    public void onError(int var1, String var2) throws RemoteException;

    public static abstract class Stub
    extends Binder
    implements IAccountAuthenticatorResponse {
        private static final String DESCRIPTOR = StringFog.decrypt("EgsWBAoHO10CDBEfHAEaAEs7NwYNMAYNGzMFHQcLHREbFQQaMAExCgEABgEdFg==");
        static final int TRANSACTION_onResult = 1;
        static final int TRANSACTION_onRequestContinued = 2;
        static final int TRANSACTION_onError = 3;

        public Stub() {
            this.attachInterface(this, StringFog.decrypt("EgsWBAoHO10CDBEfHAEaAEs7NwYNMAYNGzMFHQcLHREbFQQaMAExCgEABgEdFg=="));
        }

        public static IAccountAuthenticatorResponse asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && iin instanceof IAccountAuthenticatorResponse) {
                return (IAccountAuthenticatorResponse)iin;
            }
            return new Proxy(obj);
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            String descriptor = DESCRIPTOR;
            switch (code) {
                case 1598968902: {
                    reply.writeString(descriptor);
                    return true;
                }
                case 1: {
                    data.enforceInterface(descriptor);
                    Bundle _arg0 = 0 != data.readInt() ? (Bundle)Bundle.CREATOR.createFromParcel(data) : null;
                    this.onResult(_arg0);
                    reply.writeNoException();
                    return true;
                }
                case 2: {
                    data.enforceInterface(descriptor);
                    this.onRequestContinued();
                    reply.writeNoException();
                    return true;
                }
                case 3: {
                    data.enforceInterface(descriptor);
                    int _arg0 = data.readInt();
                    String _arg1 = data.readString();
                    this.onError(_arg0, _arg1);
                    reply.writeNoException();
                    return true;
                }
            }
            return super.onTransact(code, data, reply, flags);
        }

        public static boolean setDefaultImpl(IAccountAuthenticatorResponse impl) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException(StringFog.decrypt("AAAGMgAIPgYPGzsdGQNGWkURFwkCOhdDGwUZCgo="));
            }
            if (impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IAccountAuthenticatorResponse getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        private static class Proxy
        implements IAccountAuthenticatorResponse {
            private IBinder mRemote;
            public static IAccountAuthenticatorResponse sDefaultImpl;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return StringFog.decrypt("EgsWBAoHO10CDBEfHAEaAEs7NwYNMAYNGzMFHQcLHREbFQQaMAExCgEABgEdFg==");
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public void onResult(Bundle value) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(StringFog.decrypt("EgsWBAoHO10CDBEfHAEaAEs7NwYNMAYNGzMFHQcLHREbFQQaMAExCgEABgEdFg=="));
                    if (value != null) {
                        _data.writeInt(1);
                        value.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onResult(value);
                        return;
                    }
                    _reply.readException();
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public void onRequestContinued() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(StringFog.decrypt("EgsWBAoHO10CDBEfHAEaAEs7NwYNMAYNGzMFHQcLHREbFQQaMAExCgEABgEdFg=="));
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRequestContinued();
                        return;
                    }
                    _reply.readException();
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public void onError(int errorCode, String errorMessage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(StringFog.decrypt("EgsWBAoHO10CDBEfHAEaAEs7NwYNMAYNGzMFHQcLHREbFQQaMAExCgEABgEdFg=="));
                    _data.writeInt(errorCode);
                    _data.writeString(errorMessage);
                    boolean _status = this.mRemote.transact(3, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onError(errorCode, errorMessage);
                        return;
                    }
                    _reply.readException();
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }

    public static class Default
    implements IAccountAuthenticatorResponse {
        @Override
        public void onResult(Bundle value) throws RemoteException {
        }

        @Override
        public void onRequestContinued() throws RemoteException {
        }

        @Override
        public void onError(int errorCode, String errorMessage) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }
}

