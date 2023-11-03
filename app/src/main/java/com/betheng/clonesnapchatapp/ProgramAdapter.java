package com.betheng.clonesnapchatapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;


public class ProgramAdapter extends ArrayAdapter<String> {
    Context context;
    int[] images;
    ArrayList<String> usernames;
    public ProgramAdapter(Context context, int[] images, ArrayList<String> usernames) {
        super(context, R.layout.single_item, R.id.userTextView, usernames);
        this.context = context;
        this.images = images;
        this.usernames = usernames;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View singleItem = convertView;
        ProgramViewHolder holder = null;
        if (singleItem == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            singleItem = layoutInflater.inflate(R.layout.single_item, parent, false);
            holder = new ProgramViewHolder(singleItem);
            singleItem.setTag(holder);
        } else {
            holder = (ProgramViewHolder) singleItem.getTag();
        }
        holder.itemImage.setImageResource(images[position]);
        holder.programTitle.setText(usernames.get(position));
//        singleItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getContext(), "You clicked:" + usernames.get(position), Toast.LENGTH_SHORT).show();
//            }
//        });

        return singleItem;
    }
}
