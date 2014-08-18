package com.owl.main;

import java.util.List;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import com.owl.adapter.UserQueryAdapter;
import com.parse.ParseUser;

public class FriendSearchActivity extends Activity implements OnClickListener {

	ImageButton btnBack, btnConfirm;
	EditText etSearch;

	ListView listView;
	List<ParseUser> lstFriends;

	private UserQueryAdapter userQueryAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend_search);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		// get list view
		listView = (ListView) findViewById(R.id.listView1);

		btnBack = (ImageButton) findViewById(R.id.btn_back2);
		btnBack.setOnClickListener(this);
		btnConfirm = (ImageButton) findViewById(R.id.btn_confirm2);
		btnConfirm.setOnClickListener(this);

		etSearch = (EditText) findViewById(R.id.editTextSearch);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case (R.id.btn_back2):

			finish();
			break;

		case (R.id.btn_confirm2):

			final String searchCriteria = etSearch.getText().toString()
					.toLowerCase().trim();

			// ///// PARSE QUERIES ////////
			userQueryAdapter = new UserQueryAdapter(this, searchCriteria);
			userQueryAdapter.loadObjects();

			// create animation adapter
			SwingBottomInAnimationAdapter animAdapter = new SwingBottomInAnimationAdapter(
					userQueryAdapter);
			animAdapter.setAbsListView(listView);

			listView.setAdapter(animAdapter);
			break;

		}

	}

}
