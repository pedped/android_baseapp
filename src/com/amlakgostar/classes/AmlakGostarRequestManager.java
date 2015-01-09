package com.amlakgostar.classes;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.ata.activity.AC_Master;
import com.ata.config.config;
import com.ata.corebase.interfaces.OnResponseListener;
import com.ata.corebase.nc;
import com.ata.corebase.sf;
import com.ataalla.amlakgostar.R;
import com.crittercism.app.Crittercism;

public class AmlakGostarRequestManager {

	public static void CheckForNewNotification(final Context context) {

		// check if we have internet connection
		if (!sf.hasConnection(context)) {
			return;
		}

		/*
		 * Create a web request Check for new melk request from server Dec 24,
		 * 2014
		 */
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		// put last seen
		params.add(new BasicNameValuePair("lastid", sf
				.SettingManager_ReadString(context, "lastrequestseen")));

		// request server
		nc.WebRequest(context, config.requestUrl + "bongah/getnewrequest",
				params, new OnResponseListener() {

					@Override
					public void onUnSuccess(String message) {
						new AlertDialog.Builder(context)
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

							// check for new notification
							onResultReceived(context, result);

						} catch (Exception e) {
							e.printStackTrace();
							Crittercism.logHandledException(e);
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

	protected static void onResultReceived(Context context, String result)
			throws JSONException {

		// we have to parse new items
		JSONArray json = new JSONArray(result);

		// check if we had any new notification
		if (json.length() == 0) {
			// we have not new request
			return;
		}

		// we had some new notifications, get first item and show id
		String id = json.getJSONObject(0).getString("id");

		// store in settings manager, check id with internal settings value
		if (Integer.valueOf(sf.SettingManager_ReadString(context,
				"lastrequestseen")) < Integer.valueOf(id)) {
			// we have to set new notification seen
			sf.SettingManager_WriteString(context, "lastrequestseen", id);
		}

		// last id saved in database, try to create new notification
		String title = json.length() > 1 ? "مشتریان جدید" : "مشتری جدید";
		String description = "شما " + json.length() + " مشتری جدید دارید";

		// show Notification
		Intent intent = new Intent(context, AC_Master.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);

		// bitmap loaded , show the notification
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				context).setSmallIcon(config.NOTIFICATION_NEWREQUEST)
				.setContentTitle(title).setContentText(description);
		mBuilder.setLights(Color.argb(255, 114, 255, 0), 800, 400);

		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				intent, PendingIntent.FLAG_CANCEL_CURRENT);

		mBuilder.setContentIntent(contentIntent);
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(ns);

		mNotificationManager.notify(config.NOTIFICATION_NEWREQUEST_ID,
				mBuilder.build());

		// vibrate the app
		sf.vibrate(context, config.WebsiteNotificationVibratePattern);

	}

	public static void SetAlarmManager(Context context) {

		// check if the last time we have checked for alarm manager in not
		// lower than now
		long lastChecked = Long.valueOf(sf.SettingManager_ReadString(context,
				"lastalarm"));
		long now = sf.getUnixTime();

		if (lastChecked != 0
				&& ((now - lastChecked) * 1000) < config.ALARMMANAGER_INTERVAL) {
			// we do not need to set alarm manager
			return;
		}

		AlarmManager alarmMgr;
		PendingIntent alarmIntent;

		alarmMgr = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);

		Intent intent = new Intent(context, AlarmReceiver.class);

		alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

		alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP,
				System.currentTimeMillis(), config.ALARMMANAGER_INTERVAL,
				alarmIntent);

		// show debug info
		Log.d("Amlak Gostar", "Alarm Mananger Seted");

	}
}
