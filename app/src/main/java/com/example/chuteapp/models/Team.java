package com.example.chuteapp.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.chuteapp.DataHelper;

public class Team {
    DataHelper dh;
    SQLiteDatabase db;
    Cursor cursor;
    public String name;
    int ID;
    int qtyPlayers;

    public Team(Context context, int id) {
        dh = new DataHelper(context, "equipos.db", null, 1);
        db = dh.getWritableDatabase();
        this.ID = id;
        cursor = db.rawQuery(String.format("SELECT name FROM equipos WHERE id = %o", id), null);
        name = cursor.getString(0);
        db.close();

    }
    public Team(Context context){
        dh = new DataHelper(context, "equipos.db", null, 1);
        db = dh.getWritableDatabase();
    }
    public String createTeam(String name){
        ContentValues reg = new ContentValues();
        reg.put("name", name);
        long resp = db.insert("equipos", null, reg);
        db.close();
        if (resp == -1) {
            return "No se pudo ingresar";
        } else {
            return "Equipo registrado correctamente";
        }
    }

    public String insertTeamMember(User user){
        ContentValues reg = new ContentValues();
        reg.put("user", user.ID);
        reg.put("team", ID);
        long resp = db.insert("jugadores", null, reg);
        db.close();
        if (resp == -1) {
            return "No se pudo ingresar";
        } else {
            return "Equipo registrado correctamente";
        }
    }

    public String changeTeamName(String name) {
        this.name = name;
        ContentValues reg = new ContentValues();
        reg.put("name", name);
        long resp = db.update("equipos", reg, "id=?", new String[] {String.valueOf(ID)});
        db.close();
        if(resp == -1){
            return "No se logró modificar el equipo";
        }else {
            return "Equipo modificado";
        }
    }
    public String removeTeamMember(User user){
        long resp = db.delete("jugadores", "user = ? AND team = ?", new String[]{
                String.valueOf(user.ID),
                String.valueOf(ID)
        });
        db.close();
        if (resp == -1) {
            return "No se pudo eliminar el jugador";
        } else {
            return "Jugador eliminado correctamente";
        }
    }
    private void setQtyPlayers(){
        cursor = db.rawQuery(String.format("SELECT count(user) FROM jugadores WHERE team = %s", name), null);
        qtyPlayers = cursor.getInt(0);
    }
}
