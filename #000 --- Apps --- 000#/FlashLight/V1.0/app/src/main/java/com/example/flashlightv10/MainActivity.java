package com.example.flashlightv10;

import androidx.activity.SystemBarStyle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ImageView imgBtn;
    TextView textState;
    int currentState = 0;
    Vibrator vibrator;
    CameraManager cameraManager;
    String cameraId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportActionBar().hide();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.bg));
        }




        initComponents();
        activateObserver();
        openCredit();
        openRatingAppp();
    }
    private void initComponents(){
        imgBtn = findViewById(R.id.imgBTN);
        textState = findViewById(R.id.stateFlash);
        vibrator  = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            cameraId = cameraManager.getCameraIdList()[0]; // Assuming you want to use the first camera
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }


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
    }


    private void openCredit(){
        LinearLayout layout=findViewById(R.id.creditAppLay);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.flaticon.com/authors/mayor-icons"; // Înlocuiește cu URL-ul pe care dorești să-l deschizi

                // Crează un Intent cu acțiunea VIEW și dată adresa URL
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

                // Verifică dacă există o aplicație de navigare (browser) disponibilă pentru a deschide link-ul
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    // Tratează cazul în care nu există o aplicație de navigare disponibilă
                    // Poți deschide o altă pagină sau afișa un mesaj utilizatorului.
                    Toast.makeText(getApplicationContext(),"Credit : https://www.flaticon.com/authors/mayor-icons", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void openRatingAppp(){
        LinearLayout layout=findViewById(R.id.ratingAppLay);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://play.google.com/"; // Înlocuiește cu URL-ul pe care dorești să-l deschizi

                // Crează un Intent cu acțiunea VIEW și dată adresa URL
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

                // Verifică dacă există o aplicație de navigare (browser) disponibilă pentru a deschide link-ul
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    // Tratează cazul în care nu există o aplicație de navigare disponibilă
                    // Poți deschide o altă pagină sau afișa un mesaj utilizatorului.
                    Toast.makeText(getApplicationContext(),"No browser app", Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    /*
    <a href="https://www.flaticon.com/free-icons/shock" title="shock icons">Shock icons created by Freepik - Flaticon</a>

    <a href="https://www.flaticon.com/free-icons/success" title="success icons">Success icons created by flatart_icons - Flaticon</a>

    <a href="https://www.flaticon.com/free-icons/healthy" title="healthy icons">Healthy icons created by Mayor Icons - Flaticon</a>
    <a href="https://www.flaticon.com/free-icons/ammunition" title="ammunition icons">Ammunition icons created by Mayor Icons - Flaticon</a>
    <a href="https://www.flaticon.com/free-icons/miscellaneous" title="miscellaneous icons">Miscellaneous icons created by Mayor Icons - Flaticon</a>
    <a href="https://www.flaticon.com/free-icons/miscellaneous" title="miscellaneous icons">Miscellaneous icons created by Mayor Icons - Flaticon</a>
    <a href="https://www.flaticon.com/free-icons/frequency" title="frequency icons">Frequency icons created by Mayor Icons - Flaticon</a>
<a href="https://www.flaticon.com/free-icons/analyze" title="analyze icons">Analyze icons created by Mayor Icons - Flaticon</a>
<a href="https://www.flaticon.com/free-icons/military-tag" title="military tag icons">Military tag icons created by Mayor Icons - Flaticon</a>
<a href="https://www.flaticon.com/free-icons/ecology-and-environment" title="ecology and environment icons">Ecology and environment icons created by Mayor Icons - Flaticon</a>
    * */

}