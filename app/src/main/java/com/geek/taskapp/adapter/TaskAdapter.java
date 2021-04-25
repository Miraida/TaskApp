package com.geek.taskapp.adapter;

import android.app.AlertDialog;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geek.taskapp.databinding.ItemTaskBinding;
import com.geek.taskapp.models.Task;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private final ArrayList<Task> list;
    private ItemClickListener listener;
    private ItemTaskBinding binding;

    public TaskAdapter(ArrayList<Task> list) {
        this.list = list;
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

        public TaskViewHolder(@NonNull ItemTaskBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            binding.getRoot().setOnClickListener(this);
            binding.getRoot().setOnLongClickListener(v -> {
                showAlertDialog();
                return true;
            });
        }
        public void onBind(Task model) {
            if (getAdapterPosition() % 2 == 0) {
                binding.colorLayout.setBackgroundColor(Color.WHITE);
            }
            binding.itemTitleTextView.setText(model.getTitle());
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
                listener.onItemClick(getAdapterPosition());
            }
        }

    }
    public void setOnClickListener(ItemClickListener listener) {
        this.listener = listener;
    }
}
