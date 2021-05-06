package com.example.ciudades;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap myMap;
    private final static int REQUEST_PERMISSION_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);
    }


    private void mostrarUbicacion(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //Permiso aceptado, ejecuto funcionalidad
            myMap.setMyLocationEnabled(true);
        }else if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)){
            mostrarAlert();
        } else {
            String array[] = {Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(this, array, REQUEST_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[]permissions, int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_PERMISSION_CODE){
            if(permissions.length > 0){
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        //Permiso aceptado, ejecuto funcionalidad
                        myMap.setMyLocationEnabled(true);
                    } else {
                        // mostrar un aviso
                    }
                }
            }
        }
    }
    private void mostrarAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("askdljalkdjskljd");
        builder.setPositiveButton("Aceptar los permisos", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String array[] = {Manifest.permission.ACCESS_FINE_LOCATION};
                ActivityCompat.requestPermissions(MapsActivity.this, array, 10);
            }
        });

        builder.setNegativeButton("Rechazar los permisos", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
    @Override
    public void onMapReady(GoogleMap map) {
        myMap = map;

        mostrarUbicacion();

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                map.addMarker(new MarkerOptions().position(latLng));
            }
        });
        /* TIPO DE MAPA */
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);


        map.setTrafficEnabled(false);

        UiSettings uiSettings = map.getUiSettings();
        uiSettings.setZoomControlsEnabled(true); //controles de zoom
        uiSettings.setCompassEnabled(true); //mostrar la brújula
        uiSettings.setZoomGesturesEnabled(true); //gestos de zoom
        uiSettings.setScrollGesturesEnabled(true); //Gestos de scroll
        uiSettings.setTiltGesturesEnabled(true); //Gestos de ángulo
        uiSettings.setRotateGesturesEnabled(true); //Gestos de rotación



        // listener al pulsar un punto de interés
        map.setOnPoiClickListener(new GoogleMap.OnPoiClickListener() {
            @Override
            public void onPoiClick(PointOfInterest pointOfInterest) {
                Toast.makeText(MapsActivity.this, "Nombre del sitio: " + pointOfInterest.name, Toast.LENGTH_SHORT).show();
                LatLng ubicacion = pointOfInterest.latLng;
                map.animateCamera(CameraUpdateFactory.newLatLng(ubicacion));
            }
        });


        // añadir marker
//        MarkerOptions options1 = new MarkerOptions()
//                .position(new LatLng(38.5, -3.3))
//                .title("Sitio")
//                .snippet("Pulsa aquí para acceder")
//                .alpha(.6f)
//                .rotation(90f);
//        Marker marker1 = map.addMarker(options1);

//        MarkerOptions options2 = new MarkerOptions()
//                .position(new LatLng(38.09418604624217, -3.6312538))
//                .title("Sitio2")
//                .snippet("Pulsa aquí para acceder")
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker));
//        Marker marker2 = map.addMarker(options2);

//        MarkerOptions options3 = new MarkerOptions()
//                .position(new LatLng(39.5, -2.3))
//                .title("Sitio3")
//                .snippet("Pulsa aquí para acceder")
//                .flat(true);
//        Marker marker3 = map.addMarker(options3);

//        MarkerOptions options4 = new MarkerOptions()
//                .position(new LatLng(37.5, -4.3))
//                .title("Sitio4")
//                .snippet("Pulsa aquí para acceder")
//                .flat(true)
//                .draggable(true)
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
//        Marker marker4 = map.addMarker(options4);
//        marker4.setTag("mimarker");


        //click en el marker
//        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                if (marker.equals(marker2)) {
//                    Toast.makeText(MainActivity.this, "Escuela Estech", Toast.LENGTH_SHORT).show();
//                }
//                return false;
//            }
//        });

        //click en cartel del marker
//        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
//            @Override
//            public void onInfoWindowClick(Marker marker) {
//                if (marker.equals(marker2)) {
//                    Intent i = new Intent(Intent.ACTION_VIEW);
//                    i.setData(Uri.parse("https://escuelaestech.es"));
//                    startActivity(i);
//                }
//            }
//        });

        //eliminar marker
        //marker3.remove();


    }
}
