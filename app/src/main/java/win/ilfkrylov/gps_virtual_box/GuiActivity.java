package win.ilfkrylov.gps_virtual_box;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;

public class GuiActivity extends MainActivity {

    DrawView drawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawView = new DrawView(this, this);
        setContentView(drawView);

    }

    public void checkEnabled() {
        drawView.invalidate();
    }

    @Override
    public void change(Location location) {
        super.change(location);
        drawView.invalidate();
    }

}