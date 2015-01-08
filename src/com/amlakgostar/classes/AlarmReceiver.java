package com.amlakgostar.classes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ata.corebase.sf;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent arg1) {
		// alarm has been called, we have to request for new notification in
		// server
		Log.d("Amlak Gostar", "Alarm Received : " + sf.getUnixTime());

		// set last time received
		sf.SettingManager_WriteString(context, "lastalarm",
				"" + sf.getUnixTime());

		// check if we have Internet connection, do job
		if (sf.hasConnection(context)) {
			AmlakGostarRequestManager.CheckForNewNotification(context);
		}
	}

}
