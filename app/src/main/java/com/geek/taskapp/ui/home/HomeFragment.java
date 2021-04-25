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
import com.geek.taskapp.models.Task;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements ItemClickListener {
    TaskAdapter adapter;
    private int position;
    ArrayList<Task> list = new ArrayList<>();
    private FragmentHomeBinding binding;
    private NavController navController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new TaskAdapter(list);
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
            Task model = (Task) result.getSerializable(Keys.TASK_MODEL_KEY);
            if (isChanged) {
                list.set(position, model);
                adapter.notifyItemChanged(position);
            } else {
                list.add(model);
                adapter.notifyItemInserted(list.size() - 1);
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
    public void onItemClick(int position) {
        this.position = position;
        Task taskModel = list.get(position);
        bundle.putSerializable(Keys.TASK_MODEL_KEY, taskModel);
        openForm(true);
    }
}