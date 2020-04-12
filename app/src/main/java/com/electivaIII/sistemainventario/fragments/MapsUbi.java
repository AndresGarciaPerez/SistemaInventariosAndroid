package com.electivaIII.sistemainventario.fragments;


import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.electivaIII.sistemainventario.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapsUbi extends Fragment implements OnMapReadyCallback {


    private int MY_PERMISION_REQUEST_ACCESS_FINE_LOCATION;
    Double latitud, longitud, myLatitud, myLongitud;

    String almacen;
    TextView txtAlmacenUbicacion;
    Button btnSatelital, btnHibrido;

    private GoogleMap mMap;
    SupportMapFragment mapFragment;

    public MapsUbi() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_maps_ubi, container, false);

        getBundle();

        setAttributeText(v);

        btnSatelital = v.findViewById(R.id.btnSatelital);
        btnHibrido = v.findViewById(R.id.btnHibrido);
        btnSatelital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            }
        });

        btnHibrido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
        });

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment == null) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            ft.replace(R.id.map, mapFragment).commit();

        }
        mapFragment.getMapAsync(this);
        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        checkPermisions();
        miUbicacion();

        ConfigurationMaps();

        Antut(googleMap);

    }

    public void ConfigurationMaps() {

        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setAllGesturesEnabled(true);
        uiSettings.setRotateGesturesEnabled(true);

        mMap.setMyLocationEnabled(true);
    }

    public void Antut(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng ubi = new LatLng(latitud, longitud);
        setMarker(ubi, almacen);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubi, 16));
    }

    public void setMarker(LatLng ubi, String markerName) {
        mMap.addMarker(new MarkerOptions().position(ubi).title(markerName));
    }

    public void getBundle() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            latitud = bundle.getDouble("latitud");
            longitud = bundle.getDouble("longitud");
            almacen = bundle.getString("almacen");
        }
    }

    public void UpdateLocation(Location location) {
        if (location != null) {
            myLatitud = location.getLatitude();
            myLongitud = location.getLongitude();

            LatLng ubi = new LatLng(myLatitud, myLongitud);
            setMarker(ubi, "Mi ubicacion");
        }
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            UpdateLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    public void miUbicacion() {

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(getContext().LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        UpdateLocation(location);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1500,0, locationListener);


    }


    private void checkPermisions(){
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISION_REQUEST_ACCESS_FINE_LOCATION);
            return;
        }
    }

    public void setAttributeText(View v){

        txtAlmacenUbicacion = v.findViewById(R.id.txtAlmacenUbicacion);
        txtAlmacenUbicacion.setText("Almacen: "+almacen);
    }

}
