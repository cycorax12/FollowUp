package in.cycorax.receiver;

import in.cycorax.sms4u.service.SMSMessageService;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {

		Intent iHeartBeatService = new Intent(context, SMSMessageService.class);
		PendingIntent piHeartBeatService = PendingIntent.getService(context, 0,
				iHeartBeatService, PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(piHeartBeatService);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
				System.currentTimeMillis(), 120000, piHeartBeatService);
	}
}
