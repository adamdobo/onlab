package hu.adamdobo.onlabproject;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import hu.adamdobo.onlabproject.map.MapsInteractor;
import hu.adamdobo.onlabproject.map.MapsPresenter;
import hu.adamdobo.onlabproject.map.MapsPresenterImpl;
import hu.adamdobo.onlabproject.map.MapsView;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Created by Ádám on 5/9/2018.
 */

@RunWith(MockitoJUnitRunner.class)
public class MapsUnitTest {
    @Mock
    MapsInteractor mapsInteractor;

    @Mock
    MapsView mapsView;

    MapsPresenter presenter;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        presenter = new MapsPresenterImpl(mapsView, mapsInteractor, "key");
    }

    @Test
    public void testOnLocationRefreshed(){
        presenter.onLocationRefreshed();
        verify(mapsInteractor).getLatitude();
        verify(mapsInteractor).getLongitude();
        verify(mapsInteractor).calculateDistanceFromAddress(mapsView.getGeocoder());
        verify(mapsView).refreshMarker(new LatLng(0,0));
    }

    @Test
    public void testOnMapLoaded(){
        presenter.onMapLoaded();
        verify(mapsInteractor).subscribeToPositionChanges("key");
    }

    @Test
    public void testOnDistanceAndDurationCalculated(){
        presenter.onDistanceAndDurationCalculated();
        verify(mapsView).refreshDistanceAndDuration(mapsInteractor.getDistance(), mapsInteractor.getDuration());
    }

    @Test
    public void testOnDestroy(){
        presenter.onDestroy();
        verify(mapsView, never()).refreshDistanceAndDuration("", "");
    }

}
