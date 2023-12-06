package com.example.chuteapp;

import static com.example.chuteapp.R.id.cargaAsync;
import static com.example.chuteapp.R.id.idEquipo;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.chuteapp.MisEquipos;

import androidx.appcompat.app.AppCompatActivity;

public class Equipo extends AppCompatActivity {

    EditText edtName;
    int idBd;




    @Override
    protected void onCreate(Bundle savedInstanceState){
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipo);
        Intent intent = getIntent();
        if (intent != null){
            String id = intent.getStringExtra("contenido");
            TextView idEquipoEditar = findViewById(R.id.nombreEditar);
            idEquipoEditar.setText(id);
            this.idBd = Integer.parseInt(id);
        }
    }

    public void onClickModificar(View view){
        DataHelper dh = new DataHelper(this, "equipos.db", null, 1);
        SQLiteDatabase bd = dh.getWritableDatabase();
        ContentValues reg = new ContentValues();
        edtName = (EditText) findViewById(R.id.nombreEditar);
        reg.put("name", edtName.getText().toString());
        long resp  = bd.update("equipos", reg, "id=?", new String[]
                {String.valueOf(idBd)});
        bd.close();
        if(resp == -1){
            Toast.makeText(this, "No se pudo Modificar",
                    Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "Equipo Modificado",
                    Toast.LENGTH_LONG).show();
        }
        finish();
    }

}
