package com.geek.taskapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.geek.taskapp.R;
import com.geek.taskapp.databinding.ItemBoardBinding;
import com.geek.taskapp.ui.board.OnButtonClickListener;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.BoardViewHolder> {
    ItemBoardBinding binding;
    String[] desc = {"Powerful", "Message", "Useful"};
    int[] gif = new int[]{R.drawable.onboard_anim1, R.drawable.onboard_anim3, R.drawable.onboard_anim2};
    OnButtonClickListener listener;

    public void setOnClickListener(OnButtonClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public BoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BoardViewHolder(ItemBoardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BoardViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class BoardViewHolder extends RecyclerView.ViewHolder {
        public BoardViewHolder(@NonNull ItemBoardBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void onBind(int position) {
            Glide.with(itemView.getContext()).load(gif[position]).circleCrop().into(binding.itemBoardImageView);
            binding.itemBoardTextView.setText(desc[position]);
            if (position == 2) {
                binding.skipBtn.setVisibility(View.VISIBLE);
                binding.skipBtn.setOnClickListener(v -> listener.onClick());
            } else binding.skipBtn.setVisibility(View.GONE);
        }
    }
}
