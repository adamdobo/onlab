package hu.adamdobo.onlabproject.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by Ádám on 3/13/2018.
 */

public class DatePickerDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public static final String TAG = "DatePickerDialogFragment";
    private OnDateSelectedListener onDateSelectedListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void setOnDateSelectedListener(OnDateSelectedListener listener){
        onDateSelectedListener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        onDateSelectedListener.onDateSelected(year, month, dayOfMonth);
    }

    public interface OnDateSelectedListener {
        void onDateSelected(int year, int month, int day);
    }
}
