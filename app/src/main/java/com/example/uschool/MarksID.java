package com.example.uschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MarksID extends AppCompatActivity {

    EditText lesson,mark;
    String dayS, monthS, yearS;
    Button markAdd;

    HashMap<String,String> markSnap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks_id);
        lesson = findViewById(R.id.lesson);
        mark = findViewById(R.id.mark);
        markAdd = findViewById(R.id.markAdd);
        CalendarView calendarView = new CalendarView(this);
        calendarView = findViewById(R.id.data);
        Bundle arguments = getIntent().getExtras();
        String id = arguments.getString("ID");
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                dayS = String.valueOf(dayOfMonth);
                monthS = String.valueOf(month+1);
                yearS = String.valueOf(year);
            }
        });
        markAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int markC = Integer.parseInt(mark.getText().toString());
                if(markC<=5 && markC >=1){
                    HashMap<String,String> p = new HashMap<>();
                    p.put(lesson.getText().toString()+": "+dayS+"*"+monthS,String.valueOf(markC));
                    FirebaseDatabase.getInstance().getReference().child("Users").child(id).child("Marks").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.getValue() == null){
                                FirebaseDatabase.getInstance().getReference().child("Users").child(id).child("Marks").setValue(p).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(MarksID.this,"Оценка выставленна", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else {
                                markSnap = (HashMap<String, String>) snapshot.getValue();
                                for (Map.Entry entry: markSnap.entrySet()) p.put((String) entry.getKey(),(String) entry.getValue());
                                FirebaseDatabase.getInstance().getReference().child("Users").child(id).child("Marks").setValue(p).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()) Toast.makeText(MarksID.this,"Оценка выставленна", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }
}