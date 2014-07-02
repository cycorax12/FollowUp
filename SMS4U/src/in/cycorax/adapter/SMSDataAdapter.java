package in.cycorax.adapter;

import in.cycorax.R;
import in.cycorax.data.model.SMSDataModel;
import in.cycorax.singleton.ListItemColorSelectorSingleton;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SMSDataAdapter extends ArrayAdapter<SMSDataModel> {

	private ArrayList<SMSDataModel> list;

	public SMSDataAdapter(Context context, ArrayList<SMSDataModel> smsList) {

		super(context, R.layout.sms_item_list_view, smsList);
		list = smsList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflator = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			convertView = inflator.inflate(R.layout.sms_item_list_view, null);
		}
		SMSDataModel data = list.get(position);

		TextView view = (TextView) convertView
				.findViewById(R.id.list_item_text_view);
		view.setText(data.getTitle());

		view.setTextSize(40);
		view.setBackgroundColor(Color.parseColor(ListItemColorSelectorSingleton
				.getInstance().getColorCode()));
		return convertView;
	}

}
