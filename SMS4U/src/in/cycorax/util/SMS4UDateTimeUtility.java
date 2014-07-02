package in.cycorax.util;

import in.cycorax.sms4u.constants.SMS4UConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SMS4UDateTimeUtility implements SMS4UConstants {

//	private int day, month, year, hour, minute;

	// messageDateAndTime = messageDateAndTime.replace("#", " ");
	public boolean compareAndSendMessage(String messageDateAndTime) {

		messageDateAndTime = messageDateAndTime.replace("#", " ");

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		Date currentDateTime = new Date();
		String stringCurrentDateAndTime = dateFormat.format(currentDateTime);

		try {
			Date messageDateTime = dateFormat.parse(messageDateAndTime);
			Date currentDateTime1 = dateFormat.parse(stringCurrentDateAndTime);
			if (messageDateTime.compareTo(currentDateTime1) != 1) {
				return true;
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

//	private boolean compareTime(String messageTime, String currentTime) {
//		String messageTimes[] = messageTime.split(COLON_CHARACTER);
//		String currentTimes[] = currentTime.split(COLON_CHARACTER);
//		if (parseStringToInteger(messageTimes[0]) != parseStringToInteger(currentTimes[0])) {
//			return false;
//		}
//		if (parseStringToInteger(messageTimes[0]) != parseStringToInteger(currentTimes[0])) {
//			return false;
//		}
//		return true;
//	}
//
//	private boolean compareDate(String messageDate, String currentDate) {
//		return false;
//	}

//	private int parseStringToInteger(String string) {
//		return Integer.parseInt(string);
//	}

//	private void initialseDateAndTime() {
//		day = Calendar.DAY_OF_MONTH;
//		month = Calendar.MONTH;
//		year = Calendar.YEAR;
//		minute = Calendar.MINUTE;
//		hour = Calendar.HOUR_OF_DAY;
//
//	}
}
