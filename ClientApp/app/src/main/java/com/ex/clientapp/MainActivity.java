package com.ex.clientapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Button;

import com.ex.aidlserver.IAIDLColorInterface;

public class MainActivity extends AppCompatActivity {

    IAIDLColorInterface iAIDLColorService;
    private static final String TAG ="MainActivity";
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iAIDLColorService = IAIDLColorInterface.Stub.asInterface(service);
            Log.d(TAG, "Remote config Service Connected!!");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);

        Intent intent = new Intent("AIDLService");
        intent.setPackage("com.ex.aidlserver");

        bindService(intent, mConnection, BIND_AUTO_CREATE);

        //Create an onclick listener to button
        Log.d(TAG, "bindservice called");
        Button b = findViewById(R.id.button);

        b.setOnClickListener(view -> {
            try {
                int color = iAIDLColorService.getColor();
                view.setBackgroundColor(color);
            } catch (RemoteException e){

            }
        });
    }
}