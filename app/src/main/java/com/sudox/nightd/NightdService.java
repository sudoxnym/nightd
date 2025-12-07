package com.sudox.nightd;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import java.util.ArrayList;
import java.util.List;

public class NightdService extends Service {
    private static final String CHANNEL_ID = "nightd_channel";
    private static final int NOTIFICATION_ID = 1;
    
    private WindowManager windowManager;
    private List<View> overlayViews = new ArrayList<>();
    private int currentMode = 0; // 0=off, 1=dim, 2=black
    private int savedBrightness = 128;

    @Override
    public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent == null ? null : intent.getAction();
        
        if ("com.sudox.nightd.DIM".equals(action)) {
            setMode(1);
        } else if ("com.sudox.nightd.BLACK".equals(action)) {
            setMode(2);
        } else if ("com.sudox.nightd.OFF".equals(action)) {
            setMode(0);
        } else {
            // Toggle: off -> dim -> black -> off
            setMode((currentMode + 1) % 3);
        }
        
        return START_STICKY;
    }

    private void setMode(int mode) {
        // Clear existing overlays
        for (View v : overlayViews) {
            try { windowManager.removeView(v); } catch (Exception e) {}
        }
        overlayViews.clear();
        
        if (mode == 0) {
            // Off
            try {
                Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, savedBrightness);
            } catch (Exception e) {}
            currentMode = 0;
            stopForeground(true);
            stopSelf();
            return;
        }
        
        if (Settings.canDrawOverlays(this) == false) {
            Intent permIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            permIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(permIntent);
            return;
        }

        // Save brightness on first activation
        if (currentMode == 0) {
            try {
                savedBrightness = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
            } catch (Exception e) {}
        }
        
        try {
            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 0);
        } catch (Exception e) {}

        startForeground(NOTIFICATION_ID, buildNotification(mode));

        int layers = (mode == 1) ? 1 : 5;
        for (int i = 0; i < layers; i++) {
            FrameLayout layer = new FrameLayout(this);
            layer.setBackgroundColor(0xFF000000);

            WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE |
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS |
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                PixelFormat.TRANSLUCENT
            );
            params.gravity = Gravity.TOP | Gravity.START;
            params.screenBrightness = 0.0f;

            try {
                windowManager.addView(layer, params);
                overlayViews.add(layer);
            } catch (Exception e) { break; }
        }
        currentMode = mode;
    }

    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(
            CHANNEL_ID, "nightd overlay", NotificationManager.IMPORTANCE_LOW
        );
        channel.setDescription("nightd screen overlay service");
        getSystemService(NotificationManager.class).createNotificationChannel(channel);
    }

    private Notification buildNotification(int mode) {
        Intent offIntent = new Intent(this, NightdService.class);
        offIntent.setAction("com.sudox.nightd.OFF");
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, offIntent, PendingIntent.FLAG_IMMUTABLE);
        
        String modeText = (mode == 1) ? "dim" : "black";
        return new Notification.Builder(this, CHANNEL_ID)
            .setContentTitle("nightd: " + modeText)
            .setContentText("tap to disable")
            .setSmallIcon(android.R.drawable.ic_menu_view)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build();
    }

    @Override
    public IBinder onBind(Intent intent) { return null; }

    @Override
    public void onDestroy() {
        setMode(0);
        super.onDestroy();
    }
}
