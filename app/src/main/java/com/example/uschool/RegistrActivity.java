
package com.example.uschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
                registerUser();
            }
        });
    }
    private void registerUser() {
        String name = binding.name.getText().toString().trim();
        String surname = binding.surname.getText().toString().trim();
        String email = binding.Email.getText().toString().trim();
        String password = binding.password.getText().toString().trim();
        String function = binding.function.getSelectedItem().toString();
        String numberClass = binding.numberClass.getSelectedItem().toString();
        String parallel = binding.numberClass.getSelectedItem().toString();

        // Проверка на пустоту полей
        if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        // Регистрация пользователя в Firebase
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Пользователь успешно зарегистрирован
                            // Создание HashMap для хранения информации о пользователе
                            HashMap<String, String> userInfo = new HashMap<>();
                            userInfo.put("email", email);
                            userInfo.put("username", name.toLowerCase());
                            userInfo.put("usersurname", surname.toLowerCase());
                            userInfo.put("func", function);

                            // Если пользователь выбрал функцию "Ученик"
                            if (function.equals("Ученик")) {
                                userInfo.put("parallel", parallel);
                                userInfo.put("numberClass", numberClass);
                            }

                            // Сохранение информации о пользователе в базе данных Firebase
                            FirebaseDatabase.getInstance().getReference().child("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(userInfo)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                // Переход на главный экран после успешной регистрации
                                                startActivity(new Intent(RegistrActivity.this, MainMenu.class));
                                                finish();
                                            } else {
                                                Toast.makeText(RegistrActivity.this, "Ошибка регистрации пользователя", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            // Ошибка при регистрации пользователя
                            Toast.makeText(RegistrActivity.this, "Ошибка: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
