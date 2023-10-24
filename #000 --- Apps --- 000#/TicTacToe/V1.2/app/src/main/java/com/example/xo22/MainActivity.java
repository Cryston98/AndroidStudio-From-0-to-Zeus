package com.example.xo22;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    Button connectToRoom, createRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        setListener();




    }

    private void initComponents() {
            connectToRoom=findViewById(R.id.connectToRoom);
            createRoom=findViewById(R.id.createRoom);
    }

    private void setListener(){
        connectToRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ConnectRoom.class);
                startActivity(intent);
            }
        });

        createRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateRoom.class);
                startActivity(intent);
            }
        });
    }


}