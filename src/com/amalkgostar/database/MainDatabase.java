package com.amalkgostar.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.corebase.unlimited.UnlimitedList.DatabaseOrder;

public class MainDatabase {

	private Context context;
	private int DATABASE_VER = 2;
	private String DATABASE_NAME = "DATABASE_INTERNAL";
	private SQLiteOpenHelper helper;
	private SQLiteDatabase database;

	public static class UnsyncedMelk {
		public int ID;
		public String Data;
		public int MelkID;
	}

	public static class UnsyncedMelkItem {
		public static final String TABLE_NAME = "unsynced";
		public static final String ID = "id";
		public static final String DATA = "data";
		public static final String MELKID = "melkid";
	}

	public MainDatabase(Context con) {
		this.context = con;
	}

	public class DB_Helper extends SQLiteOpenHelper {

		public DB_Helper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VER);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			try {
				// ORDER ITEM
				db.execSQL("CREATE TABLE unsynced ("
						+ "id INTEGER PRIMARY KEY AUTOINCREMENT, "
						+ "melkid INTEGER NULL , " + "data STRING );");

				// TODO , add index

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS unsynced");
			onCreate(db);
		}

	}

	public MainDatabase open() {
		helper = new DB_Helper(this.context);
		database = helper.getWritableDatabase();
		return this;
	}

	public void close() {
		helper.close();
	}

	public long Unsynced_Add(String data) {
		ContentValues values = new ContentValues();
		values.put(UnsyncedMelkItem.DATA, data);
		return database.insert(UnsyncedMelkItem.TABLE_NAME, null, values);
	}

	public long Unsynced_AddMelkID(String ID, String MelkID) {
		ContentValues values = new ContentValues();
		values.put(UnsyncedMelkItem.MELKID, MelkID);
		return database.update(UnsyncedMelkItem.TABLE_NAME, values, "id = "
				+ ID, null);
	}

	public UnsyncedMelk Unsynced_GetByID(String ID) {
		String whereFunction = "id = '" + ID + "'";
		Cursor cr = database
				.query(UnsyncedMelkItem.TABLE_NAME, new String[] { "id",
						"data", "melkid" }, whereFunction, null, null, null,
						null);

		if (cr.moveToNext()) {
			UnsyncedMelk item = new UnsyncedMelk();
			item.ID = cr.getInt(0);
			item.Data = cr.getString(1);
			item.MelkID = cr.getInt(2);
			return item;
		}
		return null;
	}

	public List<UnsyncedMelk> Unsynced_GetAllItems() {

		// fetch default values
		return this.Unsynced_GetAllItems(0, 1000, DatabaseOrder.Asc);

	}

	public void Unsynced_Remove(int ID) {
		database.delete(UnsyncedMelkItem.TABLE_NAME, "id =" + ID, null);
	}

	public List<UnsyncedMelk> Unsynced_GetAllItems(int startPos, int limit,
			DatabaseOrder databaseOrder) {

		Cursor cr = database.query(UnsyncedMelkItem.TABLE_NAME, new String[] {
				"id", "data", "melkid" }, null, null, null, null, "id "
				+ (databaseOrder == DatabaseOrder.Desc ? "DESC" : "ASC"),
				startPos + "," + limit);

		List<UnsyncedMelk> items = new ArrayList<UnsyncedMelk>();
		while (cr.moveToNext()) {
			UnsyncedMelk item = new UnsyncedMelk();
			item.ID = cr.getInt(0);
			item.Data = cr.getString(1);
			item.MelkID = cr.getInt(2);
			items.add(item);
		}

		return items;
	}
}
