package win.ilfkrylov.gps_virtual_box;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends Activity {

    LocationManager locationManager;

    Location previousLocation;
    Location currentLocation;



    Location destination = new Location("");

    Double bearing = null;
    Float distance;
    double destinationBearing;
    double currentBearing;

    final int MIN_DISTANCE = 30; // meters


//    DrawView drawView;
public void setDestination() {

    HttpURLConnection connection = null;
    try {
        URL url = new URL("http://ilfkrylov.win/location");
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setUseCaches(false);
        connection.setConnectTimeout(500);
        connection.setConnectTimeout(500);

        connection.addRequestProperty("User-Agent", "Mozilla/5.0");

        connection.connect();
        int respCode = connection.getResponseCode();

        if (HttpURLConnection.HTTP_OK == respCode) {
            BufferedReader redaer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            System.out.println(redaer.readLine());
        } else {
            System.out.println(respCode);
        }
    } catch (Exception e) {
        e.printStackTrace();
        destination.setLatitude(56.844646d);
        destination.setLongitude(53.212201d);
    } finally {
        if (connection != null) {
            connection.disconnect();
        }
    }

}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

//        drawView = new DrawView(this, this);
//        setContentView(drawView);

        setDestination();

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 1000 * 3, 10, locationListener);
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 1000 * 10, 10, locationListener);
        checkEnabled();
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }

    LocationListener locationListener = new MainLocationListener(this);

    public void checkEnabled() {
    }

    public void change(Location location) {
        previousLocation = currentLocation;
        currentLocation = location;

        if (previousLocation != null) {
            currentBearing = previousLocation.bearingTo(location);
            destinationBearing = location.bearingTo(destination);
            bearing = destinationBearing - currentBearing;
            distance = location.distanceTo(destination);
        }
    }
}