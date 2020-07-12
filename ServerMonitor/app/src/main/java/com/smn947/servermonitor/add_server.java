package com.smn947.servermonitor;
import android.app.*;
import android.content.*;
import android.database.sqlite.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import android.view.View.*;
import com.smn947.servermonitor.db.*;

public class add_server extends Activity {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_server);


	}

	public void goBack () {
		Intent newAct = new Intent(this, MainActivity.class);
		//newAct.putExtra("key", value);  //parameters to be passed
		this.startActivity(newAct);

	}

	public void addServer (View v) {
		String nombre = ((EditText) findViewById(R.id.add_nombre)).getText().toString();
		String tipo = ((EditText) findViewById(R.id.add_tipo)).getText().toString();
		String destino = ((EditText) findViewById(R.id.add_destino)).getText().toString();
		String estado = ((EditText) findViewById(R.id.add_estado)).getText().toString();


		serverDBHelper dbHelper = new serverDBHelper(this);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db != null) {
			// Insert con ContentValues
			ContentValues cv = new ContentValues();
			cv.put("NOMBRE", nombre);
			cv.put("TIPO", tipo);
			cv.put("DESTINO", destino);
			cv.put("ESTADO", estado);
			db.insert("servert", null, cv);
		}
		goBack();
	}
}
