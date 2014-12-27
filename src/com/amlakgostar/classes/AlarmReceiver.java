package com.amlakgostar.classes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent arg1) {
		// alarm has been called, we have to request for new notification in
		// server
		Log.d("Amlak Gostar", "Alarm Received");
		AmlakGostarRequestManager.CheckForNewNotification(context);

	}

}
