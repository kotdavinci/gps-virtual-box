package win.ilfkrylov.gps_virtual_box;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.TextView;

import java.util.Date;

public class XmlActivity extends MainActivity {
    TextView tvEnabledGPS;
    TextView tvLocationGPS;
    TextView tvEnabledNet;
    TextView tvLocationNet;
    TextView tvLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_xml);
        tvEnabledGPS = (TextView) findViewById(R.id.tvEnabledGPS);
        tvLocationGPS = (TextView) findViewById(R.id.tvLocationGPS);
        tvEnabledNet = (TextView) findViewById(R.id.tvEnabledNet);
        tvLocationNet = (TextView) findViewById(R.id.tvLocationNet);
        tvLocation = (TextView) findViewById(R.id.tvLocation);

    }


    @Override
    public void change(Location location) {
        super.change(location);
        if (location == null)
            return;
        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            tvLocationGPS.setText(formatLocation(location));
        } else if (location.getProvider().equals(
                LocationManager.NETWORK_PROVIDER)) {
            tvLocationNet.setText(formatLocation(location));
        }
        tvLocation.setText(getLocationText());
    }

    private String getLocationText() {
        return String.format("Destination: %s\n" +
                "Previous: %s\n" +
                "Current: %s\n" +
                "Distance: %s m", formatLocation(destination), formatLocation(previousLocation), formatLocation(currentLocation), String.valueOf(distance));
    }


    private String formatLocation(Location location) {
        if (location == null)
            return "";
        return String.format(
                "lat = %1$.6f, lon = %2$.6f",
                location.getLatitude(), location.getLongitude());
    }

    public void checkEnabled() {
        tvEnabledGPS.setText("Enabled: "
                + locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER));
        tvEnabledNet.setText("Enabled: "
                + locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER));
    }

    public void onClickLocationSettings(View view) {
        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    }


    public void onClickGUI(View view) {
        startActivity(new Intent(this, GuiActivity.class));
    }
}
