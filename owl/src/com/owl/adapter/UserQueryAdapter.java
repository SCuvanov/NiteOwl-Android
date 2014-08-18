package com.owl.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.owl.main.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class UserQueryAdapter extends ParseQueryAdapter<ParseUser> {

	ImageButton btnAdd;

	public UserQueryAdapter(Context context, final String searchCriteria) {

		//refine query to not show currentUser

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

		btnAdd = (ImageButton) v.findViewById(R.id.imageButtonAdd);
		btnAdd.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Perform action on click
				Drawable background = v.getResources().getDrawable(
						R.drawable.ui_border_green);
				Drawable img = v.getResources().getDrawable(
						R.drawable.ico_friend_add_green);

				btnAdd.setBackground(background);
				btnAdd.setImageDrawable(img);

				final ParseUser currentUser = ParseUser.getCurrentUser();

				if (currentUser != null) {
					{

						// structure some type of check to see if the pUser
						// already exists as a friend to determine
						// whether or not the btnAdd should be colored a
						// specific way and if the button unfriends/friends etc.

						ParseRelation<ParseUser> relation = currentUser
								.getRelation("Friends");
						relation.add(pUser);
						currentUser.saveInBackground();

					}
				}

			}
		});

		return v;

	}

}
