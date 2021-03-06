package com.geek.taskapp.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.geek.taskapp.Prefs;
import com.geek.taskapp.databinding.FragmentProfileBinding;

import org.jetbrains.annotations.NotNull;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    Prefs prefs;
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("TAG", "onCreateView: Profile" );
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        binding.profileImageView.setOnClickListener(v -> checkPermission());
               prefs  = new Prefs(requireContext());
               if (prefs.getProfileText()!=null)
                binding.profileEditText.setText(prefs.getProfileText());
        return binding.getRoot();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("TAG", "onStop: Profile" );
        prefs.saveProfileText(binding.profileEditText.getText().toString());
    }

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    2000);
        } else {
            openGallery();
        }
    }

    private void openGallery() {
        Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(cameraIntent, 1000);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1000) {
                Uri returnUri = data.getData();
                binding.profileImageView.setImageURI(returnUri);
            }
        }
    }
}