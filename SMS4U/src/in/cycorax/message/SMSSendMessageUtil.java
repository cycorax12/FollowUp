package in.cycorax.message;

import in.cycorax.data.model.SMSDataModel;
import android.telephony.SmsManager;

public class SMSSendMessageUtil {
	public void sendMessage(SMSDataModel data) {
		try {
			String[] smsNumbers = data.getNumbersForSMS().split(",");
			for (String smsNumber : smsNumbers) {
				smsNumber = smsNumber.trim();
				SmsManager smsManager = SmsManager.getDefault();
				smsManager.sendTextMessage(smsNumber, null, data.getMessage(),
						null, null);
			}
		} catch (Exception e) {

		}
	}
}
