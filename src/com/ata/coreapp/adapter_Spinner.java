package com.ata.coreapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ataalla.amlakgostar.R;
import com.corebase.imageloader.ImageLoader;



public class adapter_Spinner extends BaseAdapter {
	private Context context;
	private Activity ac;
	private ImageLoader imageLoader;

	public adapter_Spinner(Context context) {

		this.context = context;
		this.ac = (Activity) context;
		imageLoader = new ImageLoader(context);
	}

	private List<String> items = new ArrayList<String>();

	public int getCount() {
		return items.size();
	}

	public String getItem(int arg0) {
		return items.get(arg0);
	}

	public long getItemId(int arg0) {
		return 0;
	}
 
	public View getView(final int position, View arg1, ViewGroup arg2) {

		LayoutInflater inflater = ac.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.lvi_spinner, null);

		final String property = items.get(position);

		// get the title
		TextView txt_Title = (TextView) rowView.findViewById(R.id.sp_title);
		txt_Title.setText(property);

		return rowView;
	}

	public void Add(String item) {
		items.add(item);
	}

	public void Add(List<String> items) {
		for (String item : items) {
			Add(item);
		}
	}

	public void Refresh() {
		this.notifyDataSetChanged();
	}

	public void clear() {
		items.clear();

	}
}
