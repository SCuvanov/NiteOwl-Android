package com.example.owl;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

public class AlertDialogFragment extends DialogFragment {


	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		LayoutInflater inflater = getActivity().getLayoutInflater();
		View convertView = (View) inflater.inflate(R.layout.dialog_view, null);

		// defining the alertdialog
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(convertView);
		builder.setTitle(R.string.forgot_password);
		
		
		
		
		


		return builder.create();
	}
	
	
}
