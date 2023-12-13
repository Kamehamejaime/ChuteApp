package com.example.chuteapp;

import static com.example.chuteapp.R.id.cargaAsync;
import static com.example.chuteapp.R.id.idEquipo;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.chuteapp.MisEquipos;
import com.example.chuteapp.models.Team;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Equipo extends AppCompatActivity {

    EditText edtName;
    Team targetTeam;
    String team;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final static String TAG = "Equipo";
    DocumentReference documentReference;




    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipo);
        edtName = findViewById(R.id.nombreEditar);
        Bundle bundle = getIntent().getExtras();
        team = (String) bundle.get("targetTeam");
        documentReference = db.collection("Teams").document(team);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()){
                        targetTeam = new Team(document.getString("name"), document.getString("userId"), document.getLong("qtyPlayers"));
                        Log.d(TAG, "DocumentSnapshot getted successfully");
                        edtName.setText(targetTeam.getName());
                    } else {
                        Log.d(TAG, "No hay documento");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    public void onClickModificar(View view){
        targetTeam.setName(edtName.getText().toString());
        documentReference
                .update("name", targetTeam.getName())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                        Toast.makeText(Equipo.this, "Equipo modificado con exito", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                        Toast.makeText(Equipo.this, "Error al modificar el equipo", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
