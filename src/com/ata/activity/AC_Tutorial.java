package com.ata.activity;

import java.util.Locale;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.ata.corebase.CoreActivity;
import com.ataalla.amlakgostar.R;

public class AC_Tutorial extends CoreActivity {

	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	private Button btn_Next;
	private Button btn_Back;
	private Button btn_StartApp;
	private int currentstep = 0;
	private Button btn_Start; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac__tutorial);

		getActionBar().hide();

		// load views
		btn_Next = findButton(R.id.acTutorial_btn_Next);
		btn_Back = findButton(R.id.acTutorial_btn_Preview);
		btn_StartApp = findButton(R.id.acTutorial_btn_StartAPP);
		btn_Start = findButton(R.id.acTutorial_btn_Start);

		// step change
		onStepChange();

		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				currentstep = arg0;
				onStepChange();
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

		btn_Start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				mViewPager.setCurrentItem(currentstep + 1, true);
				onStepChange();

			}
		});

		btn_Next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				mViewPager.setCurrentItem(currentstep + 1, true);
				onStepChange();

			}
		});

		btn_Back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				mViewPager.setCurrentItem(currentstep - 1, true);
				onStepChange();

			}
		});

		btn_StartApp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// set setting value
				setSettingValue("visitedtutorial", "1");

				// finish activity
				finish();

			}
		});

	}

	private void onStepChange() {
		switch (currentstep) {
		case 0:
			btn_Back.setVisibility(View.GONE);
			btn_Next.setVisibility(View.GONE);
			btn_StartApp.setVisibility(View.GONE);
			btn_Start.setVisibility(View.VISIBLE);
			break;

		case 1:
			btn_Back.setVisibility(View.GONE);
			btn_Next.setVisibility(View.VISIBLE);
			btn_StartApp.setVisibility(View.GONE);
			btn_Start.setVisibility(View.GONE);
			break;
		case 2:
		case 3:
			btn_Back.setVisibility(View.VISIBLE);
			btn_Next.setVisibility(View.VISIBLE);
			btn_StartApp.setVisibility(View.GONE);
			break;
		case 4:
			btn_Back.setVisibility(View.GONE);
			btn_Next.setVisibility(View.GONE);
			btn_StartApp.setVisibility(View.VISIBLE);
			break;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ac__tutorial, menu);
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

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class
			// below).
			return PlaceholderFragment.newInstance(position + 1);
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 6;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment(
					sectionNumber);
			return fragment;
		}

		private int section;

		public PlaceholderFragment(int section) {
			this.section = section;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			View rootView = null;

			switch (section - 2) {
			case -1:
				rootView = inflater
						.inflate(R.layout.ts_start, container, false);
				break;
			case 0:
				rootView = inflater.inflate(R.layout.ts_one, container, false);
				break;
			case 1:
				rootView = inflater.inflate(R.layout.ts_two, container, false);
				break;
			case 2:
				rootView = inflater
						.inflate(R.layout.ts_three, container, false);
				break;
			case 3:
				rootView = inflater.inflate(R.layout.ts_four, container, false);
				break;
			case 4:
				rootView = inflater.inflate(R.layout.ts_five, container, false);
				break;
			default:
				rootView = inflater.inflate(R.layout.ts_one, container, false);
				break;
			}
			return rootView;
		}
	}

}
