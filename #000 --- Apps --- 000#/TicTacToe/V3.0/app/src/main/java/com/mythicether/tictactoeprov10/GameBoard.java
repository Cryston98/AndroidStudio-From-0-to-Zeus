package com.mythicether.tictactoeprov10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GameBoard extends AppCompatActivity {

    ImageView e1,e2,e3,e4,e5,e6,e7,e8,e9;
    boolean playTurn = true;
    ArrayList<Boolean> filledBox = new ArrayList<Boolean>();
    int totalGamesPlayed=0, winGamesByComp=0,winGamesByPlayer=0;
    String winnerID="NO";
    TextView playWin,compWin;
    Button resetGame,exitGame;
    int levelMode=3;
    ImageView dangerPosition=null;
    int currentExperience = 0;
    String userNameID = "NoN";
    String rankID = "NoN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_board);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            levelMode = extras.getInt("levelMode");
            // Now you have the data from the first activity
        }
        initHide();
        initComp();
        setListener();
    }

    private void initComp(){
        for (int i=0;i<10;i++){
            filledBox.add(false);
        }

        e1 = findViewById(R.id.elem11);
        e2 = findViewById(R.id.elem12);
        e3 = findViewById(R.id.elem13);

        e4 = findViewById(R.id.elem21);
        e5 = findViewById(R.id.elem22);
        e6 = findViewById(R.id.elem23);

        e7 = findViewById(R.id.elem31);
        e8 = findViewById(R.id.elem32);
        e9 = findViewById(R.id.elem33);

        compWin = findViewById(R.id.compWin);
        playWin = findViewById(R.id.playWin);
        resetGame = findViewById(R.id.resetGame);
        exitGame = findViewById(R.id.exitGame);

    }

    private void setListener(){
        e1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData(1);
            }
        });
        e2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData(2);
            }
        });
        e3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData(3);
            }
        });

        e4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData(4);
            }
        });
        e5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData(5);
            }
        });
        e6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData(6);
            }
        });

        e7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData(7);
            }
        });
        e8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData(8);
            }
        });
        e9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData(9);
            }
        });

        resetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });

        exitGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitGame();
            }
        });


    }



    private void setData(int idElem){

        ImageView tmp = null;
        switch (idElem){
            case 1:
                tmp = e1;
                break;
            case 2:
                tmp = e2;
                break;
            case 3:
                tmp = e3;
                break;
            case 4:
                tmp = e4;
                break;
            case 5:
                tmp = e5;
                break;
            case 6:
                tmp = e6;
                break;
            case 7:
                tmp = e7;
                break;
            case 8:
                tmp = e8;
                break;
            case 9:
                tmp = e9;
                break;
            default :
                break;
        }
        if (tmp!=null && filledBox.get(idElem)==false)
        {
            if (playTurn==true  && logicGame()!=true){

                // Obține resursa de imagine
                Drawable drawable = getResources().getDrawable(R.drawable.baseline_clear_24);

                // Setează imaginea pentru ImageView cu dimensiuni reduse
                int targetWidth = 200; // Alege lățimea dorită
                int targetHeight = 200; // Alege înălțimea dorită
                drawable.setBounds(0, 0, targetWidth, targetHeight);


                tmp.setImageDrawable(drawable);
                tmp.setTag("X");
                playTurn=false;
                filledBox.set(idElem,true);
                checkFillComplet();


            }
        }
    }

    private void CompInteligence(){


        // Pentru a genera un număr întreg într-un anumit interval, poți face astfel:
        int min = 1;
        int max = 9;

        ArrayList<Integer> invalidBoard = new ArrayList<>();
        for (int i=0;i<filledBox.size();i++){
            if (filledBox.get(i)==true)
                invalidBoard.add(i);
        }

        Random random = new Random();
        int numarAleatoriu;

        do{
            numarAleatoriu = random.nextInt((max - min) + 1) + min;
        } while(invalidBoard.contains(numarAleatoriu));

        ImageView tmp = null;
        switch (numarAleatoriu){
            case 1:
                tmp = e1;
                break;
            case 2:
                tmp = e2;
                break;
            case 3:
                tmp = e3;
                break;
            case 4:
                tmp = e4;
                break;
            case 5:
                tmp = e5;
                break;
            case 6:
                tmp = e6;
                break;
            case 7:
                tmp = e7;
                break;
            case 8:
                tmp = e8;
                break;
            case 9:
                tmp = e9;
                break;
            default :
                break;
        }


        tmp.setImageDrawable(getResources().getDrawable(R.drawable.baseline_radio_button_unchecked_24_red));
        tmp.setTag("O");
        filledBox.set(numarAleatoriu,true);
        playTurn=true;
        checkFillComplet();


    }

    private boolean logicGame(){

        if (e1.getTag() == e2.getTag() && e2.getTag()== e3.getTag()){
            winnerID = e1.getTag().toString();
            updateGameWinMode(1);
            return true;
        }
        if (e1.getTag() == e4.getTag() && e4.getTag()== e7.getTag()){
            winnerID = e1.getTag().toString();
            updateGameWinMode(4);
            return true;
        }
        if (e1.getTag() == e5.getTag() && e5.getTag()== e9.getTag()){
            winnerID = e1.getTag().toString();
            updateGameWinMode(5);
            return true;
        }
        if (e3.getTag() == e5.getTag() && e5.getTag()== e7.getTag()){
            winnerID = e3.getTag().toString();
            updateGameWinMode(6);
            return true;
        }
        if (e3.getTag() == e6.getTag() && e6.getTag()== e9.getTag()){
            winnerID = e3.getTag().toString();
            updateGameWinMode(2);
            return true;
        }
        if (e2.getTag() == e5.getTag() && e5.getTag()== e8.getTag()){
            winnerID = e2.getTag().toString();
            updateGameWinMode(7);
            return true;
        }
        if (e4.getTag() == e5.getTag() && e5.getTag()== e6.getTag()){
            winnerID = e4.getTag().toString();
            updateGameWinMode(8);
            return true;
        }
        if (e7.getTag() == e8.getTag() && e8.getTag()== e9.getTag()){
            winnerID = e7.getTag().toString();
            updateGameWinMode(3);
            return true;
        }

        return false;
    }

    private void  checkFillComplet(){
        boolean completGame = true;

        if (logicGame()){
            if (winnerID == "X") {
                winGamesByPlayer++;
                playWin.setText("Win : "+winGamesByPlayer);
            } else {
                winGamesByComp++;
                compWin.setText("Win : "+winGamesByComp);
            }
            Toast.makeText(getApplicationContext(),"WINNNN :  "+winnerID,Toast.LENGTH_SHORT).show();
            resetGame.setEnabled(true);
        }else{
            //testeaza daca toate casutele sunt pline si au fost setate cu valoare true
            // daca gaseste una care este falsa asta inseamna ca nu e gata jocul. si nici nu a castigat nimeni
            for (int i=1;i<10;i++){
                if (filledBox.get(i)==false)
                    completGame=false;
            }

            if (completGame){
                Toast.makeText(getApplicationContext(),"The game was finished",Toast.LENGTH_SHORT).show();
                resetGame.setEnabled(true);
                //activate reset button;
            }else{
                if (playTurn==false){
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // This code will be executed on the UI thread after the delay
                            if (levelMode==1){
                                CompInteligence();
                            } else if (levelMode==2) {
                                CompInteligenceMedium();
                            }else if(levelMode==3){
                                CompInteligenceAdvance();
                            }
                        }
                    }, 1000);
                }
            }
        }
    }

    private boolean checkDangerToLose(){




        ///Colturile 1,3,7,9 au grad 3 de pericol;
        if (e2.getTag()==e3.getTag() && e2.getTag()=="X"){
            dangerPosition=e1;
            return true;
        }
        if (e5.getTag()==e9.getTag() && e5.getTag()=="X"){
            dangerPosition=e1;
            return true;
        }

        if(e4.getTag()==e7.getTag() && e4.getTag()=="X"){
            dangerPosition=e1;
            return true;
        }


        if (e1.getTag()==e2.getTag() && e1.getTag()=="X"){
            dangerPosition=e3;
            return true;
        }

        if (e5.getTag()==e7.getTag() && e5.getTag()=="X"){
            dangerPosition=e3;
            return true;
        }

        if (e6.getTag()==e9.getTag() && e6.getTag()=="X"){
            dangerPosition=e3;
            return true;
        }


        if (e1.getTag()==e5.getTag() && e1.getTag()=="X"){
            dangerPosition=e9;
            return true;
        }

        if (e3.getTag()==e6.getTag() && e3.getTag()=="X"){
            dangerPosition=e9;
            return true;
        }

        if (e7.getTag()==e8.getTag() && e7.getTag()=="X"){
            dangerPosition=e9;
            return true;
        }



        if (e1.getTag()==e4.getTag() && e1.getTag()=="X"){
            dangerPosition=e7;
            return true;
        }

        if (e8.getTag()==e9.getTag() && e8.getTag()=="X"){
            dangerPosition=e7;
            return true;
        }

        if (e3.getTag()==e5.getTag() && e5.getTag()=="X"){
            dangerPosition=e7;
            return true;
        }


        //Centrul 5 are gradul 4 de pericol
        if(e1.getTag()==e9.getTag() && e1.getTag()=="X"){
            dangerPosition=e5;
            return true;
        }

        if(e2.getTag()==e8.getTag() && e2.getTag()=="X"){
            dangerPosition=e5;
            return true;
        }

        if(e3.getTag()==e7.getTag() && e3.getTag()=="X"){
            dangerPosition=e5;
            return true;
        }

        if(e4.getTag()==e6.getTag() && e4.getTag()=="X"){
            dangerPosition=e5;
            return true;
        }


        //Punctele 2,4,6,8 au gradul 2 de pericol
        if(e1.getTag()==e3.getTag() && e1.getTag()=="X"){
            dangerPosition=e2;
            return true;
        }

        if(e5.getTag()==e8.getTag() && e5.getTag()=="X"){
            dangerPosition=e2;
            return true;
        }


        if(e1.getTag()==e7.getTag() && e1.getTag()=="X"){
            dangerPosition=e4;
            return true;
        }

        if(e5.getTag()==e6.getTag() && e5.getTag()=="X"){
            dangerPosition=e4;
            return true;
        }

        if(e5.getTag()==e4.getTag() && e4.getTag()=="X"){
            dangerPosition=e6;
            return true;
        }

        if(e3.getTag()==e9.getTag() && e3.getTag()=="X"){
            dangerPosition=e6;
            return true;
        }



        if(e2.getTag()==e5.getTag() && e2.getTag()=="X"){
            dangerPosition=e8;
            return true;
        }

        if(e7.getTag()==e9.getTag() && e7.getTag()=="X"){
            dangerPosition=e8;
            return true;
        }

        dangerPosition = null;
        return false;
    }
    private ImageView getFirsthDangerPositionDetected(){
        ///Colturile 1,3,7,9 au grad 3 de pericol;
        if (e2.getTag()==e3.getTag() && e2.getTag()=="X"){
            return e1;
        }
        if (e5.getTag()==e9.getTag() && e5.getTag()=="X"){
            return  e1;
        }

        if(e4.getTag()==e7.getTag() && e4.getTag()=="X"){
            return  e1;
        }


        if (e1.getTag()==e2.getTag() && e1.getTag()=="X"){
            return  e3;
        }

        if (e5.getTag()==e7.getTag() && e5.getTag()=="X"){
           return  e3;

        }

        if (e6.getTag()==e9.getTag() && e6.getTag()=="X"){
            return e3;
        }


        if (e1.getTag()==e5.getTag() && e1.getTag()=="X"){
            return  e9;
        }

        if (e3.getTag()==e6.getTag() && e3.getTag()=="X"){
            return  e9;

        }

        if (e7.getTag()==e8.getTag() && e7.getTag()=="X"){
           return e9;
        }

        if (e1.getTag()==e4.getTag() && e1.getTag()=="X"){
            return e7;
        }

        if (e8.getTag()==e9.getTag() && e8.getTag()=="X"){
            return  e7;
        }

        if (e3.getTag()==e5.getTag() && e5.getTag()=="X"){
            return e7;
        }


        //Centrul 5 are gradul 4 de pericol
        if(e1.getTag()==e9.getTag() && e1.getTag()=="X"){
            return  e5;
        }

        if(e2.getTag()==e8.getTag() && e2.getTag()=="X"){
            return  e5;
        }

        if(e3.getTag()==e7.getTag() && e3.getTag()=="X"){
            return  e5;
        }

        if(e4.getTag()==e6.getTag() && e4.getTag()=="X"){
            return e5;
        }


        //Punctele 2,4,6,8 au gradul 2 de pericol
        if(e1.getTag()==e3.getTag() && e1.getTag()=="X"){
            return e2;
        }

        if(e5.getTag()==e8.getTag() && e5.getTag()=="X"){
            return e2;
        }


        if(e1.getTag()==e7.getTag() && e1.getTag()=="X"){
            return e4;
        }

        if(e5.getTag()==e6.getTag() && e5.getTag()=="X"){
            return e4;
        }

        if(e5.getTag()==e4.getTag() && e4.getTag()=="X"){
            return e6;
        }

        if(e3.getTag()==e9.getTag() && e3.getTag()=="X"){
            return e6;
        }



        if(e2.getTag()==e5.getTag() && e2.getTag()=="X"){
            return e8;
        }

        if(e7.getTag()==e9.getTag() && e7.getTag()=="X"){
            return e8;
        }
        return null;
    }

    private ArrayList<Integer> listDangerPositionArrised(){
        ///Colturile 1,3,7,9 au grad 3 de pericol;
        ArrayList<Integer> listDangerPosition = new ArrayList<>();

        if (e2.getTag()==e3.getTag() && e2.getTag()=="X"){
            listDangerPosition.add(1);
        }
        if (e5.getTag()==e9.getTag() && e5.getTag()=="X"){
            listDangerPosition.add(1);
        }

        if(e4.getTag()==e7.getTag() && e4.getTag()=="X"){
            listDangerPosition.add(1);
        }


        if (e1.getTag()==e2.getTag() && e1.getTag()=="X"){
            listDangerPosition.add(3);
        }

        if (e5.getTag()==e7.getTag() && e5.getTag()=="X"){
            listDangerPosition.add(3);
        }

        if (e6.getTag()==e9.getTag() && e6.getTag()=="X"){
            listDangerPosition.add(3);
        }


        if (e1.getTag()==e5.getTag() && e1.getTag()=="X"){
            listDangerPosition.add(9);
        }

        if (e3.getTag()==e6.getTag() && e3.getTag()=="X"){
            listDangerPosition.add(9);
        }

        if (e7.getTag()==e8.getTag() && e7.getTag()=="X"){
            listDangerPosition.add(9);
        }

        if (e1.getTag()==e4.getTag() && e1.getTag()=="X"){
            listDangerPosition.add(7);
        }

        if (e8.getTag()==e9.getTag() && e8.getTag()=="X"){
            listDangerPosition.add(7);
        }

        if (e3.getTag()==e5.getTag() && e5.getTag()=="X"){
            listDangerPosition.add(7);
        }


        //Centrul 5 are gradul 4 de pericol
        if(e1.getTag()==e9.getTag() && e1.getTag()=="X"){
            listDangerPosition.add(5);
        }

        if(e2.getTag()==e8.getTag() && e2.getTag()=="X"){
            listDangerPosition.add(5);
        }

        if(e3.getTag()==e7.getTag() && e3.getTag()=="X"){
            listDangerPosition.add(5);
        }

        if(e4.getTag()==e6.getTag() && e4.getTag()=="X"){
            listDangerPosition.add(5);
        }


        //Punctele 2,4,6,8 au gradul 2 de pericol
        if(e1.getTag()==e3.getTag() && e1.getTag()=="X"){
            listDangerPosition.add(2);
        }

        if(e5.getTag()==e8.getTag() && e5.getTag()=="X"){
            listDangerPosition.add(2);
        }


        if(e1.getTag()==e7.getTag() && e1.getTag()=="X"){
            listDangerPosition.add(4);
        }

        if(e5.getTag()==e6.getTag() && e5.getTag()=="X"){
            listDangerPosition.add(4);
        }

        if(e5.getTag()==e4.getTag() && e4.getTag()=="X"){
            listDangerPosition.add(6);
        }

        if(e3.getTag()==e9.getTag() && e3.getTag()=="X"){
            listDangerPosition.add(6);
        }


        if(e2.getTag()==e5.getTag() && e2.getTag()=="X"){
            listDangerPosition.add(8);
        }

        if(e7.getTag()==e9.getTag() && e7.getTag()=="X"){
            listDangerPosition.add(8);
        }
        Set<Integer> uniqueSet = new HashSet<>(listDangerPosition);
        ArrayList<Integer> collapsedList = new ArrayList<>(uniqueSet);

        return collapsedList;
    }

    private void CompInteligenceMedium(){


        // Pentru a genera un număr întreg într-un anumit interval, poți face astfel:
        int min = 1;
        int max = 9;

        ArrayList<Integer> invalidBoard = new ArrayList<>();
        ArrayList<Integer> dangerPositionFinded = listDangerPositionArrised();
        ArrayList<Integer> dangerPositionSolved = new ArrayList<>();
        int dangerPositionInt;




//
//        ArrayList<ImageView> dangerSolved = new ArrayList<>();
//        ImageView dangerToSolve=null;
//
//        for (int i=0;i<dangerPositionFinded.size();i++){
//
//            Log.e("EELE:",dangerPositionFinded.get(i).toString());
//
//            if (dangerPositionFinded.get(i).getTag()=="X" || dangerPositionFinded.get(i).getTag()=="O"){
//                dangerSolved.add(dangerPositionFinded.get(i));
//            }else{
//                dangerToSolve = dangerPositionFinded.get(i);
//                filledBox.set()
//            }
//        }
//
//






        if (dangerPositionFinded.size()==0){
            Toast.makeText(getApplicationContext(),"No Danger",Toast.LENGTH_SHORT).show();
            for (int i=0;i<filledBox.size();i++){
            if (filledBox.get(i)==true)
                invalidBoard.add(i);
            }
            Random random = new Random();
            int numarAleatoriu;

            do{
                numarAleatoriu = random.nextInt((max - min) + 1) + min;
            } while(invalidBoard.contains(numarAleatoriu));
            ImageView tmp = null;
            switch (numarAleatoriu){
                case 1:
                    tmp = e1;
                    break;
                case 2:
                    tmp = e2;
                    break;
                case 3:
                    tmp = e3;
                    break;
                case 4:
                    tmp = e4;
                    break;
                case 5:
                    tmp = e5;
                    break;
                case 6:
                    tmp = e6;
                    break;
                case 7:
                    tmp = e7;
                    break;
                case 8:
                    tmp = e8;
                    break;
                case 9:
                    tmp = e9;
                    break;
                default :
                    break;
            }
            tmp.setBackgroundResource(R.drawable.baseline_radio_button_unchecked_24);
            tmp.setTag("O");
            filledBox.set(numarAleatoriu,true);
            playTurn=true;
            checkFillComplet();

        }else if (dangerPositionFinded.size()>dangerPositionSolved.size()){
            for (int i=0;i<dangerPositionFinded.size();i++){
                    if (playTurn)
                        break;
                    // elementul este liber si reprezinta un pericol
                    if (getStatusElement(dangerPositionFinded.get(i)))
                    {
                        dangerPositionInt=Integer.valueOf(dangerPositionFinded.get(i).toString());
                        ImageView tmp = null;
                        switch (dangerPositionInt){
                            case 1:
                                tmp = e1;
                                break;
                            case 2:
                                tmp = e2;
                                break;
                            case 3:
                                tmp = e3;
                                break;
                            case 4:
                                tmp = e4;
                                break;
                            case 5:
                                tmp = e5;
                                break;
                            case 6:
                                tmp = e6;
                                break;
                            case 7:
                                tmp = e7;
                                break;
                            case 8:
                                tmp = e8;
                                break;
                            case 9:
                                tmp = e9;
                                break;
                            default :
                                break;
                        }
                        tmp.setBackgroundResource(R.drawable.radio);
                        tmp.setTag("O");
                        filledBox.set(dangerPositionInt,true);
                        dangerPositionSolved.add(dangerPositionInt);
                        playTurn=true;
                        checkFillComplet();
                    }else{
                            Toast.makeText(getApplicationContext(),"Danger Solved",Toast.LENGTH_SHORT).show();
                            for (int j=0;j<filledBox.size();j++){
                                if (filledBox.get(j)==true)
                                    invalidBoard.add(j);
                            }
                            Random random = new Random();
                            int numarAleatoriu;

                            do{
                                numarAleatoriu = random.nextInt((max - min) + 1) + min;
                            } while(invalidBoard.contains(numarAleatoriu));
                            ImageView tmp = null;
                            switch (numarAleatoriu){
                                case 1:
                                    tmp = e1;
                                    break;
                                case 2:
                                    tmp = e2;
                                    break;
                                case 3:
                                    tmp = e3;
                                    break;
                                case 4:
                                    tmp = e4;
                                    break;
                                case 5:
                                    tmp = e5;
                                    break;
                                case 6:
                                    tmp = e6;
                                    break;
                                case 7:
                                    tmp = e7;
                                    break;
                                case 8:
                                    tmp = e8;
                                    break;
                                case 9:
                                    tmp = e9;
                                    break;
                                default :
                                    break;
                            }
                            tmp.setBackgroundResource(R.drawable.radio);
                            tmp.setTag("O");
                            filledBox.set(numarAleatoriu,true);
                            playTurn=true;
                            checkFillComplet();
                     }
            }
            Toast.makeText(getApplicationContext(),"Danger findend",Toast.LENGTH_SHORT).show();
        }



    }

    private boolean getStatusElement(int id){
        ImageView tmp = null;

        switch (id){
            case 1:
                tmp = e1;
                break;
            case 2:
                tmp = e2;
                break;
            case 3:
                tmp = e3;
                break;
            case 4:
                tmp = e4;
                break;
            case 5:
                tmp = e5;
                break;
            case 6:
                tmp = e6;
                break;
            case 7:
                tmp = e7;
                break;
            case 8:
                tmp = e8;
                break;
            case 9:
                tmp = e9;
                break;
            default :
                break;
        }
        if (tmp!=null && tmp.getTag().toString()!="O" && tmp.getTag().toString()!="X")
            return  true;
        return false;
    }

    private void CompInteligenceAdvance(){

        ImageView tmp =null;
        int idFillBox = 0;
        int min = 1;
        int max = 9;
        ArrayList<Integer> invalidPositionToSelect = new ArrayList<>();


        if (checkOportunityToWinAndGetPosition()!=-1){
            idFillBox = checkOportunityToWinAndGetPosition();
        }else if(checkDangerToLoseAndGetPosition()!=-1){
            idFillBox = checkDangerToLoseAndGetPosition();
        }else if(checkDangerToLoseAndGetPosition()==-1 && checkOportunityToWinAndGetPosition()==-1){
            //seteaza positie random din cele libere - cu vectorul invalidPositionToSelect- selectam care positi nu sunt libere;
            for (int j=0;j<filledBox.size();j++){
                if (filledBox.get(j)==true)
                    invalidPositionToSelect.add(j);
            }

            Random random = new Random();
            do{
                idFillBox = random.nextInt((max - min) + 1) + min;
            } while(invalidPositionToSelect.contains(idFillBox));
        }

        //aici trebuie sa selectez care caseta sa o aleg
            //
        //trebuie sa am grije sa nu suprascriu adica sa setez care casute sunt libere


        //deci prima data fac o lista cu casutele libere. de selectat.--deja o am creata in vectorul fileldBox
            //am grije sa nu ma bat --deci din caustele libere sa le aleg pe acelea care as fi in pericol daca nu leas bifa.--
        // deci prima oara testez daca exista oportunitate sa castig daca da o aleg pe aceea daca nu le aleg pe acelea care sunt danger
        // iar daca nu e nici oportunitat, nici danger dam la nimereala.
            //din casutele libere sa le aleg pe aceea care imi ofera sans sa castig daca o aleg chiar daca daca exista o casuta periculoasa.

        switch (idFillBox){
            case 1:
                tmp = e1;
                break;
            case 2:
                tmp = e2;
                break;
            case 3:
                tmp = e3;
                break;
            case 4:
                tmp = e4;
                break;
            case 5:
                tmp = e5;
                break;
            case 6:
                tmp = e6;
                break;
            case 7:
                tmp = e7;
                break;
            case 8:
                tmp = e8;
                break;
            case 9:
                tmp = e9;
                break;
            default :
                break;
        }
        tmp.setBackgroundResource(R.drawable.radio);
        tmp.setTag("O");
        filledBox.set(idFillBox,true);
        playTurn=true;
        checkFillComplet();
    }

    private Integer checkOportunityToWinAndGetPosition(){

        int postionToWin = -1;

        ///Colturile 1,3,7,9 au grad 3 de pericol;
        if (e2.getTag()==e3.getTag() && e2.getTag()=="O" && e1.getTag()!="X"){
            return 1;
        }

        if (e5.getTag()==e9.getTag() && e5.getTag()=="O" && e1.getTag()!="X"){
            return 1;
        }

        if(e4.getTag()==e7.getTag() && e4.getTag()=="0" && e1.getTag()!="X"){
            return 1;
        }


        if (e1.getTag()==e2.getTag() && e1.getTag()=="O" && e3.getTag()!="X"){
            return 3;
        }

        if (e5.getTag()==e7.getTag() && e5.getTag()=="O" && e3.getTag()!="X"){
            return 3;
        }

        if (e6.getTag()==e9.getTag() && e6.getTag()=="O" && e3.getTag()!="X"){
            return 3;
        }


        if (e1.getTag()==e5.getTag() && e1.getTag()=="O" && e9.getTag()!="X"){
            return 9;
        }

        if (e3.getTag()==e6.getTag() && e3.getTag()=="O" && e9.getTag()!="X"){
            return 9;
        }

        if (e7.getTag()==e8.getTag() && e7.getTag()=="O" && e9.getTag()!="X"){
            return 9;
        }



        if (e1.getTag()==e4.getTag() && e1.getTag()=="O" && e7.getTag()!="X"){
            return 7;
        }

        if (e8.getTag()==e9.getTag() && e8.getTag()=="O" &&  e7.getTag()!="X"){
            return 7;
        }

        if (e3.getTag()==e5.getTag() && e5.getTag()=="O" && e7.getTag()!="X"){
            return 7;
        }


        //Centrul 5 are gradul 4 de pericol
        if(e1.getTag()==e9.getTag() && e1.getTag()=="O" &&  e5.getTag()!="X"){
            return 5;
        }

        if(e2.getTag()==e8.getTag() && e2.getTag()=="O" && e5.getTag()!="X"){
            return 5;
        }

        if(e3.getTag()==e7.getTag() && e3.getTag()=="O" && e5.getTag()!="X"){
            return 5;
        }

        if(e4.getTag()==e6.getTag() && e4.getTag()=="O" && e5.getTag()!="X"){
            return 5;
        }


        //Punctele 2,4,6,8 au gradul 2 de pericol
        if(e1.getTag()==e3.getTag() && e1.getTag()=="O" && e2.getTag()!="X"){
            return 2;
        }

        if(e5.getTag()==e8.getTag() && e5.getTag()=="O" && e2.getTag()!="X"){
            return 2;
        }


        if(e1.getTag()==e7.getTag() && e1.getTag()=="O" && e4.getTag()!="X"){
            return 4;
        }

        if(e5.getTag()==e6.getTag() && e5.getTag()=="O" && e4.getTag()!="X"){
            return 4;
        }

        if(e5.getTag()==e4.getTag() && e4.getTag()=="O" && e6.getTag()!="X"){
            return 6;
        }

        if(e3.getTag()==e9.getTag() && e3.getTag()=="O" && e6.getTag()!="X"){
            return 6;
        }



        if(e2.getTag()==e5.getTag() && e2.getTag()=="O" && e8.getTag()!="X"){
            return 8;
        }

        if(e7.getTag()==e9.getTag() && e7.getTag()=="O" && e8.getTag()!="X"){
            return 8;
        }

        return postionToWin;
    }
    private Integer checkDangerToLoseAndGetPosition(){

        int postionToLose = -1;

        ///Colturile 1,3,7,9 au grad 3 de pericol;
        if (e2.getTag()==e3.getTag() && e2.getTag()=="X" && e1.getTag()!="O"){
            return 1;
        }

        if (e5.getTag()==e9.getTag() && e5.getTag()=="X" && e1.getTag()!="O"){
            return 1;
        }

        if(e4.getTag()==e7.getTag() && e4.getTag()=="X" && e1.getTag()!="O"){
            return 1;
        }


        if (e1.getTag()==e2.getTag() && e1.getTag()=="X" && e3.getTag()!="O"){
            return 3;
        }

        if (e5.getTag()==e7.getTag() && e5.getTag()=="X" && e3.getTag()!="O"){
            return 3;
        }

        if (e6.getTag()==e9.getTag() && e6.getTag()=="X" && e3.getTag()!="O"){
            return 3;
        }


        if (e1.getTag()==e5.getTag() && e1.getTag()=="X" && e9.getTag()!="O"){
            return 9;
        }

        if (e3.getTag()==e6.getTag() && e3.getTag()=="X" && e9.getTag()!="O"){
            return 9;
        }

        if (e7.getTag()==e8.getTag() && e7.getTag()=="X" && e9.getTag()!="O"){
            return 9;
        }

        if (e1.getTag()==e4.getTag() && e1.getTag()=="X" && e7.getTag()!="O"){
            return 7;
        }

        if (e8.getTag()==e9.getTag() && e8.getTag()=="X" &&  e7.getTag()!="O"){
            return 7;
        }

        if (e3.getTag()==e5.getTag() && e5.getTag()=="X" && e7.getTag()!="O"){
            return 7;
        }


        //Centrul 5 are gradul 4 de pericol
        if(e1.getTag()==e9.getTag() && e1.getTag()=="X" &&  e5.getTag()!="O"){
            return 5;
        }

        if(e2.getTag()==e8.getTag() && e2.getTag()=="X" && e5.getTag()!="O"){
            return 5;
        }

        if(e3.getTag()==e7.getTag() && e3.getTag()=="X" && e5.getTag()!="O"){
            return 5;
        }

        if(e4.getTag()==e6.getTag() && e4.getTag()=="X" && e5.getTag()!="O"){
            return 5;
        }


        //Punctele 2,4,6,8 au gradul 2 de pericol
        if(e1.getTag()==e3.getTag() && e1.getTag()=="X" && e2.getTag()!="O"){
            return 2;
        }

        if(e5.getTag()==e8.getTag() && e5.getTag()=="X" && e2.getTag()!="O"){
            return 2;
        }


        if(e1.getTag()==e7.getTag() && e1.getTag()=="X" && e4.getTag()!="O"){
            return 4;
        }

        if(e5.getTag()==e6.getTag() && e5.getTag()=="X" && e4.getTag()!="O"){
            return 4;
        }

        if(e5.getTag()==e4.getTag() && e4.getTag()=="X" && e6.getTag()!="O"){
            return 6;
        }

        if(e3.getTag()==e9.getTag() && e3.getTag()=="X" && e6.getTag()!="O"){
            return 6;
        }


        if(e2.getTag()==e5.getTag() && e2.getTag()=="O" && e8.getTag()!="O"){
            return 8;
        }

        if(e7.getTag()==e9.getTag() && e7.getTag()=="O" && e8.getTag()!="O"){
            return 8;
        }

        return postionToLose;
    }
    private void CompInteligenceMaster(){


        //aici trebuie sa selectez care caseta sa o aleg
        //
        //trebuie sa am grije sa nu suprascriu adica sa setez care casute sunt libere


        //deci prima data fac o lista cu casutele libere. de selectat.--deja o am creata in vectorul fileldBox
        //am grije sa nu ma bat --deci din caustele libere sa le aleg pe acelea care as fi in pericol daca nu leas bifa.--
        // deci prima oara testez daca exista oportunitate sa castig daca da o aleg pe aceea daca nu le aleg pe acelea care sunt danger
        // iar daca nu e nici oportunitat, nici danger dam la in functie de logica ce ofera ce mai mare probabilitate.
        // .
        //din casutele libere sa le aleg pe aceea care imi ofera sans sa castig daca o aleg chiar daca daca exista o casuta periculoasa.

    }

    private void resetGame(){
        playTurn = true;
        winnerID="NO";
        resetGame.setEnabled(false);

        e1.setBackgroundResource(R.color.brg);
        e1.setImageDrawable(null);
        e1.setTag(1);

        e2.setBackgroundResource(R.color.brg);
        e2.setImageDrawable(null);
        e2.setTag(2);

        e3.setBackgroundResource(R.color.brg);
        e3.setImageDrawable(null);
        e3.setTag(3);

        e4.setBackgroundResource(R.color.brg);
        e4.setImageDrawable(null);
        e4.setTag(4);

        e5.setBackgroundResource(R.color.brg);
        e5.setImageDrawable(null);
        e5.setTag(5);

        e6.setBackgroundResource(R.color.brg);
        e6.setImageDrawable(null);
        e6.setTag(6);

        e7.setBackgroundResource(R.color.brg);
        e7.setImageDrawable(null);
        e7.setTag(7);

        e8.setBackgroundResource(R.color.brg);
        e8.setImageDrawable(null);
        e8.setTag(8);

        e9.setBackgroundResource(R.color.brg);
        e9.setImageDrawable(null);
        e9.setTag(9);

        for (int i=0;i<10;i++)
        {
            filledBox.set(i,false);
        }

        totalGamesPlayed++;
    }

    private void updateDataGame(){
        // Obțineți SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), getApplicationContext().MODE_PRIVATE);

        // Obțineți un editor pentru a putea modifica SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //obtine data anterioare
        // Accesați informații
        currentExperience = sharedPreferences.getInt("userExp", 0);

        currentExperience+=1000000;
        // Actualizați valoarea folosind cheia corespunzătoare
        editor.putInt("userExp", currentExperience);

// Salvați modificările
        editor.apply();

    }

    private void updateGameWinMode(int mode){
        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        // Obțineți un editor pentru a putea modifica SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();

        switch (mode){
            case 1:   editor.putInt("winPath1", 1);break;
            case 2:   editor.putInt("winPath2", 1);break;
            case 3:   editor.putInt("winPath3", 1);break;
            case 4:   editor.putInt("winPath4", 1);break;
            case 5:   editor.putInt("winPath5", 1);break;
            case 6:   editor.putInt("winPath6", 1);break;
            case 7:   editor.putInt("winPath7", 1);break;
            case 8:   editor.putInt("winPath8", 1);break;
            default: break;
        }

        // Salvați modificările
        editor.apply();
        checkInsignEightPath();
    }

    private void checkInsignEightPath(){
        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);

        int w1,w2,w3,w4,w5,w6,w7,w8;
        w1=sharedPreferences.getInt("winPath1", 0);
        w2=sharedPreferences.getInt("winPath2", 0);
        w3=sharedPreferences.getInt("winPath3", 0);
        w4=sharedPreferences.getInt("winPath4", 0);
        w5=sharedPreferences.getInt("winPath5", 0);
        w6=sharedPreferences.getInt("winPath6", 0);
        w7=sharedPreferences.getInt("winPath7", 0);
        w8=sharedPreferences.getInt("winPath8", 0);

        if (w1==w2 && w3==w4 && w5==w6 && w7==w8 && w2==w3 && w4==w5 && w6==w7 && w8==1){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("insignEightPath", 1);
            editor.apply();
        }
    }
    private void exitGame(){
        updateDataGame();
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