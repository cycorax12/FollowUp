package in.cycorax.db.sqlite;

import in.cycorax.data.model.SMSDataModel;
import in.cycorax.sms4u.constants.SMS4UConstants;

import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SMS4UDBHelper extends SQLiteOpenHelper implements SMS4UConstants {

	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_NAME = "smsDatabase";
	private static final String TABLE_NAME = "sms4u";

	private static final String KEY_ID = "_id";
	private static final String KEY_SMS_TITLE = "sms_title";
	private static final String KEY_SMS_NUMBERS = "sms_numbers";

	private static final String KEY_SMS_MESSAGE = "sms_message";
	private static final String KEY_SMS_DATE_TIME = "sms_date_time";

	// private SQLiteDatabase databaseInstance;

	public SMS4UDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		String sql = " CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( "
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ KEY_SMS_TITLE + " TEXT, " + KEY_SMS_NUMBERS + " TEXT, "
				+ KEY_SMS_DATE_TIME + " TEXT, " + KEY_SMS_MESSAGE + " TEXT)";
		database.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(database);
	}

	public void add(SMSDataModel dataModel) {
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_SMS_TITLE, dataModel.getTitle());
		values.put(KEY_SMS_MESSAGE, dataModel.getMessage());
		values.put(KEY_SMS_NUMBERS, dataModel.getNumbersForSMS());
		values.put(KEY_SMS_DATE_TIME, dataModel.getDateAndTime());
		database.insert(TABLE_NAME, null, values);
		database.close();
	}

	public static Date loadDate(Cursor cursor) {

		return new Date(cursor.getColumnIndex(KEY_SMS_DATE_TIME));
	}

	public void update(SMSDataModel dataModel) {
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_SMS_TITLE, dataModel.getTitle());
		values.put(KEY_SMS_MESSAGE, dataModel.getMessage());
		values.put(KEY_SMS_NUMBERS, dataModel.getNumbersForSMS());
		values.put(KEY_SMS_DATE_TIME, dataModel.getDateAndTime());
		database.update(TABLE_NAME, values, KEY_ID + " = ?",
				new String[] { String.valueOf(dataModel.getId()) });
		database.close();
	}

	public static Long persistDate(Date date) {

		if (date != null) {
			return date.getTime();
		}
		return null;
	}

	public void delete(int smsId) {
		SQLiteDatabase database = this.getWritableDatabase();
		database.delete(TABLE_NAME, KEY_ID + "=" + smsId, null);
		database.close();
	}

	public ArrayList<SMSDataModel> fetchAllMessages() {
		ArrayList<SMSDataModel> smsList = new ArrayList<SMSDataModel>();
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery("select * from " + TABLE_NAME, null);
		if (cursor.moveToFirst()) {
			do {
				SMSDataModel newObject = new SMSDataModel();
				int keyId = cursor.getInt(cursor.getColumnIndex(KEY_ID));
				String title = cursor.getString(cursor
						.getColumnIndex(KEY_SMS_TITLE));
				String message = cursor.getString(cursor
						.getColumnIndex(KEY_SMS_MESSAGE));
				String smsNumbers = cursor.getString(cursor
						.getColumnIndex(KEY_SMS_NUMBERS));
				String dateAndTime = cursor.getString(cursor
						.getColumnIndex(KEY_SMS_DATE_TIME));
				newObject.setDateAndTime(dateAndTime);
				newObject.setId(keyId);
				newObject.setTitle(title);
				newObject.setMessage(message);
				newObject.setNumbersForSMS(smsNumbers);
				if (dateAndTime != null) {
					String[] dateTime = dateAndTime.split(HASH_CHARACTER);
					if (dateTime != null && dateTime.length == 2) {
						newObject.setDate(dateTime[0]);
						newObject.setTime(dateTime[1]);
					}
				}
				smsList.add(newObject);
				// do what ever you want here
			} while (cursor.moveToNext());
		}
		database.close();
		cursor.close();
		return smsList;
	}

	public void fetchMessage() {

	}
}
