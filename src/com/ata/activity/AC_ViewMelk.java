package com.ata.activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.ata.config.config;
import com.ata.corebase.CoreActivity;
import com.ataalla.amlakgostar.R;
import com.corebase.unlimited.UnlimitedDatabase;
import com.corebase.unlimited.UnlimitedDatabase.UnlimitedDatabaseItem;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

public class AC_ViewMelk extends CoreActivity {

	private SliderLayout mDemoSlider;
	private ImageView img_ViewOnMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_viewmelk);

		// Hide Logo
		hideLogo(this);

		// get item id and fetch information from database
		String melkID = getIntent().getExtras().getString("melkid");

		// get from database
		UnlimitedDatabase un = new UnlimitedDatabase(this,
				config.DATABASE_AMLAK);
		un.open();

		try {
			// Get Melk
			UnlimitedDatabaseItem melkDB = un.GetByID(melkID);

			// Load Views
			loadViews(melkDB.Data);

			// Load Information
			loadInformation(melkDB.Data);

			// Load Images
			loadImages(melkDB.Data);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(getApplicationContext(),
					"خطا در هنگام خواندن اطلاعات", Toast.LENGTH_LONG).show();
			finish();
			return;
		}
	}

	private void loadImages(String data) throws JSONException {

		mDemoSlider = (SliderLayout) findViewById(R.id.slider);

		mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);

		mDemoSlider
				.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);

		mDemoSlider.setCustomAnimation(new DescriptionAnimation());

		mDemoSlider.setDuration(4000);

		// Load Image From JSON
		JSONObject json = new JSONObject(data);
		JSONArray imageArray = json.getJSONArray("imagearray");
		for (int i = 0; i < imageArray.length(); i++) {
			String link = imageArray.getString(i);
			TextSliderView tSlider = new TextSliderView(this);
			tSlider.image(link);
			mDemoSlider.addSlider(tSlider);
		}

	}

	private void loadInformation(String data) throws JSONException {
		JSONObject json = new JSONObject(data);

		// Title
		findTextView(R.id.acViewMelk_txt_PropertyTitle).setText(
				json.getString("header"));

		// Mahdode
		findTextView(R.id.acViewMelk_txt_PropertySection).setText(
				json.getString("area"));

		// Bedroom
		findTextView(R.id.acViewMelk_txt_PropertyBeds).setText(
				json.getString("bedroom"));

		// Bathroom
		findTextView(R.id.acViewMelk_txt_PropertyBaths).setText(
				json.getString("bath"));

		// Zirbana
		findTextView(R.id.acViewMelk_txt_PropertyZirbana).setText(
				json.getString("zirbana"));

		// Metraj
		findTextView(R.id.acViewMelk_txt_PropertyMetraj).setText(
				json.getString("metraj"));

		// Address
		findTextView(R.id.acViewMelk_txt_PropertyAddress).setText(
				json.getString("privateaddress"));

		// Description
		findTextView(R.id.acViewMelk_et_Description).setText(
				json.getString("description"));
		if (json.getString("description").trim().length() == 0) {
			findTextView(R.id.acViewMelk_et_Description).setVisibility(
					View.GONE);
		}

		// Phone
		findTextView(R.id.acViewMelk_txt_PropertyPhone).setText(
				json.getString("phone"));

		// Mobile
		findTextView(R.id.acViewMelk_txt_PropertyMobile).setText(
				json.getString("mobile"));

		// Sale Price
		findTextView(R.id.acViewMelk_txt_PropertySalePrice).setText(
				json.getString("salepricehuman"));

		// Rahn Price
		findTextView(R.id.acViewMelk_txt_PropertyRahnPrice).setText(
				json.getString("rahnhuman"));

		// Ejare Price
		findTextView(R.id.acViewMelk_txt_PropertyEjare).setText(
				json.getString("ejarehuman"));

		// check for the type
		String melkType = json.getString("type");
		String melkPurpose = json.getString("purpose");

		if (melkType.equals("خانه") || melkType.equals("آپارتمان")
				|| melkType.equals("دفتر کار") || melkType.equals("اتاق کار")) {

			// we have to search for bed
			findLinearLayout(R.id.acViewMelk_ll_Bedroom).setVisibility(
					View.VISIBLE);
		} else {
			findLinearLayout(R.id.acViewMelk_ll_Bedroom).setVisibility(
					View.GONE);
		}

		if (melkType.equals("خانه")) {

			// we have to search for bed
			findLinearLayout(R.id.acViewMelk_ll_Baths).setVisibility(
					View.VISIBLE);
			findLinearLayout(R.id.acViewMelk_ll_PropertyMetraj).setVisibility(
					View.VISIBLE);
			findLinearLayout(R.id.acViewMelk_ll_Zirbana).setVisibility(
					View.VISIBLE);

		} else if (melkType.equals("آپارتمان")) {
			findLinearLayout(R.id.acViewMelk_ll_Baths).setVisibility(
					View.VISIBLE);
			findLinearLayout(R.id.acViewMelk_ll_PropertyMetraj).setVisibility(
					View.GONE);
			findLinearLayout(R.id.acViewMelk_ll_Zirbana).setVisibility(
					View.VISIBLE);
		} else if (melkType.equals("زمین")) {
			findLinearLayout(R.id.acViewMelk_ll_Baths).setVisibility(View.GONE);
			findLinearLayout(R.id.acViewMelk_ll_PropertyMetraj).setVisibility(
					View.VISIBLE);
			findLinearLayout(R.id.acViewMelk_ll_Zirbana).setVisibility(
					View.GONE);
		} else if (melkType.equals("اتاق کار")) {
			findLinearLayout(R.id.acViewMelk_ll_Baths).setVisibility(View.GONE);
			findLinearLayout(R.id.acViewMelk_ll_PropertyMetraj).setVisibility(
					View.GONE);
			findLinearLayout(R.id.acViewMelk_ll_Zirbana).setVisibility(
					View.VISIBLE);
		}

		else if (melkType.equals("دفتر کار")) {
			findLinearLayout(R.id.acViewMelk_ll_Baths).setVisibility(
					View.VISIBLE);
			findLinearLayout(R.id.acViewMelk_ll_PropertyMetraj).setVisibility(
					View.GONE);
			findLinearLayout(R.id.acViewMelk_ll_Zirbana).setVisibility(
					View.VISIBLE);
		}

		else if (melkType.equals("ویلا")) {
			findLinearLayout(R.id.acViewMelk_ll_Baths).setVisibility(
					View.VISIBLE);
			findLinearLayout(R.id.acViewMelk_ll_PropertyMetraj).setVisibility(
					View.VISIBLE);
			findLinearLayout(R.id.acViewMelk_ll_Zirbana).setVisibility(
					View.VISIBLE);
		}

		// check for price type
		if (melkPurpose.equals("فروش")) {
			findLinearLayout(R.id.acViewMelk_ll_Ejare).setVisibility(View.GONE);
			findLinearLayout(R.id.acViewMelk_ll_RahnPrice).setVisibility(
					View.GONE);
			findLinearLayout(R.id.acViewMelk_ll_SalePrice).setVisibility(
					View.VISIBLE);

		} else if (melkPurpose.equals("رهن و اجاره")) {
			findLinearLayout(R.id.acViewMelk_ll_Ejare).setVisibility(
					View.VISIBLE);
			findLinearLayout(R.id.acViewMelk_ll_RahnPrice).setVisibility(
					View.VISIBLE);
			findLinearLayout(R.id.acViewMelk_ll_SalePrice).setVisibility(
					View.GONE);
		} else {
			findLinearLayout(R.id.acViewMelk_ll_Ejare).setVisibility(
					View.VISIBLE);
			findLinearLayout(R.id.acViewMelk_ll_RahnPrice).setVisibility(
					View.VISIBLE);
			findLinearLayout(R.id.acViewMelk_ll_SalePrice).setVisibility(
					View.VISIBLE);
		}

	}

	private void loadViews(final String data) throws JSONException {

		JSONObject json = new JSONObject(data);

		final double latitude = json.getDouble("latitude");
		final double longitude = json.getDouble("longitude");
		final String Address = json.getString("privateaddress");

		img_ViewOnMap = (ImageView) findImageView(R.id.acviewMelk_img_ViewOnMap);
		img_ViewOnMap.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(AC_ViewMelk.this, AC_ViewMap.class);
				intent.putExtra("latitude", latitude);
				intent.putExtra("longitude", longitude);
				intent.putExtra("address", Address);
				startActivity(intent);

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ac__view_melk, menu);
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
