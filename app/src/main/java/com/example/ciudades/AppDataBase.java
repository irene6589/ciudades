package com.example.ciudades;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = City.class, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public static final String DataB_Nombre = "db_city";

    public abstract DaoCiudad daoCity();

    private static AppDataBase database;

    public static AppDataBase getDatabase(final Context context) {
        if (database == null){
            synchronized (AppDataBase.class){
                database = Room.databaseBuilder(context.getApplicationContext(),
                        AppDataBase.class, DataB_Nombre).build();
            }
        }
        return database;
    }

    static final ExecutorService dbWriterExecutor = Executors.newFixedThreadPool(1);
}
