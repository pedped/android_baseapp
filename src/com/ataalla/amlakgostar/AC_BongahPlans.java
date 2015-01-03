package com.ataalla.amlakgostar;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.amlakgostar.classes.PublicRequest;
import com.ata.corebase.CoreActivity;
import com.corebase.interfaces.OnUnlimitedListClickListner;
import com.corebase.interfaces.OnUnlimitedListLoadListner;
import com.corebase.unlimited.UnlimitedAdapter.UnlimitListAdapterItem;
import com.corebase.unlimited.UnlimitedAdapter.UnlimitListAdapterItem.UnlimitListAdapterItemType;
import com.corebase.unlimited.UnlimitedList;
import com.corebase.unlimited.UnlimitedList.ItemObject;

public class AC_BongahPlans extends CoreActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_bongahplans);

		// load list
		loadListView();
	}

	private void loadListView() {

		UnlimitedList ul = new UnlimitedList(getContext(),
				R.id.acBongahPlans_ul_Master, R.layout.lvi_bongahplans);

		// use offline data when not exist
		ul.setSwitchOffline(true);

		// store data in database for Offline usage
		ul.setStoreOffline(false);

		// set limit
		ul.setLimit(50);

		// link
		ul.setLink("http://amlak.edspace.org/api/bongah/getbongahplans");

		// VIEWS
		ul.getListView().setDividerHeight(0);
		ul.getListView().setDivider(null);

		// set view item
		ul.addItem("title", new UnlimitListAdapterItem("title", "title",
				UnlimitListAdapterItemType.TextView));

		ul.addItem("day", new UnlimitListAdapterItem("day", "day",
				UnlimitListAdapterItemType.TextView));

		ul.addItem("price", new UnlimitListAdapterItem("price", "price",
				UnlimitListAdapterItemType.TextView));

		ul.addItem("description", new UnlimitListAdapterItem("description",
				"description", UnlimitListAdapterItemType.TextView));

		// set on item click listner
		ul.setOnItemClickListner(new OnUnlimitedListClickListner() {

			@Override
			public void onClickListner(String JsonItem, AdapterView<?> arg0,
					View arg1, int arg2, long arg3) {

				try {
					JSONObject object = new JSONObject(JsonItem);
					String id = object.getString("id");

					// request purchase
					PublicRequest.RequestPurchase(getContext(), id, true);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

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
				// hide loading
				// setLoading(false);
			}

			@Override
			public void OnError() {

				// unable to load list
				new AlertDialog.Builder(getContext())
						.setTitle("خطا در هنگام خواندن اطلاعات")
						.setMessage(
								"در هنگام دریافت لیست قیمت ها خطایی روی داد")
						.setPositiveButton(R.string.ok,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										finish();
									}
								}).create().show();

			}
		});

		// refresh the list
		ul.Begin();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ac__bongah_plans, menu);
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
