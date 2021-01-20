package com.example.NeckLine.HomeHelper;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.NeckLine.R;

public class ModelsViewHolder extends RecyclerView.ViewHolder {

    ImageView itemImage;
    TextView itemName;
    CardView model;

    public ModelsViewHolder(@NonNull View itemView) {
        super(itemView);

        itemImage = itemView.findViewById(R.id.itemImage);
        itemName = itemView.findViewById(R.id.itemName);
        model = itemView.findViewById(R.id.model);
    }
}
