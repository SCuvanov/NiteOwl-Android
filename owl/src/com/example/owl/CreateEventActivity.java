package com.example.owl;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.owl.model.Event;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class CreateEventActivity extends FragmentActivity implements
		OnClickListener {

	ImageButton btnBack;
	ImageButton btnCreatePic;
	ImageButton btnConfirm;

	private static int RESULT_LOAD_IMAGE = 2;

	EditText etDesc;
	EditText etTitle;
	Button btTime;
	Button btDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_event);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


		btnBack = (ImageButton) findViewById(R.id.btn_back1);
		btnBack.setOnClickListener(this);

		btnCreatePic = (ImageButton) findViewById(R.id.btn_create_pic);
		btnCreatePic.setOnClickListener(this);

		btnConfirm = (ImageButton) findViewById(R.id.btn_confirm);
		btnConfirm.setOnClickListener(this);

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
			break;

		case (R.id.btn_create_pic):

			Intent intent = new Intent();
			intent.setType("image/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(
					Intent.createChooser(intent, "Select Picture"),
					RESULT_LOAD_IMAGE);
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

			// define event object
			Event newEvent = new Event();
			newEvent.setTitle(title);
			newEvent.setDesc(desc);
			newEvent.setTime(time);
			newEvent.setDate(date);
			newEvent.setUser(ParseUser.getCurrentUser());

			// create access control list, and set object to read-only
			ParseACL acl = new ParseACL();
			acl.setPublicReadAccess(true);
			newEvent.setACL(acl);

			// publish to ParseDB
			newEvent.saveInBackground(new SaveCallback() {
				@Override
				public void done(ParseException e) {
					// Update the display 
					showPrimaryActivity();
				}
			});

			// Log.e("Title", etTitle.getText().toString());
			// Log.e("Desc", etDesc.getText().toString());
			// Log.e("Time", etTime.getText().toString());
			break;
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
	
	private void showPrimaryActivity() {
		Intent intent = new Intent(this, NaviActivity.class);
		startActivity(intent);
		finish();
	}

}
