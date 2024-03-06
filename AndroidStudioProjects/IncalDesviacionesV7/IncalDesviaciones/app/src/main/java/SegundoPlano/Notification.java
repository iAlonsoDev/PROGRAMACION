package SegundoPlano;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.incaldesviaciones.VistaDesviaciones;

public class Notification {
    private PendingIntent notificationPendingIntent;

    /**
     * This is the method  called to create the Notification
     */
    public android.app.Notification setNotification(Context context, String title, String text, int icon) {
        if (notificationPendingIntent == null) {
            Intent notificationIntent = new Intent(context, VistaDesviaciones.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            // notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            notificationPendingIntent = PendingIntent.getActivity(context,
                    0,
                    notificationIntent,
                    PendingIntent.FLAG_IMMUTABLE);
        }

        android.app.Notification notification;

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // OREO
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            CharSequence name = "INCAL S.A DE C.V.";
            //mContext.getString(R.string.channel_name);
            int importance = NotificationManager.IMPORTANCE_NONE;

            String CHANNEL_ID = "TI";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            //String description = mContext.getString(R.string.notifications_description);
            String description = "Incal Desviaciones: Departamento de TI";
            channel.setDescription(description);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
            notification = notificationBuilder
                    //the log is PNG file format with a transparent background
                    .setSmallIcon(icon)
                    .setColor(ContextCompat.getColor(context, android.R.color.white))
                    .setContentTitle(title)
                    .setContentText(text)
                    .setContentIntent(notificationPendingIntent)
                    .build();

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            notification = new NotificationCompat.Builder(context, "channel")
                    // to be defined in the MainActivity of the app
                    .setSmallIcon(icon)
                    .setContentTitle(title)
//                    .setColor(mContext.getResources().getColor(R.color.colorAccent))
                    .setContentText(text)
                    .setPriority(android.app.Notification.VISIBILITY_SECRET)
                    .setContentIntent(notificationPendingIntent).build();
        } else {
            notification = new NotificationCompat.Builder(context, "channel")
                    // to be defined in the MainActivity of the app
                    .setSmallIcon(icon)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setPriority(android.app.Notification.VISIBILITY_SECRET)
                    .setContentIntent(notificationPendingIntent).build();
        }

        return notification;
    }

}

