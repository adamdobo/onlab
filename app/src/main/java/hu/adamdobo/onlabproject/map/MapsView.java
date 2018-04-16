package hu.adamdobo.onlabproject.map;

import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Ádám on 4/11/2018.
 */

public interface MapsView {

    void refreshMarker(LatLng latLng);

    Geocoder getGeocoder();

    void refreshDistanceAndDuration(String distanceFromAddress, String duration);
}
