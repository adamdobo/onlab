package hu.adamdobo.onlabproject.fbcloudmessaging;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import java.util.HashMap;

import hu.adamdobo.onlabproject.R;
import hu.adamdobo.onlabproject.drawer.DrawerActivity;

/**
 * Created by Ádám on 4/25/2018.
 */

public class MyNotificationManager {
    private Context context;
    public static String CHANNEL_ID = "MyNotificationChannel";
    private static String CHANNEL_NAME = "ForegroundServiceChannel";
    private static String CHANNEL_DESCRIPTION = "Notification channel for foreground service";
    private static MyNotificationManager mInstance;


    private MyNotificationManager(Context context) {
        this.context = context;
    }

    public static synchronized MyNotificationManager getInstance(Context context){
        if(mInstance ==null){
            mInstance = new MyNotificationManager(context);
        }
        return mInstance;
    }
    @RequiresApi(Build.VERSION_CODES.O)
    public void createMainNotificationChannel() {
        String id = CHANNEL_ID;
        String name = CHANNEL_NAME;
        String description = CHANNEL_DESCRIPTION;
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel mChannel = new NotificationChannel(id, name, importance);
        mChannel.setDescription(description);
        mChannel.enableLights(true);
        mChannel.setLightColor(Color.RED);
        mChannel.enableVibration(true);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.createNotificationChannel(mChannel);
    }

   public void displayNotification(String title, String body, String icon){
       HashMap<String, Integer> iconMap = new HashMap<>();
       iconMap.put(icon, R.drawable.ic_gavel_black_24dp);
       NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
               .setSmallIcon(iconMap.get(icon).intValue())
               .setContentTitle(title)
               .setContentText(body);

       Intent intent = new Intent(context, DrawerActivity.class);

       PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

       mBuilder.setContentIntent(pendingIntent);

       NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

       if(notificationManager != null){
           notificationManager.notify(1, mBuilder.build());
       }
   }
}
