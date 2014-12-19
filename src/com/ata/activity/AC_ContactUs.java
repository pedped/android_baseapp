package com.ata.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ata.config.appinfo;
import com.ata.coreapp.R;
import com.ata.corebase.CoreActivity;
import com.ata.corebase.sf;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class AC_ContactUs extends CoreActivity {

	private Button btn_sendmessage;
	private GoogleMap map;
	private TextView txt_Address;
	private TextView txt_Phone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_contactus);

		// Load the View
		map = ((MapFragment) getFragmentManager().findFragmentById(
				R.id.acContactUs_fragmentMap)).getMap();
		txt_Address = (TextView) findViewById(R.id.acContactUs_txt_Address);
		txt_Phone = (TextView) findViewById(R.id.acContactUs_txt_Phone);

		btn_sendmessage = (Button) findViewById(R.id.acContactUs_btn_SendMessage);
		btn_sendmessage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				sendMessage();
			}
		});

		// Load Information
		LatLng location = new LatLng(29.62642087743759, 52.51815554051245);
		Marker marker = map.addMarker(new MarkerOptions().position(location)
				.title(getString(R.string.app_name)));
		// Marker kiel = map.addMarker(new MarkerOptions()
		// .position(KIEL)
		// .title("Kiel")
		// .snippet("Kiel is cool")
		// .icon(BitmapDescriptorFactory
		// .fromResource(R.drawable.ic_launcher)));

		// Move the camera instantly to hamburg with a zoom of 15.
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 5));

		// Zoom in, animating the camera.
		map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

		txt_Phone.setText(appinfo.phoneNumber);
		txt_Address.setText(appinfo.address);
	}

	protected void sendMessage() {

		final EditText et = new EditText(this);
		et.setHint(R.string.enter_your_message);
		new AlertDialog.Builder(this)
				.setTitle(R.string.send_message)
				.setPositiveButton(R.string.send,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {

								sf.sendTextMessage(AC_ContactUs.this, et
										.getText().toString() , appinfo.contactPhoneNumber);

							}
						}).setView(et).create().show();
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
