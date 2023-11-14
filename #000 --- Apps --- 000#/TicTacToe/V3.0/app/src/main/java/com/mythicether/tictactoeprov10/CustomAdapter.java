package com.mythicether.tictactoeprov10;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {

    private String [] myRanksName;
    private String [] myRanksDesk;
    private int [] myRanksDrawable;
    private LayoutInflater inflater;

    public CustomAdapter(Context ctx, String [] listNameRanks, String [] listDeskRank, int [] listImgRank) {
        myRanksName=listNameRanks;
        myRanksDesk=listDeskRank;
        myRanksDrawable=listImgRank;
        inflater=LayoutInflater.from(ctx);

    }


    @Override
    public int getCount() {
        return myRanksName.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view=inflater.inflate(R.layout.activity_item_ranks,null);
        TextView nameRankUI = (TextView) view.findViewById(R.id.titleRank);
        TextView deskRankUI = (TextView) view.findViewById(R.id.deskRank);
        ImageView imgRankUI = (ImageView) view.findViewById(R.id.imgRank);

        nameRankUI.setText(myRanksName[i]);
        deskRankUI.setText(myRanksDesk[i]);
        imgRankUI.setImageDrawable(view.getResources().getDrawable(myRanksDrawable[i]));

        return view;
    }
}
