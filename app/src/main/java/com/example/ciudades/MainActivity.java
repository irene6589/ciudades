package com.example.ciudades;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int RESULT_ACTIVITY = 5;

    private AdapterCity adapterCity;
    private CityRepository cityRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityRepository = new CityRepository(getApplicationContext());

        FloatingActionButton bAddCity = findViewById(R.id.buttonAdd);
        bAddCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CityActivity.class);
                startActivityForResult(i, 5);
            }
        });
        FloatingActionButton bAllCities = findViewById(R.id.allCities);
        bAllCities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(i);
            }
        });


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapterCity = new AdapterCity(this);
        recyclerView.setAdapter(adapterCity);

        final TextView cantidad = findViewById(R.id.cantidad);
        LiveData<Integer> liveCantidad = cityRepository.getCount();
        liveCantidad.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                cantidad.setText(String.valueOf(integer));
            }
        });

        SwitchCompat cityvisit = findViewById(R.id.visitC);
        cityvisit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                changeTasksOption(isChecked);
            }
        });
        mMediatorTasks.observe(this, new Observer<List<City>>(){
            @Override
            public void onChanged(List<City> cities){
                adapterCity.setCiudades(cities);}
        });
        changeTasksOption(false);
    }
    private final MutableLiveData<Boolean> mSelectedIndex = new MutableLiveData<>();

    private final LiveData<List<City>> mMediatorTasks = Transformations.switchMap(mSelectedIndex, new Function<Boolean, LiveData<List<City>>>() {
        @Override
        public LiveData<List<City>> apply(Boolean index) {
            if (index) {
                return cityRepository.getciudadVisitada();
            } else {
                return cityRepository.getCiudades();
            }
        }
    });

    public void changeTasksOption(boolean index) {
        mSelectedIndex.setValue(index);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_ACTIVITY) {
            if (data != null) {
                City city = data.getParcelableExtra("ciudad");
                cityRepository.insertarCiudad(city);
            }
        } else {
            Toast.makeText(this, "Hay un error al insertar la ciudad", Toast.LENGTH_SHORT).show();
        }
    }

}