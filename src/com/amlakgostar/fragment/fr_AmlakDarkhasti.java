package com.amlakgostar.fragment;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.ata.activity.AC_ViewMelkRequest;
import com.ata.config.config;
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

public class fr_AmlakDarkhasti extends Fragment {

	protected String TAG = "fr_AmlakDarkhasti";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fr_amlakdarkhasti, container,
				false);

		// load melks
		loadMelks((UnlimitedListView) rootView
				.findViewById(R.id.frAmlakDarkhasti_lv_Master));

		return rootView;
	}

	private void loadMelks(UnlimitedListView unlimitedListView) {

		UnlimitedList ul = new UnlimitedList((Context) getActivity(),
				unlimitedListView, R.layout.lvi_amlakdarkhasti);

		// use offline data when not exist
		ul.setSwitchOffline(true);

		// store data in database for offline usage
		ul.setStoreOffline(true);
		ul.setDatabaseName(config.DATABASE_AMLAKDARKHASTI);
		ul.setDatabaseOrder(DatabaseOrder.Desc);

		// set limit
		ul.setLimit(50);

		// link
		ul.setLink("http://amlak.edspace.org/api/bongah/getmelkrequest");

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

		ul.addItem("rate", new UnlimitListAdapterItem("melkscanbesentcount",
				"rate", UnlimitListAdapterItemType.TextView));

		// set on item click listner
		ul.setOnItemClickListner(new OnUnlimitedListClickListner() {

			@Override
			public void onClickListner(String JsonItem, AdapterView<?> arg0,
					View arg1, int arg2, long arg3) {

				// start activity for intent
				Intent intent = new Intent(getActivity(),
						AC_ViewMelkRequest.class);
				intent.putExtra("info", JsonItem);
				startActivity(intent);

				// log clicked melk info
				Log.d(TAG, JsonItem);

			}
		});

		// set on load listener
		ul.setOnLoadListner(new OnUnlimitedListLoadListner() {

			@Override
			public void Anytime() {
				// TODO Auto-generated method stub

			}

			@Override
			public void OnError() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onMoreLoad(List<ItemObject> items) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFirstLoad(List<ItemObject> items) {

				// the first item is the last request from server, we have to
				// store that in settings manager, it will be used in feature
				// for retiring new notification
				if (items.size() > 0) {
					try {

						String id = new JSONObject(items.get(0).jsonObject)
								.getString("id");
						// check id with internal settings value
						String lastSeenID = sf.SettingManager_ReadString(
								(Context) getActivity(), "lastrequestseen");
						int lastNotificationSeen = Integer.valueOf(lastSeenID);

						// compare result
						if (lastNotificationSeen < Integer.valueOf(id)) {
							// we have to set new notification seen
							sf.SettingManager_WriteString(getActivity(),
									"lastrequestseen", id);

						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});

		// refresh the list
		ul.Begin();
	}
}
