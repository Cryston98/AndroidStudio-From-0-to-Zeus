package com.mythicether.tictactoeprov10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    LinearLayout btnStatus,btnPlay,btnOther,btnPlayerVsComputer,btnPlayerVsPlayer,rankDetails;
    RelativeLayout playFragment, statusFragment,otherFragment;
    TextView playText,statusText,otherText,usernameUI,userRankUI,userExpUI;
    Vibrator vibrator;

    List<String> nameInsignList;
    List<String> tagInsignList;
    List<Integer> imgInsignList;
    RecyclerView insignsLayout;
    GridAdapter gridAdapter;

    String userName;
    Integer userRank;
    int userExp;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView w1,w2,w3,w4,w5,w6,w7,w8;

    Button resetEightPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComp();

    }

    private void initComp(){
        if (getSupportActionBar()!=null)
            getSupportActionBar().hide();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.bg));
        }

        sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        // Obțineți un editor pentru a putea modifica SharedPreferences
        editor = sharedPreferences.edit();


        userRankUI=findViewById(R.id.userRankUI);
        usernameUI=findViewById(R.id.userNameUI);
        userExpUI = findViewById(R.id.userExpUI);

        playFragment=findViewById(R.id.playFragment);
        statusFragment=findViewById(R.id.statusFragment);
        otherFragment=findViewById(R.id.otherFragment);

        rankDetails=findViewById(R.id.rankDetails);


        hideNavBar();

        w1=findViewById(R.id.w1);
        w2=findViewById(R.id.w2);
        w3=findViewById(R.id.w3);
        w4=findViewById(R.id.w4);
        w5=findViewById(R.id.w5);
        w6=findViewById(R.id.w6);
        w7=findViewById(R.id.w7);
        w8=findViewById(R.id.w8);

        getUserDate();

        
        btnStatus=findViewById(R.id.btnStatus);
        btnPlay=findViewById(R.id.btnPlay);
        btnOther=findViewById(R.id.btnOther);

        statusText=findViewById(R.id.statusText);
        playText=findViewById(R.id.playText);
        otherText=findViewById(R.id.otherText);

        vibrator  = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        btnPlayerVsComputer=findViewById(R.id.playerVsComputer);
        btnPlayerVsPlayer = findViewById(R.id.playerVsPlayer);

        resetEightPath = findViewById(R.id.resetEightPaht);



        insignsLayout=findViewById(R.id.dateInsigne);
        nameInsignList=new ArrayList<>();
        imgInsignList=new ArrayList<>();
        tagInsignList=new ArrayList<>();

        nameInsignList.add("Cele 8 drumuri de aur");
        nameInsignList.add("Patru capcane mortale");
        nameInsignList.add("Crearea & Distrugerea");
        //jocul presupune ca sa ajugi sa formezi o linie pt a castiga in alcelasi tip in care concurezi cu alti..
        //deci in timp ce urmaresti sa creezi castigul propriu trebuie sa urmaresti sa distrugi castigul altora.
        nameInsignList.add("adsa ");

        tagInsignList.add("EightPath");
        tagInsignList.add("Patru");
        tagInsignList.add("3orie");
        tagInsignList.add("adsa");

        imgInsignList.add(R.drawable.baseline_alt_route_24);
        imgInsignList.add(R.drawable.baseline_all_inclusive_24);
        imgInsignList.add(R.drawable.baseline_brightness_medium_24);
        imgInsignList.add(R.drawable.baseline_hive_24);

        gridAdapter=new GridAdapter(getApplicationContext(),nameInsignList,tagInsignList,imgInsignList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3,GridLayoutManager.VERTICAL,false);
        insignsLayout.setLayoutManager(gridLayoutManager);
        insignsLayout.setAdapter(gridAdapter);




        setListener();
        selectGameMode();
    }

    private void setListener(){

        resetEightPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Adăugați informații în SharedPreferences
                editor.putInt("winPath1", 0);
                editor.putInt("winPath2", 0);
                editor.putInt("winPath3", 0);
                editor.putInt("winPath4", 0);
                editor.putInt("winPath5", 0);
                editor.putInt("winPath6", 0);
                editor.putInt("winPath7", 0);
                editor.putInt("winPath8", 0);
                editor.putInt("insignEightPath", 0);

                // Salvați modificările
                editor.apply();// Notificare adapterului despre schimbare
                insignsLayout.setAdapter(null);
                insignsLayout.setAdapter(gridAdapter);
            }
        });
        btnStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otherText.setText("");
                playText.setText("");
                statusText.setText("Status");
                statusFragment.setVisibility(View.VISIBLE);
                playFragment.setVisibility(View.GONE);
                otherFragment.setVisibility(View.GONE);


                btnStatus.setBackgroundResource(R.drawable.round_bakround);
                btnOther.setBackground(null);
                btnPlay.setBackground(null);

                callVibration();
            }
        });


        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otherText.setText("");
                playText.setText("Play");
                statusText.setText("");

                statusFragment.setVisibility(View.GONE);
                playFragment.setVisibility(View.VISIBLE);
                otherFragment.setVisibility(View.GONE);

                btnPlay.setBackgroundResource(R.drawable.round_bakround);
                btnOther.setBackground(null);
                btnStatus.setBackground(null);

                callVibration();
            }
        });


        btnOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otherText.setText("Others");
                playText.setText("");
                statusText.setText("");

                statusFragment.setVisibility(View.GONE);
                playFragment.setVisibility(View.GONE);
                otherFragment.setVisibility(View.VISIBLE);

                btnOther.setBackgroundResource(R.drawable.round_bakround);
                btnStatus.setBackground(null);
                btnPlay.setBackground(null);

                callVibration();

            }
        });
        rankDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListRanks.class);
                startActivity(intent);
            }
        });
    }


    private void selectGameMode(){

        btnPlayerVsComputer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SelectLevel.class);
                startActivity(intent);
            }
        });
    }

    private void getUserDate(){
            // Obțineți SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);

            // Accesați informații
            userName = sharedPreferences.getString("userName", "Erys");
            userRank = sharedPreferences.getInt("userRank", -1);
            userExp = sharedPreferences.getInt("userExp", -1);

        w1.setText("R1-"+sharedPreferences.getInt("winPath1", 0));
        w2.setText("R2-"+sharedPreferences.getInt("winPath2", 0));
        w3.setText("R3-"+sharedPreferences.getInt("winPath3", 0));
        w4.setText("R4-"+sharedPreferences.getInt("winPath4", 0));
        w5.setText("R5-"+sharedPreferences.getInt("winPath5", 0));
        w6.setText("R6-"+sharedPreferences.getInt("winPath6", 0));
        w7.setText("R7-"+sharedPreferences.getInt("winPath7", 0));
        w8.setText("R8-"+sharedPreferences.getInt("winPath8", 0));

            usernameUI.setText(userName);
            userExpUI.setText(formatareNumar(userExp));

            if (userRank==-1)
                userRankUI.setText("Rank : NoN");
            else
                userRankUI.setText("Rank : "+getRank(userRank));

    }
    public String formatareNumar(int numar) {
        // Convertirea numărului la șir de caractere
        String numarString = String.valueOf(numar);

        // Calcularea lungimii șirului
        int lungime = numarString.length();

        // Construirea unui șir de caractere pentru rezultat
        StringBuilder rezultat = new StringBuilder();

        // Parcurgerea șirului și adăugarea de puncte
        for (int i = 0; i < lungime; i++) {
            rezultat.append(numarString.charAt(i));

            // Adăugarea unui punct după fiecare cifră a miilor, zecilor de mii, sutelor de mii și miilor de mii
            if ((lungime - i - 1) % 3 == 0 && i != lungime - 1) {
                rezultat.append('.');
            }
        }

        // Returnarea rezultatului sub formă de șir de caractere formatat
        return "+ "+rezultat.toString()+" EXP";
    }

    private String getRank(Integer intRank){
        switch (intRank){
            case 0 : return "Ucenic";
            case 1 : return "Explorator";
            case 2 : return "Gardian";
            case 3 : return "Rege";
            case 4 : return "Maestru";
            case 5 : return "Nemuritor";
            case 6 : return "Zeu";
        }
        return "NiN";
    }
    private void callVibration(){
        if (vibrator.hasVibrator()) {
            vibrator.vibrate(50);
        } else {
            // Tratează cazul în care dispozitivul nu are funcția de vibrație
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateProfile();

    }

    private void updateProfile(){
        getUserDate();
        insignsLayout.setAdapter(null);
        insignsLayout.setAdapter(gridAdapter);
    }
    private void hideNavBar(){
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION  | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY ;
        decorView.setSystemUiVisibility(uiOptions);
    }
}