package com.geek.taskapp.ui.board;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geek.taskapp.Prefs;
import com.geek.taskapp.R;
import com.geek.taskapp.adapter.BoardAdapter;
import com.geek.taskapp.databinding.FragmentBoardBinding;

import org.jetbrains.annotations.NotNull;


public class BoardFragment extends Fragment {
    BoardAdapter boardAdapter;
    NavController navController;
    FragmentBoardBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boardAdapter = new BoardAdapter();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBoardBinding.inflate(inflater,container,false);
        navController = NavHostFragment.findNavController(this);
        ViewPager2 pager = binding.pager;
        pager.setAdapter(boardAdapter);
        binding.dots.setViewPager2(pager);
        boardAdapter.setOnClickListener(new OnButtonClickListener() {
            @Override
            public void onClick() {
                close();
            }
        });
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
              requireActivity().finish();
            }
        });

        return binding.getRoot();
    }

    private void close() {
        Prefs prefs = new Prefs(requireContext());
        prefs.saveBoardState();
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigateUp();
    }
}