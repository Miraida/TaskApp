package com.geek.taskapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.geek.taskapp.R;
import com.geek.taskapp.constants.Keys;
import com.geek.taskapp.databinding.FragmentFormBinding;

import org.jetbrains.annotations.NotNull;

public class FormFragment extends Fragment {
    private boolean isChanged = false;
    private FragmentFormBinding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFormBinding.inflate(inflater, container, false);
        checkData();
        binding.addButton.setOnClickListener(v -> save());
        return binding.getRoot();
    }

    private void checkData() {
        if (getArguments() != null) {
            binding.editText.setText(getArguments().getString(Keys.TITLE_TEXT_KEY));
            isChanged = true;
        }
    }

    private void save() {
        getParentFragmentManager().setFragmentResult(Keys.REQUEST_KEY_HOME_FRAGMENT, getBundle());
        close();
    }

    private Bundle getBundle() {
        String text = binding.editText.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString(Keys.TITLE_TEXT_KEY, text);
        bundle.putBoolean(Keys.IS_CHANGED_BOOLEAN, isChanged);
        return bundle;
    }

    private void close() {
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigateUp();
    }
}