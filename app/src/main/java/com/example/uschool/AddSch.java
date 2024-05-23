package com.example.uschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddSch extends AppCompatActivity {

    Button addSch;
    Spinner week,num,liter;
    EditText[] lessonTv = new EditText[7];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sch);

        addSch = findViewById(R.id.addSch);
        week = findViewById(R.id.weekSch);
        num = findViewById(R.id.numSch);
        liter = findViewById(R.id.literSch);
        lessonTv[0] = findViewById(R.id.oneL);
        lessonTv[1] = findViewById(R.id.twoL);
        lessonTv[2] = findViewById(R.id.threeL);
        lessonTv[3] = findViewById(R.id.fourL);
        lessonTv[4] = findViewById(R.id.fiveL);
        lessonTv[5] = findViewById(R.id.sixL);
        lessonTv[6] = findViewById(R.id.sevenL);

        addSch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> sch = new HashMap<>();
                for (int i = 0; i < 7; i++) {
                    String tempLesson = lessonTv[i].getText().toString().trim();
                    if (!tempLesson.isEmpty()) sch.put(String.valueOf(i+1),tempLesson);
                }
                FirebaseDatabase.getInstance().getReference().child("Sch").child(num.getSelectedItem().toString()+liter.getSelectedItem().toString()).child(week.getSelectedItem().toString()).setValue(sch).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) Toast.makeText(getApplicationContext(),"Расписание добавленно!",Toast.LENGTH_SHORT).show();
                        else Toast.makeText(getApplicationContext(),"Ошибка!",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}