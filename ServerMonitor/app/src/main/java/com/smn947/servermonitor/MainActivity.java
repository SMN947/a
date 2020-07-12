package com.smn947.servermonitor;

import android.app.*;
import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import android.graphics.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.view.ViewGroup.*;
import android.widget.*;
import java.util.*;
import com.smn947.servermonitor.extras.*;
import com.smn947.servermonitor.broadcast_receivers.*;
import com.smn947.servermonitor.db.*;
import android.net.*;
import java.io.*;

public class MainActivity extends Activity {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		NotificationEventReceiver.setupAlarm(getApplicationContext());
		
		serverDBHelper dbHelper = new serverDBHelper(this);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		if (db != null) {
			ArrayList<View> serversSaved = new ArrayList<View>();

			Cursor c = db.query(
				serverContract.serverEntry.TABLE_NAME,  // Nombre de la tabla
				null,  // Lista de Columnas a consultar                     
				null,  // Columnas para la cláusula WHERE                               
				null,  // Valores a comparar con las columnas del WHERE                           
				null,  // Agrupar con GROUP BY                                    
				null,  // Condición HAVING para GROUP BY                                     
				null  // Cláusula ORDER BY                                
			);

			while (c.moveToNext()) {
				String NOMBRE = c.getString(c.getColumnIndex(serverContract.serverEntry.NOMBRE));

				HashMap<String, String> hm = new HashMap<String, String>(); 

				hm.put("Tipo", getFromDb(c,"tipo")); 
				hm.put("Destino", getFromDb(c, "destino")); 
				hm.put("Estado", getFromDb(c, "estado"));

				LinearLayout Contenedor = new LinearLayout(this);
				Contenedor.setLayoutParams(layoutHelpers.Layout.mp_wc);
				Contenedor.setOrientation(LinearLayout.VERTICAL);

				TextView Nombre = new TextView(this);
				Nombre.setText(NOMBRE);
				Nombre.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
				Nombre.setTextColor(Color.parseColor("#000000"));
				
				TableLayout Cuerpo = layoutHelpers.GenRows(hm, this);

				Contenedor.addView(Nombre);
				Contenedor.addView(Cuerpo);

				serversSaved.add(Contenedor);

			}
			
			LinearLayout ll = new LinearLayout(this);
			ll.setOrientation(LinearLayout.VERTICAL);
			ll.setLayoutParams(layoutHelpers.Layout.mp_wc);

			for (int i = 0; i < serversSaved.size(); i++) {
				ll.addView(serversSaved.get(i));
			}

			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View v = inflater.inflate(R.layout.main, null);
 
			ScrollView sv = v.findViewById(R.id.fragment_container);
			sv.addView(ll);
			setContentView(v);
			
			TextView logs = findViewById(R.id.logs);
			
			Ping png = new Ping();
			
			String l1 = png.ExecuteP(this, "170.80.97.54");
			String l2 = "";//png.ExecuteP(this, "www.google.com");
			
			logs.setText(l1 + "\n--------------------------\n" + l2);

		}
	}

	public void activityAdd (View v) {
		Intent newAct = new Intent(this, add_server.class);
		newAct.putExtra("desde", "main");
		this.startActivity(newAct);

	}
	public String getFromDb(Cursor c, String key) {
		return c.getString(c.getColumnIndex(key));
	}

	
	
}
