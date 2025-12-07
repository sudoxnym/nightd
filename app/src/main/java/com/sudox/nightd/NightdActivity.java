package com.sudox.nightd;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

public class NightdActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Check overlay permission first
        if (!Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + getPackageName())
            );
            startActivityForResult(intent, 100);
        } else {
            toggleService();
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && Settings.canDrawOverlays(this)) {
            toggleService();
        }
        finish();
    }

    private void toggleService() {
        Intent intent = new Intent(this, NightdService.class);
        intent.setAction("com.sudox.nightd.TOGGLE");
        startForegroundService(intent);
    }
}
