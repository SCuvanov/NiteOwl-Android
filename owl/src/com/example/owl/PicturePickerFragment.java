package com.example.owl;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.owl.adapter.PictureAdapter;
import com.example.owl.model.PictureItem;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import com.parse.ParseImageView;

public class PicturePickerFragment extends DialogFragment {

	ListView listView;
	ArrayList<PictureItem> pictureItems = new ArrayList<PictureItem>();

	// A String[] array that will hold the names of the items.
	public static final String[] descriptions = { "BBQ", "Breakfast", "Camping", "Cocktails", "Dinner", "Drinks", "Lunch", "Party", "Meeting",
			"Restaurant", "Birthday", "Cocktails", "Drinks", "Party", "Meeting",
			"Birthday"};
	public static final Integer[] images = { R.drawable.bbq, R.drawable.breakfast, R.drawable.camping, R.drawable.cocktail, R.drawable.dinner, R.drawable.beer, R.drawable.lunch, R.drawable.party,
		R.drawable.meeting, R.drawable.restaurant, R.drawable.birthday, R.drawable.cocktail, R.drawable.beer, R.drawable.party,
		R.drawable.meeting, R.drawable.birthday };

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		LayoutInflater layoutInflater = getActivity().getLayoutInflater();

		View convertView = (View) layoutInflater.inflate(
				R.layout.picture_picker_dialog_view, null);

		// defining the alertdialog
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(convertView);

		// populating the array list
		for (int i = 0; i < descriptions.length; i++) {
			PictureItem item = new PictureItem(images[i], descriptions[i]);
			pictureItems.add(item);
		}

		// defining listview and using array adapter
		listView = (ListView) convertView.findViewById(R.id.listViewFragment2);
		PictureAdapter adapter = new PictureAdapter(getActivity(), pictureItems);
		
		SwingBottomInAnimationAdapter animAdapter = new SwingBottomInAnimationAdapter(adapter);
		animAdapter.setAbsListView(listView);
		
		
		listView.setAdapter(animAdapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {

				int temp_resId = images[position];
				String resId = Integer.toString(temp_resId);
				Drawable myDrawable = getResources().getDrawable(temp_resId);

				ParseImageView imageView = (ParseImageView) getActivity()
						.findViewById(R.id.createImageView);
				imageView.setImageDrawable(myDrawable);

				getDialog().dismiss();

			}

		});

		adapter.addAll(pictureItems);

		return builder.create();

	}

}
