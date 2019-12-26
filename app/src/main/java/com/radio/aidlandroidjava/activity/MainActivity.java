package com.radio.aidlandroidjava.activity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.radio.aidlandroidjava.IMyAidlInterface;
import com.radio.aidlandroidjava.R;
import com.radio.aidlandroidjava.callbacks.IFetchUpdatedListCallback;
import com.radio.aidlandroidjava.model.DataModel;
import com.radio.aidlandroidjava.service.MyService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private IMyAidlInterface mIMyAidlInterface;
    private IFetchUpdatedListCallback mIFetchUpdatedListCallback =
            new IFetchUpdatedListCallback.Stub() {
                @Override
                public void onListUpdated(DataModel dataModel) {
                    Log.v(TAG, dataModel.toString());
                }

                @Override
                public IBinder asBinder() {
                    return this;
                }
            };

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mIMyAidlInterface = IMyAidlInterface.Stub.asInterface(iBinder);
            Toast.makeText(MainActivity.this, "Service connected.", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mIMyAidlInterface = null;
            Toast.makeText(MainActivity.this, "Service disconnected.", Toast.LENGTH_SHORT).show();
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        bindService(new Intent(this, MyService.class).setPackage(
                "com.radio.aidlandroidjava.service"), mServiceConnection, Service.BIND_AUTO_CREATE);
    }

    public void onButtonClick(View view) {
        try {
            if (mIMyAidlInterface != null) {
                String value = mIMyAidlInterface.getMessage();
                List<String> listArray = new ArrayList<>();
                listArray.add("10");
                listArray.add("11");
                listArray.add("12");
                DataModel dataModel = new DataModel("Data", 12, listArray, true);
                mIMyAidlInterface.fetchUpdatedList(dataModel,
                        mIFetchUpdatedListCallback);
                ((TextView) findViewById(R.id.result)).setText(value);
                Log.v(TAG, value);
            } else {
                Log.v(TAG, "AIDL not initialized.");
            }
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(mServiceConnection);
    }
}
