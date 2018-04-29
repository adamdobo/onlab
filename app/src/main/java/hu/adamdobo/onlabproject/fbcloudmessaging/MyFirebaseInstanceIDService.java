package hu.adamdobo.onlabproject.fbcloudmessaging;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Ádám on 4/28/2018.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService{

    private static String TAG = "Registration";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token:" + refreshedToken);
    }
}
