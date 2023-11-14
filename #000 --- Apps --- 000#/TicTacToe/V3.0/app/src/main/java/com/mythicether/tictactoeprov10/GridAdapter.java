package com.mythicether.tictactoeprov10;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {

    List<String> nameInsign;
    List<String> tagInsign;
    List<Integer> imgInsign;
    Context myContext;
    LayoutInflater layoutInflater;

    public GridAdapter(Context context, List<String> nameInsign, List<String> tagInsign, List<Integer> imgInsign) {
        this.nameInsign = nameInsign;
        this.tagInsign=tagInsign;
        this.imgInsign = imgInsign;
        this.myContext = context;
        this.layoutInflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.custom_grid_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridAdapter.ViewHolder holder, int position) {
        holder.imageIsg.setImageDrawable(myContext.getResources().getDrawable(imgInsign.get(position)));
        holder.nameIsg.setText(nameInsign.get(position));
        holder.nameIsg.setTag(tagInsign.get(position));
        holder.layoutGrid.setTag(tagInsign.get(position));
        checkStatusPlayer(holder.layoutGrid);
    }

    @Override
    public int getItemCount() {
        return nameInsign.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameIsg;
        LinearLayout layoutGrid;
        ImageView imageIsg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameIsg = (TextView) itemView.findViewById(R.id.nameIsg);
            imageIsg = (ImageView) itemView.findViewById(R.id.imageIsg);
            layoutGrid= (LinearLayout) itemView.findViewById(R.id.layoutGrid);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "POZ:" + nameIsg.getTag(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    private void checkStatusPlayer(View view) {
        SharedPreferences sharedPreferences = myContext.getSharedPreferences(myContext.getPackageName(), myContext.MODE_PRIVATE);
        int insignEightPath = sharedPreferences.getInt("insignEightPath", 0);
        TextView tt= (TextView) view.findViewById(R.id.nameIsg);

        if (tt.getTag()=="EightPath" && insignEightPath==1) {
            view.setBackgroundColor(myContext.getColor(R.color.bg));
        }
    }
}