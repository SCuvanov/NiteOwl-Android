package com.owl.main;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

public class UserDetailsActivity extends Activity implements OnClickListener {

	TextView tvBio, tvUsername, tvTagline;
	ImageButton btnAdd, btnBack;
	ParseImageView ivDetailProfilePic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_details);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		tvUsername = (TextView) findViewById(R.id.text_name_user);
		tvBio = (TextView) findViewById(R.id.text_bio_user);
		tvTagline = (TextView) findViewById(R.id.text_tagline_user);

		btnAdd = (ImageButton) findViewById(R.id.btn_addFriend);
		btnAdd.setOnClickListener(this);
		btnBack = (ImageButton) findViewById(R.id.btn_backUD);
		btnBack.setOnClickListener(this);

		ivDetailProfilePic = (ParseImageView) findViewById(R.id.imageViewUserDetail);

		Intent intent = getIntent();
		final String tempUsername = intent.getStringExtra("username");
		queryUser(tempUsername);

	}

	public void queryUser(String username) {
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.whereEqualTo("username", username);
		query.findInBackground(new FindCallback<ParseUser>() {
			public void done(List<ParseUser> objects, ParseException e) {
				if (e == null) {
					// The query was successful.
					final ParseUser pUser = objects.get(0);

					String pUsername = pUser.getUsername();
					String pBio = pUser.getString("bio");
					String pTagline = pUser.getString("tagline");

					tvUsername.setText(pUsername);
					tvBio.setText(pBio);
					tvTagline.setText(pTagline);

					ParseFile imageFile = pUser.getParseFile("photo");
					if (imageFile != null) {
						ivDetailProfilePic.setParseFile(imageFile);
						ivDetailProfilePic.loadInBackground();
					}

					// query to find if the parse relationship exists to change
					// the friend icon from white to green/visa versa

				} else {
					// Something went wrong.
				}
			}
		});
	}

	public void queryAddUser(String username) {
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.whereEqualTo("username", username);
		query.findInBackground(new FindCallback<ParseUser>() {
			public void done(List<ParseUser> objects, ParseException e) {
				if (e == null) {
					// The query was successful.
					ParseUser pUser = objects.get(0);

					final ParseUser currentUser = ParseUser.getCurrentUser();

					if (currentUser != null) {
						{

							// structure some type of check to see if the pUser
							// already exists as a friend to determine
							// whether or not the btnAdd should be colored a
							// specific way and if the button unfriends/friends
							// etc.

							Drawable img = getResources().getDrawable(
									R.drawable.ico_friend_add_green);

							btnAdd.setImageDrawable(img);

							ParseRelation<ParseUser> relation = currentUser
									.getRelation("Friends");
							relation.add(pUser);
							currentUser.saveInBackground();

						}
					}

				} else {
					// Something went wrong.
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.btn_addFriend:
			String tempUsername = (String) tvUsername.getText().toString();

			queryAddUser(tempUsername);

			break;

		case R.id.btn_backUD:

			finish();
			overridePendingTransition(0, 0);
			break;

		}
	}

}
