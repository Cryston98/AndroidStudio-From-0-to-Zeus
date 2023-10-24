package com.example.xo22;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelUuid;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;


public class ConnectRoom extends AppCompatActivity {

    final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    Set<BluetoothDevice> paired_devices;

    private static final  int REQUEST_ENABLE_BT = 2;
    private static final  int REQUEST_DISCOVERABLE_BT = 3;
    int REQUEST_BLUETOOTH_PERMISION = 4;
    int REQUEST_BLUETOOTH_ADMINT_PERMISION = 5;
    int REQUEST_BLUETOOTH_CONNECT_PERMISION = 6;
    int REQUEST_COARSE_LOCATION_PERMISION = 7;
    int REQUEST_FINE_LOCATION_PERMISSION = 8;

    String[] locationPermissions = {Manifest.permission.ACCESS_FINE_LOCATION};
    int EASY_FINE_LOCATION = 9;

    ArrayAdapter arrayAdapter;
    ListView pairDevUI;
    Button bt_status,bta_status,btc_status,btcl_status,btfl_status;
    private static final String TAG = "BT_STATUS";
    private static Handler handler;
    private final static  int ERROR_READ =0;
    BluetoothDevice btDevice = null;
   // UUID btDeviceUUID = UUID.fromString()

    private ActivityResultLauncher<String> requestLocationPermissionLauncher;
    Button btn_1;
    ScanCallback scanCallback;
    BluetoothLeScanner scanner;
    ArrayList<String> uuid_String = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_room);

        initComp();
        callForBTPermission(this);
        callForBTAPermission(this);
        callForBTCPermission(this);
        callForFineLocationPermision(this);
        callForCoarseLocationPermision(this);
        getLocationEasyPermision();


        checkBluetoothStatus();
        getListPairedDevices();

       Button btn_2=findViewById(R.id.btn_2);


        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scaneDevice();
            }
        });
    }

    private void scaneDevice(){

        if (bluetoothAdapter == null) {
            // Dispozitivul tău nu suportă Bluetooth.
            return;
        }

        if (!bluetoothAdapter.isEnabled()) {
            // Bluetooth nu este activat. Poți solicita activarea Bluetooth aici.
            return;
        }
        Toast.makeText(this,"SCANNING...",Toast.LENGTH_SHORT).show();

        // Înregistrează un BroadcastReceiver pentru a primi rezultatele scanării.
        registerReceiver(bluetoothReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));

        // Pornirea scanării Bluetooth.
        bluetoothAdapter.startDiscovery();

    }

    private void initComp(){
        pairDevUI=findViewById(R.id.pairDevUI);
        bt_status=findViewById(R.id.bt_status);
        bta_status=findViewById(R.id.bta_status);
        btc_status=findViewById(R.id.btc_status);

        btcl_status=findViewById(R.id.btcl_status);
        btfl_status=findViewById(R.id.btfl_status);
        btn_1=findViewById(R.id.btn_1);

    }

    private void checkBluetoothStatus(){
        if (bluetoothAdapter==null){
            Toast.makeText(getApplicationContext(), "Device not support BT Service!", Toast.LENGTH_SHORT).show();
        }
        if (!bluetoothAdapter.isEnabled()){
            enableBluetoothService();
        }
    }
    private void enableBluetoothService(){
      Intent myIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
      startActivityForResult(myIntent,REQUEST_ENABLE_BT);
    }

    private void disableBluetoothService(){
        bluetoothAdapter.disable();
    }

    private void createBTRoom(){
        if (!bluetoothAdapter.isDiscovering()){
            showMsg("Making your device visible!");
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            startActivityForResult(intent,REQUEST_DISCOVERABLE_BT);
        }
    }


    private void getListPairedDevices(){
        if (bluetoothAdapter.isEnabled()){


            paired_devices = bluetoothAdapter.getBondedDevices();
            if (paired_devices.size()>0){

                ArrayList<String> deviceName = new ArrayList<String>(paired_devices.size());
                ArrayList<String> deviceMAC = new ArrayList<String>(paired_devices.size());

                for (BluetoothDevice tmp : paired_devices){
                    deviceName.add(tmp.getName());
                    deviceMAC.add((tmp.getAddress()));//MAC Address
                }
              //  arrayAdapter =  new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, deviceName);
               // pairDevUI.setAdapter(arrayAdapter);
            }
        }
    }

    private void showMsg(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }

    private void getLocationEasyPermision(){
        if (EasyPermissions.hasPermissions(getApplicationContext(), locationPermissions)) {
            // Permisiunea de locație este deja acordată.
            // Poți continua cu operațiile de locație.
        } else {
            // Permisiunea de locație nu este acordată, așa că solicit-o.
            PermissionRequest request = new PermissionRequest.Builder(this, EASY_FINE_LOCATION, locationPermissions)
                    .setRationale("Este necesară permisiunea pentru accesul la locație.")
                    .setPositiveButtonText("OK")
                    .setNegativeButtonText("Anulare")
                    .build();

            EasyPermissions.requestPermissions(request);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT){
            if (resultCode== RESULT_OK){
                Toast.makeText(getApplicationContext(),"BT Service was Enabled!",Toast.LENGTH_SHORT).show();
                getListPairedDevices();
            }else{
                Toast.makeText(getApplicationContext(),"BT Service Error!",Toast.LENGTH_SHORT).show();;

            }
        }
    }

    private void callForBTPermission(Activity myActivity){
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.BLUETOOTH) == PackageManager.PERMISSION_GRANTED) {
            // Permisiunea BLUETOOTH este acordată.
            bt_status.setBackgroundColor(Color.CYAN);
        } else {
            // Permisiunea BLUETOOTH nu este acordată.
            bt_status.setBackgroundColor(Color.RED);
            ActivityCompat.requestPermissions(myActivity, new String[]{Manifest.permission.BLUETOOTH}, REQUEST_BLUETOOTH_PERMISION);
        }
    }

    private void callForBTAPermission(Activity myActivity){
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.BLUETOOTH_ADMIN) == PackageManager.PERMISSION_GRANTED) {
            // Permisiunea BLUETOOTH este acordată.
            bta_status.setBackgroundColor(Color.CYAN);
        } else {
            // Permisiunea BLUETOOTH nu este acordată.
            bta_status.setBackgroundColor(Color.RED);
            ActivityCompat.requestPermissions(myActivity, new String[]{Manifest.permission.BLUETOOTH_ADMIN}, REQUEST_BLUETOOTH_ADMINT_PERMISION);
        }
    }
    private void callForBTCPermission(Activity myActivity){
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
            // Permisiunea BLUETOOTH este acordată.
            btc_status.setBackgroundColor(Color.CYAN);
        } else {
            // Permisiunea BLUETOOTH nu este acordată.
            btc_status.setBackgroundColor(Color.RED);
            ActivityCompat.requestPermissions(myActivity, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, REQUEST_BLUETOOTH_CONNECT_PERMISION);
        }
    }

    private void callForCoarseLocationPermision(Activity myActivity){
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Permisiunea BLUETOOTH este acordată.
            // Puteți continua cu operațiunile care necesită această permisiune.
            btcl_status.setBackgroundColor(Color.RED);
        } else {
            // Permisiunea BLUETOOTH nu este acordată.
            btcl_status.setBackgroundColor(Color.RED);
            ActivityCompat.requestPermissions(myActivity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_COARSE_LOCATION_PERMISION);
        }
    }

    private void callForFineLocationPermision(Activity myActivity){
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Permisiunea BLUETOOTH este acordată.
            // Puteți continua cu operațiunile care necesită această permisiune.
            btfl_status.setBackgroundColor(Color.CYAN);
        } else {
            // Permisiunea BLUETOOTH nu este acordată.
            btfl_status.setBackgroundColor(Color.RED);
            ActivityCompat.requestPermissions(myActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION_PERMISSION);
        }
    }








    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_BLUETOOTH_PERMISION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bt_status.setText("B1");
                // Permisiunea pentru camera a fost acordată.
            } else {
                bt_status.setText("B2");
                // Permisiunea pentru camera a fost refuzată.

            }
        }


        if (requestCode == REQUEST_BLUETOOTH_ADMINT_PERMISION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bta_status.setText("B1");
                // Permisiunea pentru camera a fost acordată.
            } else {
                bta_status.setText("B2");
                // Permisiunea pentru camera a fost refuzată.

            }
        }

        if (requestCode == REQUEST_BLUETOOTH_CONNECT_PERMISION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                btc_status.setText("B1");
                // Permisiunea pentru camera a fost acordată.
            } else {
                btc_status.setText("B2");
                // Permisiunea pentru camera a fost refuzată.

            }
        }

        if (requestCode == REQUEST_FINE_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                btfl_status.setText("B1");
                // Permisiunea pentru camera a fost acordată.
            } else {
                btfl_status.setText("B2");
                // Permisiunea pentru camera a fost refuzată.

            }
        }

        if (requestCode == REQUEST_COARSE_LOCATION_PERMISION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                btcl_status.setText("B1");
                // Permisiunea pentru camera a fost acordată.
            } else {
                btcl_status.setText("B2");
                // Permisiunea pentru camera a fost refuzată.

            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disableBluetoothService();
    }

    private final BroadcastReceiver bluetoothReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            Toast.makeText(context,"0000",Toast.LENGTH_SHORT).show();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                Toast.makeText(context,"Find",Toast.LENGTH_SHORT).show();
                // Se găsește un dispozitiv Bluetooth în jur.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceAddress = device.getAddress();
                uuid_String.add(deviceAddress);


                // Poți să faci ce dorești cu datele dispozitivului aici.
            }
            arrayAdapter =  new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, uuid_String);
            pairDevUI.setAdapter(arrayAdapter);
        }
    };

}