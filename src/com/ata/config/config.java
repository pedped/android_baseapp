package com.ata.config;

import com.ataalla.amlakgostar.R;

public class config {

	public static final String appLogTitle = "Core App";
	public static final int NOTIFICATIONID_WEBSITENOTIFICATION = 10;
	public static final long[] WebsiteNotificationVibratePattern = new long[] {
			200, 200, 200, 200 };
	public static final int NewWebsiteNotificationIcon = R.drawable.ic_launcher;
	public static String requestUrl = "http://amlak.edspace.org/api/";
	public static String DATABASE_AMLAKDARKHASTI = "amlakdarkashti";
	public static String DATABASE_AMLAK = "amlak";
	public static String uploadURL = "http://amlak.edspace.org/api/file/upload";
	public static String uploadImageURL = "http://amlak.edspace.org/api/file/uploadimage";

	public static final int NOTIFICAION_UPDATE_ID = 999;
	public static final int NOTIFICATION_COLOR = 888888;
	public static final int NOTIFICATION_DRAWABLE = R.drawable.finish_flag;
	public static final int NOTIFICATION_NEWREQUEST = R.drawable.manager;
	public static final int NOTIFICATION_NEWREQUEST_ID = 1001;
	public static final Integer ALARMMANAGER_INTERVAL = 1800000;

}
