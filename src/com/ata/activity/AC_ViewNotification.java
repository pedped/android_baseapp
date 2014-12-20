package com.ata.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import com.ata.config.config;
import com.ata.corebase.CoreActivity;
import com.ata.corebase.sf;
import com.ataalla.amlakgostar.R;

public class AC_ViewNotification extends CoreActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_viewnotification);

		// get notification infos
		int id = getIntent().getExtras().getInt("id");
		String title = getIntent().getExtras().getString("title");
		String description = getIntent().getExtras().getString("message");
		final String link = getIntent().getExtras().getString("link");
		String linktext = getIntent().getExtras().getString("linktext");
		long date = getIntent().getExtras().getLong("date");

		// we have to show infos to the user
		findTextView(R.id.acViewNotification_txt_Title).setText(title);
		findTextView(R.id.acViewNotification_txt_Description).setText(
				description);

		// check if we need to show button
		if (link.length() > 0) {

			// set button text
			findButton(R.id.acViewNotification_btn_ViewButton)
					.setText(linktext);

			// add event to open website when user click on button
			findButton(R.id.acViewNotification_btn_ViewButton)
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							sf.OpenUrl(getContext(), link);
						}
					});
		}

		// we have to remove notification and set last visit on
		sf.cancelNotification(getContext(),
				config.NOTIFICATIONID_WEBSITENOTIFICATION);
		//sf.SettingManager_WriteString(this, "lastnotificationsee", date + "");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ac__view_notification, menu);
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
