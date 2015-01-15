package com.ata.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.ata.config.config;
import com.ata.corebase.CoreActivity;
import com.ata.corebase.interfaces.OnResponseListener;
import com.ata.corebase.nc;
import com.ata.corebase.sf;
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

		// check if user has connection, open breadcast link
		if (sf.hasConnection(getContext())) {
			requestNotifyServer();
		}
	}

	private void requestNotifyServer() {

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		try {
			params.add(new BasicNameValuePair("deviceid", sf
					.getDeviceID(getContext())));
		} catch (Exception e) {
			// TODO: handle exception
		}

		nc.WebRequest(getContext(), config.requestUrl + "public/trackintro",
				params, new OnResponseListener() {

					@Override
					public void onUnSuccess(String message) {
						new AlertDialog.Builder(getContext())
								.setTitle(R.string.ops)
								.setMessage(message)
								.setPositiveButton(R.string.ok,
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface arg0,
													int arg1) {

											}
										}).create().show();

					}

					@Override
					public void onSuccess(String result) {
						try {

						} catch (Exception e) {
							e.printStackTrace();
						}

					}

					@Override
					public void onError() {

					}

					@Override
					public void Anytime() {

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
