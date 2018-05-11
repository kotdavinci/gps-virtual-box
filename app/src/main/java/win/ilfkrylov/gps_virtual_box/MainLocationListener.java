package win.ilfkrylov.gps_virtual_box;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

class MainLocationListener implements LocationListener {
    // TODO: disable mock location

    private MainActivity mainActivity;

    MainLocationListener(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onLocationChanged(Location location) {
        mainActivity.change(location);
    }

    @Override
    public void onProviderDisabled(String provider) {
        mainActivity.checkEnabled();
    }

    @Override
    public void onProviderEnabled(String provider) {
        mainActivity.checkEnabled();

        if (ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            ActivityCompat.requestPermissions(mainActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
}
