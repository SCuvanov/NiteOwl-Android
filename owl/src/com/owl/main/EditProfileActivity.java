package com.owl.main;

import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.model.GraphUser;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class EditProfileActivity extends Activity implements OnClickListener {

	EditText etDisplayName, etBio, etTagLine;
	ImageButton btnConfirmEP, btnBack;

	final ParseUser currentUser = ParseUser.getCurrentUser();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editprofile);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		etDisplayName = (EditText) findViewById(R.id.editTextDisplayName);
		etBio = (EditText) findViewById(R.id.editTextBio);
		etTagLine = (EditText) findViewById(R.id.editTextTagLine);

		btnConfirmEP = (ImageButton) findViewById(R.id.btn_confirmEP);
		btnConfirmEP.setOnClickListener(this);

		btnBack = (ImageButton) findViewById(R.id.btn_backEP);
		btnBack.setOnClickListener(this);

		Button linkFacebookButton = (Button) findViewById(R.id.link_facebook_button);
		linkFacebookButton.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.btn_confirmEP:
			// ParseUser currentUser = ParseUser.getCurrentUser();
			if (currentUser != null) {
				// do stuff with the user

				String displayName = etDisplayName.getText().toString().trim();
				String bio = etBio.getText().toString().trim();
				String tagLine = etTagLine.getText().toString().trim();

				if (!displayName.isEmpty()) {

					currentUser.put("displayName", displayName);
					finish();
				}
				if (!bio.isEmpty()) {
					currentUser.put("bio", bio);
					finish();
				}
				if (!tagLine.isEmpty()) {
					currentUser.put("tagline", tagLine);
					finish();
				}
				currentUser.saveInBackground();

			}

		case R.id.link_facebook_button:

			if (!ParseFacebookUtils.isLinked(currentUser)) {
				ParseFacebookUtils.link(currentUser, this, new SaveCallback() {
					@Override
					public void done(ParseException ex) {
						if (ParseFacebookUtils.isLinked(currentUser)) {
							Log.d("MyApp",
									"Woohoo, user logged in with Facebook!");
							makeMeRequest();
						}
					}
				});
			}
			break;

		case R.id.btn_backEP:

			finish();
			overridePendingTransition(0, 0);
			break;

		}

	}

	private void makeMeRequest() {
		Request request = Request.newMeRequest(ParseFacebookUtils.getSession(),
				new Request.GraphUserCallback() {
					@Override
					public void onCompleted(GraphUser user, Response response) {
						// handle response
						if (user != null) {
							// Create a JSON object to hold the profile info
							JSONObject userProfile = new JSONObject();
							try {
								// Populate the JSON object
								userProfile.put("facebookId", user.getId());
								userProfile.put("name", user.getName());
								// if (user.getLocation().getProperty("name") !=
								// null) {
								// userProfile.put("location", (String) user
								// .getLocation().getProperty("name"));
								// }
								// Save the user profile info in a user property
								ParseUser currentUser = ParseUser
										.getCurrentUser();
								currentUser.put("profile", userProfile);
								currentUser.saveInBackground();

							} catch (JSONException e) {
								Log.d(InitializeApplication.TAG,
										"Error parsing returned user data.");
							}

						} else if (response.getError() != null) {
							// handle error
							if ((response.getError().getCategory() == FacebookRequestError.Category.AUTHENTICATION_RETRY)
									|| (response.getError().getCategory() == FacebookRequestError.Category.AUTHENTICATION_REOPEN_SESSION)) {
								Log.d(InitializeApplication.TAG,
										"The facebook session was invalidated.");
							} else {
								Log.d(InitializeApplication.TAG,
										"Some other error: "
												+ response.getError()
														.getErrorMessage());
							}
						}
					}
				});
		request.executeAsync();

	}

	private void showNaviActivity() {
		Intent intent = new Intent(this, NaviActivity.class);
		startActivity(intent);
	}
}
