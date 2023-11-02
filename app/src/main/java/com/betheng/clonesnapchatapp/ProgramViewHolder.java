package com.betheng.clonesnapchatapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ProgramViewHolder {

    ImageView itemImage;
    TextView programTitle;

    ProgramViewHolder(View v){
        itemImage = v.findViewById(R.id.bitmojiImageView);
        programTitle = v.findViewById(R.id.userTextView);
    }

}
