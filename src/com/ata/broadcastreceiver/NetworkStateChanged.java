package com.ata.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ata.config.config;
import com.ata.corebase.sf;

public class NetworkStateChanged extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// when the app received booting
		Log.d(config.appLogTitle, "Internet State Changed");

		// check if we are connected or not
		if (sf.hasConnection(arg0)) {
			// internet is connected
			Log.d(config.appLogTitle, "Internet Is Connected");

			// DO OPERATION
		} else {
			// internet is disabled
			Log.d(config.appLogTitle, "Internet Is Not Connected");

			// DO OPERATION
		}
	}
}
