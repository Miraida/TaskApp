package com.geek.taskapp.adapter;

import android.app.AlertDialog;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geek.taskapp.R;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    ArrayList<String> list;
    ItemClickListener listener;

    public TaskAdapter() {
        this.list = new ArrayList<>();
    }

    public void addItem(String title) {
        list.add(title);
        notifyItemInserted(list.size() - 1);
    }

    public void setItem(String title, int position) {
        list.set(position, title);
        notifyItemChanged(position);
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView textTitle_tv;
        private String title;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(v -> {
                showAlertDialog();
                return true;
            });
            textTitle_tv = itemView.findViewById(R.id.item_title_textView);
        }


        public void onBind(String title) {
            this.title = title;
            if (getAdapterPosition() % 2 == 0) {
                itemView.findViewById(R.id.colorLayout).setBackgroundColor(Color.WHITE);
            }
            textTitle_tv.setText(title);
        }

        private void showAlertDialog() {
            AlertDialog alertDialog = new AlertDialog.Builder(itemView.getContext()).setMessage("Вы хотите удалить?")
                    .setPositiveButton("Да", (dialog, which) -> {
                        list.remove(getAdapterPosition());
                        notifyDataSetChanged();})
                    .setNegativeButton("Нет", null)
                    .create();
            alertDialog.show();
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClick(getAdapterPosition(), title);
            }
        }
    }

    public void setOnClickListener(ItemClickListener listener) {
        this.listener = listener;
    }
}
