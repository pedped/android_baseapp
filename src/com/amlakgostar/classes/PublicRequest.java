package com.amlakgostar.classes;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.ata.activity.AC_BongahPlans;
import com.ata.activity.AC_SMSCreditPlans;
import com.ata.config.config;
import com.ata.corebase.nc;
import com.ata.corebase.sf;
import com.ata.corebase.interfaces.OnResponseListener;
import com.ataalla.amlakgostar.R;

public class PublicRequest {

	public static void Request_SubscribePlan(Context context) {

		// start activity
		context.startActivity(new Intent(context, AC_BongahPlans.class));

	}

	public static void Request_SMSPlan(Context context) {

		// start activity
		context.startActivity(new Intent(context, AC_SMSCreditPlans.class));

	}

	public static void RequestPurchase(final Context context, String id,
			final boolean finishActivityAfterRediect) {

		// set loading
		final ProgressDialog progressDialog = new ProgressDialog(context);
		progressDialog.setMessage("ارسال درخواست به سرور");
		progressDialog.setCancelable(false);
		progressDialog.show();

		// request server
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id", id + ""));
		nc.WebRequest(context, config.requestUrl + "bongah/purchasesmscredit",
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
							// we have to get url and try to open that
							if (result.length() > 8) {
								sf.OpenUrl(context, result);

								// we have to finish url
								if (finishActivityAfterRediect) {
									((Activity) context).finish();
								}
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
						progressDialog.cancel();
					}
				});

	}
}
