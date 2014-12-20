package com.ata.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.Toast;

import com.ata.config.config;
import com.ataalla.amlakgostar.R;
import com.ata.corebase.CoreActivity;
import com.ata.corebase.ObjectParser;
import com.ata.corebase.interfaces.OnResponseListener;
import com.ata.corebase.nc;
import com.corebase.classes.LoginResult;

public class AC_Register extends CoreActivity {

	/**
	 * loading flag
	 */
	private boolean isLoading = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_register);

		// define register event
		this.findButton(R.id.acRegister_btn_Register).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						onRegisterButtonClicked();
					}
				});

		// define login event
		this.findButton(R.id.acRegister_btn_Login).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {

						// start activity
						startActivity(new Intent(AC_Register.this,
								AC_Login.class));

						// finish current activity
						finish();

					}
				});
	}

	protected void onRegisterButtonClicked() {

		// check for regsiter flag
		if (this.isLoading) {
			// in load
			return;
		}

		// check if user accepting term of service
		if (!((CheckBox) findViewById(R.id.acRegister_chb_Term)).isChecked()) {
			// user do not accept term of service!
			Toast.makeText(this, R.string.you_must_accept_term_of_usage,
					Toast.LENGTH_SHORT).show();
			return;
		}

		// set flag
		this.isLoading = true;
		setLoading(true);

		// get fields
		String fname = this.findEditText(R.id.acRegister_et_FirstName)
				.getText().toString();
		String lname = this.findEditText(R.id.acRegister_et_LastName).getText()
				.toString();
		String email = this.findEditText(R.id.acRegister_et_Email).getText()
				.toString();
		String password = this.findEditText(R.id.acRegister_et_Password)
				.getText().toString();
		boolean gender = ((CheckBox) findViewById(R.id.acRegister_chb_Gender))
				.isChecked();

		// phone is optimal
		String phone = this.findEditText(R.id.acRegister_et_Phone).getText()
				.toString();

		/*
		 * Create a web request request to signup to the site Dec 15, 2014
		 */
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("fname", fname));
		params.add(new BasicNameValuePair("lname", lname));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		params.add(new BasicNameValuePair("gender", gender ? "1" : "0"));
		params.add(new BasicNameValuePair("phone", phone));

		nc.WebRequest(this, config.requestUrl + "public/registermobile",
				params, new OnResponseListener() {

					@Override
					public void onUnSuccess(String message) {
						new AlertDialog.Builder(AC_Register.this)
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

							// if it was successfully, we have to get
							// loginResult
							// Info
							LoginResult loginResult = ObjectParser
									.LoginResult_Parse(result);
							if (loginResult != null) {
								// success login result
								loginResult.Store(AC_Register.this);

								// go to the home page
								startActivityWithName(AC_Master.class);

								// finish current page
								finish();
							} else {
								// there was a problem
								Toast.makeText(
										getApplicationContext(),
										R.string.there_is_a_problem_in_creating_new_account,
										Toast.LENGTH_SHORT).show();

							}
						} catch (Exception e) {
							e.printStackTrace();
						}

						Toast.makeText(
								getApplicationContext(),
								R.string.there_is_a_problem_in_creating_new_account,
								Toast.LENGTH_SHORT).show();

					}

					@Override
					public void onError() {
						Toast.makeText(
								getApplicationContext(),
								R.string.there_is_a_problem_in_creating_new_account,
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void Anytime() {
						setLoading(false);
						isLoading = false;
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ac__register, menu);
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
