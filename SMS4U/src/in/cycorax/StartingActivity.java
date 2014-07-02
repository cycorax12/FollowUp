package in.cycorax;

import in.cycorax.adapter.SMSDataAdapter;
import in.cycorax.data.model.SMSDataModel;
import in.cycorax.db.sqlite.SMS4UDBHelper;
import in.cycorax.message.SMSSendMessageUtil;
import in.cycorax.sms4u.service.SMSMessageService;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ImageButton;
import android.widget.ListView;

public class StartingActivity extends Activity {
	protected static final int CONTEXTMENU_OPTION1 = 1;
	protected static final int CONTEXTMENU_OPTION2 = 2;
	protected static final int CONTEXTMENU_OPTION3 = 3;
	protected static final int CONTEXTMENU_OPTION4 = 4;
	private SMSDataAdapter dataAdapter = null;

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// Get extra info about list item that was long-pressed
		AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item
				.getMenuInfo();
		SMSDataModel currentData = dataAdapter.getItem(menuInfo.position);
		// Perform action according to selected item from context menu
		switch (item.getItemId()) {

		case CONTEXTMENU_OPTION1:
			// Show message

			Intent viewMessageIntent = new Intent(this,
					SMSViewMessageActivity.class);
			viewMessageIntent.putExtra("currentValueForUpdation", currentData);
			startActivity(viewMessageIntent);

			break;

		case CONTEXTMENU_OPTION2:
			// Show message
			SMSSendMessageUtil util = new SMSSendMessageUtil();
			util.sendMessage(currentData);

			dataAdapter.remove(currentData);
			db.delete(currentData.getId());
			break;
		case CONTEXTMENU_OPTION3:
			// dataAdapter.remove(currentData);
			// db.delete(currentData.getId());
			Intent updateActivity = new Intent(this, SMSUpdateActivity.class);
			updateActivity.putExtra("currentValueForUpdation", currentData);

			startActivity(updateActivity);

			break;
		case CONTEXTMENU_OPTION4:
			dataAdapter.remove(currentData);
			db.delete(currentData.getId());
			break;
		}

		return true;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		// Set title for the context menu
		// menu.setHeaderTitle("Options");

		// Add all the menu options

		// menu.add(Menu.NONE, CONTEXTMENU_OPTION1, 0, "View Message Details");
		menu.add(Menu.NONE, CONTEXTMENU_OPTION2, 1, "Send Message Now");
		menu.add(Menu.NONE, CONTEXTMENU_OPTION3, 2, "Update Message Details");
		menu.add(Menu.NONE, CONTEXTMENU_OPTION4, 3, "Delete Message");
	}

	private ArrayList<SMSDataModel> smsList;
	private ListView listView;
	private SMS4UDBHelper db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_starting);
		listView = (ListView) findViewById(R.id.sms_list_view);
		db = new SMS4UDBHelper(this);
		smsList = new ArrayList<SMSDataModel>();
		// smsList.add(new SMSDataModel(1, "Testing my Title One", "Test"));
		// smsList.add(new SMSDataModel(1, "Testing my Title Two", "Test"));
		smsList.addAll(db.fetchAllMessages());
		dataAdapter = new SMSDataAdapter(this, smsList);
		listView.setAdapter(dataAdapter);
		registerForContextMenu(listView);
		ImageButton emptyView = (ImageButton) findViewById(R.id.empty);
		listView.setEmptyView(emptyView);
		// SwipeListViewTouchListener touchListener = new
		// SwipeListViewTouchListener(
		// listView, new SwipeListViewTouchListener.OnSwipeCallback() {
		// @Override
		// public void onSwipeLeft(ListView listView,
		// int[] reverseSortedPositions) {
		// // Log.i(this.getClass().getName(),
		// // "swipe left : pos="+reverseSortedPositions[0]);
		// // TODO : YOUR CODE HERE FOR LEFT ACTION
		// System.out.println("TEsting Left Swipe");
		// }
		//
		// @Override
		// public void onSwipeRight(ListView listView,
		// int[] reverseSortedPositions) {
		// // Log.i(ProfileMenuActivity.class.getClass().getName(),
		// // "swipe right : pos="+reverseSortedPositions[0]);
		// // TODO : YOUR CODE HERE FOR RIGHT ACTION
		// System.out.println("TEsting Right Swipe");
		// }
		// }, true, // example : left action = dismiss
		// true); // example : right action without dismiss animation
		// listView.setOnTouchListener(touchListener);
		// // Setting this scroll listener is required to ensure that during
		// // ListView scrolling,
		// // we don't look for swipes.
		// listView.setOnScrollListener(touchListener.makeScrollListener());
		// determineDensity();
		createAlarmForService();
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {

		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.starting, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.add_sms_action:
			Intent smsAddActivty = new Intent(this, SMSAddActivity.class);
			startActivity(smsAddActivty);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void onAddClick(View v) {
		Intent intent = new Intent(this, SMSAddActivity.class);
		startActivity(intent);
	}

	public void createAlarmForService() {
		Intent iHeartBeatService = new Intent(this, SMSMessageService.class);
		PendingIntent piHeartBeatService = PendingIntent.getService(this, 0,
				iHeartBeatService, PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(piHeartBeatService);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
				System.currentTimeMillis(), 120000, piHeartBeatService);
	}

	public void onAddImageButtonClick(View view) {
		Intent smsAddActivity = new Intent(this, SMSAddActivity.class);
		startActivity(smsAddActivity);
	}
}
