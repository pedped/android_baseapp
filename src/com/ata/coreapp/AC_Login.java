package com.ata.coreapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.ata.classes.LoginResult;
import com.ata.corebase.CoreActivity;
import com.ata.corebase.ObjectParser;
import com.ata.corebase.interfaces.OnResponseListener;
import com.ata.corebase.nc;
import com.ata.corebase.sf;

public class AC_Login extends CoreActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		Button btn = findButton(R.id.acLogin_btn_login);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				requestLogin();
			}
		});

	}

	protected void requestLogin() {

		// when user request login, we have to request the web
		/*
		 * Create a web request requestTitle Sep 28, 2014
		 */
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("deviceid", sf.getDeviceID(this)));
		
		params.add(new BasicNameValuePair("devicetype", "1"));
		
		params.add(new BasicNameValuePair("email", this
				.findEditText(R.id.acLogin_et_Email).getText().toString()));
		
		params.add(new BasicNameValuePair("password", this
				.findEditText(R.id.acLogin_et_Password).getText().toString()));
		
		
		nc.WebRequest(AC_Login.this, config.requestUrl + "user/loginmobile", params,
				new OnResponseListener() {

					@Override
					public void onUnSuccess(String message) {
						new AlertDialog.Builder(AC_Login.this)
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
							// check if we have received success result
							LoginResult loginResult = ObjectParser
									.LoginResult_Parse(result);
							if (loginResult != null) {
								// success login, we have to store information
								AC_Login.this
										.LogDebug("User Logged In Successfully");
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

					}

					@Override
					public void onError() {

					}
				});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ac__login, menu);
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
