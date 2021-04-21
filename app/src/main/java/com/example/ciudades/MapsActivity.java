package com.example.ciudades;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


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

    @SuppressLint("MissingPermission")
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
    @SuppressLint("MissingPermission")
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


        map.setTrafficEnabled(true); //trafico activado

        UiSettings uiSettings = map.getUiSettings();
        uiSettings.setZoomControlsEnabled(true); //controles de zoom
        uiSettings.setCompassEnabled(true); //mostrar la brújula
        uiSettings.setZoomGesturesEnabled(true); //gestos de zoom
        uiSettings.setScrollGesturesEnabled(true); //Gestos de scroll
        uiSettings.setTiltGesturesEnabled(true); //Gestos de ángulo
        uiSettings.setRotateGesturesEnabled(true); //Gestos de rotación

        // asignar max y min zoom al mapa (3 a 22)
//        map.setMinZoomPreference(6.0f);
//        map.setMaxZoomPreference(14.0f);

        //Mover cámara
//        LatLng latLng = new LatLng(38, -3);
//        CameraUpdate cu = CameraUpdateFactory.newLatLng(latLng);
//        CameraUpdate cuZoom = CameraUpdateFactory.newLatLngZoom(latLng, 8);

        // mueve la cámara a una ubicación
//        map.moveCamera(cuZoom);

//        FloatingActionButton fabRefresh = findViewById(R.id.fab_refresh);
//        fabRefresh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CameraUpdate madrid = CameraUpdateFactory.newLatLngZoom(new LatLng(40.42062134847416, -3.7042661462520288), 7);
//                map.animateCamera(madrid);
//            }
//        });

//        FloatingActionButton fabZoom = findViewById(R.id.fab_zoom);
//        fabZoom.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CameraUpdate maxZoom = CameraUpdateFactory.zoomTo(20);
//                map.animateCamera(maxZoom);
//            }
//        });

        // centrar mapa en un área
//        LatLngBounds AUSTRALIA = new LatLngBounds(new LatLng(-40.069652872281544, 110.99998935230147), new LatLng(-12.929600324985966, 155.64842237872773));

        //map.moveCamera(CameraUpdateFactory.newLatLngBounds(AUSTRALIA, 20));
        // map.moveCamera(CameraUpdateFactory.newLatLngZoom(AUSTRALIA.getCenter(), 3));

        // listener para esperar que el mapa esté cargado
//        map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
//            @Override
//            public void onMapLoaded() {
//                map.animateCamera(CameraUpdateFactory.newLatLngBounds(AUSTRALIA, 20));
//            }
//        });

        // listener al pulsar un punto de interés
//        map.setOnPoiClickListener(new GoogleMap.OnPoiClickListener() {
//            @Override
//            public void onPoiClick(PointOfInterest pointOfInterest) {
//                Toast.makeText(MainActivity.this, "Nombre del sitio: " + pointOfInterest.name, Toast.LENGTH_SHORT).show();
//                LatLng ubicacion = pointOfInterest.latLng;
//                map.animateCamera(CameraUpdateFactory.newLatLng(ubicacion));
//            }
//        });


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

        // listener draggable para arrastrar y solar marker
//        map.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
//            @Override
//            public void onMarkerDragStart(Marker marker) {
//
//            }
//
//            @Override
//            public void onMarkerDrag(Marker marker) {
//
//            }
//
//            @Override
//            public void onMarkerDragEnd(Marker marker) {
//                if (marker.getTag().equals("mimarker")) {
//                    LatLng latLng1 = marker.getPosition();
//                    double latitud = latLng1.latitude;
//                    double longitud = latLng1.longitude;
//                    Toast.makeText(MainActivity.this, "Latitud: " + latitud + ", Longitud: " + longitud, Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

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

        //Polyline
//        PolylineOptions rectOptions = new PolylineOptions()
//                .color(Color.RED)
//                .width(10f)
//                .clickable(true)
//
//                .add(new LatLng(38.096821, -3.627834)) //1er punto
//                .add(new LatLng(38.096855, -3.627126)) // Hacia al este
//                .add(new LatLng(38.097779, -3.627174)) // Hacia el norte
//                .add(new LatLng(38.097760, -3.627908)) // Hacia el oeste
//                .add(new LatLng(38.097496, -3.628524));// Hacia el suroeste
//
//        Polyline campoFutbol = map.addPolyline(rectOptions);

//        rectOptions = new PolylineOptions()
//                .color(Color.YELLOW)
//                .width(10f)
//                .clickable(true)
//
//                .add(new LatLng(38.096821, -3.627834)) //init point
//                .add(new LatLng(38.09745732808713, -3.6278839818218995));
//
//        map.addPolyline(rectOptions);

        //Polygon
//        PolygonOptions options = new PolygonOptions().strokeColor(Color.RED)
//                .strokeWidth(20f)
//                .fillColor(ContextCompat.getColor(this, R.color.semiTransparent))
//                .zIndex(10)
//                .clickable(true)
//
//                .add(new LatLng(43.75341412588365, -9.593843256556998)) //1er punto
//                .add(new LatLng(42.986742508776935, 3.5018585563121136)) // Hacia al este
//                .add(new LatLng(36.401581867949574, 0.8211947624026644)) // Hacia el norte
//                .add(new LatLng(36.860036902084985, -10.560640034688271)) // Hacia el oeste
//                .add(new LatLng(43.75341412588365, -9.593843256556998));// Hacia el suroeste
//
//        Polygon spainLine = map.addPolygon(options);

        // circle
//        CircleOptions circleOptions = new CircleOptions()
//                .center(new LatLng(38.93515236530402, -100.83512605947585))
//                .radius(2000000)
//                .strokeColor(Color.BLUE)
//                .strokeWidth(10f)
//                .clickable(true);
//        Circle circleUsa = map.addCircle(circleOptions);

        // click sobre las formas
//        map.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
//            @Override
//            public void onPolylineClick(Polyline polyline) {
//                if (polyline.equals(campoFutbol)) {
//                    Toast.makeText(MainActivity.this, "Dar vueltas al campo", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        map.setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener() {
//            @Override
//            public void onPolygonClick(Polygon polygon) {
//                if (polygon.equals(spainLine))
//                    Toast.makeText(MainActivity.this, "España incomunicada", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        map.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {
//            @Override
//            public void onCircleClick(Circle circle) {
//                if (circle.equals(circleUsa)) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                    builder.setMessage("Disparar misil?");
//                    builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            if (circleUsa != null) {
//                                circleUsa.remove();
//                            }
//                            CircleOptions circleOptions = new CircleOptions()
//                                    .center(new LatLng(38.93515236530402, -100.83512605947585))
//                                    .radius(2000000)
//                                    .strokeColor(Color.BLUE)
//                                    .fillColor(Color.BLUE)
//                                    .strokeWidth(10f);
//                            map.addCircle(circleOptions);
//                        }
//                    });
//                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            if (circleUsa != null) {
//                                circleUsa.remove();
//                            }
//                        }
//                    });
//                    builder.create().show();
//                }
//            }
//        });
    }
}
