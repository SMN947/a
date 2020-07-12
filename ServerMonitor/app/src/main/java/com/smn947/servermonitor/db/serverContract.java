package com.smn947.servermonitor.db;
import android.provider.*;

public class serverContract {

	public static abstract class serverEntry implements BaseColumns {
		public static final String TABLE_NAME ="servert";

		public static final String NOMBRE= "nombre";
		public static final String TIPO = "tipo";
		public static final String DESTINO = "destino";
		public static final String ESTADO = "estado";
	}
}
