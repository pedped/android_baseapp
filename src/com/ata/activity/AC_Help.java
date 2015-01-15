package com.ata.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.ata.config.config;
import com.ata.corebase.CoreActivity;
import com.ata.corebase.interfaces.OnResponseListener;
import com.ata.corebase.nc;
import com.ataalla.amlakgostar.R;

public class AC_Help extends CoreActivity {

	private TextView webview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_help);

		// get webview
		webview = (TextView) findViewById(R.id.acTutorial_tv_Main);

		// we have to load default text
		webview.setText(Html.fromHtml(getSettingValue("tut")));

		// request new totorial
		requestTutorial();
	}

	private void requestTutorial() {
		/*
		 * Create a web request requestTitle Jan 8, 2015
		 */
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		nc.WebRequest(getContext(), config.requestUrl + "bongah/androidtut",
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

							// check if tutorial changed
							if (!result.equals(getSettingValue("tut"))) {
								// show the new text
								webview.setText(Html.fromHtml(result));
							}

							// we have to store the received string
							setSettingValue("tut", result);

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
		getMenuInflater().inflate(R.menu.ac__tutorial, menu);
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
