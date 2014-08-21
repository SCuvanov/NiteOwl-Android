package com.owl.main;

import java.io.ByteArrayOutputStream;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.model.GraphUser;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class EditProfileActivity extends Activity implements OnClickListener {

	EditText etDisplayName, etBio, etTagLine;
	ImageButton btnConfirmEP, btnBack;
//	Button btnChangePic;
//
//	final int PHOTO_WIDTH = 150;
//	final int PHOTO_HEIGHT = 150;

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

//		btnChangePic = (Button) findViewById(R.id.btn_change_picture);
//		btnChangePic.setOnClickListener(this);

		btnBack = (ImageButton) findViewById(R.id.btn_backEP);
		btnBack.setOnClickListener(this);

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

//		case R.id.btn_change_picture:
//			Intent intent = new Intent(
//					Intent.ACTION_PICK,
//					android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//
//			intent.setType("image/*");
//			intent.putExtra("crop", "true");
//			intent.putExtra("scale", true);
//			intent.putExtra("outputX", PHOTO_WIDTH);
//			intent.putExtra("outputY", PHOTO_HEIGHT);
//			intent.putExtra("aspectX", 1);
//			intent.putExtra("aspectY", 1);
//			intent.putExtra("return-data", true);
//			startActivityForResult(intent, 1);

		case R.id.btn_backEP:

			finish();
			overridePendingTransition(0, 0);
			break;

		}

	}

//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		if (resultCode != RESULT_OK) {
//			return;
//		}
//
//		if (requestCode == 1) {
//			final Bundle extras = data.getExtras();
//
//			if (extras != null) {
//
//				ParseUser currentUser = ParseUser.getCurrentUser();
//
//				Bitmap bitmap = extras.getParcelable("data");
//
//				ByteArrayOutputStream stream = new ByteArrayOutputStream();
//				bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//				byte[] data1 = stream.toByteArray();
//
//				ParseFile profilePicture = new ParseFile("profilePicture",
//						data1);
//				// photo.saveInBackground();
//				currentUser.put("photo", profilePicture);
//				currentUser.saveInBackground();
//
//			}
//		}
//	}

}
