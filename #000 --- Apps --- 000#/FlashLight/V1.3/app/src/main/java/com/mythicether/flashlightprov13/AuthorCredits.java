package com.mythicether.flashlightprov13;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


public class AuthorCredits extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_credits);

        if (getSupportActionBar()!=null)
        getSupportActionBar().hide();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.bg));
        }
        hideNavBar();

        TextView url1,url2,url3,titleHead;

        url1=findViewById(R.id.url1);
        url2=findViewById(R.id.url2);
        url3=findViewById(R.id.url3);
        titleHead=findViewById(R.id.titleHead);

        titleHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(myIntent);
                finish();
            }
        });

        url1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUrl(1);
            }
        });

        url2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUrl(2);
            }
        });

        url3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUrl(3);
            }
        });


    }

    private void openUrl(int ID){
        String url ="";

        if (ID==1){
            url = "https://www.flaticon.com/free-icon/on_11286408";
        }else if(ID ==2){
            url= "https://www.flaticon.com/free-icon/on_11286377";
        }else if(ID ==3){
            url = "https://www.flaticon.com/free-icon/innovation_8658679\n";
        }


        // Crează un Intent cu acțiunea VIEW și dată adresa URL
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        // Verifică dacă există o aplicație de navigare (browser) disponibilă pentru a deschide link-ul
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            // Tratează cazul în care nu există o aplicație de navigare disponibilă
            // Poți deschide o altă pagină sau afișa un mesaj utilizatorului.
            Toast.makeText(getApplicationContext(),"Device cannot opeen :"+url, Toast.LENGTH_LONG).show();
        }
    }

    private void hideNavBar(){
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY ;
        decorView.setSystemUiVisibility(uiOptions);
    }

    public void setNoNavBar(){
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }
}