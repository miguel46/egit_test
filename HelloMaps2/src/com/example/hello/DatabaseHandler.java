package com.example.hello;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "myLocations";

	// Location table name
	private static final String TABLE_LOCATIONS = "locations";

	// Location Table Columns names
	private static final String KEY_LATITUDE = "latitude";
	private static final String KEY_LONGITUDE = "longitude";


	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {

		String CREATE_LOCATIONS_TABLE = "CREATE TABLE " + TABLE_LOCATIONS + "("
				+ KEY_LATITUDE + " TEXT PRIMARY KEY, " + KEY_LONGITUDE + " TEXT"+ ");";
		
		  


		db.execSQL(CREATE_LOCATIONS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATIONS);

	}


	// Adding new contact
	public void addLocation(Location location) {


		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues value = new ContentValues();

		value.put(KEY_LATITUDE, Double.toString(location.getLatitude())); // latitude
		
		value.put(KEY_LONGITUDE, Double.toString(location.getLongitude())); // longitude

		// Inserting Row
		//db.insert(TABLE_LOCATIONS, null, value);
		db.close(); // Closing database connection


	}



	// Getting All Contacts
	public List<Location> getAllLocations() {

		 List<Location> locationList = new ArrayList<Location>();
		    
		 // Select All Query
		    String selectQuery = "SELECT  * FROM " + TABLE_LOCATIONS;
		 
		    SQLiteDatabase db = this.getWritableDatabase();
		    Cursor cursor = db.rawQuery(selectQuery, null);
		 
		    // looping through all rows and adding to list
		    if (cursor.moveToFirst()) {
		        do {
		        	Location location=null;
		        	
		        	location.setLatitude(Integer.parseInt(cursor.getString(0)));
		        	location.setLongitude(Integer.parseInt(cursor.getString(1)));
		        	
		          //  contact.setID(Integer.parseInt(cursor.getString(0)));
		          
		            // Adding contact to list
		            locationList.add(location);
		            
		        } while (cursor.moveToNext());
		    
		    }
		 
		    // return contact list
		    return locationList;	
	}
	
	
//	public int updateContact(Contact contact) {
//	    SQLiteDatabase db = this.getWritableDatabase();
//	 
//	    ContentValues values = new ContentValues();
//	    values.put(KEY_NAME, contact.getName());
//	    values.put(KEY_PH_NO, contact.getPhoneNumber());
//	 
//	    // updating row
//	    return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
//	            new String[] { String.valueOf(contact.getID()) });
//	}
	
	
	
	
}