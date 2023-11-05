package com.example.foodapp;

import android.app.Activity;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryRvAdapter extends RecyclerView.Adapter<CategoryRvAdapter.ViewHolder> {

    private final int RESOURCE_ID;
    private ArrayList<CategoryModel> mCategories;
    private int position;

    public CategoryRvAdapter(int resourceId, ArrayList<CategoryModel> categories) {
        RESOURCE_ID = resourceId;
        this.mCategories = categories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(RESOURCE_ID, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryModel category = mCategories.get(holder.getAdapterPosition());
        holder.textName.setText(category.getName());

        //TODO: set photo
        if (!TextUtils.equals(category.getPhoto(), "")) {
            Picasso.get()
                    .load(category.getPhoto())
                    .into(holder.imagePhoto);
        }

        //click, longClick
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPosition(holder.getAdapterPosition());
                Toast.makeText(
                                view.getContext(),
                                category.getName(),
                                Toast.LENGTH_LONG)
                        .show();
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                setPosition(holder.getAdapterPosition());
                return false;
            }
        });
    }
    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        private ImageView imagePhoto;
        private TextView textName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagePhoto = itemView.findViewById(R.id.imagePhoto);
            textName = itemView.findViewById(R.id.textName);

            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            Activity activity = (Activity) view.getContext();
            activity.getMenuInflater().inflate(
                    R.menu.menu_context_rv,
                    contextMenu
            );
        }
    }

}
