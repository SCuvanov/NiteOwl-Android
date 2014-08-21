package com.owl.main;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class EditProfileActivity extends Activity implements OnClickListener {

	EditText etBio, etTagLine, etPass, etPass2;
	ImageButton btnConfirmEP, btnBack;

	final ParseUser currentUser = ParseUser.getCurrentUser();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editprofile);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		etBio = (EditText) findViewById(R.id.editTextBio);
		etTagLine = (EditText) findViewById(R.id.editTextTagLine);

		etPass = (EditText) findViewById(R.id.editTextChangePassword);
		etPass2 = (EditText) findViewById(R.id.editTextChangePassword2);

		btnConfirmEP = (ImageButton) findViewById(R.id.btn_confirmEP);
		btnConfirmEP.setOnClickListener(this);

		// btnChangePic = (Button) findViewById(R.id.btn_change_picture);
		// btnChangePic.setOnClickListener(this);

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
				String bio = etBio.getText().toString().trim();
				String tagLine = etTagLine.getText().toString().trim();
				String password = etPass.getText().toString().trim();
				String password2 = etPass2.getText().toString().trim();

				if (!bio.isEmpty()) {
					currentUser.put("bio", bio);
				}
				if (!tagLine.isEmpty()) {
					currentUser.put("tagline", tagLine);
				}
				if (!password.isEmpty() && isMatching(password, password2)) {
					currentUser.setPassword(password);

				}

				currentUser.saveInBackground(new SaveCallback() {
					public void done(ParseException e) {
						if (e != null) {

						} else {

							Context context = getApplicationContext();
							CharSequence text = "Profile Updated!";
							int duration = Toast.LENGTH_SHORT;

							Toast toast = Toast.makeText(context, text,
									duration);
							toast.show();

							finish();

						}
					}
				});

			}

		case R.id.btn_backEP:

			finish();
			overridePendingTransition(0, 0);
			break;

		}

	}

	private boolean isMatching(String pass1, String pass2) {
		if (pass1.equals(pass2)) {
			return true;
		} else {
			return false;
		}
	}

}
