package com.example.uschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AnnouncementsAddActivity extends AppCompatActivity {

    EditText name, textAnn;
    Button addAnn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements_add);

        name = findViewById(R.id.nameAnn);
        textAnn = findViewById(R.id.textAnn);
        addAnn = findViewById(R.id.addAnn);

        addAnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!name.getText().toString().isEmpty() && !textAnn.getText().toString().isEmpty()){
                    HashMap<String,String> temp = new HashMap<String, String>();
                    temp.put("Название",name.getText().toString());
                    temp.put("Текст объявления",textAnn.getText().toString());
                    FirebaseDatabase.getInstance().getReference().child("Announcements").setValue(temp).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) Toast.makeText(AnnouncementsAddActivity.this,"Обьявлене добавленно", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else Toast.makeText(AnnouncementsAddActivity.this, "Есть пустые поля", Toast.LENGTH_SHORT).show();
            }
        });
    }
}