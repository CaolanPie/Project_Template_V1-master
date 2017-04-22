package ocl.com.project_template_v1;

import android.app.DatePickerDialog;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Locale;

/**
 * Created by Caolán on 19/04/2017.
 */

class setDate implements View.OnFocusChangeListener, DatePickerDialog.OnDateSetListener {

    private EditText editText;
    private Calendar myCalendar;
    private Context myContext;

    public setDate(EditText editText, Context ctx){
        Log.i(">> setDate"," :: setDate OnCreate");
        this.editText = editText;
        this.editText.setOnFocusChangeListener(this);
        myContext = ctx;
        myCalendar = Calendar.getInstance();
    }

    /**
     * This is triggered on the date being set
     * @param view
     * @param year
     * @param monthOfYear
     * @param dayOfMonth
     */
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
// this.editText.setText();
        Log.i(">> setDate"," :: onDateSet");

        String myFormat = "MMM dd, yyyy"; //In which you need put here
        SimpleDateFormat sdformat = new SimpleDateFormat(myFormat, Locale.US);
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        editText.setText(sdformat.format(myCalendar.getTime()));
    }

    /**
     * This is triggered on the change of focus
     * @param v
     * @param hasFocus
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
// TODO Auto-generated method stub
        Log.i(">> setDate"," :: onFocusChange");
        if(hasFocus){
            new DatePickerDialog(myContext, this, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    }

}

