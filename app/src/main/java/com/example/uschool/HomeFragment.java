package com.example.uschool;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class HomeFragment extends Fragment {
    Button marks, add, open, marksStud, eatAdd, announcementsAdd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Создаем базовый View, который будет показан, пока данные загружаются
        final View baseView = inflater.inflate(R.layout.base_fragment, container, false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String func = snapshot.child("func").getValue(String.class);
                    if ("Администратор".equals(func)) {
                        // Заменяем базовый View на View администратора
                        setFragmentView(inflater, container, R.layout.fragment_home_teacher);
                    } else {
                        // Заменяем базовый View на View студента
                        setFragmentView(inflater, container, R.layout.fragment_home_student);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Обработка ошибки
                }
            });
        }
        // Возвращаем базовый View
        return baseView;
    }

    private void setFragmentView(LayoutInflater inflater, ViewGroup container, int layoutId) {
        // Заменяем содержимое контейнера на новый View
        container.removeAllViews();
        View view= inflater.inflate(layoutId, container, true);
        if(layoutId == R.layout.fragment_home_teacher)
        {
            marks = view.findViewById(R.id.marks);
            add = view.findViewById(R.id.add);
            eatAdd = view.findViewById(R.id.eat);
            announcementsAdd = view.findViewById(R.id.announcements);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getContext(), AddSch.class));
                }
            });

            marks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getContext(),MarksActivity.class));
                }
            });

            eatAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getContext(), EatAddActivity.class));
                }
            });
            announcementsAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getContext(), AnnouncementsAddActivity.class));
                }
            });
        }
        if (layoutId == R.layout.fragment_home_student) {
            open = view.findViewById(R.id.lookSch);
            marksStud = view.findViewById(R.id.marksStud);

            open.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getContext(),SchStudent.class));
                }
            });
            marksStud.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getContext(), MarksStudentActivity.class));
                }
            });
        }

    }
}