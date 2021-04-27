package com.geek.taskapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geek.taskapp.App;
import com.geek.taskapp.databinding.ItemTaskBinding;
import com.geek.taskapp.models.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<Task> list;
    private ItemClickListener listener;
    private ItemTaskBinding binding;
    private final Context context;

    public TaskAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void addList(List<Task> list) {
        this.list.addAll(list);
    }

    public void updateList(List<Task> list) {
        this.list = list;
        notifyItemRangeChanged(0, list.size());
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
           // binding.idTextView.setText(String.valueOf(model.getId()));
            binding.itemTitleTextView.setText(model.getTitle());
            binding.itemCreatedTextView.setText(DateUtils.formatDateTime
                    (context, model.getCreatedAt(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
                            | DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR));
        }

        private void showAlertDialog() {
            AlertDialog alertDialog = new AlertDialog.Builder(itemView.getContext()).setMessage("Вы хотите удалить?")
                    .setPositiveButton("Да", (dialog, which) -> {
                        App.appDataBase.taskDao().delete(list.get(getAdapterPosition()).getId());
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
