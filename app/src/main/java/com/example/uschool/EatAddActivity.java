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

public class EatAddActivity extends AppCompatActivity {

    EditText first,second,drink,candy;
    Button addEat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eat_add);
        first = findViewById(R.id.first);
        second = findViewById(R.id.second);
        drink = findViewById(R.id.drink);
        candy = findViewById(R.id.candy);
        addEat = findViewById(R.id.addEat);

        addEat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,String> eats = new HashMap<String,String>();
                eats.put("Первое", first.getText().toString());
                eats.put("Второе", second.getText().toString());
                eats.put("Напиток", drink.getText().toString());
                eats.put("Десерт", candy.getText().toString());
                FirebaseDatabase.getInstance().getReference().child("Eat").setValue(eats).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) Toast.makeText(EatAddActivity.this,"Меню добавленно", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}