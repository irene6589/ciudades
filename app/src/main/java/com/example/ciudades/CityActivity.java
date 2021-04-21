package com.example.ciudades;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.google.android.material.textfield.TextInputEditText;

public class CityActivity extends AppCompatActivity {
    private CityRepository cityRepository;
    private City ciudad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_activity);

        cityRepository = new CityRepository(getApplicationContext());
        //boolean edicion = getIntent().getBooleanExtra("Edicion", false);
        String ciudad_id = getIntent().getStringExtra("Ciudad");
        TextInputEditText nombre = findViewById(R.id.addcity_name);
        TextInputEditText pais = findViewById(R.id.addPais);
        TextInputEditText lat = findViewById(R.id.lat);
        TextInputEditText longitud = findViewById(R.id.longitud);
        SwitchCompat addFav = (SwitchCompat) findViewById(R.id.visitC);
        SwitchCompat addVisit = (SwitchCompat) findViewById(R.id.visitada);
        ImageView imagen = findViewById(R.id.imageView2);
        Button add_img = findViewById(R.id.add_img);
        Button add_city = findViewById(R.id.add_city);
        TextView titulo = findViewById(R.id.titulo);


       //
       // add_city.setText("Editar");
       // titulo.setText("Edicion de Ciudad");
       // ciudad = cityRepository.getCiudad(ciudad_id).getValue();
       // nombre.setText(ciudad.getCity_nombre());
       // pais.setText(ciudad.getPais());
       // lat.setText(String.valueOf(ciudad.getLatitud()));
       // longitud.setText(String.valueOf(ciudad.getLongitud()));
       // addFav.setActivated(ciudad.isFavorito());
       // addVisit.setActivated(ciudad.isVisitado());



        add_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //  if(edicion){
            //       ciudad.setCity_nombre(nombre.getText().toString().isEmpty() ? "" : nombre.getText().toString());
            //       ciudad.setPais(pais.getText().toString().isEmpty() ? "" : pais.getText().toString());
            //       ciudad.setLatitud(Double.parseDouble(lat.getText().toString().isEmpty() ? "0.0" : lat.getText().toString()));
            //       ciudad.setLongitud(Double.parseDouble(longitud.getText().toString().isEmpty() ? "0.0" : longitud.getText().toString()));
            //       ciudad.setFavorito(addFav.isActivated());
            //       ciudad.setVisitado(addVisit.isActivated());
            //       cityRepository.updateCity(ciudad);
            //       finish();
               // } else {
                   ciudad = new City();
                   ciudad.setCity_nombre(nombre.getText().toString().isEmpty() ? "" : nombre.getText().toString());
                   ciudad.setPais(pais.getText().toString().isEmpty() ? "" : pais.getText().toString());
                   ciudad.setLatitud(Double.parseDouble(lat.getText().toString().isEmpty() ? "0.0" : lat.getText().toString()));
                   ciudad.setLongitud(Double.parseDouble(longitud.getText().toString().isEmpty() ? "0.0" : longitud.getText().toString()));
                  // ciudad.setFavorito(addFav.isChecked());
                   ciudad.setVisitado(addVisit.isChecked());
                   cityRepository.insertarCiudad(ciudad);
                   finish();

               // }

            }
        });
    }


}
