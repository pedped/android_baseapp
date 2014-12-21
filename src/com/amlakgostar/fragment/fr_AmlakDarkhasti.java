package com.amlakgostar.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ata.config.config;
import com.ataalla.amlakgostar.R;
import com.corebase.unlimited.UnlimitedAdapter.UnlimitListAdapterItem;
import com.corebase.unlimited.UnlimitedAdapter.UnlimitListAdapterItem.UnlimitListAdapterItemType;
import com.corebase.unlimited.UnlimitedList;
import com.corebase.unlimited.UnlimitedList.DatabaseOrder;
import com.corebase.unlimited.UnlimitedListView;

public class fr_AmlakDarkhasti extends Fragment {
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

		// set view item
		ul.addItem("type", new UnlimitListAdapterItem("type", "type",
				UnlimitListAdapterItemType.TextView));

		ul.addItem("pricerange", new UnlimitListAdapterItem("pricerange",
				"pricerange", UnlimitListAdapterItemType.TextView));

		ul.addItem("phone", new UnlimitListAdapterItem("phone", "phone",
				UnlimitListAdapterItemType.TextView));

		ul.addItem("rate", new UnlimitListAdapterItem("rate", "rate",
				UnlimitListAdapterItemType.RatingBar));

		// refresh the list
		ul.Begin();
	}
}
