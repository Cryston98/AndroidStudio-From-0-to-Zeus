package com.example.xo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.Manifest;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView bt_permision, bta_permision, fl_permision, btc_permision,cr_permision,bsc_permision;
    Button bt_btn, bta_btn, fl_btn, btc_btn, bt_enable,cr_btn,bt_game,bsc_btn;

    int REQUEST_BLUETOOTH_PERMISION = 1;
    int REQUEST_BLUETOOTH_ADMINT_PERMISION = 2;
    int REQUEST_BLUETOOTH_CONNECT_PERMISION = 3;
    int REQUEST_ENABLE_BT = 4;
    int REQUEST_FINE_LOCATION_PERMISION = 5;
    int REQUEST_COARSE_LOCATION_PERMISION = 6;
    int REQUEST_BLUETOOTH_SCAN_PERMISION = 7;
    int REQUEST_SC_BT = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        observerZeus(this);
    }

    private void initComponents() {
        bt_permision = findViewById(R.id.bt_permision_result);
        bta_permision = findViewById(R.id.bta_permision_result);
        btc_permision = findViewById(R.id.btc_permision_result);
        fl_permision = findViewById(R.id.fl_permision_result);
        cr_permision = findViewById(R.id.cr_permision_result);
        bsc_permision = findViewById(R.id.bsc_permision_result);




        bt_btn = findViewById(R.id.bt_permision);
        bta_btn = findViewById(R.id.bta_permision);
        btc_btn = findViewById(R.id.btc_permision);
        fl_btn = findViewById(R.id.fl_permision);
        cr_btn = findViewById(R.id.cr_permision);
        bsc_btn = findViewById(R.id.bsc_permision);


        bt_enable = findViewById(R.id.bt_enable);
        bt_game = findViewById(R.id.bt_game);

    }

    private void observerZeus(Activity myActivity) {

        bt_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gameBoardIntent = new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(gameBoardIntent);
            }
        });
        bt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CHECK PERMISION
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.BLUETOOTH) == PackageManager.PERMISSION_GRANTED) {
                    // Permisiunea BLUETOOTH este acordată.
                    // Puteți continua cu operațiunile care necesită această permisiune.
                    bt_permision.setText("BT:TRUE");

                    bt_btn.setVisibility(View.GONE);
                    bta_btn.setVisibility(View.VISIBLE);
                } else {
                    // Permisiunea BLUETOOTH nu este acordată.
                    // Puteți informa utilizatorul sau gestiona altfel acest caz.
                    bt_permision.setText("BT:FALSE_1");
                    ActivityCompat.requestPermissions(myActivity, new String[]{Manifest.permission.BLUETOOTH}, REQUEST_BLUETOOTH_PERMISION);
                }
            }
        });


        bta_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CHECK PERMISION
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.BLUETOOTH_ADMIN) == PackageManager.PERMISSION_GRANTED) {
                    // Permisiunea BLUETOOTH este acordată.
                    // Puteți continua cu operațiunile care necesită această permisiune.
                    bta_permision.setText("BTA:TRUE");
                    bta_btn.setVisibility(View.GONE);
                    fl_btn.setVisibility(View.VISIBLE);
                } else {
                    // Permisiunea BLUETOOTH nu este acordată.
                    // Puteți informa utilizatorul sau gestiona altfel acest caz.
                    bta_permision.setText("BTA:FALSE_1");
                    ActivityCompat.requestPermissions(myActivity, new String[]{Manifest.permission.BLUETOOTH_ADMIN}, REQUEST_BLUETOOTH_ADMINT_PERMISION);
                }
            }
        });


        fl_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CHECK PERMISION
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    // Permisiunea BLUETOOTH este acordată.
                    // Puteți continua cu operațiunile care necesită această permisiune.
                    fl_permision.setText("FL:TRUE");
                    fl_btn.setVisibility(View.GONE);
                    cr_btn.setVisibility(View.VISIBLE);
                } else {
                    // Permisiunea BLUETOOTH nu este acordată.
                    // Puteți informa utilizatorul sau gestiona altfel acest caz.
                    fl_permision.setText("BTA:FALSE_1");
                    ActivityCompat.requestPermissions(myActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION_PERMISION);
                }
            }
        });

        cr_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CHECK PERMISION
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    // Permisiunea BLUETOOTH este acordată.
                    // Puteți continua cu operațiunile care necesită această permisiune.
                    cr_permision.setText("FL:TRUE");
                    cr_btn.setVisibility(View.GONE);
                    bsc_btn.setVisibility(View.VISIBLE);
                } else {
                    // Permisiunea BLUETOOTH nu este acordată.
                    // Puteți informa utilizatorul sau gestiona altfel acest caz.
                    cr_permision.setText("BTA:FALSE_1");
                    ActivityCompat.requestPermissions(myActivity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_COARSE_LOCATION_PERMISION);
                }
            }
        });

        bsc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CHECK PERMISION
                Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                //trebuie testata permisiunea connect.
                startActivityForResult(enableBluetoothIntent, REQUEST_SC_BT);
