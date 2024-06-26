package com.example.uschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class MainMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
<<<<<<< HEAD
    TextView Email_tv, name_tv, secondName_tv;
    public static String Email, username, usersurname, func;
=======
    TextView Email_tv, name_tv, func_tv;
    String Email, username, usersurname, func;
>>>>>>> a19749c54f95d2dea5daf278b78881224a69d449
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        if (FirebaseAuth.getInstance().getCurrentUser() == null)
        {
            startActivity(new Intent(MainMenu.this,LoginActivity.class));
        }
<<<<<<< HEAD

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View navHeader = navigationView.getHeaderView(0);
        TextView twNavBarName = (TextView) navHeader.findViewById(R.id.Email_header);
        Email_tv = (TextView) navHeader.findViewById(R.id.Email_fragment);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
=======
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View navHeader = navigationView.getHeaderView(0);
        Email_tv = (TextView) navHeader.findViewById(R.id.Email_header);
        name_tv = (TextView) navHeader.findViewById(R.id.name_header);
        func_tv = (TextView) navHeader.findViewById(R.id.func_header);
>>>>>>> a19749c54f95d2dea5daf278b78881224a69d449
            FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Email = snapshot.child("email").getValue().toString();
                    username = snapshot.child("username").getValue().toString();
                    usersurname = snapshot.child("usersurname").getValue().toString();
                    func = snapshot.child("func").getValue().toString();
                    Email_tv.setText(Email);
<<<<<<< HEAD
=======
                    name_tv.setText(username + " " + usersurname);
                    func_tv.setText(func);

>>>>>>> a19749c54f95d2dea5daf278b78881224a69d449
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
<<<<<<< HEAD
        }
=======
>>>>>>> a19749c54f95d2dea5daf278b78881224a69d449
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.nav_home) getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        if(item.getItemId() == R.id.nav_settings) getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsFragment()).commit();
        if(item.getItemId() == R.id.nav_about) getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AboutFragment()).commit();
        if(item.getItemId() == R.id.nav_logout)
        {
            Toast.makeText(this, "Выход из приложения...", Toast.LENGTH_SHORT).show();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            android.os.Process.killProcess(android.os.Process.myPid());
        }
        if(item.getItemId() == R.id.nav_logout_acc){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainMenu.this,LoginActivity.class));
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}