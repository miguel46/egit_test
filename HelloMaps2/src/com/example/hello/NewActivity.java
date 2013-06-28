package com.example.hello;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NewActivity extends Activity {

	final DatabaseHandler db = new DatabaseHandler(this);
	
	TextView contactId =null;


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.newactivity);

		acitivity_main();
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}




	private void acitivity_main() {

		Intent my = getIntent();
		
		//GET THE EXTRA FROM THE PREVIOUS ACTIVITY
		String userName =  my.getStringExtra("userName");

		final TextView wellcomeText = (TextView) findViewById(R.id.textView1);


		contactId = (TextView) findViewById(R.id.contactId);

		
		final EditText longitudeText = (EditText) findViewById(R.id.coordOutput);

		final EditText latituteText = (EditText) findViewById(R.id.latitudeText);

		wellcomeText.setText(userName+ " coordinates!");

		Button button = (Button) findViewById(R.id.button1);

		Button mapViewButton = (Button) findViewById(R.id.MapView);
		
		//MAP ACTIVITYYY
		final Intent mapActivityIntent = new Intent(this, MapActivity.class);

		
		mapViewButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				startActivity(mapActivityIntent);

				
			}
		});
		
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				//String phoneNo = "";
				String sms = "Longitude: "+longitudeText.getText().toString();

				try {

					SmsManager smsManager = SmsManager.getDefault();
					//	smsManager.sendTextMessage(phoneNo, null, sms, null, null);

					//					Toast.makeText(getApplicationContext(), "SMS Sent!",
					//							Toast.LENGTH_LONG).show();
				} catch (Exception e) {
					//					Toast.makeText(getApplicationContext(),
					//							"SMS faild, please try again later!",
					//							Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}

			}
		});
		
		
		Button chooseContactButton = (Button) findViewById(R.id.contact);
		
		
		chooseContactButton.setOnClickListener(new OnClickListener() {
			final int CONTACT_PICKER_RESULT = 100;
			@Override
			public void onClick(View v) {
				Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, Contacts.CONTENT_URI);  
				startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);

				
				
				
			}
		});

	
		

		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


		LocationListener locationListener = new LocationListener() {

			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub

			}


			@Override
			public void onLocationChanged(
					android.location.Location location) {
				if(location!=null){
					double longitude = location.getLongitude();

					double latitude = location.getLatitude();

					longitudeText.setText("LONG: "+longitude);

					latituteText.setText("LAT: "+latitude);

					mapActivityIntent.putExtra("location", location);
					
					db.addLocation(location);

				}

			}
		};

		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
	}
	
	@Override
	public void onActivityResult(int reqCode, int resultCode, Intent data) {
		super.onActivityResult(reqCode, resultCode, data);

		if (resultCode == RESULT_OK) {


			Cursor cursor = null;
			String phone = "";
			try {

				Uri result = data.getData();

				// get the contact id from the Uri
				String id = result.getLastPathSegment();


				cursor = getContentResolver().query(Phone.CONTENT_URI,
						null, Phone.CONTACT_ID + "=?", new String[] { id },
						null);

				int PhoneIdx = cursor.getColumnIndex(Phone.DATA);


				if (cursor.moveToFirst()) {

					phone = cursor.getString(PhoneIdx);

				} else {

				}
			} catch (Exception e) {


			} finally {
				if (cursor != null) {
					cursor.close();
				}
				contactId.setText(phone);

			}

		}
	}
	
}
