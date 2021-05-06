package com.example.ciudades;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.TextInputEditText;

public class CityActivity extends AppCompatActivity implements OnMapReadyCallback {

    private CityRepository cityRepository;
    private City ciudad;
    private Marker marca;
    private double lat;
    private double longit;
    private TextView latit;
    private TextView longitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_activity);

        cityRepository = new CityRepository(getApplicationContext());
        TextInputEditText nombre = findViewById(R.id.addcity_name);
        TextInputEditText pais = findViewById(R.id.addPais);
        latit = findViewById(R.id.lat);
        longitud = findViewById(R.id.longi);
        SwitchCompat addVisit = (SwitchCompat) findViewById(R.id.visitada);
        ImageView imagen = findViewById(R.id.imageView2);
        Button add_img = findViewById(R.id.add_img);
        Button add_city = findViewById(R.id.add_city);


        MapView mapitadichoso = findViewById(R.id.mapV1);
        mapitadichoso.onCreate(savedInstanceState);
        mapitadichoso.getMapAsync(this);
        mapitadichoso.onResume();

        add_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nombre.getText().toString().isEmpty() || pais.getText().toString().isEmpty() || marca==null){
                   mostrarError();
                } else {
                    ciudad = new City();
                    ciudad.setCity_nombre(nombre.getText().toString());
                    ciudad.setPais(pais.getText().toString());
                    ciudad.setLatitud(lat);
                    ciudad.setLongitud(longit);
                    ciudad.setVisitado(addVisit.isChecked());
                    cityRepository.insertarCiudad(ciudad);
                    finish();
                }

            }
        });
    }
    private void mostrarError() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Debes completar todos los campos y añadir el marcador correspondiente");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }


    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                //entro, compruebo si es nulo, al ser la primera vez es nulo entonces va directamente a la siguiente linea,
                //si pulso una segunda vez, remueve el marker anterior, explicaciones para mi misma Sergio no me hagas caso :D
                if(marca!=null){
                    marca.remove();
                }
                marca = map.addMarker(new MarkerOptions().position(latLng)); // añado el marker
                lat = latLng.latitude;
                longit = latLng.longitude;
                latit.setText(String.valueOf(lat));
                longitud.setText(String.valueOf(longit));


            }
        });
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        UiSettings uiSettings = map.getUiSettings();
        uiSettings.setZoomControlsEnabled(true); //controles de zoom
        uiSettings.setZoomGesturesEnabled(true); //gestos de zoom
        uiSettings.setScrollGesturesEnabled(true); //Gestos de scroll

    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
