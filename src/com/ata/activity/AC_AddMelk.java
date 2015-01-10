package com.ata.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.amalkgostar.database.MainDatabase;
import com.ata.config.config;
import com.ata.coreapp.adapter_Spinner;
import com.ata.corebase.CoreActivity;
import com.ata.corebase.nc;
import com.ata.corebase.sf;
import com.ataalla.amlakgostar.R;
import com.corebase.interfaces.onUploadListner;
import com.crittercism.app.Crittercism;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.android.gms.maps.GoogleMap;

public class AC_AddMelk extends CoreActivity implements LocationListener {

	public static class CityState {
		public String StateID;
		public String CityID;
		public String CityName;
		public String StateName;

		public CityState(String cityID, String stateID, String cityName,
				String stateName) {
			super();
			StateID = stateID;
			CityID = cityID;
			CityName = cityName;
			StateName = stateName;
		}

	}

	private void loadLocationReciver() {
		LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
		lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 0.1f,
				this);
	}

	private static final int TAKE_PICTURE = 0;

	private Spinner sp_manzoor;
	private Spinner sp_type;
	private HashMap<String, String> hashTypes;
	private HashMap<String, String> hashFor;
	private EditText et_Zirbana;
	private EditText et_Metraj;
	private EditText et_Bedroom;
	private EditText et_Bath;
	private EditText et_Description;
	private EditText et_SalePrice;
	private EditText et_Rahn;
	private EditText et_Ejare;
	private EditText et_Phone;
	private EditText et_Mobile;
	private EditText et_Blvd;
	private EditText et_Address;
	private Button btn_Send;
	private Button btn_AddImage;
	private ListView listViewImage;
	private GoogleMap map;
	private Spinner sp_State;
	private Spinner sp_City;
	private List<CityState> allCityList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_addmelk);

		// leave log
		Crittercism.leaveBreadcrumb("مشاهده صفحه افزودن ملک");

		// add type and noe
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

		allCityList = new ArrayList<CityState>();
		addAllCityList();

		loadNoe();
		loadManzoor();
		loadState();

		// load Views
		loadViews();

		// Load Map Fragment
		// loadMap();

		// load location receiver
		loadLocationReciver();

	}

	private void addAllCityList() {

		allCityList.add(new CityState("1", "1", "تبريز", "آذربايجان شرقي"));
		allCityList.add(new CityState("2", "1", "كندوان", "آذربايجان شرقي"));
		allCityList.add(new CityState("3", "1", "بندر شرفخانه",
				"آذربايجان شرقي"));
		allCityList.add(new CityState("4", "1", "مراغه", "آذربايجان شرقي"));
		allCityList.add(new CityState("5", "1", "ميانه", "آذربايجان شرقي"));
		allCityList.add(new CityState("6", "1", "شبستر", "آذربايجان شرقي"));
		allCityList.add(new CityState("7", "1", "مرند", "آذربايجان شرقي"));
		allCityList.add(new CityState("8", "1", "جلفا", "آذربايجان شرقي"));
		allCityList.add(new CityState("9", "1", "سراب", "آذربايجان شرقي"));
		allCityList.add(new CityState("10", "1", "هاديشهر", "آذربايجان شرقي"));
		allCityList.add(new CityState("11", "1", "بناب", "آذربايجان شرقي"));
		allCityList.add(new CityState("12", "1", "كليبر", "آذربايجان شرقي"));
		allCityList.add(new CityState("13", "1", "تسوج", "آذربايجان شرقي"));
		allCityList.add(new CityState("14", "1", "اهر", "آذربايجان شرقي"));
		allCityList.add(new CityState("15", "1", "هريس", "آذربايجان شرقي"));
		allCityList.add(new CityState("16", "1", "عجبشير", "آذربايجان شرقي"));
		allCityList.add(new CityState("17", "1", "هشترود", "آذربايجان شرقي"));
		allCityList.add(new CityState("18", "1", "ملكان", "آذربايجان شرقي"));
		allCityList
				.add(new CityState("19", "1", "بستان آباد", "آذربايجان شرقي"));
		allCityList.add(new CityState("20", "1", "ورزقان", "آذربايجان شرقي"));
		allCityList.add(new CityState("21", "1", "اسكو", "آذربايجان شرقي"));
		allCityList.add(new CityState("22", "1", "آذر شهر", "آذربايجان شرقي"));
		allCityList.add(new CityState("23", "1", "قره آغاج", "آذربايجان شرقي"));
		allCityList.add(new CityState("24", "1", "ممقان", "آذربايجان شرقي"));
		allCityList.add(new CityState("25", "1", "صوفیان", "آذربايجان شرقي"));
		allCityList.add(new CityState("26", "1", "ایلخچی", "آذربايجان شرقي"));
		allCityList.add(new CityState("27", "1", "خسروشهر", "آذربايجان شرقي"));
		allCityList.add(new CityState("28", "1", "باسمنج", "آذربايجان شرقي"));
		allCityList.add(new CityState("29", "1", "سهند", "آذربايجان شرقي"));
		allCityList.add(new CityState("30", "2", "اروميه", "آذربايجان غربي"));
		allCityList.add(new CityState("31", "2", "نقده", "آذربايجان غربي"));
		allCityList.add(new CityState("32", "2", "ماكو", "آذربايجان غربي"));
		allCityList.add(new CityState("33", "2", "تكاب", "آذربايجان غربي"));
		allCityList.add(new CityState("34", "2", "خوي", "آذربايجان غربي"));
		allCityList.add(new CityState("35", "2", "مهاباد", "آذربايجان غربي"));
		allCityList.add(new CityState("36", "2", "سر دشت", "آذربايجان غربي"));
		allCityList.add(new CityState("37", "2", "چالدران", "آذربايجان غربي"));
		allCityList.add(new CityState("38", "2", "بوكان", "آذربايجان غربي"));
		allCityList.add(new CityState("39", "2", "مياندوآب", "آذربايجان غربي"));
		allCityList.add(new CityState("40", "2", "سلماس", "آذربايجان غربي"));
		allCityList.add(new CityState("41", "2", "شاهين دژ", "آذربايجان غربي"));
		allCityList.add(new CityState("42", "2", "پيرانشهر", "آذربايجان غربي"));
		allCityList.add(new CityState("43", "2", "سيه چشمه", "آذربايجان غربي"));
		allCityList.add(new CityState("44", "2", "اشنويه", "آذربايجان غربي"));
		allCityList.add(new CityState("45", "2", "چایپاره", "آذربايجان غربي"));
		allCityList.add(new CityState("46", "2", "پلدشت", "آذربايجان غربي"));
		allCityList.add(new CityState("47", "2", "شوط", "آذربايجان غربي"));
		allCityList.add(new CityState("48", "3", "اردبيل", "اردبيل"));
		allCityList.add(new CityState("49", "3", "سرعين", "اردبيل"));
		allCityList.add(new CityState("50", "3", "بيله سوار", "اردبيل"));
		allCityList.add(new CityState("51", "3", "پارس آباد", "اردبيل"));
		allCityList.add(new CityState("52", "3", "خلخال", "اردبيل"));
		allCityList.add(new CityState("53", "3", "مشگين شهر", "اردبيل"));
		allCityList.add(new CityState("54", "3", "مغان", "اردبيل"));
		allCityList.add(new CityState("55", "3", "نمين", "اردبيل"));
		allCityList.add(new CityState("56", "3", "نير", "اردبيل"));
		allCityList.add(new CityState("57", "3", "كوثر", "اردبيل"));
		allCityList.add(new CityState("58", "3", "كيوي", "اردبيل"));
		allCityList.add(new CityState("59", "3", "گرمي", "اردبيل"));
		allCityList.add(new CityState("60", "4", "اصفهان", "اصفهان"));
		allCityList.add(new CityState("61", "4", "فريدن", "اصفهان"));
		allCityList.add(new CityState("62", "4", "فريدون شهر", "اصفهان"));
		allCityList.add(new CityState("63", "4", "فلاورجان", "اصفهان"));
		allCityList.add(new CityState("64", "4", "گلپايگان", "اصفهان"));
		allCityList.add(new CityState("65", "4", "دهاقان", "اصفهان"));
		allCityList.add(new CityState("66", "4", "نطنز", "اصفهان"));
		allCityList.add(new CityState("67", "4", "نايين", "اصفهان"));
		allCityList.add(new CityState("68", "4", "تيران", "اصفهان"));
		allCityList.add(new CityState("69", "4", "كاشان", "اصفهان"));
		allCityList.add(new CityState("70", "4", "فولاد شهر", "اصفهان"));
		allCityList.add(new CityState("71", "4", "اردستان", "اصفهان"));
		allCityList.add(new CityState("72", "4", "سميرم", "اصفهان"));
		allCityList.add(new CityState("73", "4", "درچه", "اصفهان"));
		allCityList.add(new CityState("74", "4", "کوهپایه", "اصفهان"));
		allCityList.add(new CityState("75", "4", "مباركه", "اصفهان"));
		allCityList.add(new CityState("76", "4", "شهرضا", "اصفهان"));
		allCityList.add(new CityState("77", "4", "خميني شهر", "اصفهان"));
		allCityList.add(new CityState("78", "4", "شاهين شهر", "اصفهان"));
		allCityList.add(new CityState("79", "4", "نجف آباد", "اصفهان"));
		allCityList.add(new CityState("80", "4", "دولت آباد", "اصفهان"));
		allCityList.add(new CityState("81", "4", "زرين شهر", "اصفهان"));
		allCityList.add(new CityState("82", "4", "آران و بيدگل", "اصفهان"));
		allCityList.add(new CityState("83", "4", "باغ بهادران", "اصفهان"));
		allCityList.add(new CityState("84", "4", "خوانسار", "اصفهان"));
		allCityList.add(new CityState("85", "4", "مهردشت", "اصفهان"));
		allCityList.add(new CityState("86", "4", "علويجه", "اصفهان"));
		allCityList.add(new CityState("87", "4", "عسگران", "اصفهان"));
		allCityList.add(new CityState("88", "4", "نهضت آباد", "اصفهان"));
		allCityList.add(new CityState("89", "4", "حاجي آباد", "اصفهان"));
		allCityList.add(new CityState("90", "4", "تودشک", "اصفهان"));
		allCityList.add(new CityState("91", "4", "ورزنه", "اصفهان"));
		allCityList.add(new CityState("92", "5", "ايلام", "ايلام"));
		allCityList.add(new CityState("93", "5", "مهران", "ايلام"));
		allCityList.add(new CityState("94", "5", "دهلران", "ايلام"));
		allCityList.add(new CityState("95", "5", "آبدانان", "ايلام"));
		allCityList.add(new CityState("96", "5", "شيروان چرداول", "ايلام"));
		allCityList.add(new CityState("97", "5", "دره شهر", "ايلام"));
		allCityList.add(new CityState("98", "5", "ايوان", "ايلام"));
		allCityList.add(new CityState("99", "5", "سرابله", "ايلام"));
		allCityList.add(new CityState("100", "6", "بوشهر", "بوشهر"));
		allCityList.add(new CityState("101", "6", "تنگستان", "بوشهر"));
		allCityList.add(new CityState("102", "6", "دشتستان", "بوشهر"));
		allCityList.add(new CityState("103", "6", "دير", "بوشهر"));
		allCityList.add(new CityState("104", "6", "ديلم", "بوشهر"));
		allCityList.add(new CityState("105", "6", "كنگان", "بوشهر"));
		allCityList.add(new CityState("106", "6", "گناوه", "بوشهر"));
		allCityList.add(new CityState("107", "6", "ريشهر", "بوشهر"));
		allCityList.add(new CityState("108", "6", "دشتي", "بوشهر"));
		allCityList.add(new CityState("109", "6", "خورموج", "بوشهر"));
		allCityList.add(new CityState("110", "6", "اهرم", "بوشهر"));
		allCityList.add(new CityState("111", "6", "برازجان", "بوشهر"));
		allCityList.add(new CityState("112", "6", "خارك", "بوشهر"));
		allCityList.add(new CityState("113", "6", "جم", "بوشهر"));
		allCityList.add(new CityState("114", "6", "کاکی", "بوشهر"));
		allCityList.add(new CityState("115", "6", "عسلویه", "بوشهر"));
		allCityList.add(new CityState("116", "6", "بردخون", "بوشهر"));
		allCityList.add(new CityState("117", "7", "تهران", "تهران"));
		allCityList.add(new CityState("118", "7", "ورامين", "تهران"));
		allCityList.add(new CityState("119", "7", "فيروزكوه", "تهران"));
		allCityList.add(new CityState("120", "7", "ري", "تهران"));
		allCityList.add(new CityState("121", "7", "دماوند", "تهران"));
		allCityList.add(new CityState("122", "7", "اسلامشهر", "تهران"));
		allCityList.add(new CityState("123", "7", "رودهن", "تهران"));
		allCityList.add(new CityState("124", "7", "لواسان", "تهران"));
		allCityList.add(new CityState("125", "7", "بومهن", "تهران"));
		allCityList.add(new CityState("126", "7", "تجريش", "تهران"));
		allCityList.add(new CityState("127", "7", "فشم", "تهران"));
		allCityList.add(new CityState("128", "7", "كهريزك", "تهران"));
		allCityList.add(new CityState("129", "7", "پاكدشت", "تهران"));
		allCityList.add(new CityState("130", "7", "چهاردانگه", "تهران"));
		allCityList.add(new CityState("131", "7", "شريف آباد", "تهران"));
		allCityList.add(new CityState("132", "7", "قرچك", "تهران"));
		allCityList.add(new CityState("133", "7", "باقرشهر", "تهران"));
		allCityList.add(new CityState("134", "7", "شهريار", "تهران"));
		allCityList.add(new CityState("135", "7", "رباط كريم", "تهران"));
		allCityList.add(new CityState("136", "7", "قدس", "تهران"));
		allCityList.add(new CityState("137", "7", "ملارد", "تهران"));
		allCityList
				.add(new CityState("138", "8", "شهركرد", "چهارمحال بختياري"));
		allCityList
				.add(new CityState("139", "8", "فارسان", "چهارمحال بختياري"));
		allCityList.add(new CityState("140", "8", "بروجن", "چهارمحال بختياري"));
		allCityList.add(new CityState("141", "8", "چلگرد", "چهارمحال بختياري"));
		allCityList.add(new CityState("142", "8", "اردل", "چهارمحال بختياري"));
		allCityList
				.add(new CityState("143", "8", "لردگان", "چهارمحال بختياري"));
		allCityList.add(new CityState("144", "8", "سامان", "چهارمحال بختياري"));
		allCityList.add(new CityState("145", "9", "قائن", "خراسان جنوبي"));
		allCityList.add(new CityState("146", "9", "فردوس", "خراسان جنوبي"));
		allCityList.add(new CityState("147", "9", "بيرجند", "خراسان جنوبي"));
		allCityList.add(new CityState("148", "9", "نهبندان", "خراسان جنوبي"));
		allCityList.add(new CityState("149", "9", "سربيشه", "خراسان جنوبي"));
		allCityList.add(new CityState("150", "9", "طبس مسینا", "خراسان جنوبي"));
		allCityList.add(new CityState("151", "9", "قهستان", "خراسان جنوبي"));
		allCityList.add(new CityState("152", "9", "درمیان", "خراسان جنوبي"));
		allCityList.add(new CityState("153", "10", "مشهد", "خراسان رضوي"));
		allCityList.add(new CityState("154", "10", "نيشابور", "خراسان رضوي"));
		allCityList.add(new CityState("155", "10", "سبزوار", "خراسان رضوي"));
		allCityList.add(new CityState("156", "10", "كاشمر", "خراسان رضوي"));
		allCityList.add(new CityState("157", "10", "گناباد", "خراسان رضوي"));
		allCityList.add(new CityState("158", "10", "طبس", "خراسان رضوي"));
		allCityList
				.add(new CityState("159", "10", "تربت حيدريه", "خراسان رضوي"));
		allCityList.add(new CityState("160", "10", "خواف", "خراسان رضوي"));
		allCityList.add(new CityState("161", "10", "تربت جام", "خراسان رضوي"));
		allCityList.add(new CityState("162", "10", "تايباد", "خراسان رضوي"));
		allCityList.add(new CityState("163", "10", "قوچان", "خراسان رضوي"));
		allCityList.add(new CityState("164", "10", "سرخس", "خراسان رضوي"));
		allCityList.add(new CityState("165", "10", "بردسكن", "خراسان رضوي"));
		allCityList.add(new CityState("166", "10", "فريمان", "خراسان رضوي"));
		allCityList.add(new CityState("167", "10", "چناران", "خراسان رضوي"));
		allCityList.add(new CityState("168", "10", "درگز", "خراسان رضوي"));
		allCityList.add(new CityState("169", "10", "كلات", "خراسان رضوي"));
		allCityList.add(new CityState("170", "10", "طرقبه", "خراسان رضوي"));
		allCityList.add(new CityState("171", "10", "سر ولایت", "خراسان رضوي"));
		allCityList.add(new CityState("172", "11", "بجنورد", "خراسان شمالي"));
		allCityList.add(new CityState("173", "11", "اسفراين", "خراسان شمالي"));
		allCityList.add(new CityState("174", "11", "جاجرم", "خراسان شمالي"));
		allCityList.add(new CityState("175", "11", "شيروان", "خراسان شمالي"));
		allCityList.add(new CityState("176", "11", "آشخانه", "خراسان شمالي"));
		allCityList.add(new CityState("177", "11", "گرمه", "خراسان شمالي"));
		allCityList.add(new CityState("178", "11", "ساروج", "خراسان شمالي"));
		allCityList.add(new CityState("179", "12", "اهواز", "خوزستان"));
		allCityList.add(new CityState("180", "12", "ايرانشهر", "خوزستان"));
		allCityList.add(new CityState("181", "12", "شوش", "خوزستان"));
		allCityList.add(new CityState("182", "12", "آبادان", "خوزستان"));
		allCityList.add(new CityState("183", "12", "خرمشهر", "خوزستان"));
		allCityList.add(new CityState("184", "12", "مسجد سليمان", "خوزستان"));
		allCityList.add(new CityState("185", "12", "ايذه", "خوزستان"));
		allCityList.add(new CityState("186", "12", "شوشتر", "خوزستان"));
		allCityList.add(new CityState("187", "12", "انديمشك", "خوزستان"));
		allCityList.add(new CityState("188", "12", "سوسنگرد", "خوزستان"));
		allCityList.add(new CityState("189", "12", "هويزه", "خوزستان"));
		allCityList.add(new CityState("190", "12", "دزفول", "خوزستان"));
		allCityList.add(new CityState("191", "12", "شادگان", "خوزستان"));
		allCityList.add(new CityState("192", "12", "بندر ماهشهر", "خوزستان"));
		allCityList
				.add(new CityState("193", "12", "بندر امام خميني", "خوزستان"));
		allCityList.add(new CityState("194", "12", "اميديه", "خوزستان"));
		allCityList.add(new CityState("195", "12", "بهبهان", "خوزستان"));
		allCityList.add(new CityState("196", "12", "رامهرمز", "خوزستان"));
		allCityList.add(new CityState("197", "12", "باغ ملك", "خوزستان"));
		allCityList.add(new CityState("198", "12", "هنديجان", "خوزستان"));
		allCityList.add(new CityState("199", "12", "لالي", "خوزستان"));
		allCityList.add(new CityState("200", "12", "رامشیر", "خوزستان"));
		allCityList.add(new CityState("201", "12", "حمیدیه", "خوزستان"));
		allCityList.add(new CityState("202", "12", "دغاغله", "خوزستان"));
		allCityList.add(new CityState("203", "12", "ملاثانی", "خوزستان"));
		allCityList.add(new CityState("204", "12", "شادگان", "خوزستان"));
		allCityList.add(new CityState("205", "12", "ویسی", "خوزستان"));
		allCityList.add(new CityState("206", "13", "زنجان", "زنجان"));
		allCityList.add(new CityState("207", "13", "ابهر", "زنجان"));
		allCityList.add(new CityState("208", "13", "خدابنده", "زنجان"));
		allCityList.add(new CityState("209", "13", "كارم", "زنجان"));
		allCityList.add(new CityState("210", "13", "ماهنشان", "زنجان"));
		allCityList.add(new CityState("211", "13", "خرمدره", "زنجان"));
		allCityList.add(new CityState("212", "13", "ايجرود", "زنجان"));
		allCityList.add(new CityState("213", "13", "زرين آباد", "زنجان"));
		allCityList.add(new CityState("214", "13", "آب بر", "زنجان"));
		allCityList.add(new CityState("215", "13", "قيدار", "زنجان"));
		allCityList.add(new CityState("216", "14", "سمنان", "سمنان"));
		allCityList.add(new CityState("217", "14", "شاهرود", "سمنان"));
		allCityList.add(new CityState("218", "14", "گرمسار", "سمنان"));
		allCityList.add(new CityState("219", "14", "ايوانكي", "سمنان"));
		allCityList.add(new CityState("220", "14", "دامغان", "سمنان"));
		allCityList.add(new CityState("221", "14", "بسطام", "سمنان"));
		allCityList.add(new CityState("222", "15", "زاهدان",
				"سيستان و بلوچستان"));
		allCityList.add(new CityState("223", "15", "چابهار",
				"سيستان و بلوچستان"));
		allCityList.add(new CityState("224", "15", "خاش", "سيستان و بلوچستان"));
		allCityList.add(new CityState("225", "15", "سراوان",
				"سيستان و بلوچستان"));
		allCityList
				.add(new CityState("226", "15", "زابل", "سيستان و بلوچستان"));
		allCityList
				.add(new CityState("227", "15", "سرباز", "سيستان و بلوچستان"));
		allCityList.add(new CityState("228", "15", "نيكشهر",
				"سيستان و بلوچستان"));
		allCityList.add(new CityState("229", "15", "ايرانشهر",
				"سيستان و بلوچستان"));
		allCityList
				.add(new CityState("230", "15", "راسك", "سيستان و بلوچستان"));
		allCityList.add(new CityState("231", "15", "ميرجاوه",
				"سيستان و بلوچستان"));
		allCityList.add(new CityState("232", "16", "شيراز", "فارس"));
		allCityList.add(new CityState("233", "16", "اقليد", "فارس"));
		allCityList.add(new CityState("234", "16", "داراب", "فارس"));
		allCityList.add(new CityState("235", "16", "فسا", "فارس"));
		allCityList.add(new CityState("236", "16", "مرودشت", "فارس"));
		allCityList.add(new CityState("237", "16", "خرم بيد", "فارس"));
		allCityList.add(new CityState("238", "16", "آباده", "فارس"));
		allCityList.add(new CityState("239", "16", "كازرون", "فارس"));
		allCityList.add(new CityState("240", "16", "ممسني", "فارس"));
		allCityList.add(new CityState("241", "16", "سپيدان", "فارس"));
		allCityList.add(new CityState("242", "16", "لار", "فارس"));
		allCityList.add(new CityState("243", "16", "فيروز آباد", "فارس"));
		allCityList.add(new CityState("244", "16", "جهرم", "فارس"));
		allCityList.add(new CityState("245", "16", "ني ريز", "فارس"));
		allCityList.add(new CityState("246", "16", "استهبان", "فارس"));
		allCityList.add(new CityState("247", "16", "لامرد", "فارس"));
		allCityList.add(new CityState("248", "16", "مهر", "فارس"));
		allCityList.add(new CityState("249", "16", "حاجي آباد", "فارس"));
		allCityList.add(new CityState("250", "16", "نورآباد", "فارس"));
		allCityList.add(new CityState("251", "16", "اردكان", "فارس"));
		allCityList.add(new CityState("252", "16", "صفاشهر", "فارس"));
		allCityList.add(new CityState("253", "16", "ارسنجان", "فارس"));
		allCityList.add(new CityState("254", "16", "قيروكارزين", "فارس"));
		allCityList.add(new CityState("255", "16", "سوريان", "فارس"));
		allCityList.add(new CityState("256", "16", "فراشبند", "فارس"));
		allCityList.add(new CityState("257", "16", "سروستان", "فارس"));
		allCityList.add(new CityState("258", "16", "ارژن", "فارس"));
		allCityList.add(new CityState("259", "16", "گويم", "فارس"));
		allCityList.add(new CityState("260", "16", "داريون", "فارس"));
		allCityList.add(new CityState("261", "16", "زرقان", "فارس"));
		allCityList.add(new CityState("262", "16", "خان زنیان", "فارس"));
		allCityList.add(new CityState("263", "16", "کوار", "فارس"));
		allCityList.add(new CityState("264", "16", "ده بید", "فارس"));
		allCityList.add(new CityState("265", "16", "باب انار/خفر", "فارس"));
		allCityList.add(new CityState("266", "16", "بوانات", "فارس"));
		allCityList.add(new CityState("267", "16", "خرامه", "فارس"));
		allCityList.add(new CityState("268", "16", "خنج", "فارس"));
		allCityList.add(new CityState("269", "16", "سیاخ دارنگون", "فارس"));
		allCityList.add(new CityState("270", "17", "قزوين", "قزوين"));
		allCityList.add(new CityState("271", "17", "تاكستان", "قزوين"));
		allCityList.add(new CityState("272", "17", "آبيك", "قزوين"));
		allCityList.add(new CityState("273", "17", "بوئين زهرا", "قزوين"));
		allCityList.add(new CityState("274", "18", "قم", "قم"));
		allCityList.add(new CityState("275", "19", "طالقان", "کرج"));
		allCityList.add(new CityState("276", "19", "نظرآباد", "کرج"));
		allCityList.add(new CityState("277", "19", "اشتهارد", "کرج"));
		allCityList.add(new CityState("278", "19", "هشتگرد", "کرج"));
		allCityList.add(new CityState("279", "19", "كن", "کرج"));
		allCityList.add(new CityState("280", "19", "آسارا", "کرج"));
		allCityList.add(new CityState("281", "19", "شهرک گلستان", "کرج"));
		allCityList.add(new CityState("282", "19", "اندیشه", "کرج"));
		allCityList.add(new CityState("283", "19", "كرج", "کرج"));
		allCityList.add(new CityState("284", "19", "نظر آباد", "کرج"));
		allCityList.add(new CityState("285", "19", "گوهردشت", "کرج"));
		allCityList.add(new CityState("286", "19", "ماهدشت", "کرج"));
		allCityList.add(new CityState("287", "19", "مشکین دشت", "کرج"));
		allCityList.add(new CityState("288", "20", "سنندج", "كردستان"));
		allCityList.add(new CityState("289", "20", "ديواندره", "كردستان"));
		allCityList.add(new CityState("290", "20", "بانه", "كردستان"));
		allCityList.add(new CityState("291", "20", "بيجار", "كردستان"));
		allCityList.add(new CityState("292", "20", "سقز", "كردستان"));
		allCityList.add(new CityState("293", "20", "كامياران", "كردستان"));
		allCityList.add(new CityState("294", "20", "قروه", "كردستان"));
		allCityList.add(new CityState("295", "20", "مريوان", "كردستان"));
		allCityList.add(new CityState("296", "20", "صلوات آباد", "كردستان"));
		allCityList.add(new CityState("297", "20", "حسن آباد", "كردستان"));
		allCityList.add(new CityState("298", "21", "كرمان", "كرمان"));
		allCityList.add(new CityState("299", "21", "راور", "كرمان"));
		allCityList.add(new CityState("300", "21", "بابك", "كرمان"));
		allCityList.add(new CityState("301", "21", "انار", "كرمان"));
		allCityList.add(new CityState("302", "21", "کوهبنان", "كرمان"));
		allCityList.add(new CityState("303", "21", "رفسنجان", "كرمان"));
		allCityList.add(new CityState("304", "21", "بافت", "كرمان"));
		allCityList.add(new CityState("305", "21", "سيرجان", "كرمان"));
		allCityList.add(new CityState("306", "21", "كهنوج", "كرمان"));
		allCityList.add(new CityState("307", "21", "زرند", "كرمان"));
		allCityList.add(new CityState("308", "21", "بم", "كرمان"));
		allCityList.add(new CityState("309", "21", "جيرفت", "كرمان"));
		allCityList.add(new CityState("310", "21", "بردسير", "كرمان"));
		allCityList.add(new CityState("311", "22", "كرمانشاه", "كرمانشاه"));
		allCityList
				.add(new CityState("312", "22", "اسلام آباد غرب", "كرمانشاه"));
		allCityList.add(new CityState("313", "22", "سر پل ذهاب", "كرمانشاه"));
		allCityList.add(new CityState("314", "22", "كنگاور", "كرمانشاه"));
		allCityList.add(new CityState("315", "22", "سنقر", "كرمانشاه"));
		allCityList.add(new CityState("316", "22", "قصر شيرين", "كرمانشاه"));
		allCityList.add(new CityState("317", "22", "گيلان غرب", "كرمانشاه"));
		allCityList.add(new CityState("318", "22", "هرسين", "كرمانشاه"));
		allCityList.add(new CityState("319", "22", "صحنه", "كرمانشاه"));
		allCityList.add(new CityState("320", "22", "پاوه", "كرمانشاه"));
		allCityList.add(new CityState("321", "22", "جوانرود", "كرمانشاه"));
		allCityList.add(new CityState("322", "22", "شاهو", "كرمانشاه"));
		allCityList.add(new CityState("323", "23", "ياسوج",
				"كهكيلويه و بويراحمد"));
		allCityList.add(new CityState("324", "23", "گچساران",
				"كهكيلويه و بويراحمد"));
		allCityList
				.add(new CityState("325", "23", "دنا", "كهكيلويه و بويراحمد"));
		allCityList.add(new CityState("326", "23", "دوگنبدان",
				"كهكيلويه و بويراحمد"));
		allCityList.add(new CityState("327", "23", "سي سخت",
				"كهكيلويه و بويراحمد"));
		allCityList.add(new CityState("328", "23", "دهدشت",
				"كهكيلويه و بويراحمد"));
		allCityList.add(new CityState("329", "23", "ليكك",
				"كهكيلويه و بويراحمد"));
		allCityList.add(new CityState("330", "24", "گرگان", "گلستان"));
		allCityList.add(new CityState("331", "24", "آق قلا", "گلستان"));
		allCityList.add(new CityState("332", "24", "گنبد كاووس", "گلستان"));
		allCityList.add(new CityState("333", "24", "علي آباد كتول", "گلستان"));
		allCityList.add(new CityState("334", "24", "مينو دشت", "گلستان"));
		allCityList.add(new CityState("335", "24", "تركمن", "گلستان"));
		allCityList.add(new CityState("336", "24", "كردكوي", "گلستان"));
		allCityList.add(new CityState("337", "24", "بندر گز", "گلستان"));
		allCityList.add(new CityState("338", "24", "كلاله", "گلستان"));
		allCityList.add(new CityState("339", "24", "آزاد شهر", "گلستان"));
		allCityList.add(new CityState("340", "24", "راميان", "گلستان"));
		allCityList.add(new CityState("341", "25", "رشت", "گيلان"));
		allCityList.add(new CityState("342", "25", "منجيل", "گيلان"));
		allCityList.add(new CityState("343", "25", "لنگرود", "گيلان"));
		allCityList.add(new CityState("344", "25", "رود سر", "گيلان"));
		allCityList.add(new CityState("345", "25", "تالش", "گيلان"));
		allCityList.add(new CityState("346", "25", "آستارا", "گيلان"));
		allCityList.add(new CityState("347", "25", "ماسوله", "گيلان"));
		allCityList.add(new CityState("348", "25", "آستانه اشرفيه", "گيلان"));
		allCityList.add(new CityState("349", "25", "رودبار", "گيلان"));
		allCityList.add(new CityState("350", "25", "فومن", "گيلان"));
		allCityList.add(new CityState("351", "25", "صومعه سرا", "گيلان"));
		allCityList.add(new CityState("352", "25", "بندرانزلي", "گيلان"));
		allCityList.add(new CityState("353", "25", "كلاچاي", "گيلان"));
		allCityList.add(new CityState("354", "25", "هشتپر", "گيلان"));
		allCityList.add(new CityState("355", "25", "رضوان شهر", "گيلان"));
		allCityList.add(new CityState("356", "25", "ماسال", "گيلان"));
		allCityList.add(new CityState("357", "25", "شفت", "گيلان"));
		allCityList.add(new CityState("358", "25", "سياهكل", "گيلان"));
		allCityList.add(new CityState("359", "25", "املش", "گيلان"));
		allCityList.add(new CityState("360", "25", "لاهیجان", "گيلان"));
		allCityList.add(new CityState("361", "25", "خشک بيجار", "گيلان"));
		allCityList.add(new CityState("362", "25", "خمام", "گيلان"));
		allCityList.add(new CityState("363", "25", "لشت نشا", "گيلان"));
		allCityList.add(new CityState("364", "25", "بندر کياشهر", "گيلان"));
		allCityList.add(new CityState("365", "26", "خرم آباد", "لرستان"));
		allCityList.add(new CityState("366", "26", "ماهشهر", "لرستان"));
		allCityList.add(new CityState("367", "26", "دزفول", "لرستان"));
		allCityList.add(new CityState("368", "26", "بروجرد", "لرستان"));
		allCityList.add(new CityState("369", "26", "دورود", "لرستان"));
		allCityList.add(new CityState("370", "26", "اليگودرز", "لرستان"));
		allCityList.add(new CityState("371", "26", "ازنا", "لرستان"));
		allCityList.add(new CityState("372", "26", "نور آباد", "لرستان"));
		allCityList.add(new CityState("373", "26", "كوهدشت", "لرستان"));
		allCityList.add(new CityState("374", "26", "الشتر", "لرستان"));
		allCityList.add(new CityState("375", "26", "پلدختر", "لرستان"));
		allCityList.add(new CityState("376", "27", "ساري", "مازندران"));
		allCityList.add(new CityState("377", "27", "آمل", "مازندران"));
		allCityList.add(new CityState("378", "27", "بابل", "مازندران"));
		allCityList.add(new CityState("379", "27", "بابلسر", "مازندران"));
		allCityList.add(new CityState("380", "27", "بهشهر", "مازندران"));
		allCityList.add(new CityState("381", "27", "تنكابن", "مازندران"));
		allCityList.add(new CityState("382", "27", "جويبار", "مازندران"));
		allCityList.add(new CityState("383", "27", "چالوس", "مازندران"));
		allCityList.add(new CityState("384", "27", "رامسر", "مازندران"));
		allCityList.add(new CityState("385", "27", "سواد كوه", "مازندران"));
		allCityList.add(new CityState("386", "27", "قائم شهر", "مازندران"));
		allCityList.add(new CityState("387", "27", "نكا", "مازندران"));
		allCityList.add(new CityState("388", "27", "نور", "مازندران"));
		allCityList.add(new CityState("389", "27", "بلده", "مازندران"));
		allCityList.add(new CityState("390", "27", "نوشهر", "مازندران"));
		allCityList.add(new CityState("391", "27", "پل سفيد", "مازندران"));
		allCityList.add(new CityState("392", "27", "محمود آباد", "مازندران"));
		allCityList.add(new CityState("393", "27", "فريدون كنار", "مازندران"));
		allCityList.add(new CityState("394", "28", "اراك", "مركزي"));
		allCityList.add(new CityState("395", "28", "آشتيان", "مركزي"));
		allCityList.add(new CityState("396", "28", "تفرش", "مركزي"));
		allCityList.add(new CityState("397", "28", "خمين", "مركزي"));
		allCityList.add(new CityState("398", "28", "دليجان", "مركزي"));
		allCityList.add(new CityState("399", "28", "ساوه", "مركزي"));
		allCityList.add(new CityState("400", "28", "سربند", "مركزي"));
		allCityList.add(new CityState("401", "28", "محلات", "مركزي"));
		allCityList.add(new CityState("402", "28", "شازند", "مركزي"));
		allCityList.add(new CityState("403", "29", "بندرعباس", "هرمزگان"));
		allCityList.add(new CityState("404", "29", "قشم", "هرمزگان"));
		allCityList.add(new CityState("405", "29", "كيش", "هرمزگان"));
		allCityList.add(new CityState("406", "29", "بندر لنگه", "هرمزگان"));
		allCityList.add(new CityState("407", "29", "بستك", "هرمزگان"));
		allCityList.add(new CityState("408", "29", "حاجي آباد", "هرمزگان"));
		allCityList.add(new CityState("409", "29", "دهبارز", "هرمزگان"));
		allCityList.add(new CityState("410", "29", "انگهران", "هرمزگان"));
		allCityList.add(new CityState("411", "29", "ميناب", "هرمزگان"));
		allCityList.add(new CityState("412", "29", "ابوموسي", "هرمزگان"));
		allCityList.add(new CityState("413", "29", "بندر جاسك", "هرمزگان"));
		allCityList.add(new CityState("414", "29", "تنب بزرگ", "هرمزگان"));
		allCityList.add(new CityState("415", "29", "بندر خمیر", "هرمزگان"));
		allCityList.add(new CityState("416", "29", "پارسیان", "هرمزگان"));
		allCityList.add(new CityState("417", "29", "قشم", "هرمزگان"));
		allCityList.add(new CityState("418", "30", "همدان", "همدان"));
		allCityList.add(new CityState("419", "30", "ملاير", "همدان"));
		allCityList.add(new CityState("420", "30", "تويسركان", "همدان"));
		allCityList.add(new CityState("421", "30", "نهاوند", "همدان"));
		allCityList.add(new CityState("422", "30", "كبودر اهنگ", "همدان"));
		allCityList.add(new CityState("423", "30", "رزن", "همدان"));
		allCityList.add(new CityState("424", "30", "اسدآباد", "همدان"));
		allCityList.add(new CityState("425", "30", "بهار", "همدان"));
		allCityList.add(new CityState("426", "31", "يزد", "يزد"));
		allCityList.add(new CityState("427", "31", "تفت", "يزد"));
		allCityList.add(new CityState("428", "31", "اردكان", "يزد"));
		allCityList.add(new CityState("429", "31", "ابركوه", "يزد"));
		allCityList.add(new CityState("430", "31", "ميبد", "يزد"));
		allCityList.add(new CityState("431", "31", "طبس", "يزد"));
		allCityList.add(new CityState("432", "31", "بافق", "يزد"));
		allCityList.add(new CityState("433", "31", "مهريز", "يزد"));
		allCityList.add(new CityState("434", "31", "اشكذر", "يزد"));
		allCityList.add(new CityState("435", "31", "هرات", "يزد"));
		allCityList.add(new CityState("436", "31", "خضرآباد", "يزد"));
		allCityList.add(new CityState("437", "31", "شاهديه", "يزد"));
		allCityList.add(new CityState("438", "31", "حمیدیه شهر", "يزد"));
		allCityList.add(new CityState("439", "31", "سید میرزا", "يزد"));
		allCityList.add(new CityState("440", "31", "زارچ", "يزد"));

	}

	private File imageFile;
	private Bitmap bitMap;

	private File lastImageFile;

	private SliderLayout mSlider;

	private List<String> imageList;

	private boolean sendingMelkInfo = false;

	private ProgressDialog ProgressDialog;

	private double latitude = 0;
	private double longitude = 0;

	public void pickImage() {
		// create intent with ACTION_IMAGE_CAPTURE action
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		// this part to save captured image on provided path
		lastImageFile = new File(Environment.getExternalStorageDirectory(),
				"amlakgostar/melkimage_" + sf.getUnixTime() + ".jpg");
		lastImageFile.getParentFile().mkdirs();
		Uri photoPath = Uri.fromFile(lastImageFile);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, photoPath);

		// start camera activity
		startActivityForResult(intent, TAKE_PICTURE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {

		if (requestCode == TAKE_PICTURE && resultCode == RESULT_OK
				&& intent != null) {

			// resize image first
			if (!lastImageFile.exists()) {
				Toast.makeText(
						getContext(),
						"خطا در هنگام دریافت تصویر، لطفا حافظه و دوربین گوشی را چک نمایید",
						Toast.LENGTH_LONG).show();
				return;
			}

			sf.ResizeImage(getContext(), lastImageFile.getPath(), 80, 800, 600);

			TextSliderView tSlider = new TextSliderView(this);
			tSlider.image(lastImageFile);
			mSlider.addSlider(tSlider);
			imageList.add(lastImageFile.getPath());

			Toast.makeText(getApplicationContext(),
					"تصویر با موفقیت اضافه گردید", Toast.LENGTH_SHORT).show();

			if (imageList.size() > 0) {
				mSlider.setVisibility(View.VISIBLE);
			}

			sf.ScrollView_ScrollToEnd((ScrollView) findViewById(R.id.acAddMelk_scrollView_Scroller));

		}
	}

	// method to check if you have a Camera
	private boolean hasCamera() {
		return getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_CAMERA);
	}

	// method to check you have Camera Apps
	private boolean hasDefualtCameraApp(String action) {
		final PackageManager packageManager = getPackageManager();
		final Intent intent = new Intent(action);
		List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
				PackageManager.MATCH_DEFAULT_ONLY);

		return list.size() > 0;

	}

	// private void loadMap() {
	// // Load the View
	// map = ((MapFragment) getFragmentManager().findFragmentById(
	// R.id.acAddMelk_fragmentMap)).getMap();
	//
	// // Load Information
	// LatLng location = new LatLng(29.62642087743759, 52.51815554051245);
	// Marker marker = map.addMarker(new MarkerOptions().position(location)
	// .title(getString(R.string.app_name)));
	// // Marker kiel = map.addMarker(new MarkerOptions()
	// // .position(KIEL)
	// // .title("Kiel")
	// // .snippet("Kiel is cool")
	// // .icon(BitmapDescriptorFactory
	// // .fromResource(R.drawable.ic_launcher)));
	//
	// // Move the camera instantly to hamburg with a zoom of 15.
	// map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 5));
	//
	// // Zoom in, animating the camera.
	// map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
	// }

	private void loadViews() {

		et_Zirbana = findEditText(R.id.acAddMelk_et_zirbana);
		et_Metraj = findEditText(R.id.acAddMelk_et_MetrajZamin);
		et_Bedroom = findEditText(R.id.acAddMelk_et_Bedroom);
		et_Bath = findEditText(R.id.acAddMelk_et_Bath);
		et_Description = findEditText(R.id.acAddMelk_et_Description);
		et_SalePrice = findEditText(R.id.acAddMelk_et_SalePrice);
		et_Rahn = findEditText(R.id.acAddMelk_et_Rahn);
		et_Ejare = findEditText(R.id.acAddMelk_et_Ejare);
		et_Phone = findEditText(R.id.acAddMelk_et_Phone);
		et_Mobile = findEditText(R.id.acAddMelk_et_Mobile);
		et_Blvd = findEditText(R.id.acAddMelk_et_Blvd);
		et_Address = findEditText(R.id.acAddMelk_et_Address);

		btn_Send = findButton(R.id.acAddMelk_btn_Add);
		btn_AddImage = findButton(R.id.acAddMelk_btn_AddImage);
		listViewImage = (ListView) findViewById(R.id.acAddMelk_lv_ImageList);

		btn_AddImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				pickImage();
			}
		});

		btn_Send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				onRequestSendMelkInfo();
			}
		});

		// adapter
		imageList = new ArrayList<String>();
		mSlider = (SliderLayout) findViewById(R.id.acAddmelk_slider_Master);
		mSlider.setPresetTransformer(SliderLayout.Transformer.FlipHorizontal);
		mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
		mSlider.setCustomAnimation(new DescriptionAnimation());
		mSlider.setDuration(4000);
		mSlider.setVisibility(View.GONE);

	}

	protected void onRequestSendMelkInfo() {

		if (!isValid()) {
			// user entered valid fields are not valid
			return;
		}

		// check if we are not sending melk info now
		if (this.sendingMelkInfo) {
			// we are currently sending melk info
			return;
		}

		// check if we are offline
		if (!sf.hasConnection(this)) {
			// we have to store offline
			storeOffline();
			return;
		}

		// set flag and show send progress dialog
		ProgressDialog = new ProgressDialog(this);
		ProgressDialog.setMessage("در حال ارسال اطلاعات به سامانه");
		ProgressDialog.show();
		sendingMelkInfo = true;

		// send information
		try {
			uploadInformation();
		} catch (Exception e) {
			e.printStackTrace();
			finish();
		}

	}

	private void uploadInformation() throws Exception {

		// get melk information
		List<NameValuePair> params = getSendParameters(false);

		nc.UploadFile(getContext(), config.requestUrl + "bongah/addmelk",
				imageList, params, new onUploadListner() {

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
					public void onStart() {
						// TODO Auto-generated method stub

					}

					@Override
					public void onPercentChange(long procceed, long length,
							int percent) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onError() {
						// TODO Auto-generated method stub

					}

					@Override
					public void onCompleted(String result) {

						// check if we have any melk item
						try {
							JSONObject object = new JSONObject(result);
							int totalMelkPhoneListner = object
									.getInt("totallistner");
							int melkID = object.getInt("melkid");
							afterMelkAdded(melkID, totalMelkPhoneListner);

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

					@Override
					public void Anytime() {
						ProgressDialog.cancel();
						sendingMelkInfo = false;
					}
				});

	}

	private boolean isValid() {

		String type = sp_type.getSelectedItem().toString();
		String manzoor = sp_manzoor.getSelectedItem().toString();

		String blvd = et_Blvd.getText().toString();
		String description = et_Description.getText().toString();
		String mobile = et_Mobile.getText().toString();
		String phone = et_Phone.getText().toString();

		String state = sp_State.getSelectedItem().toString();
		String city = sp_City.getSelectedItem().toString();
		String address = et_Address.getText().toString();

		// dynamic parameters
		String bedroom = et_Bedroom.getText().toString();
		String bath = et_Bath.getText().toString();
		String zirbana = et_Zirbana.getText().toString();
		String rahn = et_Rahn.getText().toString();
		String saleprice = et_SalePrice.getText().toString();
		String ejare = et_Ejare.getText().toString();
		String metraj = et_Metraj.getText().toString();

		// check dynamic fields
		if (type.equals("خانه") || type.equals("آپارتمان")
				|| type.equals("دفتر کار") || type.equals("اتاق کار")
				|| type.equals("ویلا")) {

			// we have to search for bed
			if (bedroom.length() == 0) {
				showError("شما میبایست تعداد اتاق را وارد نمایید");
				findEditText(R.id.acAddMelk_et_Bedroom).requestFocus();
				return false;
			}

		}

		if (type.equals("خانه") || type.equals("آپارتمان")
				|| type.equals("دفتر کار") || type.equals("ویلا")) {

			// we have to search for bed
			if (bath.length() == 0) {
				showError("شما میبایست تعداد حمام و دستشویی را وارد نمایید");
				findEditText(R.id.acAddMelk_et_Bath).requestFocus();
				return false;
			}

		}

		if (type.equals("خانه") || type.equals("آپارتمان")
				|| type.equals("دفتر کار") || type.equals("اتاق کار")
				|| type.equals("ویلا")) {

			// we have to search for bed
			if (zirbana.length() == 0) {
				showError("شما میبایست زیربنای ملک را وارد نمایید");
				findEditText(R.id.acAddMelk_et_zirbana).requestFocus();
				return false;
			}

		}

		if (type.equals("خانه") || type.equals("ویلا")) {

			// we have to search for bed
			if (metraj.length() == 0) {
				showError("شما میبایست متراژ را وارد نمایید");
				findEditText(R.id.acAddMelk_et_MetrajZamin).requestFocus();
				return false;
			}

		}

		if (manzoor.equals("فروش")) {

			// we have to search for bed
			if (saleprice.length() == 0) {
				showError("شما میبایست قیمت فروش را وارد نمایید");
				findEditText(R.id.acAddMelk_et_SalePrice).requestFocus();
				return false;
			}

		}

		if (manzoor.equals("رهن و اجاره")) {

			// we have to search for bed
			if (rahn.length() == 0) {
				showError("شما میبایست قیمت رهن را وارد نمایید"
						+ "."
						+ " در صورتی رهن رایگان است، مقدار صفر به اجاره اختصاص دهید");
				findEditText(R.id.acAddMelk_et_Rahn).requestFocus();
				return false;
			}

			if (ejare.length() == 0) {
				showError("شما میبایست قیمت اجاره را وارد نمایید"
						+ "."
						+ " در صورتی اجاره رایگان است، مقدار صفر به اجاره اختصاص دهید");
				findEditText(R.id.acAddMelk_et_Ejare).requestFocus();
				return false;
			}

		}

		if (mobile.length() == 0) {
			showError("شما میبایست شماره موبایل صاحب ملک را وارد نمایید");
			findEditText(R.id.acAddMelk_et_Mobile).requestFocus();
			return false;
		}

		if (phone.length() == 0) {
			showError("شما میبایست شماره تماس ثابت صاحب را وارد نمایید، در صورتی که ملک شماره ثابت ندارد، شماره تماس مشاور املاک خود را وارد نمایید");
			findEditText(R.id.acAddMelk_et_Phone).requestFocus();
			return false;
		}

		// check required fields
		if (blvd.length() == 0) {
			showError("شما میبایست بلوار اصلی را وارد نمایید");
			findEditText(R.id.acAddMelk_et_Blvd).requestFocus();
			return false;
		}

		if (address.length() == 0) {
			showError("شما میبایست آدرس را وارد نمایید، آدرس ملک در قسمت های دیگر برنامه مورد استفاده قرار خواهد گرفت");
			findEditText(R.id.acAddMelk_et_Address).requestFocus();
			return false;
		}
		return true;
	}

	private void showError(String message) {
		new AlertDialog.Builder(getContext())
				.setTitle(R.string.ops)
				.setMessage(message)
				.setPositiveButton(R.string.ok,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {

							}
						}).create().show();

	}

	protected void afterMelkAdded(final int melkID, int totalMelkPhoneListner) {

		// check if we have moshati
		if (totalMelkPhoneListner > 0) {
			new AlertDialog.Builder(getContext())
					.setTitle("مشتری")
					.setMessage(
							"تبریک! ملک شما با موفقیت به سامانه اضافه گردید. در حال حاضر "
									+ totalMelkPhoneListner + " "
									+ "مشتری برای ملک شما یافت گردید")
					.setPositiveButton("مشاهده مشتریان",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {

									Intent intent = new Intent(getContext(),
											AC_SuggestMelkPhoneListner.class);
									intent.putExtra("melkid", melkID);
									startActivity(intent);
								}
							}).setOnCancelListener(new OnCancelListener() {

						@Override
						public void onCancel(DialogInterface arg0) {

							// close the activity
							finish();

						}
					}).create().show();
		} else {
			Toast.makeText(getContext(), "ملک با موفقیت به سرور ارسال گردید",
					Toast.LENGTH_LONG).show();

			// finish activity
			finish();
		}

	}

	private List<NameValuePair> getSendParameters(boolean addImageToList)
			throws Exception {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("address", et_Address.getText()
				.toString()));

		params.add(new BasicNameValuePair("blvd", et_Blvd.getText().toString()));
		params.add(new BasicNameValuePair("description", et_Description
				.getText().toString()));
		params.add(new BasicNameValuePair("mobile", et_Mobile.getText()
				.toString()));
		params.add(new BasicNameValuePair("phone", et_Phone.getText()
				.toString()));
		params.add(new BasicNameValuePair("city", sp_City.getSelectedItem()
				.toString()));
		params.add(new BasicNameValuePair("manzoor", sp_manzoor
				.getSelectedItem().toString()));
		params.add(new BasicNameValuePair("state", sp_State.getSelectedItem()
				.toString()));
		params.add(new BasicNameValuePair("type", sp_type.getSelectedItem()
				.toString()));
		params.add(new BasicNameValuePair("latitude", latitude + ""));
		params.add(new BasicNameValuePair("longitude", longitude + ""));

		// dynamic parameters
		params.add(new BasicNameValuePair("bedroom", et_Bedroom.getText()
				.toString()));
		params.add(new BasicNameValuePair("bath", et_Bath.getText().toString()));
		params.add(new BasicNameValuePair("zirbana", et_Zirbana.getText()
				.toString()));
		params.add(new BasicNameValuePair("rahn", et_Rahn.getText().toString()));
		params.add(new BasicNameValuePair("saleprice", et_SalePrice.getText()
				.toString()));
		params.add(new BasicNameValuePair("ejare", et_Ejare.getText()
				.toString()));
		params.add(new BasicNameValuePair("metraj", et_Metraj.getText()
				.toString()));

		// check if we have to add image to list
		if (addImageToList) {
			int number = 1;
			for (String imageItem : imageList) {
				params.add(new BasicNameValuePair("file" + number, imageItem));
				number++;
			}
		}
		return params;
	}

	private void storeOffline() {

		if (!isValid()) {
			// user entered valid fields are not valid
			return;
		}

		// show success message
		new AlertDialog.Builder(getContext())
				.setTitle(R.string.ops)
				.setMessage(
						"ملک شما با موفقیت در گوشی ذخیره گردید، در هنگام اتصال به اینترنت ملک شما به سرور ارسال خواهد گردید")
				.setPositiveButton("ذخیره آفلاین",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								try {
									onRequestOfflineSave();
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									new AlertDialog.Builder(getContext())
											.setTitle(R.string.ops)
											.setMessage(
													"خطا در هنگام ذخیره سازی اطلاعات در حافطه داخلی گوشی")
											.setPositiveButton(
													R.string.ok,
													new DialogInterface.OnClickListener() {

														@Override
														public void onClick(
																DialogInterface arg0,
																int arg1) {

														}
													}).create().show();
								}
							}
						})
				.setNegativeButton("برگشست",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {

							}
						})
				.setNeutralButton("تنظیمات اینترنت",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {

							}
						}).create().show();
	}

	protected void onRequestOfflineSave() throws Exception {

		// user requested to save offline , we have to get parameters and sacve
		// to offline mode
		List<NameValuePair> sendParameters = getSendParameters(true);
		MainDatabase db = new MainDatabase(getContext());
		db.open();
		db.Unsynced_Add(sf.ConvertBasicNameValuePairToJSON(sendParameters)
				.toString());
		db.close();

		// saved successfully
		Toast.makeText(
				getApplicationContext(),
				"اطلاعات ملک با موفقیت در گوشی ذخیره گردید، در هنگام اتصال به اینترنت مشخصات ملک به سرور ارسال خواهد گردید",
				Toast.LENGTH_LONG).show();
		finish();

	}

	private void loadState() {
		sp_State = (Spinner) findViewById(R.id.acAddMelk_sp_State);
		final List<String> list = new ArrayList<String>();
		final List<String> SatteIDList = new ArrayList<String>();
		for (CityState cityState : allCityList) {
			if (!list.contains(cityState.StateName)) {
				list.add(cityState.StateName);
				SatteIDList.add(cityState.StateID);
			}

		}

		adapter_Spinner adp = new adapter_Spinner(this);
		adp.Add(list);
		sp_State.setAdapter(adp);
		sp_State.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				String ostanID = SatteIDList.get(pos);
				loadCity(ostanID);

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		adp.Refresh();
	}

	private void loadCity(String stateID) {

		// we have to get city in that list

		sp_City = (Spinner) findViewById(R.id.acAddMelk_sp_City);
		final List<String> list = new ArrayList<String>();
		for (CityState citySate : allCityList) {
			if (citySate.StateID.equals(stateID)) {
				list.add(citySate.CityName);
			}
		}

		adapter_Spinner adp = new adapter_Spinner(this);
		adp.Add(list);
		sp_City.setAdapter(adp);
		sp_City.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				String cityID = list.get(pos);

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		adp.Refresh();
	}

	private void loadManzoor() {
		sp_manzoor = (Spinner) findViewById(R.id.acAddMelk_sp_Purpose);
		final List<String> list = new ArrayList<String>();
		String[] item = hashFor.keySet().toArray(new String[0]);
		for (String string : item) {
			list.add(string);
		}

		adapter_Spinner adp = new adapter_Spinner(this);
		adp.Add(list);
		sp_manzoor.setAdapter(adp);
		sp_manzoor.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				String melkPurpose = list.get(pos);
				// check for price type
				if (melkPurpose.equals("فروش")) {
					findLinearLayout(R.id.acAddMelk_ll_Ejare).setVisibility(
							View.GONE);
					findLinearLayout(R.id.acAddMelk_ll_Rahn).setVisibility(
							View.GONE);
					findLinearLayout(R.id.acAddMelk_ll_SalePrice)
							.setVisibility(View.VISIBLE);

				} else if (melkPurpose.equals("رهن و اجاره")) {
					findLinearLayout(R.id.acAddMelk_ll_Ejare).setVisibility(
							View.VISIBLE);
					findLinearLayout(R.id.acAddMelk_ll_Rahn).setVisibility(
							View.VISIBLE);
					findLinearLayout(R.id.acAddMelk_ll_SalePrice)
							.setVisibility(View.GONE);
				} else {
					findLinearLayout(R.id.acAddMelk_ll_Ejare).setVisibility(
							View.VISIBLE);
					findLinearLayout(R.id.acAddMelk_ll_Rahn).setVisibility(
							View.VISIBLE);
					findLinearLayout(R.id.acAddMelk_ll_SalePrice)
							.setVisibility(View.VISIBLE);
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
		sp_type = (Spinner) findViewById(R.id.acAddMelk_sp_Type);
		final List<String> list = new ArrayList<String>();
		String[] item = hashTypes.keySet().toArray(new String[0]);
		for (String string : item) {
			list.add(string);
		}

		adapter_Spinner adp = new adapter_Spinner(this);
		adp.Add(list);
		sp_type.setAdapter(adp);
		adp.Refresh();

		sp_type.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				String melkType = list.get(pos);
				if (melkType.equals("خانه") || melkType.equals("آپارتمان")
						|| melkType.equals("دفتر کار")
						|| melkType.equals("اتاق کار")) {

					// we have to search for bed
					findLinearLayout(R.id.acAddMelk_ll_Bedroom).setVisibility(
							View.VISIBLE);
				} else {
					findLinearLayout(R.id.acAddMelk_ll_Bedroom).setVisibility(
							View.GONE);
				}

				if (melkType.equals("خانه")) {

					// we have to search for bed
					findLinearLayout(R.id.acAddMelk_ll_Bath).setVisibility(
							View.VISIBLE);
					findLinearLayout(R.id.acAddMelk_ll_Metraj).setVisibility(
							View.VISIBLE);
					findLinearLayout(R.id.acAddMelk_ll_Zirbana).setVisibility(
							View.VISIBLE);

				} else if (melkType.equals("آپارتمان")) {
					findLinearLayout(R.id.acAddMelk_ll_Bath).setVisibility(
							View.VISIBLE);
					findLinearLayout(R.id.acAddMelk_ll_Metraj).setVisibility(
							View.GONE);
					findLinearLayout(R.id.acAddMelk_ll_Zirbana).setVisibility(
							View.VISIBLE);
				} else if (melkType.equals("زمین")) {
					findLinearLayout(R.id.acAddMelk_ll_Bath).setVisibility(
							View.GONE);
					findLinearLayout(R.id.acAddMelk_ll_Metraj).setVisibility(
							View.VISIBLE);
					findLinearLayout(R.id.acAddMelk_ll_Zirbana).setVisibility(
							View.GONE);
				} else if (melkType.equals("اتاق کار")) {
					findLinearLayout(R.id.acAddMelk_ll_Bath).setVisibility(
							View.GONE);
					findLinearLayout(R.id.acAddMelk_ll_Metraj).setVisibility(
							View.GONE);
					findLinearLayout(R.id.acAddMelk_ll_Zirbana).setVisibility(
							View.VISIBLE);
				}

				else if (melkType.equals("دفتر کار")) {
					findLinearLayout(R.id.acAddMelk_ll_Bath).setVisibility(
							View.VISIBLE);
					findLinearLayout(R.id.acAddMelk_ll_Metraj).setVisibility(
							View.GONE);
					findLinearLayout(R.id.acAddMelk_ll_Zirbana).setVisibility(
							View.VISIBLE);
				}

				else if (melkType.equals("ویلا")) {
					findLinearLayout(R.id.acAddMelk_ll_Bath).setVisibility(
							View.VISIBLE);
					findLinearLayout(R.id.acAddMelk_ll_Metraj).setVisibility(
							View.VISIBLE);
					findLinearLayout(R.id.acAddMelk_ll_Zirbana).setVisibility(
							View.VISIBLE);
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}

		});

		sp_type.setSelection(2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ac__add_melk, menu);
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

	@Override
	public void onLocationChanged(Location arg0) {

		this.latitude = arg0.getLatitude();
		this.longitude = arg0.getLongitude();

	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}
}
