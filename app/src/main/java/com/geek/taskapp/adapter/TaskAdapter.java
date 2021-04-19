package com.geek.taskapp.adapter;

import android.app.AlertDialog;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geek.taskapp.databinding.ItemTaskBinding;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private final ArrayList<String> list = new ArrayList<>();
    private ItemClickListener listener;
    private ItemTaskBinding binding;

    public void addItem(String title) {
        list.add(title);
        notifyItemInserted(list.size()-1);
    }

    public void setItem(String title, int position) {
        list.set(position, title);
        notifyItemChanged(position);
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskViewHolder(ItemTaskBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
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
        private String title;

        public TaskViewHolder(@NonNull ItemTaskBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            binding.getRoot().setOnClickListener(this);
            binding.getRoot().setOnLongClickListener(v -> {
                showAlertDialog();
                return true;
            });
        }


        public void onBind(String title) {
            this.title = title;
            if (getAdapterPosition() % 2 == 0) {
                binding.colorLayout.setBackgroundColor(Color.WHITE);
            }
            binding.itemTitleTextView.setText(title);
        }

        private void showAlertDialog() {
            AlertDialog alertDialog = new AlertDialog.Builder(itemView.getContext()).setMessage("Вы хотите удалить?")
                    .setPositiveButton("Да", (dialog, which) -> {
                        list.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                    })
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
