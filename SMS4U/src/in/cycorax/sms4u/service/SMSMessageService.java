package in.cycorax.sms4u.service;

import in.cycorax.data.model.SMSDataModel;
import in.cycorax.db.sqlite.SMS4UDBHelper;
import in.cycorax.message.SMSSendMessageUtil;
import in.cycorax.util.SMS4UDateTimeUtility;

import java.util.ArrayList;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class SMSMessageService extends Service {

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		// System.out.println("Service Started.");
		// SmsManager smsManager = SmsManager.getDefault();
		// smsManager.sendTextMessage("+919028480426", null, "Testing code",
		// null,
		// null);
		SMS4UDBHelper helper = new SMS4UDBHelper(this);
		ArrayList<SMSDataModel> allMessages = helper.fetchAllMessages();
		if (allMessages != null && !allMessages.isEmpty()) {
			SMS4UDateTimeUtility utility = new SMS4UDateTimeUtility();
			SMSSendMessageUtil messageUtil = new SMSSendMessageUtil();
			for (SMSDataModel smsDataModel : allMessages) {
				if (utility
						.compareAndSendMessage(smsDataModel.getDateAndTime())) {
					messageUtil.sendMessage(smsDataModel);
					helper.delete(smsDataModel.getId());
				}
			}
		}

		//this.stopSelf();
		return super.onStartCommand(intent, flags, startId);

	}

	private String TAG = SMSMessageService.class.getName();

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(TAG, "Service creating... ");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i(TAG, "Service Destroying.... ");
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
