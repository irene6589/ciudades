package com.example.ciudades;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DaoCiudad {
    @Insert
    void insertarCiudad (City ciudad);

    @Update
    void updateCity (City ciudad);

    @Delete
    void deleteCiudad (City ciudad);

    @Delete
    void deleteCiudades (List<City> ciudades);

    @Query("SELECT * FROM city")
    LiveData<List<City>> getAllCities();

    @Query("SELECT * FROM city WHERE id = :id")
    LiveData<City> getCiudad(String id);

    @Query("SELECT * FROM city WHERE visitado=1")
    LiveData<List<City>> ciudadVisitada();

    @Query("SELECT COUNT(*) FROM city")
    LiveData<Integer> countList();


}
