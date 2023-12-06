package com.example.chuteapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class register extends MainActivity {
    private EditText registerEmail, registerPassword, confirmPassword, registerUsername;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerEmail = findViewById(R.id.registerEmail);
        registerPassword = findViewById(R.id.registerPassword);
        confirmPassword = findViewById(R.id.confirmPassword);
        registerUsername = findViewById(R.id.registerUsername);
    }

    public void onClickRegister(View view){
        String email = registerEmail.getText().toString();
        String password = registerPassword.getText().toString();
        String confirm = confirmPassword.getText().toString();
        String username = registerUsername.getText().toString();

        if (password.equals(confirm)){
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                UserProfileChangeRequest profileCreate = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(username)
                                                .build();
                                assert user != null;
                                user.updateProfile(profileCreate)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()) {
                                                    Log.d(TAG, "Nombre de usuario generado");
                                                    updateUI(user);
                                                }
                                            }
                                        });
                            }
                            else{
                                Log.w(TAG, "createUSerWithEmail:failure", task.getException());
                                Toast.makeText(register.this, "Error al crear usuario", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
    private void updateUI(FirebaseUser user) {
        Intent perfilIntent = new Intent(this, Perfil.class).putExtra("user", user);
        startActivity(perfilIntent);
    }
}