package com.smn947.servermonitor.notifications;

import android.app.*;
import android.content.*;
import android.net.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import android.support.v4.content.*;
import android.util.*;
import com.smn947.servermonitor.*;
import com.smn947.servermonitor.broadcast_receivers.*;
import com.smn947.servermonitor.extras.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import android.widget.*;
import java.io.*;


/**
 * Created by klogi
 *
 *
 */
public class NotificationIntentService extends IntentService {

    private static final int NOTIFICATION_ID = 1;
    private static final String ACTION_START = "ACTION_START";
    private static final String ACTION_DELETE = "ACTION_DELETE";

    public NotificationIntentService () {
        super(NotificationIntentService.class.getSimpleName());
    }

    public static Intent createIntentStartNotificationService (Context context) {
        Intent intent = new Intent(context, NotificationIntentService.class);
        intent.setAction(ACTION_START);
        return intent;
    }

    public static Intent createIntentDeleteNotification (Context context) {
        Intent intent = new Intent(context, NotificationIntentService.class);
        intent.setAction(ACTION_DELETE);
        return intent;
    }

    @Override
    protected void onHandleIntent (Intent intent) {
        Log.d(getClass().getSimpleName(), "onHandleIntent, started handling a notification event");
        try {
            String action = intent.getAction();
            if (ACTION_START.equals(action)) {
                processStartNotification();
            }
        }
		finally {
            WakefulBroadcastReceiver.completeWakefulIntent(intent);
        }
    }

    private void processDeleteNotification (Intent intent) {
        // Log something?
    }

    private void processStartNotification () {
		
		//Ping PingServer = null;
        // Do something. For example, fetch fresh data from backend to create a rich notification?
		//try {
		//PingServer = ping("170.80.97.54", this);
		//}
		//catch (MalformedURLException e) {}

		final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

		/*String msg = "Server: " + PingServer.host + "\n";
		 msg += "Time :" + PingServer.cnt + "\n";
		 msg += "Over: " + PingServer.net;*/
		String msg = "";

		builder.setSmallIcon(R.drawable.ic_launcher)
            .setContentTitle("Servers Status")
            .setContentText("Run at: " + (new Date()).toLocaleString())
            .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
            .build();

        Intent mainIntent = new Intent(this, NotificationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setDeleteIntent(NotificationEventReceiver.getDeleteIntent(this));

        final NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID, builder.build());
    }



	public static Ping ping (String url, Context ctx) {
		Ping r = new Ping();
		if (isNetworkConnected(ctx)) {
			r.net = getNetworkType(ctx);
			try {
				String hostAddress;
				long start = System.currentTimeMillis();
				hostAddress = url; //InetAddress.getByName(url.getHost()).getHostAddress();
				long dnsResolved = System.currentTimeMillis();
				Socket socket = new Socket(hostAddress, 80);
				socket.close();
				long probeFinish = System.currentTimeMillis();
				r.dns = (int) (dnsResolved - start);
				r.cnt = (int) (probeFinish - dnsResolved);
				r.host = url;//.getHost();
				r.ip = hostAddress;
			}
			catch (Exception ex) {
				r.e = "Unable to ping";
			}
		}
		return r;
	}

	public static boolean isNetworkConnected (Context context) {
		ConnectivityManager cm =
            (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
	}

	@Nullable
	public static String getNetworkType (Context context) {
		ConnectivityManager cm =
            (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if (activeNetwork != null) {
			return activeNetwork.getTypeName();
		}
		return null;
	}
}
