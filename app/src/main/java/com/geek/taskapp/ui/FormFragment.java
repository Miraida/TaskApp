package com.geek.taskapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.geek.taskapp.R;

public class FormFragment extends Fragment {
    EditText editText;
    boolean isChanged = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.editText);
        checkData();
        view.findViewById(R.id.add_button).setOnClickListener(v -> {
            save();
        });
    }

    private void checkData() {
        if (getArguments() != null) {
            editText.setText(getArguments().getString("TEXT_KEY"));
            isChanged = true;
        }
    }

    private void save() {
        String text = editText.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString("TITLE_TEXT", text);
        bundle.putBoolean("isChanged", isChanged);
        getParentFragmentManager().setFragmentResult("rk_task", bundle);
        close();
    }

    private void close() {
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigateUp();
    }
}