package com.example.uschool;

import androidx.annotation.NonNull;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MarksActivity extends ListActivity {

    private ArrayAdapter<String> mAdapter;

    private HashMap<String, HashMap<String, String>> users = new HashMap<>();
    private ArrayList<String> students = new ArrayList<String>();
    private ArrayList<String> studentsID = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseDatabase.getInstance().getReference().child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users = (HashMap) snapshot.getValue();
                for (Map.Entry<String, HashMap<String, String>> entry : users.entrySet()) {
                    String key = entry.getKey();
                    HashMap<String, String> innerMap = entry.getValue();
                    if(users.get(key).get("func").equals("Администратор")) continue;
                    else {
                        students.add(users.get(key).get("usersurname") + " " + users.get(key).get("username") + " " + users.get(key).get("numberClass") + users.get(key).get("parallel"));
                        studentsID.add(key);
                    }
                }
                mAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, students);
                setListAdapter(mAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(this,MarksID.class);
        intent.putExtra("ID",studentsID.get(position));
        startActivity(intent);
    }
}