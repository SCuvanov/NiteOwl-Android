package com.example.owl;

import java.util.Calendar;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.Button;
import android.widget.TimePicker;

public class TimePickerFragment extends DialogFragment implements
        TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user

        String time;

        if (hourOfDay >= 12) {
            if (hourOfDay == 12) {
                time = String.format(("12") + ":" + convertMinute(minute) + "PM");
                Button b = (Button) getActivity().findViewById(R.id.btn_time);
                b.setText(time);

            } else {
                time = String.format((hourOfDay - 12) + ":" + convertMinute(minute) + "PM");
                Button b = (Button) getActivity().findViewById(R.id.btn_time);
                b.setText(time);
            }
        } else {
            if (hourOfDay == 0) {
                time = String.format(("12") + ":" + convertMinute(minute) + "AM");
                Button b = (Button) getActivity().findViewById(R.id.btn_time);
                b.setText(time);
            } else {
                time = String.format((hourOfDay) + ":" + convertMinute(minute) + "AM");
                Button b = (Button) getActivity().findViewById(R.id.btn_time);
                b.setText(time);
            }
        }


    }

    public String convertMinute(int minute) {
        String nMinute;

        if (minute < 10) {
            nMinute = String.format("0" + minute);
            return nMinute;
        } else {
            nMinute = String.format("" + minute);
            return nMinute;
        }
    }
}
