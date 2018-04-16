package hu.adamdobo.onlabproject.map;

import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import hu.adamdobo.onlabproject.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, MapsView{

    private GoogleMap mMap;
    private Marker marker;
    private double latitude, longitude;
    private MapsPresenter presenter;
    private Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        geocoder = new Geocoder(this);
        presenter = new MapsPresenterImpl(this, new MapsInteractorImpl(), intent.getStringExtra("item_id"));
        latitude = intent.getDoubleExtra("latitude", 0);
        longitude = intent.getDoubleExtra("longitude", 0);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng delivery = new LatLng(latitude, longitude);
        marker =  mMap.addMarker(new MarkerOptions().position(delivery).title("Your delivery"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(delivery, 15), 3000, null);
        presenter.onMapLoaded();
    }

    @Override
    public void refreshMarker(LatLng latLng) {
        if(marker != null) {
            marker.setPosition(latLng);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15), 3000, null);
        }
    }

    @Override
    public Geocoder getGeocoder() {
        return geocoder;
    }

    @Override
    public void refreshDistanceAndDuration(String distanceFromAddress, String duration) {
        if(marker != null){
            marker.setSnippet("Distance: " + distanceFromAddress
                    + " Duration: " + duration);
            marker.showInfoWindow();
        }
    }
}
