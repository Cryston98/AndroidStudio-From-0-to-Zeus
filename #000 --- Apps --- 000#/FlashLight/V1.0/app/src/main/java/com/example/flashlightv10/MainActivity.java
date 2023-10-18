package com.example.flashlightv10;

import androidx.activity.SystemBarStyle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ImageView imgBtn;
    TextView textState;
    int currentState = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


        initComponents();
        activateObserver();
    }
    private void initComponents(){
        imgBtn = findViewById(R.id.imgBTN);
        textState = findViewById(R.id.stateFlash);
    }
    private void activateObserver(){
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentState == 0) {
                    textState.setText("Power OFF");
                    imgBtn.setImageResource(R.drawable.shutdown);
                    currentState = 1;
                } else {
                    textState.setText("Power ON");
                    imgBtn.setImageResource(R.drawable.power);
                    currentState = 0;
                }
            }
        });
    }
}