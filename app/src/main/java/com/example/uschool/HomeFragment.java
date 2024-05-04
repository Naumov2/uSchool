package com.example.uschool;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class HomeFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        if (MainMenu.func == "Учитель") return inflater.inflate(R.layout.fragment_home_teacher, container, false);
//        else return inflater.inflate(R.layout.fragment_home_student, container, false);
        return inflater.inflate(R.layout.fragment_home_teacher, container, false);
    }
}