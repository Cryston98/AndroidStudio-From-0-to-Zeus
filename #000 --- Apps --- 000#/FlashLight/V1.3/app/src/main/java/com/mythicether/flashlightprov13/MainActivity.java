package com.mythicether.flashlightprov13;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.mythicether.flashlightprov13.R;

public class MainActivity extends AppCompatActivity {


    ImageView imgBtn;
    TextView textState;
    int currentState = 0;
    Vibrator vibrator;
    CameraManager cameraManager;
    String cameraId = null;
    ImageView menuBtn;
    PopupMenu popupMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar()!=null)
            getSupportActionBar().hide();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.bg));
        }


        initComponents();
        activateObserver();
        hideNavBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideNavBar();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        hideNavBar();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        hideNavBar();
    }

    private void initComponents(){
        menuBtn = findViewById(R.id.menuBTN);
        imgBtn = findViewById(R.id.imgBTN);
        textState = findViewById(R.id.stateFlash);
        vibrator  = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            cameraId = cameraManager.getCameraIdList()[0]; // Assuming you want to use the first camera
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }


        popupMenu = new PopupMenu(this, menuBtn);
        popupMenu.getMenuInflater().inflate(R.menu.mymenu, popupMenu.getMenu());

// Setează un listener pentru a trata acțiunile din meniu
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.credits) {
                    // Acțiune pentru opțiunea 1
                    Intent myIntent = new Intent(getApplicationContext(), AuthorCredits.class);
                    startActivity(myIntent);
                    return true;
                } else if (item.getItemId() == R.id.rating) {
                    // Acțiune pentru opțiunea 2
                    Intent myIntent2 = new Intent(getApplicationContext(), AuthorCredits.class);
                    startActivity(myIntent2);
                    return true;
                }
                // Adaugă alte opțiuni după nevoie
                return false;
            }
        });
    }
    private void activateObserver(){
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentState == 0) {
                    if (vibrator.hasVibrator()) {
                        vibrator.vibrate(50);
                    } else {
                        // Tratează cazul în care dispozitivul nu are funcția de vibrație
                    }
                    textState.setText("Power ON");
                    textState.setTextColor(getResources().getColor(R.color.green));
                    imgBtn.setImageResource(R.drawable.on);
                    currentState = 1;

                    try {
                        // To turn on the flashlight
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (cameraId!=null)
                                cameraManager.setTorchMode(cameraId, true);
                        }
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }


                } else {
                    if (vibrator.hasVibrator()) {
                        vibrator.vibrate(50);
                    } else {
                        // Tratează cazul în care dispozitivul nu are funcția de vibrație
                    }
                    try {
                        // To turn on the flashlight
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (cameraId!=null)
                                cameraManager.setTorchMode(cameraId, false);
                        }
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }

                    textState.setText("Power OFF");
                    textState.setTextColor(getResources().getColor(R.color.white));
                    imgBtn.setImageResource(R.drawable.off);
                    currentState = 0;
                }
            }
        });
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenu.show();
                // hideNavBar();
            }
        });
    }


    private void hideNavBar(){
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY ;
        decorView.setSystemUiVisibility(uiOptions);
    }





}