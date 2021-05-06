package com.example.ciudades;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CityRepository {

    private final DaoCiudad daoCity;
    private final AppDataBase dataBase;

    public CityRepository(Context context) {
        dataBase = AppDataBase.getDatabase(context);
        daoCity = dataBase.daoCity();
    }

    public void updateCiudad(City ciudad) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                daoCity.updateCity(ciudad);
            }
        }).start();
    }

    public LiveData<Integer> getCount() {
        return daoCity.countList();
    }

    public void deleteCiudad(City ciudad){
        new Thread(new Runnable() {
            @Override
            public void run() {
                daoCity.deleteCiudad(ciudad);
            }
        }).start();
    }

    public void deleteCiudades(List<City> ciudades){
        new Thread(new Runnable() {
            @Override
            public void run() { daoCity.deleteCiudades(ciudades); }
        }).start();
    }

    public void insertarCiudad(City ciudad) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                daoCity.insertarCiudad(ciudad);
            }
        }).start();
    }

    public LiveData<City> getCiudad(String id){
        return daoCity.getCiudad(id);
    }

    public LiveData<List<City>> getCiudades(){
        return daoCity.getAllCities();
    }

    public LiveData<List<City>> getciudadVisitada() { return daoCity.ciudadVisitada(); }

}
