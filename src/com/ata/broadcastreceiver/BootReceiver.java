package com.ata.broadcastreceiver;

import com.ata.config.config;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// when the app received booting
		Log.d(config.appLogTitle, "Received New Boot");
	}
}
