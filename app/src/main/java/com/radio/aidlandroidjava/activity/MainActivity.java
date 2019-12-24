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

import androidx.appcompat.app.AppCompatActivity;

import com.radio.aidlandroidjava.IMyAidlInterface;
import com.radio.aidlandroidjava.R;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private IMyAidlInterface mIMyAidlInterface;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mIMyAidlInterface = IMyAidlInterface.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mIMyAidlInterface = null;
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
        Intent intent = new Intent();
        intent.setAction("service.aidl");
        intent.setPackage("com.radio.aidlandroidjava.service");

        bindService(intent, mServiceConnection, Service.BIND_AUTO_CREATE);
    }

    public void onButtonClick(View view) {
        try {
            String value = mIMyAidlInterface.getMessage();
            Log.v(TAG, value);
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
    }
}
