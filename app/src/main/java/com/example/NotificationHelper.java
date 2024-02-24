package com.example;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import com.example.NotiHandlerActivity;
import com.example.alarmmanagers.MainActivity;
import androidx.core.app.NotificationCompat;
public class NotificationHelper {
    private static final String CHANNEL_ID = "Your_Channel_ID";
    private static final String CHANNEL_NAME = "Your_Channel_Name";
    private static final int NOTIFICATION_ID = 1;

    /**
     * showNotificationBtn method
     * <p> Demonstrate a notification with one button building & firing
     * </p>
     *
     * @param context the context object that triggered the method
     * */
    public static void showNotificationBtn(Context context,String text) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        Intent intentOk = new Intent(context, NotiHandlerActivity.class);
        intentOk.putExtra("isOk", true);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                -1, intentOk, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent intentSnooze = new Intent(context, NotiHandlerActivity.class);
        intentSnooze.putExtra("isOk", false);
        PendingIntent pendingIntent2 = PendingIntent.getActivity(context,
                -2, intentSnooze, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(android.R.drawable.ic_menu_close_clear_cancel,
                        "OK", pendingIntent)
                .addAction(android.R.drawable.ic_menu_close_clear_cancel,
                        "SNOOZE", pendingIntent2)
                .setAutoCancel(true);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
