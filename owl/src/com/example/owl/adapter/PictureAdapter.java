package com.example.owl.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.owl.R;
import com.example.owl.model.PictureItem;
import com.parse.ParseImageView;

public class PictureAdapter extends ArrayAdapter<PictureItem> {

	public PictureAdapter(Context context, List<PictureItem> objects) {
		super(context, R.layout.picture_picker_dialog_view_detail);
		// TODO Auto-generated constructor stub
	}

	/* private view holder class */
	private class ViewHolder {
		ParseImageView imageView;
		TextView txtDesc;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		PictureItem item = getItem(position);

		// Check if an existing view is being reused, otherwise inflate the view
		ViewHolder viewHolder;

		if (convertView == null) {
			viewHolder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = inflater.inflate(
					R.layout.picture_picker_dialog_view_detail, null);
			viewHolder.imageView = (ParseImageView) convertView
					.findViewById(R.id.imageView123);
			viewHolder.txtDesc = (TextView) convertView
					.findViewById(R.id.textView123);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		// Populate the data into the template view using the data object
		viewHolder.txtDesc.setText(item.getDesc());
		viewHolder.imageView.setImageResource(item.getImageId());
		// Return the completed view to render on screen
		return convertView;

	}

}
