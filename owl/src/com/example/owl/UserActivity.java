package com.example.owl;

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

import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class UserActivity extends Fragment implements OnClickListener,
		OnItemClickListener {

	Context context;
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

		// Inflate the layout for this fragment
		LinearLayout mLayout = (LinearLayout) inflater.inflate(
				R.layout.activity_user_fragment, container, false);

		Button logoutButton = (Button) mLayout.findViewById(R.id.facebook_button_log_out);
		logoutButton.setOnClickListener(this);

		
		return mLayout;
	}

	@Override
	public void onResume() {
		super.onResume();

		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {
			// Check if the user is currently logged
			// and show any cached content
			// updateViewsWithProfileInfo();
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
		case R.id.facebook_button_log_out:
			Log.e(TAG, "Tapped sign out");
			// Log the user out
			ParseUser.logOut();

			// Go to the login view
			showLoginActivity();
			break;
			
		case R.id.facebook_sync_button:
			Log.e(TAG, "Tapped sync button");
			final ParseUser currentUser = ParseUser.getCurrentUser();
			
			//linking facebook account with parse account
			
			
//			if (!ParseFacebookUtils.isLinked(currentUser)) {
//				  ParseFacebookUtils.link(currentUser, this, new SaveCallback() {
//				    @Override
//				    public void done(ParseException ex) {
//				      if (ParseFacebookUtils.isLinked(currentUser)) {
//				        Log.d("MyApp", "Woohoo, user logged in with Facebook!");
//				      }
//				    }
//				  });
//				}
			
			
			
			
		}

	}

	private void showLoginActivity() {
		Intent intent = new Intent(getActivity(), MainActivity.class);
		startActivity(intent);
	}

}