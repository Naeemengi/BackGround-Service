package com.flotcaptainest.alarmmanagerservice;

import android.Manifest;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;

public class AppMonitoringBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        if (RemoteConfig.getConfig().isShowLogs()) {
//            //  Toast.makeText(context,"AppMonitoringBroadCastReceiver Running ", Toast.LENGTH_SHORT).show();
////            LogCat.show("Alarm Running" + "" + new Date().toString());
//        }
//        if (PrefManager.getInstance(context).getDriverOnlineStatus()) {
        if (true) {
//            if (LocationUtils.checkHighAccuracyLocationMode(context)) {
            if (true) {
//              / startOnlineWatcherService(noservices.class, context);
                scheduleJob(context);
            }
        }
    }

    public static void startOnlineWatcherService(Class<?> serviceClass, Context context) {
//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        if (false) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
//        if (PrefManager.getInstance(context).getDriverOnlineStatus()) {
        if (true) {
//            if (!isMyServiceRunning(noservices.class, context)) {
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    context.startForegroundService(new Intent(context, noservices.class));}
                else {
                    context.startService(new Intent(context, noservices.class));
                }
//            }
            if (! AlarmManger.getInstance(context).isRepeatingAlaramManagerExist(context)) {
                AlarmManger.getInstance(context).StartRepeatingMonitoring(context);
            }
        }
            //        else {
//            Utils.stopOnlineWatcherService(OnlineWatcherService.class, context);
//        }
    }
    public static void scheduleJob(Context context) {
        ComponentName serviceComponent = new ComponentName(context, noservices.class);
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
        builder.setMinimumLatency(1 * 1000); // wait at least
        builder.setOverrideDeadline(3 * 1000); // maximum delay
        //builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED); // require unmetered network
        //builder.setRequiresDeviceIdle(true); // device should be idle
        //builder.setRequiresCharging(false); // we don't care if the device is charging or not
        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
        jobScheduler.schedule(builder.build());
    }
}
