package win.ilfkrylov.gps_virtual_box;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.location.LocationManager;
import android.view.View;

class DrawView extends View {
    private MainActivity mainActivity;
    private Paint paint;
    private Paint textPain;
    final int mWidth;
    final int mHeight;
    final int mCenterX;
    final int mCenterY;
    final float scale;
    final int radius;
    Rect button;


    public DrawView(MainActivity mainActivity, Context context) {
        super(context);
        this.mainActivity = mainActivity;

        scale = context.getResources().getDisplayMetrics().density;

        mWidth = context.getResources().getDisplayMetrics().widthPixels;
        mHeight = context.getResources().getDisplayMetrics().heightPixels;
        radius = (int) (150 * scale);
        mCenterX = mWidth / 2;
        mCenterY = (int) (mHeight - radius - 80 * scale);

        button = new Rect(mCenterX - 20, mCenterY - 20, mCenterX + 20, mCenterY + 20);

        paint = new Paint();


    }

    private void drawBox(Canvas canvas) {
        paint.setColor(Color.WHITE);
        paint.setTextSize(40 * scale);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setStrokeWidth(1 * scale);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawText("GPS Virtual Box", mCenterX, 40 * scale, paint);

        String boxText;
        if (mainActivity.distance != null && mainActivity.distance < mainActivity.MIN_DISTANCE) {
            paint.setColor(Color.GREEN);
            boxText = "OPEN";
        } else {
            paint.setColor(Color.RED);
            boxText = "CLOSED";
        }

        canvas.drawRect(mCenterX - 100 * scale, 70 * scale, mCenterX + 100 * scale, 140 * scale, paint);

        paint.setColor(Color.BLACK);
        paint.setTextSize(40 * scale);
        canvas.drawText(boxText, mCenterX, 120 * scale, paint);

    }

    private void drawStatus(Canvas canvas) {
        String text = getStatus();

        if (text != null) {
            paint.setColor(Color.WHITE);
            paint.setTextSize(30 * scale);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setStrokeWidth(1 * scale);
            paint.setStyle(Paint.Style.FILL);

            canvas.drawText(text, mCenterX, mHeight - 40 * scale, paint);
        }
    }

    private String getStatus() {
        if (!mainActivity.locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return "GPS disabled";
        } else if (mainActivity.currentLocation == null) {
            return "Search GPS";
        } else if (mainActivity.bearing == null) {
            return "Move";
        }
        return null;
    }

    private void drawTarget(Canvas canvas) {
        paint.setColor(Color.GREEN);

        paint.setStrokeWidth(4 * scale);

        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(mCenterX, mCenterY, radius, paint); // big circle

        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(mCenterX, mCenterY, 20 * scale, paint); // center circle

        if (mainActivity.bearing != null) {
            double angle = Math.toRadians(mainActivity.bearing);
            int x = (int) (radius * Math.sin(angle));
            int y = (int) (radius * Math.cos(angle));
            canvas.drawLine(mCenterX, mCenterY, mCenterX + x, mCenterY - y, paint);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.DKGRAY);

        drawTarget(canvas);
        drawStatus(canvas);
        drawBox(canvas);

    }




}
