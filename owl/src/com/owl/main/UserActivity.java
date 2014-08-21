package com.owl.main;

import java.io.ByteArrayOutputStream;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class UserActivity extends Fragment implements OnClickListener {

	Context context;
	public ParseImageView ivProfilePic;

	private TextView tvUserName, tvBio, tvTagLine;

	private static final String TAG = "OwlSample";
	private static final int RESULT_OK = 0;
	private static final int RESULT_LOAD_IMAGE = 1;

	final int PHOTO_WIDTH = 300;
	final int PHOTO_HEIGHT = 300;

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

		Button logoutButton = (Button) mLayout
				.findViewById(R.id.log_out_button);
		logoutButton.setOnClickListener(this);

		Button editProfileButton = (Button) mLayout
				.findViewById(R.id.btn_editProfile);
		editProfileButton.setOnClickListener(this);

		ImageButton btnFindFriends = (ImageButton) mLayout
				.findViewById(R.id.btn_friends);
		btnFindFriends.setOnClickListener(this);

		tvUserName = (TextView) mLayout.findViewById(R.id.text_name);
		tvBio = (TextView) mLayout.findViewById(R.id.text_bio);
		tvTagLine = (TextView) mLayout.findViewById(R.id.text_tagline);
		ivProfilePic = (ParseImageView) mLayout
				.findViewById(R.id.imageViewUserProfile);

		ivProfilePic.setOnClickListener(this);

		updateViewsWithProfileInfo();
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.log_out_button:
			Log.e(TAG, "Tapped sign out");
			onLogoutButtonClicked();
			break;

		case R.id.btn_editProfile:
			Log.e(TAG, "Tapped editProfile button");

			Intent intent = new Intent(getActivity(), EditProfileActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(intent);
			break;

		case R.id.btn_friends:
			Log.e(TAG, "Tapped editProfile button");

			Intent intent1 = new Intent(getActivity(),
					FriendSearchActivity.class);
			intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(intent1);
			break;

		case R.id.imageViewUserProfile:

			Intent cropIntent = new Intent();
			cropIntent.setType("image/*");
			cropIntent.setAction(Intent.ACTION_PICK);

			cropIntent.putExtra("crop", "true");
			cropIntent.putExtra("scale", true);
			cropIntent.putExtra("outputX", PHOTO_WIDTH);
			cropIntent.putExtra("outputY", PHOTO_HEIGHT);
			cropIntent.putExtra("aspectX", 1);
			cropIntent.putExtra("aspectY", 1);
			cropIntent.putExtra("return-data", true);

			startActivityForResult(cropIntent, RESULT_LOAD_IMAGE);

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

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == RESULT_LOAD_IMAGE) {
			Bundle extras2 = data.getExtras();
			if (extras2 != null) {
				Bitmap bitmap = extras2.getParcelable("data");
				ivProfilePic.setImageBitmap(bitmap);

				final ParseUser currentUser = ParseUser.getCurrentUser();

				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
				byte[] bitmapByte = stream.toByteArray();

				final ParseFile profilePicture = new ParseFile(
						"profilePicture", bitmapByte);
				profilePicture.saveInBackground(new SaveCallback() {
					public void done(ParseException e) {
						if (e != null) {

						} else {

							// check to see if ParseFile exists, if it does
							// delete it and set new ParseFile
							currentUser.remove("photo");
							currentUser.put("photo", profilePicture);
							currentUser.saveInBackground();
							updateViewsWithProfileInfo();
						}
					}
				});

			}
		}

	}

	private void updateViewsWithProfileInfo() {
		ParseUser currentUser = ParseUser.getCurrentUser();

		if (currentUser.getUsername() != null) {
			tvUserName.setText(currentUser.getUsername());
		} else {
			tvUserName.setText("");
		}

		if (currentUser.getString("bio") != null) {
			tvBio.setText(currentUser.getString("bio"));
		} else {
			tvBio.setText("");
		}

		if (currentUser.getString("tagline") != null) {
			tvTagLine.setText(currentUser.getString("tagline"));
		} else {
			tvTagLine.setText("");
		}

		ParseFile imageFile = currentUser.getParseFile("photo");
		if (imageFile != null) {
			ivProfilePic.setParseFile(imageFile);
			ivProfilePic.loadInBackground();
		}

	}

}