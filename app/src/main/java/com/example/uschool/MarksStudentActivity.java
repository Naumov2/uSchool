package com.example.uschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MarksStudentActivity extends ListActivity {

    private ArrayAdapter<String> mAdapter;
    private ArrayList<String> marks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Marks").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() != null)
                {
                    HashMap<String, String> temp = (HashMap<String, String>) snapshot.getValue();
                    for (Map.Entry entry: temp.entrySet()) marks.add("Предмет и дата: " + firstUp(entry.getKey().toString()) + " " + "Оценка: " + entry.getValue());
                }
                mAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, marks);
                setListAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public String firstUp(String str){
        return String.valueOf(str.toCharArray()[0]).toUpperCase() + str.substring(1).replace('*','.');
    }

}