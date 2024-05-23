package com.example.uschool;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class SchStudent extends AppCompatActivity {
    TextView[] weekTv = new TextView[7];
    final List<String> week = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sch_student);
        weekTv[0] = findViewById(R.id.mondayTv);
        weekTv[1] = findViewById(R.id.tuesdayTv);
        weekTv[2] = findViewById(R.id.wednesdayTv);
        weekTv[3] = findViewById(R.id.thursdayTv);
        weekTv[4] = findViewById(R.id.fridayTv);
        weekTv[5] = findViewById(R.id.saturdayTv);
        weekTv[6] = findViewById(R.id.sundayTv);
        week.add("Понедельник");
        week.add("Вторник");
        week.add("Среда");
        week.add("Четверг");
        week.add("Пятница");
        week.add("Суббота");
        week.add("Воскресенье"  );
        String classUserd;
        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                changeSch(snapshot.child("numberClass").getValue().toString() + snapshot.child("parallel").getValue().toString());
                Log.e("nen", snapshot.child("numberClass").toString() + snapshot.child("parallel").toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    public void changeSch(String parallel)
    {
        for (String w :
                week) {
            FirebaseDatabase.getInstance().getReference().child("Sch").child(parallel).child(w).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList sch = (ArrayList) snapshot.getValue();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(w + "\n");
                    for (int i = 1; i < sch.size(); i++) stringBuilder.append(i+":"+sch.get(i)+"\n");
                    weekTv[week.indexOf(w)].setText(stringBuilder.toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}