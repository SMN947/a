package com.smn947.servermonitor;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.InetAddress;
import android.view.*;

public class asd {

	protected void OnCreate(View view) {
		
	}
    Boolean isConnected = false,
	isWiFi = false,
	isMobile = false;

	public void qping (Context ctx) {
		ConnectivityManager ConnMan = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = ConnMan.getActiveNetworkInfo();

        // True if network is available.
        if (activeNetwork != null) {
            isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
            isMobile = activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE;
            isConnected = activeNetwork.isConnectedOrConnecting();
        }

        if (isConnected) {
            if (isWiFi) {
                Toast.makeText(ctx, "Yes, WiF", Toast.LENGTH_SHORT)
					.show();

                // ping google server for testing purpose
                if (isConnectedToThisServer("www.google.com")) {
                    Toast.makeText(ctx, "Yes, Connected to Google", Toast.LENGTH_SHORT)
						.show();
                }
				else {
                    Toast.makeText(ctx, "No Google Connection", Toast.LENGTH_SHORT)
						.show();
                }
            }


            if (isMobile) {

                Toast.makeText(ctx, "Yes, Mobile", Toast.LENGTH_SHORT)
					.show();
                if (isConnectedToThisServer("www.google.com")) {
                    Toast.makeText(ctx, "Yes, Connected to Google", Toast.LENGTH_SHORT).show();
                }
				else {
                    Toast.makeText(ctx, "No Google Connection", Toast.LENGTH_SHORT).show();
                }
            }
        }
		else {
            Toast.makeText(ctx, "No Network", Toast.LENGTH_SHORT).show();
        }
    }


    // Function that uses ping, takes server name or ip as argument.
    public boolean isConnectedToThisServer (String host) {
        // https://stackoverflow.com/questions/3905358/how-to-ping-external-ip-from-java-android
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 " + host);
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
		catch (IOException e) {
            e.printStackTrace();
        }
		catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}
