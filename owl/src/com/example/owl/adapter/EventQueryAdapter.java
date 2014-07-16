package com.example.owl.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.owl.R;
import com.example.owl.model.Event;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

public class EventQueryAdapter extends ParseQueryAdapter<Event> {

	public EventQueryAdapter(Context context) {
		// Use the QueryFactory to construct a PQA that will only show
		// Todos marked as high-pri

		super(context, new ParseQueryAdapter.QueryFactory<Event>() {
			public ParseQuery create() {
				ParseQuery query = new ParseQuery("Event");
				query.whereEqualTo("user", ParseUser.getCurrentUser());
				return query;
			}
		});
	}

	@Override
	public View getItemView(Event event, View v, ViewGroup parent) {

		if (v == null) {
			v = View.inflate(getContext(), R.layout.row_swipe, null);
		}

		super.getItemView(event, v, parent);
		
//		
//		 // Add and download the image
//		  ParseImageView todoImage = (ParseImageView) v.findViewById(R.id.icon);
//		  ParseFile imageFile = object.getParseFile("image");
//		  if (imageFile != null) {
//		    todoImage.setParseFile(imageFile);
//		    todoImage.loadInBackground();
//		  }

		// Add the title view
		TextView titleTextView = (TextView) v.findViewById(R.id.text_name);
		titleTextView.setText(event.getTitle());
		// Add the title view
		TextView descTextView = (TextView) v.findViewById(R.id.text_desc);
		descTextView.setText(event.getDesc());
	

		return v;

	}

}
