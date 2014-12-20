package com.ata.coreapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.ataalla.amlakgostar.R;
import com.corebase.unlimited.UnlimitedAdapter.UnlimitListAdapterItem;
import com.corebase.unlimited.UnlimitedAdapter.UnlimitListAdapterItem.UnlimitListAdapterItemType;
import com.corebase.unlimited.UnlimitedList;

public class ListActivity extends ActionBarActivity {

	public class SampleListItem {
		int count;
		boolean visible;
		String title;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_list);

		UnlimitedList ul = new UnlimitedList(this, R.id.acList_lv_Master,
				R.layout.listitem);
		// link
		ul.setLink("http://192.168.1.4/random.php");

		// set view item
		ul.addItem("count", new UnlimitListAdapterItem("count", "count",
				UnlimitListAdapterItemType.TextView));
		ul.addItem("name", new UnlimitListAdapterItem("name", "name",
				UnlimitListAdapterItemType.TextView));

		// refresh the list
		ul.Begin();


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list, menu);
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
