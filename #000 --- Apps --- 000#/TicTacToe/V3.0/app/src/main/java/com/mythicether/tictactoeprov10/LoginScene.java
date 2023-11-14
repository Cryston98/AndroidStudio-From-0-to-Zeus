package com.mythicether.tictactoeprov10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginScene extends AppCompatActivity {

    EditText nameUser;
    Button startGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_scene);
        initHide();
        checkLoginAlready();
    }

    private void checkLoginAlready(){
        boolean loginStatusFlag = false;
        loginStatusFlag=checkLogin();

        if (loginStatusFlag){
            continueFlowGame();
        }else{
            initComp();
            setListener();
        }
    }
    private boolean checkLogin(){
        // Obțineți SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);

        // Accesați informații
        String userName = sharedPreferences.getString("userName", "Erys");
        if (userName!="Erys"){
            return true;
        }
        return false;
    }

    private void initComp(){
        nameUser=findViewById(R.id.userNameLog);
        startGame=findViewById(R.id.startGameLog);
    }

    private void setListener(){
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namePl="";
                namePl=nameUser.getText().toString();

                if (namePl!=null && namePl.length()>=3 && namePl.length()<=8){
                    saveUserName(namePl);
                    continueFlowGame();
                }else{
                    showMsg("Name player too small or too big! Name size between 3 and 8 characters");
                }
            }
        });
    }
    private void showMsg(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }

    private void saveUserName(String usName){
        // Obțineți SharedPreferences

        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);

        // Obțineți un editor pentru a putea modifica SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Adăugați informații în SharedPreferences
        editor.putString("userName", usName);
        editor.putInt("userRank", 0);
        editor.putInt("userExp", 0);

        // Salvați modificările
        editor.apply();

    }

    private void continueFlowGame(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
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

}