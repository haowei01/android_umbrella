package com.haowei.haowei.umbrella;

import android.location.Location;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.*;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends ActionBarActivity
        implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {
    String DEBUG_GOOGLE_CLIENT = "GoogleClientAPI";
    String DEBUG_GEO_LOCATION_CHANGE = "GeoLocationChange";
    private Button mButton;
    private EditText mEditText;
    private TextView mGeoText;
    private WebService webService;
    private ActionBarActivity mainActivity;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mainActivity = this;

        mEditText = (EditText) findViewById(R.id.location_input);
        mGeoText = (TextView) findViewById(R.id.geo_location);

        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webService = new WebService(mainActivity);
                String location = mEditText.getText().toString();
                webService.execute(location);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(DEBUG_GOOGLE_CLIENT, "GoogleClientAPI connected and started");
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        Log.i(DEBUG_GEO_LOCATION_CHANGE, "Location is "+ location.toString());
        webService = new WebService(mainActivity);
        webService.execute(
                Double.toString(location.getLatitude()),
                Double.toString(location.getLongitude())
        );
        mGeoText.setText(location.toString());
        getUpdatedLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.w(DEBUG_GOOGLE_CLIENT, "GoogleClientAPI connection FAILED!");
    }

    private void getUpdatedLocation(){
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(DEBUG_GEO_LOCATION_CHANGE, "geo location changed, to " + location.toString());
        webService = new WebService(mainActivity);
        webService.execute(
                Double.toString(location.getLatitude()),
                Double.toString(location.getLongitude())
        );
        mGeoText.setText(location.toString());
    }
}
