package com.example.xo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    ListView bt_devices_UI;
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> stringArrayList = new ArrayList<String>();
    Button scan_bnt;


    private  static final int REQUEST_CODE_BLUETOOTH_SCAN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        initComponents();


        scan_bnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               bluetoothAdapter.startDiscovery();
            }
        });


        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(myReceiver, intentFilter);

        

        arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, stringArrayList);
        bt_devices_UI.setAdapter(arrayAdapter);

    }

    private void initComponents() {
        bt_devices_UI = findViewById(R.id.bt_devices);
        scan_bnt = findViewById(R.id.scan_btn);
    }

    BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                stringArrayList.add(device.getName());
                arrayAdapter.notifyDataSetChanged();
            }
        }
    };
}