package com.ata.activity;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ata.config.config;
import com.ata.corebase.CoreActivity;
import com.ata.corebase.sf;
import com.ataalla.amlakgostar.R;
import com.corebase.interfaces.OnUnlimitedCheckChangeListner;
import com.corebase.interfaces.OnUnlimitedListClickListner;
import com.corebase.unlimited.UnlimitedAdapter.UnlimitListAdapterItem;
import com.corebase.unlimited.UnlimitedAdapter.UnlimitListAdapterItem.UnlimitListAdapterItemType;
import com.corebase.unlimited.UnlimitedDatabase;
import com.corebase.unlimited.UnlimitedDatabase.UnlimitedDatabaseItem;
import com.corebase.unlimited.UnlimitedList;
import com.corebase.unlimited.UnlimitedList.ItemObject;
import com.crittercism.app.Crittercism;

public class AC_SearchResult extends CoreActivity {

	protected static final String TAG = "AC_SearchResult";
	private LinearLayout ll_Send;
	private Button btn_Send;
	private EditText et_Phone;
	private UnlimitedList ul;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_searchresult);

		// Load Views
		ll_Send = (LinearLayout) findViewById(R.id.acSearchResult_ll_SendToPhone);
		btn_Send = findButton(R.id.acSearchResult_btn_Send);
		et_Phone = findEditText(R.id.acSearchResult_et_Phone);

		// we have to get ids of unlimited database value to show for users
		int[] ids = getIntent().getExtras().getIntArray("ids");

		// open database to find values
		UnlimitedDatabase un = new UnlimitedDatabase(this,
				config.DATABASE_AMLAK);
		un.open();
		List<UnlimitedDatabaseItem> items = un.GetAllItems(ids);
		un.close();

		// set page subtitle
		getActionBar().setSubtitle(items.size() + " مورد یافت گردید");

		// create unlimited list
		ul = new UnlimitedList(getContext(), R.id.acSearchResult_ul_Master,
				R.layout.lvi_searchresult);

		// use offline data when not exist
		ul.setSwitchOffline(false);

		// store data in database for offline usage
		ul.setStoreOffline(false);

		// set limit
		ul.setLimit(50);

		// VIEWS
		ul.getListView().setDividerHeight(0);
		ul.getListView().setDivider(null);

		// link
		ul.setOfflineData(items);

		// set view item
		ul.addItem("type", new UnlimitListAdapterItem("type", "type",
				UnlimitListAdapterItemType.TextView));

		ul.addItem("purpose", new UnlimitListAdapterItem("purpose", "purpose",
				UnlimitListAdapterItemType.TextView));

		ul.addItem("area", new UnlimitListAdapterItem("area", "area",
				UnlimitListAdapterItemType.TextView));

		ul.addItem("date", new UnlimitListAdapterItem("date", "date",
				UnlimitListAdapterItemType.TextView));

		ul.addItem("id", new UnlimitListAdapterItem("id", "id",
				UnlimitListAdapterItemType.TextView));

		ul.addItem("image", new UnlimitListAdapterItem("image", "image",
				UnlimitListAdapterItemType.ImageView));

		ul.addItem("tobesend", new UnlimitListAdapterItem("tobesend",
				"tobesend", UnlimitListAdapterItemType.CheckBox));

		// set on item click listner
		ul.setOnItemClickListner(new OnUnlimitedListClickListner() {

			@Override
			public void onClickListner(String JsonItem, AdapterView<?> arg0,
					View arg1, int arg2, long arg3) {

				Log.d(TAG, JsonItem);

				// try to open activity
				try {
					JSONObject item;
					item = new JSONObject(JsonItem);
					String id = (String) item.get("id");
					Intent intent = new Intent(getContext(), AC_ViewMelk.class);
					intent.putExtra("melkid", id);
					startActivity(intent);
				} catch (JSONException e) {
					e.printStackTrace();
					Crittercism.logHandledException(e);
				}

			}
		});

		ul.setOnCheckChangeListner("tobesend",
				new OnUnlimitedCheckChangeListner() {

					@Override
					public void onCheckChange(int totalChecked,
							List<ItemObject> checkedItems) {

						// check for new size
						ll_Send.setVisibility(totalChecked > 0 ? View.VISIBLE
								: View.GONE);

					}
				});

		// refresh the list
		ul.Begin();

		// set click listenr
		btn_Send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				try {
					onRequestSendMelInfo();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Crittercism.logHandledException(e);
				}
			}
		});
	}

	protected void onRequestSendMelInfo() throws JSONException {
		// check if the user enetered phone number

		if (et_Phone.getText().length() == 0) {
			Toast.makeText(this, "شماره موبایل مشتری را وارد نمایید",
					Toast.LENGTH_LONG).show();
			return;
		}

		if (et_Phone.getText().length() != 11) {
			Toast.makeText(this, "شماره موبایل وارد شده نا معتبر است",
					Toast.LENGTH_LONG).show();
			return;
		}

		// user Entered phone, get selected items
		List<String> checkedItems = ul.getCheckedItems("tobesend");

		// send checked items information
		String message = getSMS(checkedItems);

		// request to send phone
		sf.sendMesageIntent(this, et_Phone.getText().toString(), message);
	}

	private String getSMS(List<String> checkedItems) throws JSONException {

		StringBuilder melkString = new StringBuilder();
		melkString
				.append("مشتری گرامی، املاک زیر مطابق با نیاز شما در مشاور املاک "
						+ getSettingValue("bongahname") + " موجود می باشد");

		melkString.append("\r\n\r\n");

		// get each melk info
		for (String string : checkedItems) {
			melkString.append(getMelkInfo(string) + "\r\n\r\n");
		}

		melkString.append("جهت کسب اطلاعات بیشتر، با شماره "
				+ getSettingValue("mobile") + " تماس حاصل فرمایید.");

		return melkString.toString();
	}

	private String getMelkInfo(String string) throws JSONException {

		String result = "";
		JSONObject json = new JSONObject(string);

		String type = json.getString("type");

		// add header
		result += type;

		// check for bedroom
		if (type.equals("خانه") || type.equals("آپارتمان")
				|| type.equals("دفتر کار") || type.equals("اتاق کار")
				|| type.equals("ویلا")) {

			// we have to search for bed
			result += " " + json.getString("bedroom") + " خوابه" + "،";
		}

		// add purpose
		result += " ";
		result += "واقع در  " + json.getString("area");

		// add metraj
		if (type.equals("خانه") || type.equals("ویلا")) {

			// we have to search for bed
			result += " به متراژ" + json.getString("metraj") + " متر مربع"
					+ " ";
		}

		// add zirbana
		if (type.equals("خانه") || type.equals("آپارتمان")
				|| type.equals("دفتر کار") || type.equals("اتاق کار")
				|| type.equals("ویلا")) {

			// we have to search for bed
			result += " و زیربنای " + json.getString("zirbana") + " متر مربع"
					+ " ";
		}

		// check if property is for sale
		result += "جهت " + json.getString("purpose");
		if (json.getString("purpose").equals("فروش")) {
			result += " به قیمت " + json.getString("salepricehuman");
		} else if (json.getString("purpose").equals("رهن و اجاره")) {
			result += "،رهن " + json.getString("rahnhuman");
			result += "،اجاره " + json.getString("ejarehuman");
		} else {
			result += " به قیمت " + json.getString("salepricehuman");
			result += " و یا ";
			result += " رهن " + json.getString("rahnhuman");
			result += " و اجاره " + json.getString("ejarehuman");
		}
		result += " کد ملک: " + json.getString("id");
		return result;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ac__search_result, menu);
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
