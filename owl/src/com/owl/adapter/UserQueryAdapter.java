package com.owl.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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

public class UserQueryAdapter extends ParseQueryAdapter<ParseUser> {

	ImageButton btnAdd;
	ImageView ivAdd;

	public UserQueryAdapter(Context context, final String searchCriteria) {

		// refine query to not show currentUser

		super(context, new ParseQueryAdapter.QueryFactory<ParseUser>() {
			public ParseQuery<ParseUser> create() {
				ParseQuery<ParseUser> query = ParseUser.getQuery();
				query.whereContains("username", searchCriteria);
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

		// ivAdd = (ImageView) v.findViewById(R.id.imageViewAdd);
		// ivAdd.setOnClickListener(new View.OnClickListener() {
		// public void onClick(View v) {
		//
		// final ParseUser currentUser = ParseUser.getCurrentUser();
		//
		// if (currentUser != null) {
		// {
		//
		// // structure some type of check to see if the pUser
		// // already exists as a friend to determine
		// // whether or not the btnAdd should be colored a
		// // specific way and if the button unfriends/friends etc.
		//
		// ParseRelation<ParseUser> relation = currentUser
		// .getRelation("Friends");
		// relation.add(pUser);
		// currentUser.saveInBackground();
		//
		// }
		// }
		//
		// }
		// });
		//
		return v;

	}

}
