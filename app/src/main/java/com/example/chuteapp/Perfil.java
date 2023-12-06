package com.example.chuteapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Perfil extends AppCompatActivity {
    private FirebaseUser user;
    private TextView emailTextView, usernameTextView;
    private String email, username, uID;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        emailTextView = findViewById(R.id.emailTextView);
        usernameTextView = findViewById(R.id.usernameTextView);
        Bundle bundle = getIntent().getExtras();
        user = (FirebaseUser) bundle.get("user");
        if (user != null) {
            email = user.getEmail();
            username = user.getDisplayName();
            uID = user.getUid();
        }
        setup();
    }

    public void onClickCrear(View view){
        Intent intent = new Intent(this,Crear.class);
        startActivity(intent);
    }

    public void onClickEquipos(View view){
        Intent intent = new Intent(this,Equipos.class);
        startActivity(intent);
    }
    public void onClickStore(View view){
        Intent intent = new Intent(this,StoreView.class);
        startActivity(intent);
    }

    public void onClickMisEquipos(View view){
        Intent intent = new Intent(this,MisEquipos.class).putExtra("uID", uID);
        startActivity(intent);
    }

    private void setup(){
        emailTextView.setText(email);
        usernameTextView.setText(username);
    }

    public void logOut(View view){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        Intent authIntent = new Intent(this, MainActivity.class);
        startActivity(authIntent);
    }
}
