package com.ismummy.roitech.task.adapters;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ismummy.roitech.task.R;
import com.ismummy.roitech.task.models.Task;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ISMUMMY on 10/11/2016.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private ArrayList<Task> groupArrayList;
    private String[] color = {"#FFFFFF", "#F0F8FF", "#FAEBD7", "#F0FFFF", "#F5F5DC", "#FFE4C4", "#FFEBCD", "#DFF8DC", "#ADD8E6", "#B0C4DE"};

    public TaskAdapter(ArrayList<Task> groupArrayList) {
        this.groupArrayList = groupArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_task_row, parent, false);

        return new ViewHolder(itemView);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, start, desc;
        CardView card;

        ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            start = (TextView) view.findViewById(R.id.start);
            desc = (TextView) view.findViewById(R.id.description);
            card = (CardView) view.findViewById(R.id.card);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Task group = groupArrayList.get(position);
        holder.title.setText(group.getTitle());
        holder.start.setText(group.getStart());
        holder.desc.setText(group.getDescription());
        Random random = new Random();
        String color = this.color[random.nextInt(10)];
        try {
            holder.card.setCardBackgroundColor(Color.parseColor(color));
        } catch (Exception e) {
            Log.e("error", e.getMessage() + color);
        }
    }


    @Override
    public int getItemCount() {
        return groupArrayList.size();
    }

}