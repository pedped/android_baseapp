package com.ata.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.ata.corebase.sf;
import com.ataalla.amlakgostar.R;
import com.ataalla.amlakgostar.R.id;
import com.ataalla.amlakgostar.R.layout;
import com.ataalla.amlakgostar.R.menu;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class AC_ViewMap extends Activity implements LocationListener {

	private GoogleMap map;
	private TextView txt_Address;
	private Button btn_Navigate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_viweonmap);

		// get latitude and longitude
		final double latitude = getIntent().getExtras().getDouble("latitude");
		final double longitude = getIntent().getExtras().getDouble("longitude");

		// Load the View
		map = ((MapFragment) getFragmentManager().findFragmentById(
				R.id.acViewOnMap_fr_Map)).getMap();
		txt_Address = (TextView) findViewById(R.id.acViewOnMap_txt_Address);

		btn_Navigate = (Button) findViewById(R.id.acViewOnMap_btn_Navigate);
		btn_Navigate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				navigateToMap(latitude, longitude);
			}
		});

		// Load Information
		LatLng location = new LatLng(latitude, longitude);
		Marker marker = map.addMarker(new MarkerOptions().position(location)
				.title("موقعیت"));
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

		// check if we have to show address
		if (getIntent().hasExtra("address")) {
			txt_Address.setVisibility(View.VISIBLE);
			txt_Address.setText(getIntent().getExtras().getString("address"));
		} else {
			txt_Address.setVisibility(View.GONE);
		}

		// get device width and height and change min width of fragmnet
		double pixed = sf.GetDeviceWidthHeight(this).x * 0.95d;
		btn_Navigate.setMinWidth((int) pixed);
		btn_Navigate.setOnClickListener(new OnClickListener() {

			@Override 
			public void onClick(View arg0) {

				// request to navigate
				onRequestNavigateToLocation(latitude, longitude);

			}
		});
		btn_Navigate.setVisibility(View.GONE);

	}

	protected void onRequestNavigateToLocation(double latitude, double longitude) {

		// check if enabled and if not send user to the GSP settings
		// Better solution would be to display a dialog and suggesting to
		// go to the settings
		if (!checkGpsStatus()) {
			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(intent);
		}

		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, this,
				null);
	}

	protected void alertbox(String title, String mymessage) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Your Device's GPS is Disable")
				.setCancelable(false)
				.setTitle("** Gps Status **")
				.setPositiveButton("Gps On",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// finish the current activity
								// AlertBoxAdvance.this.finish();
								Intent myIntent = new Intent(
										Settings.ACTION_LOCATION_SOURCE_SETTINGS);
								startActivity(myIntent);
								dialog.cancel();
							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// cancel the dialog box
								dialog.cancel();
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}

	private Boolean checkGpsStatus() {
		LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
		boolean enabled = service
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		return enabled;
	}

	protected void navigateToMap(double latitude, double longitude) {

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ac__view_map, menu);
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

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}
}
