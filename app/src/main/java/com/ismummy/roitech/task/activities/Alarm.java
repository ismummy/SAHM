package com.ismummy.roitech.task.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import com.ismummy.roitech.task.helpers.NotificationUtils;
import com.ismummy.roitech.task.models.Task;

import java.util.Date;

public class Alarm extends BroadcastReceiver {
    public Alarm() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Ringtone ringtone = RingtoneManager.getRingtone(context, uri);
        ringtone.play();
        Task task = (Task) intent.getExtras().getSerializable("task");
        if (task != null) {
            Intent resultIntent = new Intent(context.getApplicationContext(), Home.class);
            showNotificationMessage(context.getApplicationContext(), task.getTitle(), task.getDescription(), new Date().toString(), resultIntent);
        }
    }

    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        NotificationUtils notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }
}
