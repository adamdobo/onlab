package hu.adamdobo.onlabproject.delivery;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyLocationService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    private static final String TAG = MyLocationService.class.getSimpleName();
    private DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("deliveryItems");
    private GoogleApiClient googleApiClient;
    private String itemID;
    private LocationRequest locationRequest = LocationRequest.create();
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback = new LocationCallback(){
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            for (Location location : locationResult.getLocations()) {
                if (location != null) {
                    Log.d(TAG, "== location != null");
                    db.child(itemID).child("latitude").setValue(location.getLatitude());
                    db.child(itemID).child("longitude").setValue(location.getLongitude());
                }
            }
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        itemID = intent.getStringExtra("item_id");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setSmallestDisplacement(10);


        int priority = LocationRequest.PRIORITY_HIGH_ACCURACY;


        locationRequest.setPriority(priority);
        googleApiClient.connect();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "== Error On onConnected() Permission not granted");
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    db.child(itemID).child("latitude").setValue(location.getLatitude());
                    db.child(itemID).child("longitude").setValue(location.getLongitude());
                }
            }
        });

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
        Log.d(TAG, "Connected to Google API");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "Connection suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "Failed to connect to Google API");
    }
}
