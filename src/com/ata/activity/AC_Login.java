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
import com.ata.coreapp.R;
import com.ata.corebase.CoreActivity;
import com.ata.corebase.ObjectParser;
import com.ata.corebase.interfaces.OnResponseListener;
import com.ata.corebase.nc;
import com.ata.corebase.sf;
import com.corebase.classes.LoginResult;

public class AC_Login extends CoreActivity {

	private boolean isLoading = false;

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

		// check if we are not loading
		if (this.isLoading) {
			// we are loading now
			setLoading(true);
			return;
		}
		this.isLoading = true;

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

		nc.WebRequest(AC_Login.this, config.requestUrl + "public/loginmobile",
				params, new OnResponseListener() {

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

								// store login result
								loginResult.Store(AC_Login.this);

								// start home page,
								startActivityWithName(AC_Home.class);
								finish();
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

					}

					@Override
					public void onError() {

					}

					@Override
					public void Anytime() {
						isLoading = false;
						setLoading(false);
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
