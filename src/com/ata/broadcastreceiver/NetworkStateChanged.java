package com.ata.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.amlakgostar.classes.AmlakGostarRequestManager;
import com.ata.config.config;
import com.ata.corebase.sf;
import com.ata.events.InternetConnection;

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

				// DO OPERATION
				// check for new request
				AmlakGostarRequestManager.CheckForNewNotification(context);
			} else {
				// internet is disabled
				Log.d(config.appLogTitle, "Internet Is Not Connected");

				// DO OPERATION
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
