package com.ata.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.ata.config.config;
import com.ata.corebase.CoreActivity;
import com.ata.corebase.interfaces.OnResponseListener;
import com.ata.corebase.nc;
import com.ata.corebase.sf;
import com.ataalla.amlakgostar.R;
import com.ataalla.amlakgostar.R.id;
import com.ataalla.amlakgostar.R.layout;
import com.ataalla.amlakgostar.R.menu;
import com.ataalla.amlakgostar.R.string;
import com.corebase.interfaces.OnUnlimitedListLoadListner;
import com.corebase.unlimited.UnlimitedAdapter.UnlimitListAdapterItem;
import com.corebase.unlimited.UnlimitedAdapter.UnlimitListAdapterItem.UnlimitListAdapterItemType;
import com.corebase.unlimited.UnlimitedList;
import com.corebase.unlimited.UnlimitedList.ItemObject;

public class AC_SuggestMelkPhoneListner extends CoreActivity {

	private Button btn_Send;
	private UnlimitedList ul;
	private ProgressDialog progressDialog;
	private boolean sendingMelkInfo;
	private String melkID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_suggestphone);

		// get Melkid and find information from server
		if (!sf.hasConnection(getContext())) {
			// we do not have Internet connection
			finish();
			return;
		}

		// Load Views
		loadViews();

		// get melk id
		int melkID = getIntent().getExtras().getInt("melkid");
		this.melkID = melkID + "";
		getSuggestion(melkID);
	}

	private void loadViews() {

		btn_Send = findButton(R.id.acSuggestPhone_btn_Send);
		btn_Send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				onRequestSendInformation();
			}
		});

	}

	protected void onRequestSendInformation() {
		try {
			List<String> checkedItems = ul.getCheckedItems("tobesend");

			// set flag
			if (sendingMelkInfo) {
				// we are sending now
				return;
			}

			// set flag
			sendingMelkInfo = true;

			// create Progress dialog
			progressDialog = new ProgressDialog(getContext());
			progressDialog.setCancelable(false);
			progressDialog
					.setMessage("در حال ارسال اطلاعات ملک به مشتریان انتخابی");
			progressDialog.show();

			// create list of ids that we have to send information
			List<String> ids = new ArrayList<String>();
			for (String string : checkedItems) {
				JSONObject item = new JSONObject(string);
				ids.add(item.getString("id"));
			}

			// request to send information
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("ids", sf.combine(
					ids.toArray(new String[ids.size()]), ",")));
			params.add(new BasicNameValuePair("melkid", melkID));

			nc.WebRequest(getContext(), config.requestUrl
					+ "bongah/sendmelkinfomation", params,
					new OnResponseListener() {

						@Override
						public void onUnSuccess(String message) {
							new AlertDialog.Builder(getContext())
									.setTitle(R.string.ops)
									.setMessage(message)
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

						@Override
						public void onSuccess(String result) {
							if (Integer.valueOf(result) == -1) {
								// TODO user have to increse SMS Credit
							} else if (Integer.valueOf(result) == 1) {
								// success, melk send successfully
								Toast.makeText(
										getApplicationContext(),
										"اطلاعات ملک با موفقیت به مشتریان ارسال گردید",
										Toast.LENGTH_LONG).show();

								// finish();
								finish();
							}

						}

						@Override
						public void onError() {

						}

						@Override
						public void Anytime() {
							progressDialog.cancel();
							sendingMelkInfo = false;
						}
					});

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			finish();
		}
	}

	private void getSuggestion(int melkID) {

		ul = new UnlimitedList(getContext(), R.id.acSuggestPhone_ul_Master,
				R.layout.lvi_suggestphone);

		// use Offline data when not exist
		ul.setSwitchOffline(true);

		// store data in database for Offline usage
		ul.setStoreOffline(false);

		// set limit
		ul.setLimit(500);

		// link
		ul.setLink("http://amlak.edspace.org/api/bongah/getphonesuggestion/"
				+ melkID);

		// VIEWS
		ul.getListView().setDividerHeight(0);
		ul.getListView().setDivider(null);

		// set view item
		ul.addItem("header", new UnlimitListAdapterItem("header", "header",
				UnlimitListAdapterItemType.TextView));

		ul.addItem("pricerange", new UnlimitListAdapterItem("pricerange",
				"pricerange", UnlimitListAdapterItemType.TextView));

		// ul.addItem("phone", new UnlimitListAdapterItem("phone", "phone",
		// UnlimitListAdapterItemType.TextView));

		ul.addItem("date", new UnlimitListAdapterItem("date", "date",
				UnlimitListAdapterItemType.TextView));

		ul.addItem("phone", new UnlimitListAdapterItem("phone", "phone",
				UnlimitListAdapterItemType.TextView));

		ul.addItem("tobesend", new UnlimitListAdapterItem("tobesend",
				"tobesend", UnlimitListAdapterItemType.CheckBox));

		// set on load listener
		ul.setOnLoadListner(new OnUnlimitedListLoadListner() {

			@Override
			public void onMoreLoad(List<ItemObject> items) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFirstLoad(List<ItemObject> items) {

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
		getMenuInflater().inflate(R.menu.ac__suggest_melk_phone_listner, menu);
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
