package com.radio.aidlandroidjava.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import com.radio.aidlandroidjava.IMyAidlInterface;
import com.radio.aidlandroidjava.callbacks.IFetchUpdatedListCallback;
import com.radio.aidlandroidjava.model.DataModel;

public class MyService extends Service {
    private static final int TIME_OUT = 1000;
    private static final String TAG = "MyService";

    private IMyAidlInterface.Stub mIMyAidlInterface = new IMyAidlInterface.Stub() {
        @Override
        public String getMessage() {
            return "Hello";
        }

        @Override
        public void setDataModel(DataModel dataModel) {
            Log.e(TAG, dataModel.toString());
        }

        @Override
        public void fetchUpdatedList(DataModel dataModel,
                IFetchUpdatedListCallback iFetchUpdatedListCallback) {
            new Handler().postDelayed(() -> {
                try {
                    for (int i = 0; i < dataModel.getList().size(); i++) {
                        dataModel.getList().set(i, "" + i);
                    }

                    iFetchUpdatedListCallback.onListUpdated(dataModel);
                } catch (RemoteException e) {
                    Log.e(TAG, e.getLocalizedMessage(), e);
                }
            }, TIME_OUT);

        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mIMyAidlInterface;
    }
}