//                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED) {
//                    // Permisiunea BLUETOOTH este acordată.
//                    // Puteți continua cu operațiunile care necesită această permisiune.
//                    bsc_permision.setText("bsc:TRUE");
//                    bsc_btn.setVisibility(View.GONE);
//                    // cr_btn.setVisibility(View.VISIBLE);
//                } else {
//                    // Permisiunea BLUETOOTH nu este acordată.
//                    // Puteți informa utilizatorul sau gestiona altfel acest caz.
//                    bsc_permision.setText("BSC:FALSE_1");
//
//                    //bluetot scan --- VERSIUNEA 12 DE ANDROID
//
//                     ActivityCompat.requestPermissions(myActivity, new String[]{Manifest.permission.BLUETOOTH_SCAN}, REQUEST_BLUETOOTH_SCAN_PERMISION);
//                }
            }
        });





        btc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CHECK PERMISION
                Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                //trebuie testata permisiunea connect.
                startActivityForResult(enableBluetoothIntent, REQUEST_SC_BT);

//                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
//                    // Permisiunea BLUETOOTH este acordată.
//                    // Puteți continua cu operațiunile care necesită această permisiune.
//                    btc_permision.setText("BTC:TRUE");
//                    btc_btn.setVisibility(View.GONE);
//                    fl_btn.setVisibility(View.VISIBLE);
//                } else {
//                    // Permisiunea BLUETOOTH nu este acordată.
//                    // Puteți informa utilizatorul sau gestiona altfel acest caz.
//                    btc_permision.setText("BTC:FALSE_1");
//                    ActivityCompat.requestPermissions(myActivity, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, REQUEST_BLUETOOTH_CONNECT_PERMISION);
//                }
            }
        });





        bt_enable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Enable BTH service
                Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                //trebuie testata permisiunea connect.
                startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BT);
            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                //bluetooth is enabled;
                Toast.makeText(getApplicationContext(), "Bluetooth Service was enabled!", Toast.LENGTH_SHORT).show();

            } else if (resultCode == RESULT_CANCELED) {
                //bluetooth enabling is canceled.
                Toast.makeText(getApplicationContext(), "Process of Enabling Bluetooth Service was canceled!", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == REQUEST_SC_BT) {
            if (resultCode == RESULT_OK) {
                //bluetooth is enabled;
                Toast.makeText(getApplicationContext(), "Bluetooth SCAN  enabled!", Toast.LENGTH_SHORT).show();

            } else if (resultCode == RESULT_CANCELED) {
                //bluetooth enabling is canceled.
                Toast.makeText(getApplicationContext(), "Process SCAN canceled!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_BLUETOOTH_PERMISION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bt_permision.setText("BT:TRUE_1");
                bt_btn.setVisibility(View.GONE);
                bta_btn.setVisibility(View.VISIBLE);
                // Permisiunea pentru camera a fost acordată.
                // Puteți continua cu operațiunile care necesită această permisiune.
            } else {
                bt_permision.setText("BT:FALSE_2");
                // Permisiunea pentru camera a fost refuzată.
                // Puteți informa utilizatorul sau gestiona altfel acest caz.
            }
        }


        if (requestCode == REQUEST_BLUETOOTH_ADMINT_PERMISION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bta_permision.setText("BT:TRUE_1");
                bta_btn.setVisibility(View.GONE);
                fl_btn.setVisibility(View.VISIBLE);
                // Permisiunea pentru camera a fost acordată.
                // Puteți continua cu operațiunile care necesită această permisiune.
            } else {
                bta_permision.setText("BT:FALSE_2");
                // Permisiunea pentru camera a fost refuzată.
                // Puteți informa utilizatorul sau gestiona altfel acest caz.
            }
        }

        if (requestCode == REQUEST_BLUETOOTH_CONNECT_PERMISION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                btc_permision.setText("BTC:TRUE_1");
                btc_btn.setVisibility(View.GONE);
                fl_btn.setVisibility(View.VISIBLE);
                // Permisiunea pentru camera a fost acordată.
                // Puteți continua cu operațiunile care necesită această permisiune.
            } else {
                btc_permision.setText("BT:FALSE_2");
                // Permisiunea pentru camera a fost refuzată.
                // Puteți informa utilizatorul sau gestiona altfel acest caz.
            }
        }

        if (requestCode == REQUEST_FINE_LOCATION_PERMISION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fl_permision.setText("FL:TRUE_1");
                fl_btn.setVisibility(View.GONE);
                cr_btn.setVisibility(View.VISIBLE);
                // Permisiunea pentru camera a fost acordată.
                // Puteți continua cu operațiunile care necesită această permisiune.
            } else {
                fl_permision.setText("BT:FALSE_2");
                // Permisiunea pentru camera a fost refuzată.
                // Puteți informa utilizatorul sau gestiona altfel acest caz.
            }
        }

        if (requestCode == REQUEST_COARSE_LOCATION_PERMISION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                cr_permision.setText("cr:TRUE_1");
                cr_btn.setVisibility(View.GONE);
                //fl_btn.setVisibility(View.VISIBLE);
                // Permisiunea pentru camera a fost acordată.
                // Puteți continua cu operațiunile care necesită această permisiune.
            } else {
                cr_permision.setText("BT:FALSE_2");
                // Permisiunea pentru camera a fost refuzată.
                // Puteți informa utilizatorul sau gestiona altfel acest caz.
            }
        }

        if (requestCode == REQUEST_BLUETOOTH_SCAN_PERMISION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bsc_permision.setText("bsc:TRUE_1");
                bsc_btn.setVisibility(View.GONE);
                //fl_btn.setVisibility(View.VISIBLE);
                // Permisiunea pentru camera a fost acordată.
                // Puteți continua cu operațiunile care necesită această permisiune.
            } else {
                bsc_permision.setText("bsc:FALSE_2");
                // Permisiunea pentru camera a fost refuzată.
                // Puteți informa utilizatorul sau gestiona altfel acest caz.
            }
        }

    }
}