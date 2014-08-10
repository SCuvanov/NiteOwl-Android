package com.owl.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

public class MainActivity extends Activity implements OnClickListener {

	private static final String TAG = "OwlSample";

	EditText etSignInUsername;
	EditText etSignInPassword;
	EditText etForgotPassword;
	TextView tvForgotPassword;

	private Dialog progressDialog;
	private AlertDialog passwordDialog;

	Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		context = this;

		// sign in button
		findViewById(R.id.sign_in_button).setOnClickListener(this);
		findViewById(R.id.sign_up_button).setOnClickListener(this);

		// edittexts
		etSignInUsername = (EditText) findViewById(R.id.editTextSignInUsername);
		etSignInPassword = (EditText) findViewById(R.id.editTextSignInPassword);
		tvForgotPassword = (TextView) findViewById(R.id.textViewForgotPassword);

		tvForgotPassword.setOnClickListener(this);

		// Check if there is a currently logged in user
		// and they are linked to a Facebook account.
		final ParseUser currentUser = ParseUser.getCurrentUser();
		if ((currentUser != null)) {

			// you can add this line &&
			// ParseFacebookUtils.isLinked(currentUser)) to if statement
			// if you want to make sure the user is also linked to facebook
			// account
			// Go to the user info activity
			showPrimaryActivity();
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
	}

	private void showPrimaryActivity() {
		Intent intent = new Intent(context, NaviActivity.class);
		startActivity(intent);
		finish();
	}

	private void resetPassword(String email) {

		ParseUser.requestPasswordResetInBackground(email,
				new RequestPasswordResetCallback() {
					public void done(ParseException e) {
						if (e == null) {
							// An email was successfully sent with reset
							// instructions.
						} else {
							// Something went wrong. Look at the ParseException
							// to see what's up.
							Log.e("PARSE EXCEPTION:", "PARSE CAUSE:", e.getCause());
						}
					}
				});

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.sign_in_button:
			Log.e(TAG, "Tapped sign in");

			String username = etSignInUsername.getText().toString()
					.toLowerCase().trim();
			String password = etSignInPassword.getText().toString().trim();

			try {
				if (username.isEmpty() || password.isEmpty()) {
					if (username.isEmpty()) {
						etSignInUsername.setHintTextColor(getResources()
								.getColor(R.color.red));
						etSignInUsername.setHint("Enter Username");
					}
					if (password.isEmpty()) {
						etSignInPassword.setHintTextColor(getResources()
								.getColor(R.color.red));
						etSignInPassword.setHint("Enter Password");
					}

				}
			} finally {
				if (!username.isEmpty() || !password.isEmpty()) {
					ParseUser.logInInBackground(username, password,
							new LogInCallback() {
								public void done(ParseUser user,
										ParseException e) {
									if (user != null) {
										showPrimaryActivity();
										// Hooray! The user is logged in.
									} else {

										etSignInUsername.getText().clear();
										etSignInUsername
												.setHintTextColor(getResources()
														.getColor(R.color.red));
										etSignInUsername
												.setHint("Invalid Username or Pass");

										etSignInPassword.getText().clear();
										etSignInPassword
												.setHintTextColor(getResources()
														.getColor(R.color.red));
										etSignInPassword
												.setHint("Invalid Username or Pass");
										Log.e("LOGIN FAILED",
												"FAILED TO LOG IN");
										// Signup failed. Look at the
										// ParseException to see what happened.
									}
								}
							});
				}

			}
			break;

		case R.id.sign_up_button:
			Intent intent1 = new Intent(this, SignUpActivity.class);
			startActivity(intent1);
			Log.e(TAG, "Tapped sign up");
			break;

		case R.id.textViewForgotPassword:
			Log.e(TAG, "Tapped forgot password");
			// prompt user with dialog to enter email

			LayoutInflater li = LayoutInflater.from(this);
			View dialogView = li.inflate(R.layout.forgot_password_dialog_view,
					null);
			
			etForgotPassword = (EditText) dialogView.findViewById(R.id.editTextForgotPassword);

			AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
			builder1.setView(dialogView);

			builder1.setCancelable(true);
			builder1.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {

							String forgotPasswordEmail = etForgotPassword
									.getText().toString().trim();
							resetPassword(forgotPasswordEmail);
							

						}
					});
			builder1.setNegativeButton("No",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});

			AlertDialog alertDialog = builder1.create();
			alertDialog.show();

			Button nButton = alertDialog
					.getButton(DialogInterface.BUTTON_NEGATIVE);
			if (nButton != null) {
				nButton.setTextSize(20);
				nButton.setBackgroundResource(R.drawable.ui_button_white);
				nButton.setTextColor(getResources().getColor(R.color.gray_35));
			}

			Button pButton = alertDialog
					.getButton(DialogInterface.BUTTON_POSITIVE);
			if (pButton != null) {
				pButton.setTextSize(20);
				pButton.setBackgroundResource(R.drawable.ui_button_white);
				pButton.setTextColor(getResources().getColor(R.color.gray_35));
			}

			break;

		}

	}
}