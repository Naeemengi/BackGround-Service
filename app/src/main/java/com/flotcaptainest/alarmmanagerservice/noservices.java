package com.flotcaptainest.alarmmanagerservice;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.provider.SyncStateContract;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.JobIntentService;
import androidx.core.app.NotificationCompat;

import java.util.Timer;
import java.util.TimerTask;

public class noservices extends IntentService {

    public int counter=0;

    public noservices() {
        super("noservices");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        showOnlineNotification(getResources().getString(R.string.app_name), R.drawable.ic_launcher_background);
    }
    private void showOnlineNotification(String message, int NotificationIcon) {

        try {

            Bitmap icon = BitmapFactory.decodeResource(getResources(),
                    NotificationIcon);
            Intent notificationIntent = new Intent(this, MainActivity.class);
            notificationIntent.setAction("com.flotcaptainest.alarmmanagerservice");
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                try{
                    String id = "driver", name = "Shahi Sawari Driver App";
                    String desc = "Shahi Sawari Driver App";

                    int importance  = NotificationManager.IMPORTANCE_LOW;
                    if(message.equals("Reconnecting..."))
                        importance = NotificationManager.IMPORTANCE_LOW;
                    else
                        importance = NotificationManager.IMPORTANCE_DEFAULT;



                    NotificationChannel channel = new NotificationChannel(id, name, importance);
                    channel.setSound(null, null);
                    channel.setDescription(desc);
                    assert notificationManager != null;
                    notificationManager.createNotificationChannel(channel);

                    Notification.Builder builder = new Notification.Builder(this,id)
                            .setAutoCancel(false)
                            .setContentTitle(getResources().getString(R.string.app_name))
                            .setContentText(message)
                            //                                .setTicker(getResources().getString(R.string.shahi_driver))
                            //                                .setStyle(new Notification.BigTextStyle().bigText(getResources().getString(R.string.shahi_driver)))
                            .setSmallIcon(NotificationIcon)
                            .setContentIntent(pendingIntent)
                            .setOngoing(true);

                    Notification notification;
                    if(message.equals("Reconnecting...")){
                        notification = builder.setPriority(Notification.PRIORITY_MIN).build();
                        notification.defaults = 0;
                        notification.defaults |= Notification.DEFAULT_VIBRATE;
                    }else
                        notification = builder.build();

                    //                        notificationManager.notify(NOTIFICATION_ID , notification);
                    startForeground(101, notification);
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            } else {

                try {
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                            .setContentTitle(getResources().getString(R.string.app_name))
                            .setTicker(getResources().getString(R.string.app_name))
                            .setContentText(message)
                            .setSmallIcon(NotificationIcon)
                            .setLargeIcon(
                                    Bitmap.createScaledBitmap(icon, 128, 128, false))
                            .setContentIntent(pendingIntent)
                            .setOngoing(true);

                    Notification notification = builder.build();

                    startForeground(101, notification);
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
//                    new Utils().LogExceptionToFabric(e);

                }
            }
        } catch (Exception ex) {
            Log.d("",ex.getMessage());
        }

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {

        sendBroadcast(new Intent("YouWillNeverKillMe"));
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        sendBroadcast(new Intent("YouWillNeverKillMe"));
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sendBroadcast(new Intent("YouWillNeverKillMe"));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

//        Intent notificationIntent = new Intent(this, MainActivity.class);
//        PendingIntent pendingIntent =
//                PendingIntent.getActivity(this, 0, notificationIntent, 0);
//        String channelId = "";
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            channelId = createNotificationChannel("my_service", "My Background Service");
//        }
//
//        Notification notification =
//                new Notification.Builder(this,channelId )
//                        .setContentTitle(getText(R.string.app_name))
//                        .setContentText(getText(R.string.app_name))
//                        .setSmallIcon(R.drawable.ic_launcher_background)
//                        .setContentIntent(pendingIntent)
//                        .setTicker(getText(R.string.app_name))
//                        .build();
//
//        startForeground(101, notification);
        return START_STICKY;
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private String createNotificationChannel(String channelId ,String channelName ) {
        NotificationChannel chan =new NotificationChannel(channelId,
                channelName, NotificationManager.IMPORTANCE_HIGH);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(chan);
        return channelId;
    }



//    public boolean onStartJob(JobParameters params) {
//        startTimer();
//        Intent notificationIntent = new Intent(this, MainActivity.class);
//        PendingIntent pendingIntent =
//                PendingIntent.getActivity(this, 0, notificationIntent, 0);
//        String channelId = "";
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            channelId = createNotificationChannel("my_service", "My Background Service");
//        }
//
//        Notification notification =
//                new Notification.Builder(this,channelId )
//                        .setContentTitle(getText(R.string.app_name))
//                        .setContentText(getText(R.string.app_name))
//                        .setSmallIcon(R.drawable.ic_launcher_background)
//                        .setContentIntent(pendingIntent)
//                        .setTicker(getText(R.string.app_name))
//                        .build();
//
//        startForeground(101, notification);
//        return false;
//    }
//
//    @Override
//    public boolean onStopJob(JobParameters params) {
//        sendBroadcast(new Intent("YouWillNeverKillMe"));
//        return false;
//    }

    private Timer timer;
    private TimerTask timerTask;
    long oldTime=0;
    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, to wake up every 1 second
        timer.schedule(timerTask, 1000, 1000); //
    }

    /**
     * it sets the timer to print the counter every x seconds
     */
    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                Log.i("in timer", "in timer ++++  "+ (counter++));
            }
        };
    }

    /**
     * not needed
     */
    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
