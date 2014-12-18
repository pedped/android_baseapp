package com.ata.coreapp;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import com.ata.corebase.CoreActivity;
import com.ata.corebase.sf;

public class AC_Home extends CoreActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_home);

		// load default values
		PreferenceManager.setDefaultValues(this, R.xml.internal, false);
		PreferenceManager.setDefaultValues(this, R.xml.pref_general, false);

		// check if user is not logged in, request login, before finish
		if (sf.requireUserLogin(this) && !sf.isUserLoggedIn(this)) {

			// request login
			startActivityWithName(AC_Login.class);

			// finish activity
			finish();
			return;
		}

		// user is logged in or application do not require login
		//startActivityWithName(AC_Register.class);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ac__home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
