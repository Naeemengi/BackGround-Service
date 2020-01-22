package com.flotcaptainest.alarmmanagerservice;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;


public class AlarmManger {

    private AlarmManager Alarammanager;
    @SuppressLint("StaticFieldLeak")
    private static AlarmManger alarmManger = null;
    private Context context;
    private Intent alarmIntent;

    public static AlarmManger getInstance(Context context) {
        if (alarmManger == null) {
            alarmManger = new AlarmManger(context);
        }
        return alarmManger;
    }

    public AlarmManger(Context context) {
        this.context = context;
        Alarammanager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmIntent = new Intent(context, AppMonitoringBroadCastReceiver.class);
    }

    private PendingIntent createPendingIntent(Context context) {
        alarmIntent.setAction("com.flotcaptainest.alarmmanagerservice.noservices");
        int DRIVER_SERVICE_RESTART_PI_REQUEST_CODE = 1314;
        return PendingIntent.getBroadcast(context, DRIVER_SERVICE_RESTART_PI_REQUEST_CODE,
                alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void StartRepeatingMonitoring(Context context) {
        try {
            Alarammanager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 6000, createPendingIntent(context));//first start will start asap
//            if (RemoteConfig.getConfig().isShowLogs())
//                //  Toast.makeText(context, "Starting Monitering", Toast.LENGTH_LONG).show();
//                LogCat.show("alaram start");

        } catch (Exception e) {
//            Utils.LogExceptionToFabric(e);
            e.printStackTrace();
        }
    }

    public void cancelRepeatingAlarm(Context context) {
        try {
            Alarammanager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent i = new Intent(context, AppMonitoringBroadCastReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    context, 1314, i, 0);
            Alarammanager.cancel(pendingIntent);
            pendingIntent.cancel();
//            if (RemoteConfig.getConfig().isShowLogs())
////            Toast.makeText(context, "cancelRepeatingAlarm", Toast.LENGTH_SHORT).show();
//                LogCat.show("alaram cancel");

        } catch (Exception e) {
//            Utils.LogExceptionToFabric(e);
            e.printStackTrace();
        }
    }

    public boolean isRepeatingAlaramManagerExist(Context context) {
        Intent myIntent = new Intent(context, AppMonitoringBroadCastReceiver.class);
        myIntent.setAction("com.flotcaptainest.alarmmanagerservice.noservices");
        return (PendingIntent.getBroadcast(context, 1314,
                myIntent, PendingIntent.FLAG_NO_CREATE) != null);
    }
}
