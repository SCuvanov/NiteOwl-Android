package com.owl.main;

import java.util.List;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.owl.adapter.FriendQueryAdapter;
import com.owl.adapter.UserQueryAdapter;
import com.parse.ParseUser;

public class InviteFriendsActivity extends Activity implements OnClickListener,
		OnItemClickListener {

	ImageButton btnBack, btnConfirm;
	ImageView ivAdd;

	ListView listView;
	List<ParseUser> lstFriends;

	private FriendQueryAdapter friendQueryAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invite_friends);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		ParseUser currentUser = ParseUser.getCurrentUser();

		// get list view
		listView = (ListView) findViewById(R.id.listViewIF);
		listView.setOnItemClickListener(this);

		btnBack = (ImageButton) findViewById(R.id.btn_backIF);
		btnBack.setOnClickListener(this);
		btnConfirm = (ImageButton) findViewById(R.id.btn_confirmIF);
		btnConfirm.setOnClickListener(this);
		
		
		//create relation adapter / listview
		
//		ParseRelation relation = currentUser.getRelation("Friends");
//		ParseQuery query = relation.getQuery();
		
		friendQueryAdapter = new FriendQueryAdapter(this, currentUser);
		friendQueryAdapter.loadObjects();
		
		listView.setAdapter(friendQueryAdapter);
		
		

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case (R.id.btn_backIF):

			finish();
			overridePendingTransition(0, 0);
			break;

		case (R.id.btn_confirmIF):

			// ///// PARSE QUERIES ////////
//			userQueryAdapter = new UserQueryAdapter(this, searchCriteria);
//			userQueryAdapter.loadObjects();

			// create animation adapter
//			SwingBottomInAnimationAdapter animAdapter = new SwingBottomInAnimationAdapter(
//					userQueryAdapter);
//			animAdapter.setAbsListView(listView);
//
//			listView.setAdapter(animAdapter);
			break;

		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		// invite the users to the event


	}
}
