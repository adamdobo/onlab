package hu.adamdobo.onlabproject.map;

import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Ádám on 4/11/2018.
 */

public class MapsPresenterImpl implements MapsPresenter {
    private MapsView mapsView;
    private MapsInteractor mapsInteractor;
    private String itemID;
    private Geocoder geocoder;

    public MapsPresenterImpl(MapsView mapsView, MapsInteractor mapsInteractor, String itemID) {
        this.mapsView = mapsView;
        geocoder = mapsView.getGeocoder();
        this.mapsInteractor = mapsInteractor;
        this.itemID = itemID;
        mapsInteractor.setPresenter(this);

    }

    @Override
    public void onDestroy() {
        mapsView = null;
    }

    @Override
    public void onLocationRefreshed() {
        if(mapsView!= null){
            LatLng latLng = new LatLng(mapsInteractor.getLatitude(), mapsInteractor.getLongitude());
            mapsInteractor.calculateDistanceFromAddress(geocoder);
            mapsView.refreshMarker(latLng);
        }
    }

    @Override
    public void onMapLoaded() {
        mapsInteractor.subscribeToPositionChanges(itemID);
    }

    @Override
    public void onDistanceAndDurationCalculated() {
        if(mapsView!=null){
            mapsView.refreshDistanceAndDuration(mapsInteractor.getDistance(), mapsInteractor.getDuration());
        }
    }
}
