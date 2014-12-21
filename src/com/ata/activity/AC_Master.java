package com.ata.activity;

import java.util.Locale;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.amlakgostar.fragment.fr_AmlakDarkhasti;
import com.amlakgostar.fragment.fr_AmlakShoma;
import com.amlakgostar.fragment.fr_Search;
import com.ata.corebase.CoreActivity;
import com.ata.corebase.sf;
import com.ataalla.amlakgostar.R;
import com.corebase.interfaces.Logout;

public class AC_Master extends CoreActivity implements ActionBar.TabListener {

	SectionsPagerAdapter mSectionsPagerAdapter;
	private fr_AmlakDarkhasti fr_AmlakDarkhasti;

	ViewPager mViewPager;
	private fr_AmlakShoma fr_AmlakShoma;
	private fr_Search fr_Search;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_master);

		// check for new version
		// InternetConnection.checkForNewVersion(this);

		// load default values
		PreferenceManager.setDefaultValues(this, R.xml.internal, false);
		PreferenceManager.setDefaultValues(this, R.xml.pref_general, false);

		// check if user is not logged in, request login, before finish
		if (sf.requireUserLogin(this) && !sf.isUserLoggedIn(this)) {

			// request login
			startActivityWithName(AC_Login.class);

			// finish activity
			finish();
			return;
		}

		// check for new notification
		// InternetConnection.checkForNotification(this);

		// user is logged in or application do not require login
		// startActivityWithName(AC_Register.class);

		// create fragment
		fr_AmlakDarkhasti = new fr_AmlakDarkhasti(); 
		fr_AmlakShoma = new fr_AmlakShoma();
		fr_Search = new fr_Search();

		final ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setOffscreenPageLimit(3);

		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.ac__master, menu);
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
		} else if (id == R.id.action_logout) {
			requestLogout(new Logout() {

				@Override
				public void onLogout() {

					// user logged it out, we have to show home page
					startActivityWithName(AC_Home.class);

					// finish current activity
					finish();
				}
			});
		} else if (id == R.id.action_contactus) {
			startActivityWithName(AC_ContactUs.class);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				return fr_AmlakDarkhasti;
			case 1:
				return fr_AmlakShoma;
			case 2:
				return fr_Search;
			}
			return null;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return "املاک درخواستی";
			case 1:
				return "املاک شما";
			case 2:
				return "جستجو";
			}
			return null;
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fr_amlakdarkhasti,
					container, false);
			return rootView;
		}
	}

}
