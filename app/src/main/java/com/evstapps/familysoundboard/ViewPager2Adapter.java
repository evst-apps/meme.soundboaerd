package com.evstapps.familysoundboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;

class ViewPager2Adapter extends RecyclerView.Adapter<ViewPager2Adapter.ViewHolder> {

    private final Context ctx;
    ViewPager2Adapter(Context ctx) {
        this.ctx = ctx;
    }
    public final ArrayList<ItemContainer> itemContainers = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.activity_fragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemContainer ic = itemContainers.get(position);
        for(Item item : ic.items){
            if(item.view.getParent() != null) {
                ((ViewGroup)item.view.getParent()).removeView(item.view);
            }
            holder.flexboxLayout.addView(item.view);
        }
    }

    @Override
    public int getItemCount() {
        return itemContainers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final FlexboxLayout flexboxLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            flexboxLayout = itemView.findViewById(R.id.flex);
        }
    }
}