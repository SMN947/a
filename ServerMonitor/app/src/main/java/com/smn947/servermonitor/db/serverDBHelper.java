package com.smn947.servermonitor.db;
import android.database.sqlite.*;
import android.content.*;

public class serverDBHelper extends SQLiteOpenHelper {
		public static final int DATABASE_VERSION = 1;
		public static final String DATABASE_NAME = "servert.db";

		public serverDBHelper (Context context) {
				super(context, DATABASE_NAME, null, DATABASE_VERSION);
			}
		@Override
		public void onCreate (SQLiteDatabase sqLiteDatabase) {
				sqLiteDatabase.execSQL("CREATE TABLE " + serverContract.serverEntry.TABLE_NAME + " ("
									   + serverContract.serverEntry.NOMBRE + " TEXT,"
									   + serverContract.serverEntry.TIPO + " TEXT,"
									   + serverContract.serverEntry.DESTINO + " TEXT,"
									   + serverContract.serverEntry.ESTADO + " TEXT)");

			}

		@Override
		public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
				// No hay operaciones
			}

	}
