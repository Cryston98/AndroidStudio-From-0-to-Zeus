package com.example.xo22;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CreateRoom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}