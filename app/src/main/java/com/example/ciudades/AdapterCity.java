package com.example.ciudades;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdapterCity extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<City> listaCiudades;
    private  final Context context;
    private final CityRepository repositorio;

    public AdapterCity(Context context) {
        listaCiudades = new ArrayList<>();
        this.context = context;
        repositorio = new CityRepository(context);
    }

    static class CityHolder extends RecyclerView.ViewHolder {
        private final TextView tv_ciudad;
        private final ImageView borrar;
        private final ImageView edit;
        private final ImageView fav;
        private final ImageView visit;

        public CityHolder(@NonNull View itemView) {
            super(itemView);
            borrar = itemView.findViewById(R.id.delete);
            tv_ciudad = itemView.findViewById(R.id.city_name);
            edit = itemView.findViewById(R.id.edit);
            fav = itemView.findViewById(R.id.fav);
            visit = itemView.findViewById(R.id.visit);

        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ciudad, parent, false);
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

        if(ciudad.isFavorito()) {
            cH.fav.setVisibility(View.VISIBLE);
        } else {
            cH.fav.setVisibility(View.INVISIBLE);
        }


        cH.tv_ciudad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MapsActivity.class);
                context.startActivity(intent);
            }
        });

     // cH.edit.setOnClickListener(new View.OnClickListener() {
     //     @Override
     //     public void onClick(View v) {
     //         Intent i = new Intent (context, CityActivity.class);
     //         i.putExtra("Edicion", true);
     //         i.putExtra("Ciudad", ciudad.getId());
     //         context.startActivity(i);
     //     }
     // });

      cH.borrar.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              repositorio.deleteCiudad(ciudad);
          }
      });
    }

    @Override
    public int getItemCount() {
        return listaCiudades.size();
    }


    public void setCiudades(List<City> ciudades){
        listaCiudades = ciudades;
        notifyDataSetChanged();
    }

}
