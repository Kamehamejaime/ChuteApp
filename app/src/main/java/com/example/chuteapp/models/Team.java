package com.example.chuteapp.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.chuteapp.DataHelper;

import java.util.List;

public class Team {
    private String Id;
    String name;
    String userId;
    long qtyPlayers;

    public Team(String name, String userId, long qtyPlayers) {
        this.name = name;
        this.userId = userId;
        this.qtyPlayers = qtyPlayers;
    }

    public Team() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQtyPlayers(long qtyPlayers) {
        this.qtyPlayers = qtyPlayers;
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    public long getQtyPlayers() {
        return qtyPlayers;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    @Override
    public String toString(){
        return name;
    }
}
