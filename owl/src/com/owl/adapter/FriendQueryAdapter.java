package com.owl.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.owl.main.R;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseRelation;
import com.parse.ParseUser;

public class FriendQueryAdapter extends ParseQueryAdapter<ParseUser> {

	ImageButton btnAdd;
	ImageView ivAdd;

	public FriendQueryAdapter(Context context, final ParseUser currentUser) {

		// refine query to not show currentUser

		super(context, new ParseQueryAdapter.QueryFactory<ParseUser>() {
			public ParseQuery<ParseUser> create() {
				// ParseQuery<ParseUser> query = ParseUser.getQuery();
				ParseRelation relation = currentUser.getRelation("Friends");
				ParseQuery query = relation.getQuery();
				query.orderByAscending("Friends");
				return query;

			}
		});
	}

	@Override
	public View getItemView(final ParseUser pUser, View v, ViewGroup parent) {

		if (v == null) {
			v = View.inflate(getContext(), R.layout.search_detail, null);
		}

		super.getItemView(pUser, v, parent);

		ParseImageView todoImage = (ParseImageView) v
				.findViewById(R.id.imageViewSearch);
		ParseFile imageFile = pUser.getParseFile("photo");
		if (imageFile != null) {
			todoImage.setParseFile(imageFile);
			todoImage.loadInBackground();
		}

		// Add the title view
		final TextView titleTextView = (TextView) v
				.findViewById(R.id.textViewSearch);
		titleTextView.setText(pUser.getUsername());

		final TextView titleTextViewTagline = (TextView) v
				.findViewById(R.id.textViewSearchTagline);
		titleTextViewTagline.setText(pUser.getString("tagline"));

		return v;

	}

}
