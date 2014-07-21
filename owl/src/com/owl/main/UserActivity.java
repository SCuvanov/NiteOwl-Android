package com.owl.main;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class UserActivity extends Fragment implements OnClickListener,
		OnItemClickListener {

	Context context;
	private ProfilePictureView userProfilePictureView;
	private TextView userNameView;
	private TextView userLocationView;
	private TextView userGenderView;
	private TextView userDateOfBirthView;
	private TextView userRelationshipView;
	private static final String TAG = "OwlSample";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (container == null) {
			// We have different layouts, and in one of them this
			// fragment's containing frame doesn't exist. The fragment
			// may still be created from its saved state, but there is
			// no reason to try to create its view hierarchy because it
			// won't be displayed. Note this is not needed -- we could
			// just run the code below, where we would create and return
			// the view hierarchy; it would just never be used.
			return null;
		}

		// making request to facebook graphAPI for users profile
		// picture/information

		// Fetch Facebook user info if the session is active
		Session session = ParseFacebookUtils.getSession();
		if (session != null && session.isOpened()) {
			makeMeRequest();
		}

		// Inflate the layout for this fragment
		LinearLayout mLayout = (LinearLayout) inflater.inflate(
				R.layout.activity_user_fragment, container, false);

		Button logoutButton = (Button) mLayout
				.findViewById(R.id.log_out_button);
		logoutButton.setOnClickListener(this);
		
		Button linkFacebookButton = (Button) mLayout
				.findViewById(R.id.link_facebook_button);
		linkFacebookButton.setOnClickListener(this);
		

		userNameView = (TextView) mLayout.findViewById(R.id.text_name);
		 userProfilePictureView = (ProfilePictureView)
		 mLayout.findViewById(R.id.userProfilePicture);
		 

		return mLayout;
	}

	@Override
	public void onResume() {
		super.onResume();

		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {
			// Check if the user is currently logged
			// and show any cached content
			updateViewsWithProfileInfo();
		} else {
			// If the user is not logged in, go to the
			// activity showing the login view.
			showLoginActivity();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.log_out_button:
			Log.e(TAG, "Tapped sign out");
			onLogoutButtonClicked();
			break;
			
		case R.id.link_facebook_button:
			Log.e(TAG, "Tapped link facebook");
			
			final ParseUser currentUser = ParseUser.getCurrentUser(); 
			
			if (!ParseFacebookUtils.isLinked(currentUser)) {
				  ParseFacebookUtils.link(currentUser, getActivity(), new SaveCallback() {
				    @Override
				    public void done(ParseException ex) {
				      if (ParseFacebookUtils.isLinked(currentUser)) {
				        Log.d("MyApp", "Woohoo, user logged in with Facebook!");
				        makeMeRequest();
				      }
				    }
				  });
				}
			

		}

	}

	private void showLoginActivity() {
		Intent intent = new Intent(getActivity(), MainActivity.class);
		startActivity(intent);
	}
	
	private void onLogoutButtonClicked() {
		// Log the user out
		ParseUser.logOut();

		// Go to the login view
		showLoginActivity();
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
//								if (user.getLocation().getProperty("name") != null) {
//									userProfile.put("location", (String) user
//											.getLocation().getProperty("name"));
//								}
								// Save the user profile info in a user property
								ParseUser currentUser = ParseUser
										.getCurrentUser();
								currentUser.put("profile", userProfile);
								currentUser.saveInBackground();

								// Show the user info
								updateViewsWithProfileInfo();
								
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
								onLogoutButtonClicked();
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

	private void updateViewsWithProfileInfo() {
		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser.get("profile") != null) {
			JSONObject userProfile = currentUser.getJSONObject("profile");
			try {
				if (userProfile.getString("facebookId") != null) {
					String facebookId = userProfile.get("facebookId")
							.toString();
					userProfilePictureView.setProfileId(facebookId);
				} else {
					// Show the default, blank user profile picture
					userProfilePictureView.setProfileId(null);
				}
				if (userProfile.getString("name") != null) {
					userNameView.setText(userProfile.getString("name"));
				} else {
					userNameView.setText("");
				}
			} catch (JSONException e) {
				// handle error
			}

		}
	}

}