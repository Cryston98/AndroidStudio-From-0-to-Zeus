package com.example.xo22;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ConnectedThread extends Thread{
    private final BluetoothSocket mmSocket;
    private static final String TAG = "BT_STATUS";

    private final InputStream mmInStream;
    private final OutputStream mmOutStream;
    private String valueRead;

    public ConnectedThread(BluetoothSocket mmSocket) {
        this.mmSocket = mmSocket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        try {
            tmpIn = mmSocket.getInputStream();
        }catch (IOException e){
            Log.e(TAG,"Error when creating input strea",e);
        }

        try {
            tmpOut = mmSocket.getOutputStream();
        }catch (IOException e){
            Log.e(TAG,"Error when creating output stream",e);
        }
        mmInStream =tmpIn;
        mmOutStream=tmpOut;
    }

    public String getValueRead(){
        return valueRead;
    }

    @Override
    public void run() {

        byte[] buffer = new byte[1024];
        int bytes =0;  //bytes returned from read();
        int numberOfReading =0;  //to control the number of reading

        while (numberOfReading<1){
            try {
                buffer[bytes] =(byte)mmInStream.read();
                String readMessage;
                if (buffer[bytes]=='\n'){
                    readMessage=new String(buffer,0,bytes);
                    Log.e(TAG,readMessage);
                    valueRead=readMessage;
                    bytes=0;
                    numberOfReading++;
                }else{
                    bytes++;
                }
            }catch (IOException e){
                Log.e(TAG,"Input strea was disconected");
                break;
            }
        }
    }

    public void cancel(){
        try {
            mmSocket.close();
        }catch (IOException e){
            Log.e(TAG,"Could not clos the connecte socket",e);
        }
    }
}
