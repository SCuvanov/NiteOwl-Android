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

import com.parse.Parse;
import com.parse.ParseObject;


public class CreateEventActivity extends FragmentActivity implements
        OnClickListener {

    ImageButton btnBack;
    ImageButton btnCreatePic;
    ImageButton btnConfirm;

    private static int RESULT_LOAD_IMAGE = 2;


    EditText etDesc;
    EditText etTitle;
    Button etTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ///////PARSE//////
        Parse.initialize(this, "84A4VkKcDQCNDZWeCtIx62TtkoGufWUWlgEBNcR6", "NRg7OnjI3yqsGapBJ4ZczfkQEkCWd4Dqc9ufDpnV");

        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();
        //////////////////////////////////////////////////////

        btnBack = (ImageButton) findViewById(R.id.btn_back1);
        btnBack.setOnClickListener(this);

        btnCreatePic = (ImageButton) findViewById(R.id.btn_create_pic);
        btnCreatePic.setOnClickListener(this);

        btnConfirm = (ImageButton) findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(this);

        // edit text
        etDesc = (EditText) findViewById(R.id.editTextDesc);
        etTitle = (EditText) findViewById(R.id.editTextTitle);
        etTime = (Button) findViewById(R.id.btn_time);

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

                title = etTitle.getText().toString();
                desc = etDesc.getText().toString();
                time = etTime.getText().toString();

                //Log.e("Title", etTitle.getText().toString());
                //Log.e("Desc", etDesc.getText().toString());
                //Log.e("Time", etTime.getText().toString());
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

}
