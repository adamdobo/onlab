package hu.adamdobo.onlabproject.map;

/**
 * Created by Ádám on 4/11/2018.
 */

public interface MapsPresenter {

    void onDestroy();

    void onLocationRefreshed();

    void onMapLoaded();

    void onDistanceAndDurationCalculated();
}
