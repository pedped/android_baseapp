package com.ata.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amlakgostar.classes.AmlakGostarRequestManager;
import com.amlakgostar.classes.PublicRequest;
import com.amlakgostar.fragment.fr_AmlakDarkhasti;
import com.amlakgostar.fragment.fr_AmlakShoma;
import com.amlakgostar.fragment.fr_Search;
import com.ata.config.config;
import com.ata.corebase.AndroidBitmapEncoder;
import com.ata.corebase.CoreActivity;
import com.ata.corebase.interfaces.OnResponseListener;
import com.ata.corebase.nc;
import com.ata.corebase.sf;
import com.ata.events.InternetConnection;
import com.ataalla.amlakgostar.R;
import com.corebase.element.CustomTextView;
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

		// check for new verison
		InternetConnection.checkForNewVersion(getContext());

		// load default values
		PreferenceManager.setDefaultValues(this, R.xml.internal, false);
		PreferenceManager.setDefaultValues(this, R.xml.pref_general, false);

		// check if user is not logged in, request login, before finish
		if (sf.requireUserLogin(this) && !sf.isUserLoggedIn(this)) {

			// request login
			startActivityWithName(AC_Intro.class);

			// finish activity
			finish();
			return;
		}

		// check for new notification and verison
		InternetConnection.checkForNotification(getContext());

		// set action bar logo
		getActionBar().setLogo(R.drawable.home_icon);

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
			// actionBar.addTab(actionBar.newTab()
			// .setText(mSectionsPagerAdapter.getPageTitle(i))
			// .setTabListener(this));

			CustomTextView ctv = new CustomTextView(getContext());
			ctv.setText(mSectionsPagerAdapter.getPageTitle(i));
			ctv.setTextColor(Color.WHITE);
			ctv.setPadding(3, 12, 3, 12);
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this).setCustomView(ctv));
		}

		// set LinearLayout Click Listners
		setLayoutsClickListner();

		// hide notification
		sf.cancelNotification(getContext(), config.NOTIFICATION_NEWREQUEST_ID);

		// set alarm manager
		AmlakGostarRequestManager.SetAlarmManager(getContext());
	}

	private void setLayoutsClickListner() {

		LinearLayout ll_Days = (LinearLayout) findViewById(R.id.acMaster_ll_Days);
		LinearLayout ll_Melks = (LinearLayout) findViewById(R.id.acMaster_ll_Melks);
		LinearLayout ll_SMSCredit = (LinearLayout) findViewById(R.id.acMaster_ll_SMSCredit);

		// Days Click Listner
		ll_Days.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				onDaysClicked();
			}
		});

		ll_Melks.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				onMelksClicked();
			}
		});

		ll_SMSCredit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				onSMSCreditClicked();
			}
		});

	}

	protected void onSMSCreditClicked() {
		new AlertDialog.Builder(getContext())
				.setTitle("اعتبار پیامک")
				.setMessage(
						"اعتبار پیامک شما در حال حاضر "
								+ getSettingValue("smscredit")
								+ " عدد میباشد، از اعتبار برای ارسال ملک به کاربران استفاده می گردد")
				.setNegativeButton("برگشت", null)
				.setPositiveButton("افزایش اعتبار پیامک",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								PublicRequest.Request_SMSPlan(getContext());
							}
						}).create().show();
	}

	protected void onMelksClicked() {
		new AlertDialog.Builder(getContext())
				.setTitle("املاک بنگاه شما")
				.setMessage(
						"تعداد املاک اضافه شده ار طرف شما در سامانه "
								+ getSettingValue("melkcount")
								+ " عدد میباشد، برای افزودن ملک از کلید سمتت راست در بالای برنامه استفاده نمایید")
				.setNegativeButton("برگشت", null)
				.setPositiveButton("افزایش اعتبار پیامک",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								sf.OpenUrl(getContext(), "www.amlakgostar.ir");
							}
						}).create().show();

	}

	protected void onDaysClicked() {
		new AlertDialog.Builder(getContext())
				.setTitle("عضویت املاک گستر")
				.setMessage(
						"تعداد روزهای باقیمانده شما در سامانه املاک گستر "
								+ getSettingValue("remainday") + " روز میباشد.")
				.setNegativeButton("برگشت", null)
				.setPositiveButton("خرید عضویت",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								PublicRequest
										.Request_SubscribePlan(getContext());
							}
						}).create().show();

	}

	private void checkUserCredits() {

		// set infos by internal settings
		findTextView(R.id.acMaster_txt_SMSCredit).setText(
				getSettingValue("smscredit"));
		findTextView(R.id.acMaster_txt_YourMelk).setText(
				getSettingValue("melkcount"));
		findTextView(R.id.acMaster_txt_RemainingDays).setText(
				getSettingValue("remainday"));

		/*
		 * Create a web request Check User Credit Dec 23, 2014
		 */
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		nc.WebRequest(getContext(), config.requestUrl + "bongah/getusercredit",
				params, new OnResponseListener() {

					@Override
					public void onUnSuccess(String message) {
						new AlertDialog.Builder(getContext())
								.setTitle(R.string.ops)
								.setMessage(message)
								.setPositiveButton(R.string.ok,
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface arg0,
													int arg1) {

											}
										}).create().show();

					}

					@Override
					public void onSuccess(String result) {
						try {

							// get values
							JSONObject json = new JSONObject(result);
							String remainingDays = json.getString("remaindays");
							String SMSCredit = json.getString("smscredit");
							String Melks = json.getString("melks");

							// save in database
							setSettingValue("smscredit", SMSCredit);
							setSettingValue("melkcount", Melks);
							setSettingValue("remainday", remainingDays);

							// set view values
							findTextView(R.id.acMaster_txt_SMSCredit).setText(
									SMSCredit);
							findTextView(R.id.acMaster_txt_YourMelk).setText(
									Melks);
							findTextView(R.id.acMaster_txt_RemainingDays)
									.setText(remainingDays);

						} catch (Exception e) {
							e.printStackTrace();
						}

					}

					@Override
					public void onError() {

					}

					@Override
					public void Anytime() {

					}
				});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.ac__master, menu);
		return true;
	}

	public void SetCustomActionBar() {
		this.getActionBar().setDisplayShowCustomEnabled(true);
		this.getActionBar().setDisplayShowTitleEnabled(false);

		LayoutInflater inflator = LayoutInflater.from(this);
		View v = inflator.inflate(R.layout.titleview, null);

		// if you need to customize anything else about the text, do it here.
		// I'm using a custom TextView with a custom font in my layout xml so
		// all I need to do is set title
		((TextView) v.findViewById(R.id.title)).setText(this.getTitle());

		// assign the view to the actionbar
		this.getActionBar().setCustomView(v);
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
		} else if (id == R.id.action_add) {
			startActivityWithName(AC_AddMelk.class);
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
				return "مشتریان";
			case 1:
				return "املاک شما";
			case 2:
				return "جستجو";
			}
			return null;
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		// show last sms credit
		findTextView(R.id.acMaster_txt_SMSCredit).setText(
				getSettingValue("smscredit"));

		// check user credit
		if (sf.hasConnection(getContext())) {

			checkUserCredits();

		}

	}

	public Bitmap screenShot(View view) {

		// image naming and path to include sd card appending name you choose
		// for file
		String mPath = Environment.getExternalStorageDirectory().toString()
				+ "/" + "xPiano/screenshot_app" + System.currentTimeMillis()
				+ ".bmp";

		// create bitmap screen capture
		Bitmap bitmap;
		View v1 = view;
		v1.setDrawingCacheEnabled(true);
		bitmap = Bitmap.createBitmap(v1.getDrawingCache());
		v1.setDrawingCacheEnabled(false);

		try {
			AndroidBitmapEncoder.save(bitmap, mPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// OutputStream fout = null;
		// File imageFile = new File(mPath);

		// try {
		// fout = new FileOutputStream(imageFile);
		// bitmap.compress(Bitmap.CompressFormat., 60, fout);
		// fout.flush();
		// fout.close();
		//
		// } catch (FileNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		return bitmap;
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
