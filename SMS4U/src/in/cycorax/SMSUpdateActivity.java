package in.cycorax;

import in.cycorax.activities.DisplayContactsActivity;
import in.cycorax.data.model.SMSDataModel;
import in.cycorax.db.sqlite.SMS4UDBHelper;
import in.cycorax.sms4u.constants.SMS4UConstants;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

public class SMSUpdateActivity extends Activity implements OnClickListener,
		SMS4UConstants {

	private Button submitSMS, cancelSMS;
	private ImageButton datePicker, timePicker, addContacts;
	private EditText dateTextView, timeTextView, smsTitle, smsMessage,
			smsNumbers;
	private int mYear, mMonth, mDay, mHour, mMinute;
	private SMSDataModel currentDataModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_smsupdate);
		Intent updateIntent = getIntent();
		currentDataModel = (SMSDataModel) updateIntent
				.getSerializableExtra("currentValueForUpdation");
		datePicker = (ImageButton) findViewById(R.id.update_dateButton);
		timePicker = (ImageButton) findViewById(R.id.update_timeButton);
		submitSMS = (Button) findViewById(R.id.update_submit_sms_button);
		smsTitle = (EditText) findViewById(R.id.update_sms_title);
		addContacts = (ImageButton) findViewById(R.id.update_add_contacts);
		smsMessage = (EditText) findViewById(R.id.update_sms_message);
		smsNumbers = (EditText) findViewById(R.id.update_sms_numbers);
		cancelSMS = (Button) findViewById(R.id.update_cancel_sms_button);
		dateTextView = (EditText) findViewById(R.id.update_dateText);
		timeTextView = (EditText) findViewById(R.id.update_timeText);

		datePicker.setOnClickListener(this);
		timePicker.setOnClickListener(this);
		submitSMS.setOnClickListener(this);
		cancelSMS.setOnClickListener(this);
		addContacts.setOnClickListener(this);
		dateTextView.setEnabled(false);
		timeTextView.setEnabled(false);
		populateIncomingDataForUpdata(currentDataModel);
	}

	private void populateIncomingDataForUpdata(SMSDataModel updateData) {
		smsNumbers.setText(updateData.getNumbersForSMS());
		smsTitle.setText(updateData.getTitle());
		dateTextView.setText(updateData.getDate());
		timeTextView.setText(updateData.getTime());
		smsMessage.setText(updateData.getMessage());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.smsadd, menu);
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.smsupdate, menu);
		return true;
	}

	@Override
	public void onClick(View view) {
		if (view == datePicker) {

			// Process to get Current Date
			final Calendar c = Calendar.getInstance();
			mYear = c.get(Calendar.YEAR);
			mMonth = c.get(Calendar.MONTH);
			mDay = c.get(Calendar.DAY_OF_MONTH);

			// Launch Date Picker Dialog
			DatePickerDialog dpd = new DatePickerDialog(this,
					new DatePickerDialog.OnDateSetListener() {

						@Override
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							// Display Selected date in textbox
							dateTextView.setText(dayOfMonth + "-"
									+ (monthOfYear + 1) + "-" + year);

						}
					}, mYear, mMonth, mDay);
			dpd.show();
		}
		if (view == timePicker) {

			// Process to get Current Time
			final Calendar c = Calendar.getInstance();
			mHour = c.get(Calendar.HOUR_OF_DAY);
			mMinute = c.get(Calendar.MINUTE);
			// Launch Time Picker Dialog
			TimePickerDialog tpd = new TimePickerDialog(this,
					new TimePickerDialog.OnTimeSetListener() {

						@Override
						public void onTimeSet(TimePicker view, int hourOfDay,
								int minute) {
							// Display Selected time in textbox
							timeTextView.setText(hourOfDay + ":" + minute);
						}
					}, mHour, mMinute, false);
			tpd.show();
		}
		if (view == submitSMS) {
			if (currentDataModel != null) {
				if (validateInputs(smsNumbers, smsTitle, dateTextView,
						timeTextView, smsMessage)) {
					currentDataModel.setTitle(smsTitle.getText().toString());
					currentDataModel.setNumbersForSMS(smsNumbers.getText()
							.toString());

					currentDataModel.setDateAndTime(prepareDate(dateTextView
							.getText().toString(), timeTextView.getText()
							.toString()));
					currentDataModel
							.setMessage(smsMessage.getText().toString());
					SMS4UDBHelper helper = new SMS4UDBHelper(this);

					helper.update(currentDataModel);

					Intent startActivity = new Intent(this,
							StartingActivity.class);
					startActivity(startActivity);
				}
			}
		}
		if (view == cancelSMS) {
			Intent startActivity = new Intent(this, StartingActivity.class);
			startActivity(startActivity);
		}

		if (view == addContacts) {
			Intent showContactActivity = new Intent(this,
					DisplayContactsActivity.class);
			startActivityForResult(showContactActivity, 1);
		}

	}

	private String prepareDate(String date, String time) {

		return date + HASH_CHARACTER + time;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1) {
			if (data != null) {
				Bundle contacts = data.getExtras();
				if (contacts != null) {
					String[] contactSelected = contacts
							.getStringArray("Contacts");
					if (smsNumbers.getText() != null
							&& smsNumbers.getText().toString() != null) {
						String currentNumbers = smsNumbers.getText().toString();
						if (!currentNumbers.endsWith(COMMA_CHARACTER)) {
							currentNumbers = currentNumbers
									.concat(COMMA_CHARACTER);
						}
						StringBuilder builder = new StringBuilder();
						for (String contact : contactSelected) {
							builder.append(contact);
							builder.append(COMMA_CHARACTER);
						}
						builder.deleteCharAt(builder.length() - 1);
						smsNumbers.setText(currentNumbers.concat(builder
								.toString()));
					} else {
						StringBuilder builder = new StringBuilder();
						for (String contact : contactSelected) {
							builder.append(contact);
							builder.append(COMMA_CHARACTER);
						}
						builder.deleteCharAt(builder.length() - 1);
						smsNumbers.setText(builder.toString());
					}
				}
			}
		}
	}

	private boolean validateInputs(EditText smsNumbers, EditText smsTitle,
			EditText date, EditText time, EditText smsMessage) {
		if (smsNumbers.getText() == null
				|| smsNumbers.getText().toString() == null
				|| smsNumbers.getText().toString().equals("")
				|| smsNumbers.getText().toString().length() == 0) {
			Toast.makeText(this, "Atleast one number should be provided.",
					Toast.LENGTH_LONG).show();
			return false;
		}
		if (smsTitle.getText() == null || smsTitle.getText().toString() == null
				|| smsTitle.getText().toString().equals("")
				|| smsTitle.getText().toString().length() == 0) {
			Toast.makeText(this,
					"Provide title for identification of message.",
					Toast.LENGTH_LONG).show();
			return false;
		}
		if (date.getText() == null || date.getText().toString() == null
				|| date.getText().equals("")
				|| date.getText().toString().length() == 0) {
			Toast.makeText(this, "Provide date for message.", Toast.LENGTH_LONG)
					.show();
			return false;
		}
		if (time.getText() == null || time.getText().toString() == null
				|| time.getText().equals("")
				|| time.getText().toString().length() == 0) {
			Toast.makeText(this, "Provide time for message.", Toast.LENGTH_LONG)
					.show();
			return false;
		}
		if (smsMessage.getText() == null
				|| smsMessage.getText().toString() == null
				|| smsMessage.getText().equals("")
				|| smsMessage.getText().toString().length() == 0) {
			Toast.makeText(this, "Provide Message body.", Toast.LENGTH_LONG)
					.show();
			return false;
		}

		return true;
	}
}
