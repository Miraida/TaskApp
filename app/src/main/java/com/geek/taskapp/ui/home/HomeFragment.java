package com.geek.taskapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import androidx.navigation.fragment.NavHostFragment;

import com.geek.taskapp.R;
import com.geek.taskapp.adapter.ItemClickListener;
import com.geek.taskapp.constants.Keys;
import com.geek.taskapp.adapter.TaskAdapter;
import com.geek.taskapp.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment implements ItemClickListener {
    TaskAdapter adapter;
    private int position;
    private FragmentHomeBinding binding;
    private NavController navController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new TaskAdapter();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        initList();
        checkFragmentResult();
        return binding.getRoot();
    }
    private void checkFragmentResult() {
        getParentFragmentManager().setFragmentResultListener(Keys.REQUEST_KEY_HOME_FRAGMENT, getViewLifecycleOwner(), (requestKey, result) -> {
            boolean isChanged = result.getBoolean(Keys.IS_CHANGED_BOOLEAN);
            String title = result.getString(Keys.TITLE_TEXT_KEY);
            if (isChanged) {
                adapter.setItem(title, position);
            } else {
                adapter.addItem(title);
            }
        });
    }

    private void initList() {
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(this);
        navController = NavHostFragment.findNavController(this);
        binding.fab.setOnClickListener(v -> openForm(false));
    }

    private void openForm(boolean isDataUpdate) {
        if (isDataUpdate)
            navController.navigate(R.id.action_navigation_home_to_formFragment, bundle);
        else navController.navigate(R.id.action_navigation_home_to_formFragment);
    }

    Bundle bundle = new Bundle();

    @Override
    public void onItemClick(int position, String title) {
        this.position = position;
        bundle.putString(Keys.TITLE_TEXT_KEY, title);
        openForm(true);
    }
}