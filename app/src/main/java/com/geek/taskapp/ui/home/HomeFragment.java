package com.geek.taskapp.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.geek.taskapp.R;
import com.geek.taskapp.adapter.ItemClickListener;

import java.util.ArrayList;

import com.geek.taskapp.adapter.TaskAdapter;

public class HomeFragment extends Fragment implements ItemClickListener {
    TaskAdapter adapter;
    Bundle bundle = new Bundle();
    private ArrayList<String> list = new ArrayList<>();
    private int position;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new TaskAdapter();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.fab).setOnClickListener(v -> openForm());
        initList(view);
        checkFragmentResult();

    }

    private void checkFragmentResult() {
        getParentFragmentManager().setFragmentResultListener("rk_task", getViewLifecycleOwner(), (requestKey, result) -> {
            boolean isChanged = result.getBoolean("isChanged");
            String title = result.getString("TITLE_TEXT");
            if (isChanged) {
                Log.e("TAG", "checkFragmentResult true: " + title);
                adapter.setItem(title, position);
            } else {
                Log.e("TAG", "checkFragmentResult false: " + title);
                adapter.addItem(title);
            }
        });
    }

    private void initList(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(this);
    }

    private void openForm() {
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.formFragment);
    }

    @Override
    public void onItemClick(int position, String title) {
        this.position = position;
        bundle.putString("TEXT_KEY", title);
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.formFragment, bundle);
    }
}