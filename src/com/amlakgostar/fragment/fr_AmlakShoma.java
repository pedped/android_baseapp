package com.amlakgostar.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ata.config.config;
import com.ataalla.amlakgostar.R;
import com.corebase.unlimited.UnlimitedList;
import com.corebase.unlimited.UnlimitedListView;
import com.corebase.unlimited.UnlimitedAdapter.UnlimitListAdapterItem;
import com.corebase.unlimited.UnlimitedAdapter.UnlimitListAdapterItem.UnlimitListAdapterItemType;
import com.corebase.unlimited.UnlimitedList.DatabaseOrder;

public class fr_AmlakShoma extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fr_amlakshoma, container,
				false);

		// load melks
		loadMelks((UnlimitedListView) rootView
				.findViewById(R.id.frAmlakShoma_lv_Master));

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

		// link
		ul.setLink("http://amlak.edspace.org/api/bongah/getmelks");

		// set view item
		ul.addItem("type", new UnlimitListAdapterItem("type", "type",
				UnlimitListAdapterItemType.TextView));

		ul.addItem("purpose", new UnlimitListAdapterItem("purpose", "purpose",
				UnlimitListAdapterItemType.TextView));

		ul.addItem("price", new UnlimitListAdapterItem("price", "price",
				UnlimitListAdapterItemType.TextView));

		ul.addItem("date", new UnlimitListAdapterItem("date", "date",
				UnlimitListAdapterItemType.TextView));

		ul.addItem("image", new UnlimitListAdapterItem("image", "image",
				UnlimitListAdapterItemType.ImageView));

		// refresh the list
		ul.Begin();

	}
}