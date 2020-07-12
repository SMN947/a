
package com.smn947.servermonitor.extras;

import android.content.*;
import android.net.*;
import android.widget.*;
import java.io.*;

public class Ping {
	public String net = "NO_CONNECTION";
	public String host = "";
	public String ip = "";
	public int dns = Integer.MAX_VALUE;
	public int cnt = Integer.MAX_VALUE;
	public String e = "";

	public String ExecuteP (Context ctx, String host) {

		Boolean isWiFi = false;
		Boolean isMobile = false;
		Boolean isConnected = false;

		ConnectivityManager ConnMan = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = ConnMan.getActiveNetworkInfo();

		// True if network is available.
		if (activeNetwork != null) {
			isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
			isMobile = activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE;
			isConnected = activeNetwork.isConnectedOrConnecting();

			if (isConnected) {
				String res = isConnectedToThisServer(host, ctx);
				return res;
			}else{
				return "Is no connection available";
			}
		}else{
			 return "no network available";
		}
	}

    // Function that uses ping, takes server name or ip as argument.
    public String isConnectedToThisServer (String host, Context ctx) {
        // https://stackoverflow.com/questions/3905358/how-to-ping-external-ip-from-java-android
        Runtime runtime = Runtime.getRuntime();
        try {
			java.lang.Process ipProcess = runtime.exec("/system/bin/ping -c 1 " + host);
			int exitValue = ipProcess.waitFor();
			System.out.println(exitValue);

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(ipProcess.getInputStream()));

			String s;
			String res = "";
			while ((s = stdInput.readLine()) != null) {
				res += s + " ";
			}
			ipProcess.destroy();
			System.out.println(res);
			return res;
			//return (exitValue == 0);
        } catch (Exception e) {
            e.printStackTrace();
			return e.getMessage().toString();
        }
        //return false;
    }
}
