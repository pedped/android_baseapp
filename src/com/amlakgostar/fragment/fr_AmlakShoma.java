package com.amlakgostar.fragment;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import com.ata.activity.AC_AddMelk;
import com.ata.activity.AC_ViewMelk;
import com.ata.config.config;
import com.ataalla.amlakgostar.R;
import com.corebase.interfaces.OnUnlimitedListClickListner;
import com.corebase.unlimited.UnlimitedAdapter.UnlimitListAdapterItem;
import com.corebase.unlimited.UnlimitedAdapter.UnlimitListAdapterItem.UnlimitListAdapterItemType;
import com.corebase.unlimited.UnlimitedList;
import com.corebase.unlimited.UnlimitedList.DatabaseOrder;
import com.corebase.unlimited.UnlimitedListView;

public class fr_AmlakShoma extends Fragment {

	protected String TAG = "fr_AmlakShoma";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fr_amlakshoma, container,
				false);

		// load melks
		loadMelks((UnlimitedListView) rootView
				.findViewById(R.id.frAmlakShoma_lv_Master));

		// click listener on add melk
		((Button) rootView.findViewById(R.id.frAmlakShoma_btn_AddMelk))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						startActivity(new Intent(getActivity(),
								AC_AddMelk.class));
					}
				});

		return rootView;
	}

	private void loadMelks(UnlimitedListView unlimitedListView) {

		UnlimitedList ul = new UnlimitedList((Context) getActivity(),
				unlimitedListView, R.layout.lvi_amlakshoma);

		// use offline data when not exist
		ul.setSwitchOffline(true);

		// store data in database for offline usage
		ul.setStoreOffline(true);
		ul.setDatabaseName(config.DATABASE_AMLAK);
		ul.setDatabaseOrder(DatabaseOrder.Desc);

		// set limit
		ul.setLimit(50);

		// VIEWS
		ul.getListView().setDividerHeight(0);
		ul.getListView().setDivider(null);

		// link
		ul.setLink("http://amlak.edspace.org/api/bongah/getmelks");

		// set view item
		ul.addItem("type", new UnlimitListAdapterItem("type", "type",
				UnlimitListAdapterItemType.TextView));

		ul.addItem("purpose", new UnlimitListAdapterItem("purpose", "purpose",
				UnlimitListAdapterItemType.TextView));

		ul.addItem("area", new UnlimitListAdapterItem("area", "area",
				UnlimitListAdapterItemType.TextView));

		ul.addItem("date", new UnlimitListAdapterItem("date", "date",
				UnlimitListAdapterItemType.TextView));

		ul.addItem("image", new UnlimitListAdapterItem("image", "image",
				UnlimitListAdapterItemType.ImageView));

		// set on item click listner
		ul.setOnItemClickListner(new OnUnlimitedListClickListner() {

			@Override
			public void onClickListner(String JsonItem, AdapterView<?> arg0,
					View arg1, int arg2, long arg3) {

				Log.d(TAG, JsonItem);

				// Start View Melk
				Intent intent = new Intent(getActivity(), AC_ViewMelk.class);
				try {
					intent.putExtra("melkid",
							(new JSONObject(JsonItem)).getString("id"));
					startActivity(intent);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		// refresh the list
		ul.Begin();

	}
}