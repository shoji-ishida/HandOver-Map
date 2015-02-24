package com.example.ishida.handover;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class HandOverService extends Service {
    public HandOverService() {
        Log.d("HandOverService", "hm?");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
