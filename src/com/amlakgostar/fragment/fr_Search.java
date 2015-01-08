package com.amlakgostar.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.ata.activity.AC_SearchResult;
import com.ata.config.config;
import com.ata.coreapp.adapter_Spinner;
import com.ataalla.amlakgostar.R;
import com.corebase.unlimited.UnlimitedDatabase;
import com.corebase.unlimited.UnlimitedDatabase.UnlimitedDatabaseItem;

public class fr_Search extends Fragment {

	private Spinner sp_manzoor;
	private Spinner sp_bedsends;
	private Spinner sp_bedsstart;
	private Spinner sp_type;
	private HashMap<String, String> hashTypes;
	private HashMap<String, String> hashFor;

	private boolean fetchInformation;

	private View rootView;
	private LinearLayout ll_Bedroom;
	private LinearLayout ll_Sale;
	private LinearLayout ll_Rahn;
	private LinearLayout ll_Ejare;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fr_search, container, false);

		this.rootView = rootView;
		// load melks
		initSearch(rootView);

		return rootView;
	}

	private void initSearch(View rootView) {

		hashTypes = new HashMap<String, String>();
		hashTypes.put("خانه", "خانه");
		hashTypes.put("آپارتمان", "آپارتمان");
		hashTypes.put("دفتر کار", "دفتر کار");
		hashTypes.put("ویلا", "ویلا");
		hashTypes.put("زمین", "زمین");
		hashTypes.put("اتاق کار", "اتاق کار");

		hashFor = new HashMap<String, String>();
		hashFor.put("رهن و اجاره", "رهن و اجاره");
		hashFor.put("فروش", "فروش");

		// load linear layouts
		ll_Bedroom = (LinearLayout) rootView
				.findViewById(R.id.frSearch_ll_Bedroom);
		ll_Sale = (LinearLayout) rootView.findViewById(R.id.frSearch_ll_Sale);
		ll_Rahn = (LinearLayout) rootView.findViewById(R.id.frSearch_ll_Rahn);
		ll_Ejare = (LinearLayout) rootView.findViewById(R.id.frSearch_ll_Ejare);

		// Load Manzoor
		loadManzoor();

		// Load Noe
		loadNoe();

		// Load Otaghe Khab
		loadOtagheKhabStart();

		// loadOtagheKhabEnd
		loadOtagheKhabEnd();

		// Search Button
		Button btn_Search = (Button) rootView.findViewById(R.id.btn_search);
		btn_Search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// we have to send the request to server
				try {
					startSearch();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

	}

	private void loadManzoor() {
		sp_manzoor = (Spinner) rootView.findViewById(R.id.sp_manzoor);
		final List<String> list = new ArrayList<String>();
		String[] item = hashFor.keySet().toArray(new String[0]);
		for (String string : item) {
			list.add(string);
		}

		adapter_Spinner adp = new adapter_Spinner(getActivity());
		adp.Add(list);
		sp_manzoor.setAdapter(adp);
		sp_manzoor.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				String manzoor = list.get(pos);
				if (manzoor.equals("فروش")) {
					ll_Sale.setVisibility(View.VISIBLE);
					ll_Rahn.setVisibility(View.GONE);
					ll_Ejare.setVisibility(View.GONE);

				} else {
					ll_Sale.setVisibility(View.GONE);
					ll_Rahn.setVisibility(View.VISIBLE);
					ll_Ejare.setVisibility(View.VISIBLE);
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		adp.Refresh();
	}

	private void loadNoe() {
		sp_type = (Spinner) rootView.findViewById(R.id.sp_noe);
		final List<String> list = new ArrayList<String>();
		String[] item = hashTypes.keySet().toArray(new String[0]);
		for (String string : item) {
			list.add(string);
		}

		adapter_Spinner adp = new adapter_Spinner(getActivity());
		adp.Add(list);
		sp_type.setAdapter(adp);
		adp.Refresh();

		sp_type.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				String type = list.get(pos);
				if (type.equals("خانه") || type.equals("آپارتمان")
						|| type.equals("دفتر کار") || type.equals("اتاق کار")
						|| type.equals("ویلا")) {

					// we have to search for bed
					ll_Bedroom.setVisibility(View.VISIBLE);
				} else {
					ll_Bedroom.setVisibility(View.GONE);
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}

		});
		sp_type.setSelection(2);
	}

	private void loadOtagheKhabEnd() {
		sp_bedsends = (Spinner) rootView.findViewById(R.id.sp_bedend);
		List<String> list = new ArrayList<String>();
		list.add("0");
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		list.add("5");
		list.add("6");
		list.add("7");
		list.add("8");
		list.add("9");
		list.add("10");

		adapter_Spinner adp = new adapter_Spinner(getActivity());
		adp.Add(list);

		sp_bedsends.setAdapter(adp);
		adp.Refresh();
	}

	private void loadOtagheKhabStart() {
		sp_bedsstart = (Spinner) rootView.findViewById(R.id.sp_bedstart);
		List<String> list = new ArrayList<String>();
		list.add("0");
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		list.add("5");
		list.add("6");
		list.add("7");
		list.add("8");
		list.add("9");
		list.add("10");

		adapter_Spinner adp = new adapter_Spinner(getActivity());
		adp.Add(list);
		sp_bedsstart.setAdapter(adp);
		adp.Refresh();
	}

	protected void startSearch() throws JSONException {
		// we have to get all properties and check for the items in database
		UnlimitedDatabase un = new UnlimitedDatabase(getActivity(),
				config.DATABASE_AMLAK);
		un.open();
		List<UnlimitedDatabaseItem> items = un.GetAllItems();

		// define search result
		List<UnlimitedDatabaseItem> searchResult = new ArrayList<UnlimitedDatabase.UnlimitedDatabaseItem>();

		// find search types
		String type = sp_type.getSelectedItem().toString();
		String manzoor = sp_manzoor.getSelectedItem().toString();
		int bedStart = Integer.valueOf(sp_bedsstart.getSelectedItem()
				.toString());
		int bedEnd = Integer.valueOf(sp_bedsends.getSelectedItem().toString());

		// check if we need to search for bedstart
		boolean searchforbed = false;
		if ((type.equals("خانه") || type.equals("آپارتمان")
				|| type.equals("دفتر کار") || type.equals("اتاق کار"))
				&& bedEnd != 0) {

			// we have to search for bed
			searchforbed = true;
		}

		// Sale Price
		EditText et_MaxSalePrice = (EditText) rootView
				.findViewById(R.id.frSearch_et_MaxSalePrice);
		double maxPrice = et_MaxSalePrice.getText().length() > 0 ? Double
				.valueOf(et_MaxSalePrice.getText().toString()) : 0;
		// check if we need to search for Max Sale Price
		boolean searchForMaxSalePrice = false;
		if ((manzoor.equals("فروش")) && maxPrice > 0) {

			// we have to search for max price
			searchForMaxSalePrice = true;
		}

		// Sale Price
		EditText et_MaxEjare = (EditText) rootView
				.findViewById(R.id.frSearch_et_MaxEjarePrice);
		EditText et_MaxRahn = (EditText) rootView
				.findViewById(R.id.frSearch_et_MaxRahnPrice);

		double maxRahn = et_MaxRahn.getText().length() > 0 ? Double
				.valueOf(et_MaxRahn.getText().toString()) : 0;

		double maxEjare = et_MaxEjare.getText().length() > 0 ? Double
				.valueOf(et_MaxEjare.getText().toString()) : 0;

		// check if we need to search for Max Sale Price
		boolean searchForMaxRahnPrice = false;
		if ((manzoor.equals("رهن و اجاره")) && maxRahn > 0) {

			// we have to search for max price
			searchForMaxRahnPrice = true;
		}
		boolean searchForMaxEjarePrice = false;
		if ((manzoor.equals("رهن و اجاره")) && maxEjare > 0) {

			// we have to search for max price
			searchForMaxEjarePrice = true;
		}

		// we haveto search for values
		for (UnlimitedDatabaseItem unlimitedDatabaseItem : items) {

			// convert database value to json
			JSONObject json = new JSONObject(unlimitedDatabaseItem.Data);

			// check for search fields
			if (!json.getString("type").equals(type)) {
				// do not macth
				continue;
			}

			// check for search fields
			if (!json.getString("purpose").equals(manzoor)) {
				// do not macth
				continue;
			}

			// check for bedroom
			if (searchforbed) {
				if (json.getDouble("bedroom") > bedEnd
						|| json.getDouble("bedroom") < bedStart) {
					// we do not find good bedroom range
					continue;
				}
			}

			// check for sale price
			if (searchForMaxSalePrice) {
				if (json.getDouble("saleprice") > maxPrice) {
					// we do not find good price
					continue;
				}
			}

			// check for max rahn price
			if (searchForMaxRahnPrice) {
				if (json.getDouble("rahn") > maxRahn) {
					// we do not find good price
					continue;
				}
			}

			// check for max rahn price
			if (searchForMaxEjarePrice) {
				if (json.getDouble("ejare") > maxEjare) {
					// we do not find good price
					continue;
				}
			}

			// founded new item
			searchResult.add(unlimitedDatabaseItem);
		}

		// close database
		un.close();

		// check if we have search result
		if (searchResult.size() == 0) {
			// no result
			Toast.makeText(getActivity(), "جستجو نتیجه ای در بر نداشت",
					Toast.LENGTH_LONG).show();
			return;
		}

		// add ids to integer list
		List<Integer> searchResultIDs = new ArrayList<Integer>();
		for (UnlimitedDatabaseItem searchre : searchResult) {
			searchResultIDs.add(searchre.ID);
		}

		// start search result activity
		Intent searchResultIntent = new Intent(getActivity(),
				AC_SearchResult.class);

		Integer[] array = searchResultIDs.toArray(new Integer[searchResultIDs
				.size()]);
		int[] itemsaaa = new int[array.length];
		for (int i = 0; i < array.length; i++) {
			itemsaaa[i] = array[i];
		}
		searchResultIntent.putExtra("ids", itemsaaa);

		startActivity(searchResultIntent);
	}
}