package com.haowei.haowei.umbrella;

import android.app.Activity;
import android.location.Location;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by haowei on 8/1/15.
 */
public class MyLocationService implements LocationListener{
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    Activity mainActivity;

    void getUpdatedLocation(){
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(1000);
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    public MyLocationService(GoogleApiClient googleApiClient, Activity activity){
        mGoogleApiClient = googleApiClient;
        mainActivity = activity;
    }

    @Override
    public void onLocationChanged(Location location) {

    }
}
