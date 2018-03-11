package com.dam.proyecto.gmap.gmap;

/**
 * Created by dam on 21/02/2018.
 */

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.concurrent.Executor;

import static android.content.ContentValues.TAG;

public class LocationService extends Service {

    public LocationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate() {
        super.onCreate();
        Intent i=new Intent(this, LocationService.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        Notification.Builder constructorNotificacion = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("notificación servicio")
                .setContentText("texto servicio")
                .setContentIntent(PendingIntent.getActivity(this, 0, i, 0));
        /**
         * Un servicio normal no serviría para este caso, porque esta pensado para el ahorro de energía,
         * no haria lo que le pedimos que haga por cada intervalo.
         * Un servicio FOREGROUND hace lo que necesitamos. Lo forzamos en la siguiente linea.
         * El S. Foreground esta obligado a notificarlo al usuario.
         */
        startForeground(1, constructorNotificacion.build());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //do the job ...
        return START_STICKY; // si el servicio es muere, será recuperado cuando sea posible
    }

}