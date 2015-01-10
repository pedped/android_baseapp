package com.ata.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.amlakgostar.classes.PublicRequest;
import com.ata.corebase.CoreActivity;
import com.ata.corebase.sf;
import com.ataalla.amlakgostar.R;
import com.crittercism.app.Crittercism;

public class AC_ViewMelkRequest extends CoreActivity {

	private TextView txt_Area;
	private TextView txt_Bedroom;
	private TextView txt_City;
	private TextView txt_MelkCountInfo;
	private TextView txt_NoMelk;
	private TextView txt_Price;
	private TextView txt_Header;
	private Button btn_AddMelk;
	private Button btn_SendMelk;
	private Button btn_ViewPhoneNumber;
	private TextView txt_Date;
	private TextView txt_Phone;
	private LinearLayout ll_SendMelk;
	private LinearLayout ll_NoMelk;
	private RatingBar rb_Rate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_melkrequest);

		// find requested melk info
		final String melkInfo = getIntent().getExtras().getString("info");

		// Text
		txt_Area = findTextView(R.id.acViewMelkRequest_txt_Area);
		txt_Bedroom = findTextView(R.id.acViewMelkRequest_txt_Bedroom);
		txt_City = findTextView(R.id.acViewMelkRequest_txt_City);
		txt_Date = findTextView(R.id.acViewMelkRequest_txt_Date);
		txt_MelkCountInfo = findTextView(R.id.acViewMelkRequest_txt_MelkCountInfo);
		txt_NoMelk = findTextView(R.id.acViewMelkRequest_txt_NoMelk);
		txt_Price = findTextView(R.id.acViewMelkRequest_txt_Price);
		txt_Header = findTextView(R.id.acViewMelkRequest_txt_Header);
		txt_Phone = findTextView(R.id.acViewMelkRequest_txt_PhoneNumber);

		// Buttons
		btn_AddMelk = findButton(R.id.acViewMelkRequest_btn_AddMelk);
		btn_SendMelk = findButton(R.id.acViewMelkRequest_btn_Send);
		btn_ViewPhoneNumber = findButton(R.id.acViewMelkRequest_btn_ViewPhoneNumber);

		// Linear Layout
		ll_SendMelk = (LinearLayout) findViewById(R.id.acViewMelkRequest_ll_SendMelk);
		ll_NoMelk = (LinearLayout) findViewById(R.id.acViewMelkRequest_ll_NoMelk);
		rb_Rate = (RatingBar) findViewById(R.id.acViewMelkRequest_rb_Rate);

		// disable rate bat indicator
		rb_Rate.setIsIndicator(true);
		rb_Rate.setClickable(true);

		// Click Listener For Add Melk
		btn_AddMelk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivityWithName(AC_AddMelk.class);
			}
		});

		// click listner on view melk phone
		btn_ViewPhoneNumber.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// show end info
				showEndInfo();

			}
		});

		// parse melk info
		try {
			final JSONObject json = new JSONObject(melkInfo);

			txt_Area.setText(Html.fromHtml("مناطق درخواستی: "
					+ json.getString("area")));
			txt_City.setText(Html.fromHtml("شهر: " + json.getString("city")));
			txt_Bedroom.setText(Html.fromHtml("اتاق: "
					+ json.getString("bedroom")));
			txt_Price.setText(Html.fromHtml("محدوده قیمت: "
					+ json.getString("pricerange")));
			txt_Date.setText(Html.fromHtml("تاریخ: " + json.getString("date")));
			txt_Header.setText(Html.fromHtml(json.getString("header")));

			if (json.getString("area").trim().length() == 0) {
				txt_Area.setVisibility(View.GONE);
			}

			final String rateInfo = json.getString("rateinfo");
			// set rate
			rb_Rate.setRating(json.getLong("rate"));
			findLinearLayout(R.id.acViewMelkRequest_ll_RateHolder)
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							new AlertDialog.Builder(getContext())
									.setTitle("امتیاز املاک گستر به این مشتری")
									.setMessage(
											Html.fromHtml("<ul>"
													+ rateInfo.replace("</li>",
															".<br/><br/>")
															.trim() + "</ul>"))
									.setPositiveButton(
											R.string.ok,
											new DialogInterface.OnClickListener() {

												@Override
												public void onClick(
														DialogInterface arg0,
														int arg1) {

												}
											}).create().show();
						}
					});

			// check if user have remain days, show phone number
			if (Integer.valueOf(getSettingValue("remainday")) > 0) {

				btn_ViewPhoneNumber.setVisibility(View.GONE);

				txt_Phone.setText(json.getString("phone") + "");
				txt_Phone.setVisibility(View.VISIBLE);

			} else {
				btn_ViewPhoneNumber.setVisibility(View.VISIBLE);
				txt_Phone.setVisibility(View.GONE);
			}

			// check if we have melks to send, show the send layout, other wise
			// hide it
			if (Integer.valueOf(json.getString("melkscanbesentcount")) > 0) {
				// we have some melks to send
				ll_NoMelk.setVisibility(View.GONE);
				ll_SendMelk.setVisibility(View.VISIBLE);

				// show text
				txt_MelkCountInfo
						.setText(Html.fromHtml("شما "
								+ "<b style='color:#a52'>"
								+ json.getString("melkscanbesentcount")
								+ "</b>"
								+ " ملک متناسب با نیاز این شخص موجود دارید، برای ارسال اطلاعات املاک بر روی کلید زیر کلیک نمایید"));
			} else {
				// we have no any melk to send
				ll_NoMelk.setVisibility(View.VISIBLE);
				ll_SendMelk.setVisibility(View.GONE);
			}

			// set on click listners
			btn_SendMelk.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

					// check if user has internet connection
					if (!sf.hasConnection(getContext())) {

						// user do not have any connection
						new AlertDialog.Builder(getContext())
								.setTitle("اینترنت")
								.setMessage(
										"برای ارسال اطلاعات املاک شما نیاز به اینترنت دارید")
								.setPositiveButton("تنظیمات اینترنت",
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(
													DialogInterface arg0,
													int arg1) {
												// TODO start Internet settings
												// page

											}
										}).create().show();
					} else {

						// check if user has SMS credit
						if (Integer.valueOf(getSettingValue("remainday")) <= 0) {
							// user need to increase subscribe plans
							showEndInfo();
						} else {
							// user have remain plans
							Intent intent = new Intent(getContext(),
									AC_SendMelkInfo.class);
							intent.putExtra("info", melkInfo);
							startActivity(intent);
						}

					}
				}
			});

		} catch (JSONException e) {
			e.printStackTrace();
			Crittercism.logHandledException(e);
			finish();
		}

	}

	protected void showEndInfo() {
		// user do not have any connection
		new AlertDialog.Builder(getContext())
				.setTitle("پایان اعتبار")
				.setMessage(
						"مدت زمان اعتبار شما در سامانه املاک گستر به پایان رسیده است. برای خرید عضویت بر روی کلید زیر کلیک نمایید")
				.setPositiveButton("خرید اعتبار",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {

								// we have to request public request
								PublicRequest
										.Request_SubscribePlan(getContext());

							}
						}).create().show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ac__view_melk_request, menu);
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
