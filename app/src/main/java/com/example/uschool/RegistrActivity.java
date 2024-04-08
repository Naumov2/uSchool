package com.example.uschool;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.uschool.databinding.ActivityRegistrBinding;

public class RegistrActivity extends AppCompatActivity {
    Spinner spinner;
    private ActivityRegistrBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        binding = ActivityRegistrBinding.inflate((getLayoutInflater()));
        spinner = findViewById(R.id.function);

        binding.loginBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
}