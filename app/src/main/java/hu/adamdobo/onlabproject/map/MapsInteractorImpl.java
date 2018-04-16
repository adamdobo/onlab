package hu.adamdobo.onlabproject.map;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Handler;
import android.os.Looper;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Ádám on 4/11/2018.
 */

public class MapsInteractorImpl implements MapsInteractor {
    private DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    private double latitude, longitude;
    private MapsPresenter presenter;
    private String address;
    private String distance;
    private final static String BASE_URL = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=";
    private final static String DESTINATION = "&destinations=";
    private final static String LANGUAGE_AND_API_KEY = "&language=en-GB&key=AIzaSyAA7x2829xVHlm2aVxtFM07HradQJhgg3E";
    private String duration;

    @Override
    public void subscribeToPositionChanges(String itemID) {
        db.child("deliveryItems").child(itemID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.getKey().equals("latitude")) {
                        latitude = data.getValue(Double.class);
                    }
                    if (data.getKey().equals("longitude")) {
                        longitude = data.getValue(Double.class);
                    }
                }
                presenter.onLocationRefreshed();
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public double getLatitude() {
        return latitude;
    }

    @Override
    public double getLongitude() {
        return longitude;
    }

    @Override
    public void setPresenter(MapsPresenterImpl mapsPresenter) {
        this.presenter = mapsPresenter;
    }

    @Override
    public void calculateDistanceFromAddress(final Geocoder coder) {

        db.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("address").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    address = dataSnapshot.getValue(String.class);
                    onAddressReady(coder);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    @Override
    public void calculateDistance(LatLng latLng) {
        Location locationA = new Location("A");
        locationA.setLatitude(latLng.latitude);
        locationA.setLongitude(latLng.longitude);

        Location locationB = new Location("B");
        locationB.setLatitude(latitude);
        locationB.setLongitude(longitude);
        final OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(BASE_URL +
                        locationA.getLatitude() + "," + locationA.getLongitude() + DESTINATION +
                        locationB.getLatitude() + "," + locationB.getLongitude() + LANGUAGE_AND_API_KEY)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code" + response);
                } else {
                    String jsonData = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(jsonData);
                        JSONArray rows = jsonObject.getJSONArray("rows");
                        JSONArray elements = rows.getJSONObject(0).getJSONArray("elements");
                        JSONObject dist = elements.getJSONObject(0).getJSONObject("distance");
                        JSONObject dur = elements.getJSONObject(0).getJSONObject("duration");
                        duration = dur.getString("text");
                        distance = dist.getString("text");

                        Handler mainHandler = new Handler(Looper.getMainLooper());
                        Runnable myRunnable = new Runnable() {
                            @Override
                            public void run() {
                                presenter.onDistanceAndDurationCalculated();
                            }
                        };
                        mainHandler.post(myRunnable);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onAddressReady(Geocoder coder) {
        LatLng p1 = null;
        try {
            List<Address> addresses = coder.getFromLocationName(address, 5);
            if (addresses == null) {
                return;
            }
            Address location = addresses.get(0);
            p1 = new LatLng(location.getLatitude(),
                    location.getLongitude());

        } catch (IOException e) {
            e.printStackTrace();
        }
        calculateDistance(p1);
    }

    @Override
    public String getDistance() {
        return distance;
    }

    @Override
    public String getDuration() {
        return duration;
    }
}
