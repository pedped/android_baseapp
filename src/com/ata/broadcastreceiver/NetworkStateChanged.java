package com.ata.broadcastreceiver;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONException;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.amalkgostar.database.MainDatabase;
import com.amalkgostar.database.MainDatabase.UnsyncedMelk;
import com.amlakgostar.classes.AmlakGostarRequestManager;
import com.ata.config.config;
import com.ata.corebase.nc;
import com.ata.corebase.sf;
import com.ata.events.InternetConnection;
import com.ataalla.amlakgostar.R;
import com.corebase.interfaces.onUploadListner;
import com.crittercism.app.Crittercism;

public class NetworkStateChanged extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent arg1) {
		// when the app received booting
		Log.d(config.appLogTitle, "Internet State Changed");

		try {
			// check if we are connected or not
			if (sf.hasConnection(context)) {
				// Internet is connected
				Log.d(config.appLogTitle, "Internet Is Connected");

				InternetConnection.onInternetConnected(context);

				// try to set alarm manager
				AmlakGostarRequestManager.SetAlarmManager(context);

				// DO OPERATION
				// check for new request
				AmlakGostarRequestManager.CheckForNewNotification(context);

				// Check if we have offline melkinfo, try to upload them
				MainDatabase database = new MainDatabase(context);
				database.open();
				List<UnsyncedMelk> unsyncedItems = database
						.Unsynced_GetAllItems();
				for (UnsyncedMelk unsyncedMelk : unsyncedItems) {
					// try to upload item
					Log.d(config.appLogTitle, "TRY TO SEND MELK INFO");
					database.close();
					sendMelkInfoToServer(context, database, unsyncedMelk);
				}
				database.close();

			} else {
				// internet is disabled
				Log.d(config.appLogTitle, "Internet Is Not Connected");

				// DO OPERATION
			}
		} catch (Exception e) {
			e.printStackTrace();
			Crittercism.logHandledException(e);
		}

	}

	private void sendMelkInfoToServer(final Context context,
			final MainDatabase database, final UnsyncedMelk unsyncedMelk)
			throws JSONException {

		// get melk information
		List<NameValuePair> params = sf
				.ConvertJSON_TO_BasicNameValuePair(unsyncedMelk.Data);

		// get image list
		List<String> imageList = new ArrayList<String>();
		for (NameValuePair nameValuePair : params) {
			if (nameValuePair.getName().startsWith("file")) {
				imageList.add(nameValuePair.getValue());
			}
		}

		nc.UploadFile(context, config.requestUrl + "bongah/addmelk", imageList,
				params, new onUploadListner() {

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

						// we have to remove item from database
						database.open();
						database.Unsynced_Remove(unsyncedMelk.ID);
						database.close();

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

						// we have to remove item from dataase
						database.open();
						database.Unsynced_Remove(unsyncedMelk.ID);
						database.close();

						// show success message
						Toast.makeText(context,
								"ملک با موفقیت به سرور ارسال گردید",
								Toast.LENGTH_LONG).show();

					}

					@Override
					public void Anytime() {

					}
				});

	}

	private boolean hasInternet(Context context) {

		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		boolean isConnected = activeNetInfo != null
				&& activeNetInfo.isConnectedOrConnecting();
		return isConnected;
	}
}
