package com.mythicether.tictactoeprov10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

public class ListRanks extends AppCompatActivity {

    ListView listRanks;

    String [] namesRank = {"Ucenic","Explorator","Gardian","Rege","Maestru","Nemuritor","Zeu"};
    String [] deskRank = {
            "Inveți primi pași. Ce este și cum funcționează jocul",
            "Explorezi diferite situații și stări, chiar dacă nu vezi să te ducă către o destinație",
            "Obți cunoașterea de a te proteja de pericol cat si a-ți proteja șansa către propria victorie",
            "Iți dezvolți abilitatea de a conduce flow-ul și direcția jocului",
            "Ai devenit un mastru al alegerilor, între a te expune pericolui și a conduce direcția jocului",
            "Șansă extrem de mică de a mai muri și a mai pierde vreun joc. Ai înțeles întreg spațiul infinit de posibilități ale jocului",
            "Controlezi întreagul joc încă de la prima mutare pe tabla de joc."};
    int [] imgRank = {
            R.drawable.baseline_roller_skating_24,
            R.drawable.baseline_hub_24,
            R.drawable.baseline_security_24,
            R.drawable.baseline_castle_24,
            R.drawable.baseline_cloud_24,
            R.drawable.baseline_all_inclusive_24,
            R.drawable.baseline_bolt_24
    };

    ImageView bak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_ranks);

        initHide();
        bak=findViewById(R.id.backExit);
        bak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        listRanks = findViewById(R.id.listRanksUI);
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(),namesRank,deskRank,imgRank);
        listRanks.setAdapter(customAdapter);
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