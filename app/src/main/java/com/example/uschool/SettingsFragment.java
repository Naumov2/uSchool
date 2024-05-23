package com.example.uschool;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class SettingsFragment extends Fragment {
    TextView first, second, drink, candy;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        container.removeAllViews();
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    public SettingsFragment(){
        super(R.layout.fragment_settings);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        first = view.findViewById(R.id.first);
        second = view.findViewById(R.id.second);
        drink = view.findViewById(R.id.drink);
        candy = view.findViewById(R.id.candy);
        FirebaseDatabase.getInstance().getReference().child("Eat").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, String> temp = (HashMap<String, String>) snapshot.getValue();
                first.setText("Первое: " + temp.get("Первое"));
                second.setText("Второе: " + temp.get("Второе"));
                drink.setText("Напиток: " + temp.get("Напиток"));
                candy.setText("Десерт: " + temp.get("Десерт"));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}