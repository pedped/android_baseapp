package com.amlakgostar.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;

import com.ata.coreapp.adapter_Spinner;
import com.ataalla.amlakgostar.R;

public class fr_Search extends Fragment {

	private Spinner sp_manzoor;
	private Spinner sp_bathends;
	private Spinner sp_bathstart;
	private Spinner sp_bedsends;
	private Spinner sp_bedsstart;
	private Spinner sp_type;
	private HashMap<String, String> hashTypes;
	private HashMap<String, String> hashFor;

	private boolean fetchInformation;
 
	private View rootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fr_search, container,
				false);

		this.rootView = rootView;
		// load melks
		initSearch(rootView);

		return rootView;
	}

	private void initSearch(View rootView) {

		hashTypes = new HashMap<String, String>();
		hashTypes.put("زمین", "DBC_TYPE_LAND");
		hashTypes.put("آپارتمان", "DBC_TYPE_APARTMENT");
		hashTypes.put("خانه", "DBC_TYPE_HOUSE");
		hashTypes.put("دفتر کار", "DBC_TYPE_COMSPACE");
		hashTypes.put("محل سکونت", "DBC_TYPE_CONDO");
		hashTypes.put("ویلا", "DBC_TYPE_VILLA");

		hashFor = new HashMap<String, String>();
		hashFor.put("اجاره", "DBC_PURPOSE_RENT");
		hashFor.put("فروش", "DBC_PURPOSE_SALE");
		hashFor.put("فروش و اجاره", "DBC_PURPOSE_BOTH");

		// Load Manzoor
		loadManzoor();

		// Load Noe
		loadNoe();

		// Load Otaghe Khab
		loadOtagheKhabStart();

		// loadOtagheKhabEnd
		loadOtagheKhabEnd();

		// Load Otaghe Khab
		loadOtagheBathStart();

		// loadOtagheKhabEnd
		loadOtagheBathEnd();

		// Search Button
		Button btn_Search = (Button) rootView.findViewById(R.id.btn_search);
		btn_Search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// we have to send the request to server
				startSearch();

			}
		});

	}

	private void loadManzoor() {
		sp_manzoor = (Spinner) rootView.findViewById(R.id.sp_manzoor);
		List<String> list = new ArrayList<String>();
		String[] item = hashFor.keySet().toArray(new String[0]);
		for (String string : item) {
			list.add(string);
		}

		adapter_Spinner adp = new adapter_Spinner(getActivity());
		adp.Add(list);
		sp_manzoor.setAdapter(adp);
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
				String name = list.get(pos);
				if (name.equals("DBC_TYPE_APARTMENT")) {

				} else if (name.equals("DBC_TYPE_HOUSE")) {

				} else if (name.equals("DBC_TYPE_LAND")) {

				} else if (name.equals("DBC_TYPE_COMSPACE")) {

				} else if (name.equals("DBC_TYPE_CONDO")) {

				} else if (name.equals("DBC_TYPE_VILLA")) {

				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}

		});

	}

	private void loadOtagheBathEnd() {
		sp_bathends = (Spinner) rootView.findViewById(R.id.sp_bathend);
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
		sp_bathends.setAdapter(adp);
		adp.Refresh();
	}

	private void loadOtagheBathStart() {
		sp_bathstart = (Spinner) rootView.findViewById(R.id.sp_bathstart);
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
		sp_bathstart.setAdapter(adp);
		adp.Refresh();
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

	protected void startSearch() {
		// TODO create search
	}
}