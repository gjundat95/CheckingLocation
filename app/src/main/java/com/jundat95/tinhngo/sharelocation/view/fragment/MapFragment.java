package com.jundat95.tinhngo.sharelocation.view.fragment;

import android.app.Fragment;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;
import com.jundat95.tinhngo.sharelocation.R;
import com.jundat95.tinhngo.sharelocation.until.Until;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tinhngo on 12/1/16.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback,GoogleMap.OnPoiClickListener,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap mMap;
    private MapView mMapView;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private String lat = null, lon = null;
    private List<LatLng> listPosition = new ArrayList<LatLng>();
    private List<Marker> listMakers = new ArrayList<>();

    private static final String FILTER = "FILTER";

    //BottonSheet
    public AppCompatActivity activity;

    public static MapFragment newInstanse(AppCompatActivity activity){
        MapFragment f = new MapFragment();
        f.activity = activity;
        return f;
    }

    public MapFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_map,container,false);
        mMapView = (MapView) v.findViewById(R.id.map_fragment);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(this);
        buildGoogleApiClient();
        return  v;

    }

    public void enablePermissionLocation(){
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
        }
        GoogleMapOptions options = new GoogleMapOptions().liteMode(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // Click position show info
        mMap.setOnPoiClickListener((GoogleMap.OnPoiClickListener) this);
        setEventOnMarker();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
        }
        GoogleMapOptions options = new GoogleMapOptions().liteMode(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // Click position show info
        mMap.setOnPoiClickListener((GoogleMap.OnPoiClickListener) this);
        // Add a marker in Sydney and move the camera
//        LatLng jundat95 = new LatLng(21.590626, 105.8016043);
//        mMap.addMarker(new MarkerOptions().position(jundat95).title("Jundat95"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(jundat95, 10));

        //setCircle(googleMap);

        //shareLocation("Add position");
        setEventOnMarker();
    }

    public void shareLocation(String title) {

        if(lat != null && lon != null){
            double latitude = Until.tryParse(lat);
            double longitude = Until.tryParse(lon);
            if(latitude != 0 && longitude != 0){
                LatLng position = new LatLng(latitude,longitude);
                mMap.addMarker(new MarkerOptions().position(position).title(title));
                // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));
            }
        }

    }


    public void removeAllLocation(){
        if(listMakers.size() > 0){
            for(Marker item : listMakers){
               item.remove();
            }
        }
    }

    private void setEventOnMarker(){
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if(marker != null){
                    //Log.d("MarkerLog:",marker.getId()+marker.getTag());
                    MarkerDetailFragment fragment = MarkerDetailFragment.newInstance();
                    Bundle bundle = new Bundle();
                    fragment.setArguments(bundle);

                    fragment.show(activity.getSupportFragmentManager(),fragment.getTag());

                    return  false;
                }else
                return true;
            }
        });
    }

    public LatLng getPosition(){
        LatLng pos = null;
        if(lat != null && lon != null){
            double latitude = Until.tryParse(lat);
            double longitude = Until.tryParse(lon);
            if(latitude != 0 && longitude != 0){
                pos = new LatLng(latitude,longitude);
            }
        }
        return pos;
    }

    private void setCircle(GoogleMap map) {

        Circle circle = mMap.addCircle(new CircleOptions()
                .center(new LatLng(21.590626, 105.8016043))
                .radius(1000)
                .strokeWidth(10)
                .strokeColor(Color.GREEN)
                .fillColor(Color.argb(128, 255, 0, 0))
                .clickable(true));

        map.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {

            @Override
            public void onCircleClick(Circle circle) {
                // Flip the r, g and b components of the circle's
                // stroke color.
                int strokeColor = circle.getStrokeColor() ^ 0x00ffffff;
                circle.setStrokeColor(strokeColor);
            }
        });
    }

    @Override
    public void onPoiClick(PointOfInterest poi) {
        Toast.makeText(getActivity(), "Clicked: " +
                        poi.name + "\nPlace ID:" + poi.placeId +
                        "\nLatitude:" + poi.latLng.latitude +
                        " Longitude:" + poi.latLng.longitude,
                Toast.LENGTH_LONG).show();
    }

    synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(100); // Update location every second

        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getActivity(),"Permission Activity",Toast.LENGTH_SHORT).show();
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            lat = String.valueOf(mLastLocation.getLatitude());
            lon = String.valueOf(mLastLocation.getLongitude());

        }
        updateUI();
    }

    @Override
    public void onLocationChanged(Location location) {
        lat = String.valueOf(location.getLatitude());
        lon = String.valueOf(location.getLongitude());
        //updateUI();
    }

    private void updateUI() {
        Toast.makeText(getActivity(), "Position:"+
                        "\nLatitude:" + lat +
                        "\nLongitude:" + lon,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


}
