package com.smn947.trackergps;

import android.app.*;
import android.os.*;
import android.view.*;
import android.location.*;
import android.widget.*;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.app.Activity;
import org.w3c.dom.*;
import android.hardware.*;
import android.hardware.fingerprint.*;
import java.util.*;
import java.io.*;
import org.apache.http.*;

import java.util.logging.*;
import android.os.storage.*;
import org.apache.http.client.*;
import org.apache.http.impl.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.message.*;
import org.apache.http.client.entity.*;
import org.apache.http.util.*;
import java.net.*;

public class MainActivity extends Activity {

	LocationListener locationListener;
	String log = "SMN947 \n";
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//msg("SMN947 Developers");
	}

	public void startGps (View view) {
		TextView textView = findViewById(R.id.logs);
		log += "Iniciando aplicacion\n";
		textView.setText(log);

		LocationManager location = (LocationManager) getSystemService("location");

		if (!location.isProviderEnabled("gps")) {
			log += "GPS desactivado\n";
			textView.setText(log);
			return;
		} else {
			log += "GPS funcionando\n";
			textView.setText(log);
		}

		locationListener = new LocationListener(){
			TextView textView = findViewById(R.id.logs);
			public void onLocationChanged (Location location) {
				TextView lat = findViewById(R.id.lat);
				TextView lon =findViewById(R.id.lon);
				TextView vel =findViewById(R.id.vel);
				TextView pre =findViewById(R.id.pre);


				ProgressBar dec = findViewById(R.id.progressBar);
				int p = dec.getProgress();
				System.out.println(p);
				dec.setProgress(10);
				int t = dec.getProgress();
				System.out.println(t);

				Progress(Double.toString((60.0 * 60.0 * ((double) location.getSpeed())) / 1000));

				lat.setText(Double.toString((double) location.getLatitude()));
				lon.setText(Double.toString((double) location.getLongitude()));
				vel.setText(Double.toString((60.0 * 60.0 * ((double) location.getSpeed())) / 1000) + "Km/h");
				pre.setText(Double.toString((double) location.getAccuracy()));

				log += "Actualizado " + new Date() + "\n";
				System.out.println("Current Now" + Double.toString((double)BatteryManager.BATTERY_PROPERTY_CURRENT_NOW));
				System.out.println("Current Average" + BatteryManager.BATTERY_PROPERTY_CURRENT_AVERAGE);
				textView.setText(log);
				try {
					
					URL url = new URL("https://angelsdream.com.co/tracking/a.php");
					//URL url = new URL("http://g.php"); // URL to your application
					Map<String,Object> params = new LinkedHashMap<>();
					params.put("value", 5); // All parameters, also easy
					params.put("id", 17);

					StringBuilder postData = new StringBuilder();
					// POST as urlencoded is basically key-value pairs, as with GET
					// This creates key=value&key=value&... pairs
					for (Map.Entry<String,Object> param : params.entrySet()) {
						if (postData.length() != 0) postData.append('&');
						postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
						postData.append('=');
						postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
					}

					// Convert string to byte array, as it should be sent
					byte[] postDataBytes = postData.toString().getBytes("UTF-8");

					// Connect, easy
					HttpURLConnection conn = (HttpURLConnection)url.openConnection();
					// Tell server that this is POST and in which format is the data
					conn.setRequestMethod("POST");
					conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
					conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
					conn.setDoOutput(true);
					conn.getOutputStream().write(postDataBytes);
					
					

					TextView textView = findViewById(R.id.logs);
					
					
					// This gets the output from your server
					Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
System.out.println("ya llego");
log+="askdkdk";
textView.setText("llego");
					for (int c; (c = in.read()) >= 0;) {
						System.out.print((char)c);
						

						log += (char)c;
						textView.setText(log);
					}

					//enviqr al server
					//MainActivity.this.send(MainActivity.this.lat, MainActivity.this.lon, MainActivity.this.id, MainActivity.this.fvel);
					return;
				} catch (Exception exception) {
					return;
				}
			}

			public void onProviderDisabled (String string2) {
				//MainActivity.this.AlertNOGPS();
				TextView textView = findViewById(R.id.logs);
				log += "GPS NJ DISPONIBLE" + string2.toString();
				textView.setText(log);
			}

			public void onProviderEnabled (String string2) {
				log += "encendido gps";
				textView.setText(log);
			}

			public void onStatusChanged (String string2, int n, Bundle bundle) {
			}
		};
		if (checkSelfPermission("android.permission.ACCESS_FINE_LOCATION") != 0) {
			log += "Por favor activa el GPS";
			textView.setText(log);
			return;
		}
		location.requestLocationUpdates("gps", 0L, 0.0f, locationListener);
	}

	public void ResetLogs () {
		log = "SMN947 \n";
		TextView textView = findViewById(R.id.logs);
		textView.setText(log);
	}

	public class PostAsync extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute () {
			// TODO: Implement this method
			super.onPreExecute();
		}

		@Override
		protected String doInBackground (String... strings) {
			try {
				String URL = "https://angelsdream.com.co/tracking/controlador/reporteAndroid.php";
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(URL);

				List<NameValuePair> namevaluepair = new ArrayList<NameValuePair>(2);
				namevaluepair.add(new BasicNameValuePair("AppVersion", "test"));
				namevaluepair.add(new BasicNameValuePair("Lat", "lat"));
				namevaluepair.add(new BasicNameValuePair("Lon", "lon"));

				httppost.setEntity(new UrlEncodedFormEntity(namevaluepair));

				HttpResponse response = httpclient.execute(httppost);
				HttpEntity resEntity = response.getEntity();

				if (resEntity != null) {
					String responseStr = EntityUtils.toString(resEntity).trim();
					System.out.println(responseStr);
				}
				System.out.println(resEntity.toString());
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute (String result) {
			// TODO: Implement this method
			super.onPostExecute(result);
		}
	}

	public void Progress (String vel) {
		vel = vel.toString().toString();
		String[] numbers = vel.split("\\.");
		try {
			String dec = numbers[1];
			LogTest(vel + " - " + dec);
		} catch (Exception ex) {
			System.out.println(ex);
		}

	}
	String test = "";
	public void LogTest (String log) {
		test = log;
		TextView Logs = findViewById(R.id.testLogs);
		Logs.setText(test);
	}
}
