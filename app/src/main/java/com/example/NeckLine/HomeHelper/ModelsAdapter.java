package com.example.NeckLine.HomeHelper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.NeckLine.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ModelsAdapter extends RecyclerView.Adapter<ModelsViewHolder> {

    Context context;
    ArrayList<ModelsModel> modelsModelArrayList;

    public ModelsAdapter(Context context, ArrayList<ModelsModel> modelsModelArrayList) {
        this.context = context;
        this.modelsModelArrayList = modelsModelArrayList;
    }

    @NonNull
    @Override
    public ModelsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_item_models, parent, false);
        return new ModelsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ModelsViewHolder holder, int position) {

        holder.itemName.setText(modelsModelArrayList.get(position).getItemName());
        Picasso.get().load(modelsModelArrayList.get(position).getItemImage()).into(holder.itemImage);

        holder.model.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ModelDetailsActivity.class);
                intent.putExtra("id",modelsModelArrayList.get(position).getItemId());
                intent.putExtra("name",modelsModelArrayList.get(position).getItemName());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelsModelArrayList.size();
    }
}
