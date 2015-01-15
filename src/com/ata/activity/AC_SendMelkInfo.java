package com.ata.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.ata.config.config;
import com.ata.corebase.CoreActivity;
import com.ata.corebase.interfaces.OnResponseListener;
import com.ata.corebase.nc;
import com.ata.corebase.sf;
import com.ataalla.amlakgostar.R;
import com.corebase.interfaces.OnUnlimitedListClickListner;
import com.corebase.interfaces.OnUnlimitedListLoadListner;
import com.corebase.unlimited.UnlimitedAdapter.UnlimitListAdapterItem;
import com.corebase.unlimited.UnlimitedAdapter.UnlimitListAdapterItem.UnlimitListAdapterItemType;
import com.corebase.unlimited.UnlimitedList;
import com.corebase.unlimited.UnlimitedList.DatabaseOrder;
import com.corebase.unlimited.UnlimitedList.ItemObject;
import com.corebase.unlimited.UnlimitedListView;

public class AC_SendMelkInfo extends CoreActivity {

	protected static final String TAG = "AC_SendMelkInfo";
	private UnlimitedList ul;
	private boolean sendingMelks = false;
	private ProgressDialog progessDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_send_melk_info);

		// get info
		String info = getIntent().getExtras().getString("info");

		// get melks that can be send from server
		try {

			getMelksCanBeSent(info);

			// set click listner for send button
			((Button) findViewById(R.id.acSendMelkInfo_btn_Send))
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {

							try {
								onRequestSendMelks();
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								Toast.makeText(getContext(),
										"خطا در هنگام دریافت املاک انتخابی",
										Toast.LENGTH_SHORT).show();

							}
						}
					});

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void onRequestSendMelks() throws JSONException {

		// user want to send melk infos, first we have to get list if items
		// that need to be sent
		List<String> items = ul.getCheckedItems("tobesend");

		// check for selected size
		if (items.size() == 0) {
			Toast.makeText(getContext(),
					"شما میبایست حداقل یک ملک را جهت ارسال انتخاب نمایید",
					Toast.LENGTH_LONG);
			return;
		}

		// user has selected some items, we have to request server to send
		// that
		List<String> ids = new ArrayList<String>();
		for (String string : items) {
			// add id
			ids.add((new JSONObject(string)).getString("id"));
		}

		// request server
		requestSendMelkInfo(ids);

	}

	private void requestSendMelkInfo(List<String> ids) throws JSONException {

		// show progress bar
		if (sendingMelks) {
			// we are sending now
			return;
		}

		// set flag
		sendingMelks = true;
		progessDialog = new ProgressDialog(getContext());
		progessDialog.setCancelable(false);
		progessDialog.setMessage("در حال ارسال اطلاعات املاک");
		progessDialog.show();

		/*
		 * Create a web request Send Melk Infos Dec 24, 2014
		 */
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		// get phone listner id
		String phonelistnerid = (new JSONObject(getIntent().getExtras()
				.getString("info"))).getString("id");

		// add parameter
		params.add(new BasicNameValuePair("ids", sf.combine(
				ids.toArray(new String[ids.size()]), ",")));
		params.add(new BasicNameValuePair("phonelistnerid", phonelistnerid));

		nc.WebRequest(getContext(), config.requestUrl + "bongah/sendmelkinfo",
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
							// we have send MELK info successfully, we have to
							// receive new SMS credit and save that to info
							sf.SettingManager_WriteString(
									(Activity) getContext(), "smscredit",
									result);

							// show success message
							Toast.makeText(getApplicationContext(),
									"اطلاعات املاک با موفقیت ارسال گردید",
									Toast.LENGTH_LONG).show();

							// finish current activity
							finish();

						} catch (Exception e) {
							e.printStackTrace();
						}

					}

					@Override
					public void onError() {

					}

					@Override
					public void Anytime() {
						sendingMelks = false;
						progessDialog.cancel();
					}
				});
	}

	private void getMelksCanBeSent(String info) throws JSONException {

		// show loading
		progessDialog = new ProgressDialog(getContext());
		progessDialog
				.setMessage("در حال دریافت املاک متناسب شما برای این مشتری");
		progessDialog.show();
		findTextView(R.id.acSendMelkInfo_txt_Info).setVisibility(View.GONE);

		JSONObject infoJson = new JSONObject(info);
		int id = infoJson.getInt("id");

		ul = new UnlimitedList(
				(Context) getContext(),
				(UnlimitedListView) findViewById(R.id.acSendMelkInfo_ul_Master),
				R.layout.lvi_melktobesenditem);

		// use offline data when not exist
		ul.setSwitchOffline(false);

		// store data in database for offline usage
		ul.setStoreOffline(false);
		ul.setDatabaseName(config.DATABASE_AMLAKDARKHASTI);
		ul.setDatabaseOrder(DatabaseOrder.Desc);

		// set limit
		ul.setLimit(5000);

		// link
		ul.setLink(config.requestUrl + "bongah/fetchmelkcanbesent/"
				+ id);

		// VIEWS
		ul.getListView().setDividerHeight(0);
		ul.getListView().setDivider(null);

		// set view item

		ul.addItem("sendcheck", new UnlimitListAdapterItem("tobesend",
				"sendcheck", UnlimitListAdapterItemType.CheckBox));

		// set view item
		ul.addItem("privateaddress", new UnlimitListAdapterItem(
				"privateaddress", "privateaddress",
				UnlimitListAdapterItemType.TextView));

		ul.addItem("price", new UnlimitListAdapterItem("price", "price",
				UnlimitListAdapterItemType.TextView));

		ul.addItem("area", new UnlimitListAdapterItem("area", "area",
				UnlimitListAdapterItemType.TextView));

		ul.addItem("date", new UnlimitListAdapterItem("date", "date",
				UnlimitListAdapterItemType.TextView));

		// set on item click listner
		ul.setOnItemClickListner(new OnUnlimitedListClickListner() {

			@Override
			public void onClickListner(String JsonItem, AdapterView<?> arg0,
					View arg1, int arg2, long arg3) {

				// start activity for intent
				Intent intent = new Intent(getContext(),
						AC_ViewMelkRequest.class);
				intent.putExtra("info", JsonItem);
				startActivity(intent);

				// log clicked melk info
				Log.d(TAG, JsonItem);

			}
		});

		// show loading objects
		ul.setOnLoadListner(new OnUnlimitedListLoadListner() {

			@Override
			public void onMoreLoad(List<ItemObject> items) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFirstLoad(List<ItemObject> items) {

				// cancel progress dialog
				progessDialog.cancel();

				// show text view
				findTextView(R.id.acSendMelkInfo_txt_Info).setVisibility(
						View.VISIBLE);

				// check if we have received any object
				if (items.size() == 0) {

					// there was no melk to send
					Toast.makeText(getApplicationContext(),
							"ملکی جهت ارسال یافت نگردید", Toast.LENGTH_LONG)
							.show();

					finish();
					return;
				}

				// show text size
				findTextView(R.id.acSendMelkInfo_txt_Info)
						.setText(
								"از میان "
										+ items.size()
										+ " ملک پیدا شده زیر، املاک مورد نظر خود را جهت ارسال به مشتری انتخاب نمایید");

			}

			@Override
			public void Anytime() {
				// TODO Auto-generated method stub

			}

			@Override
			public void OnError() {
				// TODO Auto-generated method stub

			}
		});

		// refresh the list
		ul.Begin();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ac__send_melk_info, menu);
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
