package com.ata.events;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.ata.activity.AC_ViewNotification;
import com.ata.config.config;
import com.ata.coreapp.R;
import com.ata.corebase.interfaces.OnResponseListener;
import com.ata.corebase.nc;
import com.ata.corebase.sf;
import com.corebase.classes.WebsiteNotification;

public class InternetConnection {

	/**
	 * this function will handle event when user connected to Internet
	 * 
	 * @param context
	 */
	public static void onInternetConnected(Context context) {

		// we have to check if we have no new notification
		checkForNotification(context);

		// check for new app version
		checkForNewVersion(context);

	}

	/**
	 * Checks for new app version
	 * 
	 * @param context
	 */
	public static void checkForNewVersion(final Context context) {

		/*
		 * Create a web request Check for new version Dec 19, 2014
		 */
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		// params.add(new BasicNameValuePair("request", "requestname"));
		// params.add(new BasicNameValuePair("key", "value"));
		nc.WebRequest(context, config.requestUrl + "public/mobileversion",
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
						// we have to json Decode the result
						try {
							JSONObject json = new JSONObject(result);

							int version = json.getInt("versioncode");
							String versionName = json.getString("versionname");
							String md5 = json.getString("md5");
							String downloadlink = json
									.getString("downloadlink");

							// check for the android version
							try {
								PackageInfo pinfo;
								pinfo = context.getPackageManager()
										.getPackageInfo(
												context.getPackageName(), 0);
								int currentVersion = pinfo.versionCode;

								Log.d("New Version", version + " --- "
										+ currentVersion);

								// check if the version number is larger than
								// version name
								if (version > currentVersion) {
									// new version released, we have to show the
									// notification to the user about new
									// version
									NewUpdateIsAvavile(context, downloadlink,
											versionName);
								}

							} catch (NameNotFoundException e) {

								e.printStackTrace();
							}

						} catch (Exception e) {
							// TODO: handle exception
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

	private static void NewUpdateIsAvavile(Context context,
			String downloadlink, String versionName) {

		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(downloadlink));
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);

		// bitmap loaded , show the notification
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				context)
				.setSmallIcon(config.NOTIFICATION_DRAWABLE)
				.setContentTitle(
						"new version of "
								+ context.getString(R.string.app_name)
								+ "is released")
				.setContentText("version " + versionName + " is just released");
		mBuilder.setLights(Color.argb(255, 114, 255, 0), 800, 400);

		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				intent, PendingIntent.FLAG_CANCEL_CURRENT);

		mBuilder.setContentIntent(contentIntent);
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(ns);

		mNotificationManager.notify(config.NOTIFICAION_UPDATE_ID,
				mBuilder.build());

		// vibrate the app
		sf.vibrate(context, config.WebsiteNotificationVibratePattern);

	}

	public static void checkForNotification(final Context context) {

		// get last notification visit
		String lastNotificationVisit = sf.SettingManager_ReadString(context,
				"lastnotificationsee");

		// request server to check for new notification
		/*
		 * Create a web request Request New Notification Dec 18, 2014
		 */
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("lastvisit", lastNotificationVisit));
		nc.WebRequest(context, config.requestUrl + "public/newnotification",
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
							// we have received new notification
							if (result.length() > 0) {
								// we have to parse json
								WebsiteNotification notification = new WebsiteNotification();
								notification.ReadFromJson(result);

								// create new pending intent
								// we have to show the new notification
								Intent intent = new Intent(context,
										AC_ViewNotification.class);

								intent.putExtra("id", notification.id);
								intent.putExtra("title", notification.title);
								intent.putExtra("message", notification.message);
								intent.putExtra("date", notification.date);
								intent.putExtra("link", notification.link);
								intent.putExtra("linktext",
										notification.linkText);
								PendingIntent pIntent = PendingIntent
										.getActivity(context, 0, intent, 0);

								// build notification
								// the addAction re-use the same intent to keep
								// the example short
								Notification n = new NotificationCompat.Builder(
										context)
										.setContentTitle(notification.title)
										.setContentText(notification.message)
										.setSmallIcon(
												config.NewWebsiteNotificationIcon)
										.setContentIntent(pIntent)
										.setAutoCancel(false).build();

								// .addAction(R.drawable.icon, "Call",
								// pIntent)
								// .addAction(R.drawable.icon, "More",
								// pIntent)
								// .addAction(R.drawable.icon, "And more",
								// pIntent).build();

								NotificationManager notificationManager = (NotificationManager) context
										.getSystemService(Service.NOTIFICATION_SERVICE);

								notificationManager
										.notify(config.NOTIFICATIONID_WEBSITENOTIFICATION,
												n);

								// vibrate the phone
								sf.vibrate(
										context,
										config.WebsiteNotificationVibratePattern);

							}
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
}
