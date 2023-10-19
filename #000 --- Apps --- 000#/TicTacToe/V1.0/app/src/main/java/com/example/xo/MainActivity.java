package com.example.xo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    BluetoothAdapter bluetoothAdapter;
    int REQUEST_ENABLE_BT = 1; //must and integer greater then 0;
    int REQUEST_BLUETOOTH_PERMISSION = 2;
    String[] permissions = {Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.BLUETOOTH,Manifest.permission.BLUETOOTH_ADMIN,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};
    Button connectBTN;

    Set<BluetoothDevice> listBluetoothDevices;
    String[] namePairDevices;
    int indexBTPairDevices;
    ListView pairDeviceListUI;
    ArrayList<String> stringArrayList = new ArrayList<String>();

    ArrayAdapter<String> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (!checkPermission_Bluetooth()){
            ActivityCompat.requestPermissions(this,Manifest.permission.BLUETOOTH, REQUEST_ENABLE_BT);
          //  checkPermission_Bluetooth();
        }

        ActivityCompat.requestPermissions(this, permissions, REQUEST_ENABLE_BT);


     //   initUIComponents();
     //   observerZeus();
      //  initBluetooth();


    }

//    BroadcastReceiver myReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
//                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                stringArrayList.add(device.getName());
//                arrayAdapter.notifyDataSetChanged();
//            }
//        }
//    };

    private void initUIComponents() {
        connectBTN = findViewById(R.id.connectBT);
        pairDeviceListUI = findViewById(R.id.pairDeviceListUI);
    }

    private void observerZeus() {
        connectBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // setPermissionBluetooth();
                enableBluetoothService();
                scanDevices();
                getBTPairDevices();
            }
        });
    }

    private void initBluetooth() {
        //verify device support bluetooth
        //verify bluetooth is enable
        //if not then enable it

        // setPermissionBluetooth();

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {
            //device not support bluetooth
            Toast.makeText(getApplicationContext(), "THIS DEVICE NOT SUPPORT BLUETOOTH SERVICE!", Toast.LENGTH_SHORT).show();
        } else {
            if (!checkBluetoothStatus()) {
                //seteaza bluetooth pe enable
                enableBluetoothService();
            } else {
                //daca bluetooth este deja enable atunci face urm cod:
                Toast.makeText(getApplicationContext(), "BLUETOOTH SERVICE IS ENABLED!", Toast.LENGTH_SHORT).show();
                getBTPairDevices();
            }
        }
    }

    private void scanDevices() {
        bluetoothAdapter.startDiscovery();
    }

    private void setPermissionBluetooth() {
        ActivityCompat.requestPermissions(this, permissions, REQUEST_BLUETOOTH_PERMISSION);
    }

    private boolean checkBluetoothStatus() {
        return bluetoothAdapter.isEnabled();
    }

    private void disableBluetoothService() {
        //apare cu rosu de la eraore pt ca nu testat daca avem permisiunea sa apelam aceasta functie
        bluetoothAdapter.disable();
    }

    private void enableBluetoothService() {
        Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

        //apare cu rosu de la eraore pt ca nu testat daca avem permisiunea sa apelam aceasta functie
        startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                //bluetooth is enabled;
                Toast.makeText(getApplicationContext(), "Bluetooth Service was enabled!", Toast.LENGTH_SHORT).show();
                getBTPairDevices();

            } else if (resultCode == RESULT_CANCELED) {
                //bluetooth enabling is canceled.
                Toast.makeText(getApplicationContext(), "Process of Enabling Bluetooth Service was canceled!", Toast.LENGTH_SHORT).show();
            }
        }

    }


    private void getBTPairDevices() {
        listBluetoothDevices = bluetoothAdapter.getBondedDevices();

        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        //registerReceiver(myReceiver, intentFilter);

        arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, stringArrayList);

//        namePairDevices = new String[listBluetoothDevices.size()];
//        indexBTPairDevices =0;
//
//        if (listBluetoothDevices.size()>0){
//            for (BluetoothDevice device:listBluetoothDevices){
//                namePairDevices[indexBTPairDevices] = device.getName();
//                indexBTPairDevices++;
//            }
//
//            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,namePairDevices);
//
        pairDeviceListUI.setAdapter(arrayAdapter);


//        }


    }



    private void setBluetoothConnection(){

    }

    private boolean checkPermission_Bluetooth(){
        //pentru versinile mai mari de android 6 trebuie cerute permisiunile dinamic chiar daca ele au fost trecute in fisierul manifest.

        boolean resultStatus=false;
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
            // Permisiunea Bluetooth nu este acordată.
            resultStatus=false;
        } else {
            // Permisiunea Bluetooth este acordată, puteți continua cu operațiunile Bluetooth.
            resultStatus=true;
        }
        TextView brt = findViewById(R.id.btPermision);
        brt.setText("BT:"+String.valueOf(resultStatus));

        return resultStatus;
    }

    private boolean checkPermission_BluetoothAdmin(){
        boolean resultStatus=false;
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED) {
            // Permisiunea Bluetooth nu este acordată.
            resultStatus=false;
        } else {
            // Permisiunea Bluetooth este acordată, puteți continua cu operațiunile Bluetooth.
            resultStatus=true;
        }
        TextView brt = findViewById(R.id.btaPermision);
        brt.setText("BTa:"+String.valueOf(resultStatus));
        return resultStatus;
    }

    private boolean checkPermission_FineLocation(){
        boolean resultStatus=false;
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permisiunea Bluetooth nu este acordată.
            resultStatus=false;
        } else {
            // Permisiunea Bluetooth este acordată, puteți continua cu operațiunile Bluetooth.
            resultStatus=true;
        }
        return resultStatus;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disableBluetoothService();
        //unregisterReceiver(myReceiver);
    }
}