package in.cycorax.activities;

import in.cycorax.R;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

public class DisplayContactsActivity extends Activity implements
		OnItemClickListener {

	List<String> name1 = new ArrayList<String>();
	List<String> phno1 = new ArrayList<String>();
	MyAdapter ma;
	Button select;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display);

		getAllContacts(this.getContentResolver());
		ListView lv = (ListView) findViewById(R.id.lv);
		ma = new MyAdapter();
		lv.setAdapter(ma);
		lv.setOnItemClickListener(this);
		lv.setItemsCanFocus(false);
		lv.setTextFilterEnabled(true);
		// adding
		select = (Button) findViewById(R.id.button1);
		select.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				StringBuilder checkedcontacts = new StringBuilder();
				ArrayList<String> selectedContacts = new ArrayList<String>();
				for (int i = 0; i < name1.size(); i++)

				{
					if (ma.mCheckStates.get(i) == true) {
						checkedcontacts.append(name1.get(i).toString());
						checkedcontacts.append("\n");
						selectedContacts.add(phno1.get(i));

					} else {

					}

				}

				Intent intnet = new Intent();
				Bundle extras = new Bundle();
				extras.putStringArray("Contacts",
						selectedContacts.toArray(new String[selectedContacts.size()]));
				intnet.putExtras(extras);
				setResult(1, intnet);
				finish();
			}
		});

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		ma.toggle(arg2);
	}

	public void getAllContacts(ContentResolver cr) {

		Cursor phones = cr.query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,
				null, Phone.DISPLAY_NAME + " ASC");
		
		while (phones.moveToNext()) {
			String name = phones
					.getString(phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
			String phoneNumber = phones
					.getString(phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
			name1.add(name);
			phno1.add(phoneNumber);
		}

		phones.close();
	}

	class MyAdapter extends BaseAdapter implements
			CompoundButton.OnCheckedChangeListener {
		private SparseBooleanArray mCheckStates;
		LayoutInflater mInflater;
		TextView tv1, tv;
		CheckBox cb;

		MyAdapter() {
			mCheckStates = new SparseBooleanArray(name1.size());
			mInflater = (LayoutInflater) DisplayContactsActivity.this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return name1.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub

			return 0;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			View vi = convertView;
			if (convertView == null)
				vi = mInflater.inflate(R.layout.row, null);
			tv = (TextView) vi.findViewById(R.id.textView1);
			tv1 = (TextView) vi.findViewById(R.id.textView2);
			cb = (CheckBox) vi.findViewById(R.id.checkBox1);
			tv.setText("Name :" + name1.get(position));
			tv1.setText("Phone No :" + phno1.get(position));
			cb.setTag(position);
			cb.setChecked(mCheckStates.get(position, false));
			cb.setOnCheckedChangeListener(this);

			return vi;
		}

		public boolean isChecked(int position) {
			return mCheckStates.get(position, false);
		}

		public void setChecked(int position, boolean isChecked) {
			mCheckStates.put(position, isChecked);
		}

		public void toggle(int position) {
			setChecked(position, !isChecked(position));
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub

			mCheckStates.put((Integer) buttonView.getTag(), isChecked);
		}
	}
}