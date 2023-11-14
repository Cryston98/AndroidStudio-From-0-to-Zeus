package com.mythicether.tictactoeprov10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SelectLevel extends AppCompatActivity {

    FloatingActionButton fab;
    TextView selectBegginerLevel, selectMediumLevel,selectAdvanceLevel,selectLegendarLevel,selectMentorLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_level);

        initHide();
        initComp();
        setListener();
    }

    private void initComp(){

        fab=findViewById(R.id.closeLevel);
        selectBegginerLevel = findViewById(R.id.selectBegginer);
        selectMediumLevel = findViewById(R.id.selectMedium);
        selectAdvanceLevel = findViewById(R.id.selectAdvance);
        selectLegendarLevel = findViewById(R.id.selectLegendar);
        selectMentorLevel = findViewById(R.id.selectMentor);
        checkPlayerStatus();
    }

    private void checkPlayerStatus(){
        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);

        // Accesați informații
       int userRank = sharedPreferences.getInt("userRank", -1);
       int userExp = sharedPreferences.getInt("userExp", -1);

       int insignEightPath = sharedPreferences.getInt("insignEightPath", 0);
       if (insignEightPath==1){
           selectMediumLevel.setEnabled(true);
           selectMediumLevel.setBackgroundColor(getColor(R.color.bg));
           selectMediumLevel.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(this, R.drawable.baseline_lock_open_24), null);
       }

    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPlayerStatus();
    }

    private void initHide(){
        if (getSupportActionBar()!=null)
            getSupportActionBar().hide();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.bg));
        }

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION  | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY ;
        decorView.setSystemUiVisibility(uiOptions);
    }

    private void setListener(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        selectMentorLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GameBoard.class);
                intent.putExtra("levelMode", 5);
                startActivity(intent);
            }
        });
        selectLegendarLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GameBoard.class);
                intent.putExtra("levelMode", 4);
                startActivity(intent);
            }
        });
        selectAdvanceLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GameBoard.class);
                intent.putExtra("levelMode", 3);
                startActivity(intent);
            }
        });

        selectMediumLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GameBoard.class);
                intent.putExtra("levelMode", 2);
                startActivity(intent);
            }
        });

        selectBegginerLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GameBoard.class);
                intent.putExtra("levelMode", 1);
                startActivity(intent);
            }
        });
    }
}