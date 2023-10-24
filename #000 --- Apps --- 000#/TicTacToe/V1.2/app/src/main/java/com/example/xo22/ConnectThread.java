package com.example.xo22;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;


import java.io.IOException;
import java.util.UUID;


public class ConnectThread extends Thread{
    private final BluetoothSocket mmSocket;
    private static final String TAG = "BT_STATUS";
    public static Handler handler;
    private final static int ERROR_READ =0;

    public ConnectThread(BluetoothDevice device, UUID myUUID, Handler handler) {
       BluetoothSocket tmp = null;
       this.handler = handler;

       try {
           tmp = device.createInsecureRfcommSocketToServiceRecord(myUUID);

       }catch (IOException e){
           Log.e(TAG,"Sockes's creade() method failed",e);
       }

       mmSocket=tmp;
    }

    @Override
    public void run() {

        try {
            mmSocket.connect();
        }catch (IOException connectException){
            handler.obtainMessage(ERROR_READ,"Unable to conect to the BT device").sendToTarget();
            Log.e(TAG,"ConnectException"+connectException);
            try{
                mmSocket.close();
            }catch (IOException closeException){
                Log.e(TAG,"Could not close the client socket"+closeException);
            }
            return;
        }
    }

    public void cancel(){
        //close socket when tread finish
        try {
            mmSocket.close();
        }catch (IOException e){
            Log.e(TAG,"Could not close the client socket",e);
        }
    }
    public BluetoothSocket getMmSocket(){
        return mmSocket;
    }
}
