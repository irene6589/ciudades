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
   // void deleteCiudades (City ciudades);

    @Query("SELECT * FROM city")
    LiveData<List<City>> getAllCities();

    @Query("SELECT * FROM city WHERE id = :id")
    LiveData<City> getCiudad(String id);

    @Query("SELECT * FROM city WHERE visitado=1")
    LiveData<List<City>> ciudadVisitada();

    @Query("SELECT * FROM city WHERE favorito=1")
    LiveData<List<City>> ciudadFavorita();

//    @Query("INSERT INTO city VALUES (:Nombre, :Pais, :Latitud, :Longitud, 0, 0)")
 //   void insertCity(String Nombre, String Pais, double Latitud, double Longitud, boolean visitada, boolean favorita);

    @Query("SELECT * FROM city WHERE city_nombre = :Nombre")
    LiveData<List<City>> buscarCiudad(String Nombre);

    @Query("SELECT COUNT(*) FROM city")
    LiveData<Integer> countList();



}
