package com.radio.aidlandroidjava.service;

import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;

import com.radio.aidlandroidjava.IMyAidlInterface;
import com.radio.aidlandroidjava.callbacks.IFetchUpdatedListCallback;
import com.radio.aidlandroidjava.model.DataModel;

public class MyService extends IMyAidlInterface.Stub {
    private static final int TIME_OUT = 1000;
    private static final String TAG = "MyService";

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
                dataModel.getList().forEach(s -> Log.e(TAG, s));

                for (int i = 0; i < dataModel.getList().size(); i++) {
                    dataModel.getList().set(i, "" + i);
                }

                iFetchUpdatedListCallback.onListUpdated(dataModel);
            } catch (RemoteException e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
            }
        }, TIME_OUT);

    }
}
