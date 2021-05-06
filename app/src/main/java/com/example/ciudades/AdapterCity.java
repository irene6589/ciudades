package com.example.ciudades;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Camera;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class AdapterCity extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<City> listaCiudades;
    private  final Context context;
    private final CityRepository repositorio;
    private View view;
    private View view2;

    public AdapterCity(Context context) {
        listaCiudades = new ArrayList<>();
        this.context = context;
        repositorio = new CityRepository(context);
    }



    static class CityHolder extends RecyclerView.ViewHolder {
        private final TextView tv_ciudad;
        private final ImageView borrar;
        private final ImageView visit;

        public CityHolder(@NonNull View itemView) {
            super(itemView);
            borrar = itemView.findViewById(R.id.delete);
            tv_ciudad = itemView.findViewById(R.id.city_name);
            visit = itemView.findViewById(R.id.visit);

        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.ciudad, parent, false);
        return new CityHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final City ciudad = listaCiudades.get(position);

        CityHolder cH = (CityHolder) holder;
        cH.tv_ciudad.setText(ciudad.getCity_nombre());

        if(ciudad.isVisitado()){
            cH.visit.setVisibility(View.VISIBLE);
        } else {
            cH.visit.setVisibility(View.INVISIBLE);
        }

        cH.tv_ciudad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(ciudad.getCity_nombre());
                if(view2 == null)
                    view2 = LayoutInflater.from(context).inflate(R.layout.info_city, null);;
                if(view2.getParent() != null)
                    ((ViewGroup) view2.getParent()).removeView(view2);
                builder.setView(view2);
                TextView infoCiudad = view2.findViewById(R.id.infoCiudad);
                TextView infoPais = view2.findViewById(R.id.infoPais);
                MapView mapv2 = view2.findViewById(R.id.mapV2);
                mapv2.onCreate(null);

                infoCiudad.setText(ciudad.getCity_nombre());
                infoPais.setText(ciudad.getPais());
                mapv2.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(@NonNull GoogleMap googleMap) {
                        LatLng latLng = new LatLng(ciudad.getLatitud(), ciudad.getLongitud());
                        googleMap.addMarker(new MarkerOptions().title(ciudad.getCity_nombre()).position(latLng));
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                    }
                });
                mapv2.onResume();
                builder.setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                      @Override
                  public void onClick(DialogInterface dialog, int which) {
                      builder.setView(null);

                      dialog.dismiss();
                  }
              });
              builder.create().show();
          }

        });

      cH.borrar.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              repositorio.deleteCiudad(ciudad);
          }
      });
    }
//  public void OnItemClick(City ciudad) {
//      AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//      builder.setTitle(ciudad.getCiudad());
//      if (myView == null)
//          myView = LayoutInflater.from(MainActivity.this).inflate(R.layout.info_beer, null);
//      if (myView.getParent() != null)
//          ((ViewGroup) myView.getParent()).removeView(myView);
//      builder.setView(myView);
//      TextView infoCity = myView.findViewById(R.id.info_city);
//      TextView infoCountry = myView.findViewById(R.id.info_country);
//      SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//
//      infoCity.setText(ciudad.getCiudad());
//      infoCountry.setText(ciudad.getPais());
//      fragment.getMapAsync(new OnMapReadyCallback() {
//          @Override
//          public void onMapReady(GoogleMap googleMap) {
//              LatLng latLng = new LatLng(myBeer.getLat(), ciudad.getLng());
//              googleMap.addMarker(new MarkerOptions().title(ciudad.getCiudad())
//                      .position(latLng));
//              googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
//          }
//      });
//
//      builder.setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
//          @Override
//          public void onClick(DialogInterface dialog, int which) {
//              builder.setView(null);
//
//              dialog.dismiss();
//          }
//      });
//      builder.create().show();
//  }

    @Override
    public int getItemCount() {
        return listaCiudades.size();
    }


    public void setCiudades(List<City> ciudades){
        listaCiudades = ciudades;
        notifyDataSetChanged();
    }

}
