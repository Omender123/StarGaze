package star.example.stargaze.notification_services;

import android.app.IntentService;
import android.content.Intent;


import star.example.stargaze.activities.MainActivity;

public class NotificationIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public NotificationIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Intent inten = new Intent(getApplicationContext(), MainActivity.class);
        inten.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.getStringExtra("status");
        startActivity(inten);
    }
}
