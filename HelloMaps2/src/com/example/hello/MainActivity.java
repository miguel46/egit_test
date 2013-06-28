package com.example.hello;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	String userName ="";
	EditText userNameText = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.mainactivity);


		userNameText = (EditText) findViewById(R.id.userNameText);

		Button startButton = (Button) findViewById(R.id.startButton);

		//if(haveInternet(this)){

			final Intent newInt = new Intent(this, NewActivity.class);

			

			startButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

				
				
					userName=userNameText.getText().toString();

					newInt.putExtra("userName", userName);

					startActivity(newInt);
				}
			});
	//	}else{
		//	showNoConnectionDialog(this);

		//}
	}





	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}



	public static boolean haveInternet(Context context) {

		NetworkInfo networkState = (NetworkInfo) ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

		if (networkState == null || !networkState.isConnected()) {
			return false;
		}

		return true;
	}


	public static void showNoConnectionDialog(Context contextArgument) {

		final Context ctx = contextArgument;

		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setCancelable(true);
		builder.setMessage("No Connection");
		builder.setTitle("Connect me");
		builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int number) {
				ctx.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
			}
		});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int number) {
				return;
			}
		});
		builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
			public void onCancel(DialogInterface dialog) {

				return;
			}
		});

		builder.show();
	}

}



