package com.example.owl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by Sean on 6/11/2014.
 */

public class SignUpActivity extends Activity implements OnClickListener {

	EditText etUsername;
	EditText etEmail;
	EditText etPassword;
	EditText etPassword2;

	Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		context = this;

		findViewById(R.id.btn_back2).setOnClickListener(this);
		findViewById(R.id.btn_confirm2).setOnClickListener(this);

		etUsername = (EditText) findViewById(R.id.editTextUsername);
		etEmail = (EditText) findViewById(R.id.editTextEmail);
		etPassword = (EditText) findViewById(R.id.editTextPassword);
		etPassword2 = (EditText) findViewById(R.id.editTextPassword2);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back2:
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			break;

		case R.id.btn_confirm2:

			String username = etUsername.getText().toString().toLowerCase()
					.trim();
			String email = etEmail.getText().toString().trim();
			String password = etPassword.getText().toString().trim();
			String password2 = etPassword2.getText().toString().trim();

			// //////Parse///////

			ParseUser user = new ParseUser();

			// ////TO DO//////
			// - CHECK PASSWORDS MATCH //

			try {
				if (username.isEmpty() || email.isEmpty() || password.isEmpty()
						|| password2.isEmpty()) {
					if (username.isEmpty()) {
						etUsername.setHintTextColor(getResources().getColor(
								R.color.red));
						etUsername.setHint("Enter Username");
					}
					if (email.isEmpty()) {
						etEmail.setHintTextColor(getResources().getColor(
								R.color.red));
						etEmail.setHint("Enter Email");
					}
					if (password.isEmpty()) {
						etPassword.setHintTextColor(getResources().getColor(
								R.color.red));
						etPassword.setHint("Enter Password");
					}
					if (password2.isEmpty()) {
						etPassword2.setHintTextColor(getResources().getColor(
								R.color.red));
						etPassword2.setHint("Re-Type Password");
					}
				}

			} finally {
				if (!username.isEmpty() && !email.isEmpty()
						&& !password.isEmpty() && !password2.isEmpty()
						&& isMatching(password, password2)) {
					user.setUsername(username);
					user.setPassword(password);
					user.setEmail(email);

					user.signUpInBackground(new SignUpCallback() {
						public void done(ParseException e) {
							if (e == null) {
								// Hooray! Let them use the app now.
								showPrimaryActivity();
								finish();

							} else {
								Log.e("INVALID EMAIL", "INVALID EMAIL");
								// Sign up didn't succeed. Look at the
								// ParseException
								// to figure out what went wrong
								etEmail.getText().clear();
								etEmail.setHintTextColor(getResources()
										.getColor(R.color.red));
								etEmail.setHint("Invalid Email");

							}
						}
					});
				}
				else if (!isMatching(password, password2)){
					etPassword2.getText().clear();
					etPassword2.setHintTextColor(getResources()
							.getColor(R.color.red));
					etPassword2.setHint("Passwords don't match");
					
				}
			}
			break;
		}
	}

	private void showPrimaryActivity() {
		Intent intent = new Intent(context, NaviActivity.class);
		startActivity(intent);
	}

	private boolean isMatching(String pass1, String pass2) {
		if (pass1.equals(pass2)) {
			return true;
		} else {
			return false;
		}
	}
}