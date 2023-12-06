package com.example.chuteapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class MisEquipos extends AppCompatActivity {

    ListView lista;
    TextView id, carga;
    private long selectedItemID;
    private String selectedItemName, Uid;
    Button btnEdit,btnDelete;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_equipos);
        Bundle bundle = getIntent().getExtras();
        Uid = (String) bundle.get("uID");
        lista = (ListView) findViewById(R.id.listaMisEquipos);
        btnEdit = (Button) findViewById(R.id.btnEditar);
        btnDelete = (Button) findViewById(R.id.btnEliminar);
        carga = (TextView) findViewById(R.id.cargaAsync);
        MyAsyncTask asyncTask = new MyAsyncTask();
        asyncTask.execute();
    }

    public void CargarLista() {
        // Se crea un arreglo donde se almacenarán los equipos obtenidos de la BD
        List<EquipoAd> equiposlista = new ArrayList<>();
        db.collection("Equipos")
                .whereEqualTo("uID", Uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                long idBase = document.getLong("id");
                                String nameBase = document.getString("teamName");
                                EquipoAd equipo = new EquipoAd(idBase, nameBase);
                                equiposlista.add(equipo);
                            }
                            if (equiposlista.isEmpty()) {
                                // Si no hay elementos, hay que asegurarse de que el adaptador esté vacío
                                lista.setAdapter(null);
                                btnEdit.setVisibility(View.GONE);
                                btnDelete.setVisibility(View.GONE);
                            } else {
                                ArrayAdapter<EquipoAd> adapter = new ArrayAdapter<>
                                        (MisEquipos.this, android.R.layout.simple_expandable_list_item_1, equiposlista);
                                lista.setAdapter(adapter);
                                btnEdit.setVisibility(View.VISIBLE);
                                btnDelete.setVisibility(View.VISIBLE);
                                clickLista(equiposlista);
                            }
                        } else {
                            // Si el cursor está vacío, hay que asegurarse de que el adaptador y la ListView estén vacíos
                            lista.setAdapter(null);
                            btnEdit.setVisibility(View.GONE);
                            btnDelete.setVisibility(View.GONE);
                            TextView txtEquipoId = findViewById(R.id.idEquipo);
                            txtEquipoId.setText("");
                            TextView txtEquipoName = findViewById(R.id.nameEquipo);
                            txtEquipoName.setText("");
                        }
                    }
                });
    }
    public void clickLista(List<EquipoAd>list){
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Aquí obtengo el ID del elemento seleccionado en la base de datos Profe, lo logré :D
                selectedItemID = list.get(position).getId();
                selectedItemName = list.get(position).getNombre();

                TextView txtEquipoId = findViewById(R.id.idEquipo);
                txtEquipoId.setText(String.valueOf(selectedItemID));
                TextView txtEquipoName = findViewById(R.id.nameEquipo);
                txtEquipoName.setText(String.valueOf(selectedItemName));

            }
        });
    }
    public void onClickCrear(View view){
        Intent intent = new Intent(this,Crear.class).putExtra("uID", Uid);
        startActivity(intent);
    }

    public void onClickEditar(View view){
        TextView id = (TextView) findViewById(R.id.idEquipo);
        // En ActivityA
        String txtId = id.getText().toString();

        Intent intent = new Intent(MisEquipos.this, Equipo.class);
        intent.putExtra("contenido", txtId);
        startActivity(intent);
    }
    public void onClickEliminar(View view){
        DataHelper dh = new DataHelper(this, "equipos.db",  null, 1);
        SQLiteDatabase bd = dh.getWritableDatabase();

        id = (TextView) findViewById(R.id.idEquipo);

        int iD = Integer.parseInt(id.getText().toString());
        long resp = bd.delete("equipos", "id="+ iD, null);
        bd.close();

        if(resp ==-1){
            Toast.makeText(this, "No se pudo Eliminar",
                    Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Equipo Eliminado",
                    Toast.LENGTH_LONG).show();
        }
        CargarLista();
    }

    public void onClickActualizar(View view){
        TextView txtEquipoId = findViewById(R.id.idEquipo);
        txtEquipoId.setText("");
        TextView txtEquipoName = findViewById(R.id.nameEquipo);
        txtEquipoName.setText("");
        CargarLista();
    }

    public class MyAsyncTask extends AsyncTask<Void,Void,String>{
        @Override
        protected String doInBackground(Void... voids){
            try {
                Thread.sleep(3000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            return "";
        }
        @Override
        protected void onPostExecute(String result){
            carga.setVisibility(View.GONE);
            CargarLista();
            lista.setVisibility(View.VISIBLE);
        }
    }
}