package org.techtown.ssalboriadventure;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.pedro.library.AutoPermissionsListener;

import static android.widget.Toast.LENGTH_SHORT;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, AutoPermissionsListener {

    public static Activity Start;

    private GoogleMap Map;
    SupportMapFragment supportMapFragment;
    LocationManager locationManager;
    GPSListener gpsListener;
    long minTime = 0;
    float minDistance = 0;
    float distanceFloat = 100;
    Marker marker;
    Circle circle;

    TextView Distance;
    String WhereToGo;

    //35.83748234000568, 128.75443559726486

    LatLng Gate = new LatLng(35.83701, 128.75303);              // 정문
    LatLng Hall = new LatLng(35.83418, 128.75506);              // 노천강당
    LatLng Museum = new LatLng(35.83650, 128.75632);            // 박물관
    LatLng Library = new LatLng(35.83307, 128.75793);           // 중앙도서관
    LatLng LectureBuilding = new LatLng(35.83238, 128.76006);   // 종합강의동
    LatLng HeadQuarters = new LatLng(35.83005, 128.76134);      // 본부본관
    LatLng Lake = new LatLng(35.82974, 128.75956);              // 거울못
    LatLng FolkVillage = new LatLng(35.82882,128.76055);        // 민속촌

    @SuppressWarnings("all")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        setTitle("현재위치 확인하기");

        Start = MapActivity.this;

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);

        Intent mapIntent = getIntent();
        WhereToGo = mapIntent.getStringExtra("WhereToGo");

        Distance = findViewById(R.id.distance);
        gpsListener = new GPSListener();


        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,minTime,minDistance,this);

        supportMapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);

        Log.i("Map", "I'm Here");

    }

    class GPSListener implements LocationListener {

        @Override
        public void onLocationChanged(@NonNull Location location) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            showCurrentLocation(latitude,longitude);
            showDistance(latitude, longitude);

            Log.i("Map", "show current location");
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Log.i("Map", "map ready");
        Map = googleMap;

        marker = Map.addMarker(new MarkerOptions().position(Gate).title("정문"));
        marker = Map.addMarker(new MarkerOptions().position(Hall).title("노천강당"));
        marker = Map.addMarker(new MarkerOptions().position(Museum).title("박물관"));
        marker = Map.addMarker(new MarkerOptions().position(Library).title("중앙도서관"));
        marker = Map.addMarker(new MarkerOptions().position(LectureBuilding).title("종합강의동"));
        marker = Map.addMarker(new MarkerOptions().position(HeadQuarters).title("본부본관"));
        marker = Map.addMarker(new MarkerOptions().position(Lake).title("거울못"));
        marker = Map.addMarker(new MarkerOptions().position(FolkVillage).title("민속촌"));
        marker = Map.addMarker(new MarkerOptions().position(FolkVillage).title("민속촌"));

        Map.moveCamera(CameraUpdateFactory.newLatLng(Gate));
        Map.animateCamera(CameraUpdateFactory.newLatLngZoom(Gate, 17));

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

        try{
            if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, gpsListener);
                Log.i("Map", "location request");
            }
        } catch (SecurityException e){
            e.printStackTrace();
        }
    }

    private void showCurrentLocation(double latitude, double longitude) {
        LatLng currentLocation = new LatLng(latitude, longitude);
        showCurrentLocationMarker(currentLocation);
        Log.i("Map", "show current location marker");
    }

    private void showCurrentLocationMarker(LatLng currentLocation) {
        if (marker == null) {
            marker = Map.addMarker(new MarkerOptions().position(currentLocation).title("CurrentLocation"));
        } else {
            marker.remove();
            marker = Map.addMarker(new MarkerOptions().position(currentLocation).title("CurrentLocation"));
        }

        if(circle == null){
            circle = Map.addCircle(new CircleOptions().center(currentLocation).radius(100).strokeWidth(1.0f).fillColor(Color.parseColor("#1AFFFFFF")));
        } else{
            circle.remove();
            circle = Map.addCircle(new CircleOptions().center(currentLocation).radius(100).strokeWidth(1.0f).fillColor(Color.parseColor("#1AFFFFFF")));
        }
    }

    private void showDistance(double latitude, double longitude){
        Location currentLocation = new Location("currentLocation");
        Location destinationLocation = new Location("destinationLocation");
        currentLocation.setLatitude(latitude);
        currentLocation.setLongitude(longitude);

        switch (WhereToGo){
            case "정문":
                destinationLocation.setLatitude(Gate.latitude);
                destinationLocation.setLongitude(Gate.longitude);
                break;
            case "노천강당":
                destinationLocation.setLatitude(Hall.latitude);
                destinationLocation.setLongitude(Hall.longitude);
                break;
            case "박물관":
                destinationLocation.setLatitude(Museum.latitude);
                destinationLocation.setLongitude(Museum.longitude);
                break;
            case "중앙도서관":
                destinationLocation.setLatitude(Library.latitude);
                destinationLocation.setLongitude(Library.longitude);
                break;
            case "종합강의동":
                destinationLocation.setLatitude(LectureBuilding.latitude);
                destinationLocation.setLongitude(LectureBuilding.longitude);
                break;
            case "본부본관":
                destinationLocation.setLatitude(HeadQuarters.latitude);
                destinationLocation.setLongitude(HeadQuarters.longitude);
                break;
            case "거울못":
                destinationLocation.setLatitude(Lake.latitude);
                destinationLocation.setLongitude(Lake.longitude);
                break;
            case "민속촌":
                destinationLocation.setLatitude(FolkVillage.latitude);
                destinationLocation.setLongitude(FolkVillage.longitude);
                break;
        }

        distanceFloat = currentLocation.distanceTo(destinationLocation); // 단위: 미터
        Distance.setText(WhereToGo + "까지 거리 : " + distanceFloat + "m");

        if(distanceFloat < 50){
            switch (WhereToGo){
                case "정문":
                    Intent mapIntent = new Intent(getApplicationContext(), FirstActivity.class);
                    startActivity(mapIntent);
                    break;
                case "노천강당":

                    break;
                case "박물관":

                    break;
                case "중앙도서관":

                    break;
                case "종합강의동":

                    break;
                case "본부본관":

                    break;
                case "거울못":

                    break;
                case "민속촌":

                    break;
            }

        }

        Log.i("Map", "distanceFloat " + distanceFloat);
    }



    @Override
    public void onDenied(int i, String[] strings) {

    }

    @Override
    public void onGranted(int i, String[] strings) {

    }
}

