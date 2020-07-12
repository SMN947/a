package com.smn947.servermonitor.db;
import android.database.sqlite.*;
import android.content.*;

public class pingDBHelper extends SQLiteOpenHelper {
		public static final int DATABASE_VERSION = 1;
		public static final String DATABASE_NAME = "ping.db";

		public pingDBHelper (Context context) {
				super(context, DATABASE_NAME, null, DATABASE_VERSION);
			}
		@Override
		public void onCreate (SQLiteDatabase sqLiteDatabase) {
				sqLiteDatabase.execSQL("CREATE TABLE " + pingContract.pingEntry.TABLE_NAME + " ("
									   + pingContract.pingEntry.NOMBRE + " TEXT,"
									   + pingContract.pingEntry.TIPO + " TEXT,"
									   + pingContract.pingEntry.DESTINO + " TEXT,"
									   + pingContract.pingEntry.ESTADO + " TEXT)");

			}

		@Override
		public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
				// No hay operaciones
			}

	}
