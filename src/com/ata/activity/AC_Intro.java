package com.ata.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.ata.corebase.CoreActivity;
import com.ataalla.amlakgostar.R;

public class AC_Intro extends CoreActivity {

	private Button btn_Login;
	private Button btn_Register;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_intro);
		getActionBar().hide();

		// login button
		btn_Login = findButton(R.id.acIntro_btn_Login);
		btn_Login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// start login
				startActivityWithName(AC_Login.class);
			}
		});

		// register button
		btn_Register = findButton(R.id.acIntro_btn_Register);
		btn_Register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// start login
				startActivityWithName(AC_Register.class);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ac__intro, menu);
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
