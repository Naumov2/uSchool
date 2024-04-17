package com.example.uschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.uschool.databinding.ActivityRegistrBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegistrActivity extends AppCompatActivity {
    Spinner spinner;
    private ActivityRegistrBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrBinding.inflate((getLayoutInflater()));
        setContentView(binding.getRoot());

        binding.function.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1)
                {
                    binding.parallel.setVisibility(View.GONE);
                    binding.numberClass.setVisibility(View.GONE);
                }
                if (position == 0)
                {
                    binding.parallel.setVisibility(View.VISIBLE);
                    binding.numberClass.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.loginBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.name.toString().isEmpty() || binding.surname.toString().isEmpty() || binding.Email.toString().isEmpty() || binding.password.toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Есть пустые поля", Toast.LENGTH_SHORT);
                }else{
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(binding.Email.toString().trim(),binding.password.toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful())
                                    {
                                        HashMap<String,String> userInfo = new HashMap<>();
                                        if(binding.function.toString() == "Администратор") {
                                            userInfo.put("email", binding.Email.toString());
                                            userInfo.put("username", binding.name.toString().toLowerCase());
                                            userInfo.put("usersurname", binding.surname.toString().toLowerCase());
                                            userInfo.put("func", binding.function.toString());
                                        } else if (binding.function.toString() == "Ученик") {
                                            userInfo.put("email", binding.Email.toString());
                                            userInfo.put("username", binding.name.toString().toLowerCase());
                                            userInfo.put("usersurname", binding.surname.toString().toLowerCase());
                                            userInfo.put("func", binding.function.toString());
                                            userInfo.put("parallel",binding.parallel.toString());
                                            userInfo.put("numberClass",binding.numberClass.toString());
                                        }
                                        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(userInfo);
                                        startActivity(new Intent(RegistrActivity.this,MainMenu.class));
                                    }
                                }
                            });
                }
            }
        });
    }
}