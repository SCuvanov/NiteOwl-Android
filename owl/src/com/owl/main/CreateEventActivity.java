package com.owl.main;

import java.io.ByteArrayOutputStream;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.owl.model.Event;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class CreateEventActivity extends FragmentActivity implements
		OnClickListener {

	ImageButton btnBack;
	ImageButton btnCreatePic;
	ImageButton btnConfirm;
	ImageButton btnAddFriend;
	private Dialog progressDialog;

	private static int RESULT_LOAD_IMAGE = 2;
	private static int REQUEST_CODE = 999;

	EditText etDesc;
	EditText etTitle;
	Button btTime;
	Button btDate;
	ParseImageView ivCreate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_event);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		btnBack = (ImageButton) findViewById(R.id.btn_back1);
		btnBack.setOnClickListener(this);

		// btnCreatePic = (ImageButton) findViewById(R.id.btn_create_pic);
		// btnCreatePic.setOnClickListener(this);

		btnConfirm = (ImageButton) findViewById(R.id.btn_confirm);
		btnConfirm.setOnClickListener(this);

		btnAddFriend = (ImageButton) findViewById(R.id.btn_friendchooser);
		btnAddFriend.setOnClickListener(this);

		ivCreate = (ParseImageView) findViewById(R.id.createImageView);

		// edit text
		etDesc = (EditText) findViewById(R.id.editTextDesc);
		etTitle = (EditText) findViewById(R.id.editTextTitle);
		btTime = (Button) findViewById(R.id.btn_time);
		btDate = (Button) findViewById(R.id.btn_date);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_event, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case (R.id.btn_back1):

			finish();
			overridePendingTransition(0, 0);
			break;

		case (R.id.btn_confirm):
			String title;
			String desc;
			String time;
			String date;

			title = etTitle.getText().toString();
			desc = etDesc.getText().toString();
			time = btTime.getText().toString();
			date = btDate.getText().toString();

			// getting image
			Drawable drawable = ivCreate.getDrawable();
			Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

			byte[] data = stream.toByteArray();
			ParseFile photo = new ParseFile("eventPhoto", data);

			try {
				if (title.isEmpty() || desc.isEmpty() || time.equals("Time")
						|| time.equals("Set Time") || date.equals("Date")
						|| date.equals("Set Date")) {

					if (title.isEmpty()) {
						etTitle.setHintTextColor(getResources().getColor(
								R.color.red));
						etTitle.setHint("Enter Title");
					}
					if (desc.isEmpty()) {
						etDesc.setHintTextColor(getResources().getColor(
								R.color.red));
						etDesc.setHint("Enter Description");
					}
					if (time.equals("Time") || time.equals("Set Time")) {
						btTime.setTextColor(getResources()
								.getColor(R.color.red));
						btTime.setText("Set Time");
					}
					if (date.equals("Date") || date.equals("Set Date")) {
						btDate.setTextColor(getResources()
								.getColor(R.color.red));
						btDate.setText("Set Date");
					}

				}
			} finally {
				if (!title.isEmpty() && !desc.isEmpty() && !time.equals("Time")
						&& !time.equals("Set Time") && !date.equals("Date")
						&& !date.equals("Set Date")) {

					// define event object
					Event newEvent = new Event();
					newEvent.setTitle(title);
					newEvent.setDesc(desc);
					newEvent.setTime(time);
					newEvent.setDate(date);
					newEvent.setPhotoFile(photo);
					newEvent.setUser(ParseUser.getCurrentUser());

					// create access control list, and set object to read-only
					ParseACL acl = new ParseACL();
					acl.setPublicReadAccess(true);
					acl.setPublicWriteAccess(true); // objects created are
													// writable
					newEvent.setACL(acl);

					CreateEventActivity.this.progressDialog = ProgressDialog
							.show(CreateEventActivity.this, "",
									"Roasting Marshmallows...", true);

					// publish to ParseDB
					newEvent.saveInBackground(new SaveCallback() {
						@Override
						public void done(ParseException e) {
							// Update the display
							showPrimaryActivity();
							progressDialog.dismiss();
						}
					});

				}
			}

			break;

		case R.id.btn_friendchooser:

			Intent intent = new Intent(this, InviteFriendsActivity.class);
			startActivity(intent);

		}
	}

	public void showTimePickerDialog(View v) {
		DialogFragment newFragment = new TimePickerFragment();
		newFragment.show(getFragmentManager(), "timePicker");
	}

	public void showDatePickerDialog(View v) {
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getFragmentManager(), "datePicker");
	}

	public void showPicturePickerDialog(View v) {
		DialogFragment newFragment = new PicturePickerFragment();
		newFragment.show(getFragmentManager(), "picturePicker");
	}

	private void showPrimaryActivity() {
		Intent intent = new Intent(this, NaviActivity.class);
		startActivity(intent);
		finish();
	}

}
