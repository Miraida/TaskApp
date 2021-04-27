package com.geek.taskapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.geek.taskapp.App;
import com.geek.taskapp.R;
import com.geek.taskapp.constants.Keys;
import com.geek.taskapp.databinding.FragmentFormBinding;
import com.geek.taskapp.models.Task;

import org.jetbrains.annotations.NotNull;

public class FormFragment extends Fragment {
    private FragmentFormBinding binding;
    private Task model;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFormBinding.inflate(inflater, container, false);
        checkData();
        Log.d("TAG", "onCreateView: Form");
        binding.addButton.setOnClickListener(v -> save());
        return binding.getRoot();
    }

    private void checkData() {
        if (getArguments() != null) {
            long id = getArguments().getLong("ITEM_ID");
            model = App.appDataBase.taskDao().getTaskModel(id);
            binding.editText.setText(model.getTitle());
        }
    }

    private void save() {
        String title = binding.editText.getText().toString();
        Bundle bundle = new Bundle();
        if (model == null) {
             model = new Task(title, System.currentTimeMillis());
            App.appDataBase.taskDao().insert(model);
            bundle.putBoolean("isDataChanged",false);
        }
        else {
            App.appDataBase.taskDao().updateTitle(title,model.getId());
            bundle.putBoolean("isDataChanged",true);
        }
        getParentFragmentManager().setFragmentResult("add_or_update",bundle);
        close();
    }


    private void close() {
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigateUp();
    }
}