package hu.adamdobo.onlabproject.map;

import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Ádám on 4/11/2018.
 */

public interface MapsInteractor {
    
    void subscribeToPositionChanges(String itemID);
    
    double getLatitude();
    
    double getLongitude();

    void setPresenter(MapsPresenterImpl mapsPresenter);

    void calculateDistanceFromAddress(Geocoder coder);

    void calculateDistance(LatLng latLng);

    void onAddressReady(Geocoder coder);

    String getDistance();

    String getDuration();
}
