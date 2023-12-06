package com.example.chuteapp;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chuteapp.models.Team;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Crear extends AppCompatActivity {
    EditText edtNombre, cantPlayers;
    String userId;
    private final static String TAG = "CrearEquipos";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Bundle bundle = getIntent().getExtras();
        userId = (String) bundle.get("uID");
        edtNombre = (EditText) findViewById(R.id.nomEquipo);
        cantPlayers = (EditText) findViewById(R.id.cantPlayers);
    }
    public void onClickCrearEquipo(View view) {
        String teamName = edtNombre.getText().toString();
        int qtyPlayers = Integer.parseInt(cantPlayers.getText().toString());
        Team team = new Team(teamName, userId, qtyPlayers);

        db.collection("Teams")
                .add(team)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot successfully written");
                        Toast.makeText(Crear.this, "Equipo creado con éxito", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                        Toast.makeText(Crear.this, "Error al crear equipo", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}