package com.geek.taskapp.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;

import androidx.navigation.fragment.NavHostFragment;

import com.geek.taskapp.App;
import com.geek.taskapp.R;
import com.geek.taskapp.adapter.ItemClickListener;
import com.geek.taskapp.adapter.TaskAdapter;
import com.geek.taskapp.databinding.FragmentHomeBinding;
import com.geek.taskapp.models.Task;
import java.util.List;

public class HomeFragment extends Fragment implements ItemClickListener {
    TaskAdapter adapter;
    private Long position;
    List<Task> list;
    private FragmentHomeBinding binding;
    private NavController navController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        adapter = new TaskAdapter(requireContext());
        list = App.appDataBase.taskDao().getAll();
        adapter.addList(list);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        initList();
        checkFragmentResult();
        return binding.getRoot();
    }

    private void checkFragmentResult() {
       getParentFragmentManager().setFragmentResultListener("add_or_update", getViewLifecycleOwner(), (requestKey, result) -> {
           list = App.appDataBase.taskDao().getAll();
           adapter.updateList(list);
       });
    }

    private void initList() {
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(this);
        navController = NavHostFragment.findNavController(this);
        binding.fab.setOnClickListener(v -> openForm(false));
    }


    private void openForm(boolean isDataToUpdate) {
        if (isDataToUpdate) navController.navigate(R.id.action_navigation_home_to_formFragment, getBundle(position));
        else navController.navigate(R.id.action_navigation_home_to_formFragment);
    }

    private Bundle getBundle(long position) {
        Bundle bundle = new Bundle();
        bundle.putLong("ITEM_ID", position);
        return bundle;
    }

    @Override
    public void onItemClick(int position) {
        this.position = list.get(position).getId();
        openForm(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.sort_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.sort_menu) {
            adapter.updateList(App.appDataBase.taskDao().getSortedList());
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }


}