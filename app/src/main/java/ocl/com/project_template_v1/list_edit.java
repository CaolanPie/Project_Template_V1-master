package ocl.com.project_template_v1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.StringBufferInputStream;
import java.util.Date;
import java.util.Locale;

import ocl.com.project_template_v1.DBfunctions.ListOfItems;
import ocl.com.project_template_v1.DBfunctions.ListOfLists;

/**
 * Created by Caolan on 15/04/2017.
 */

public class list_edit extends AppCompatActivity {

    private ListOfLists mDbHelperLists;
    private ListOfItems mDbHelperItems;
    private String listName;
    private String ListDescription;
    private int listNumber;
    private int ourTargetRowID;
    private String dateCreated;
    private String lastEdited;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        Log.i(">> list_edit"," :: onCreate");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_edit);

        Intent intent = getIntent();

        listName = intent.getStringExtra("selectedList");
        TextView mTextView = (TextView) findViewById(R.id.nameofList);
        mTextView.setText(listName);
        listNumber = intent.getIntExtra("selectedListNo", 0);

        ViewGroup layout = (ViewGroup) findViewById(R.id.list_edit);

        mDbHelperLists = new ListOfLists(this);
        mDbHelperLists.open();
        mDbHelperItems = new ListOfItems(this);
        mDbHelperItems.open();
        Cursor ListsCursor = mDbHelperLists.fetchListOfListsRow(listNumber);

        //Get List name from row returned
        String listName = ListsCursor.getString(ListsCursor.getColumnIndex("Name"));
        TextView nameTextView = (TextView)findViewById(R.id.nameofList);
        nameTextView.setText(listName);

        //Get List Description from row returned
        String ListDescription = ListsCursor.getString(ListsCursor.getColumnIndex("Description"));
        TextView descTextView = (TextView)findViewById(R.id.description_text);
        descTextView.setText(ListDescription);

        String dateCreated = ListsCursor.getString(ListsCursor.getColumnIndex("DateCreated"));
        EditText dateTextView = (EditText)findViewById(R.id.date_created);
        //setDate fromDate = new setDate(dateTextView, this);
        dateTextView.setText( dateCreated );

        String lastEdited = ListsCursor.getString(ListsCursor.getColumnIndex("LastEdited"));
        EditText lastEdTextView = (EditText)findViewById(R.id.Last_edited);
        //setDate fromDate2 = new setDate(lastEdTextView, this);
        lastEdTextView.setText(lastEdited);

        // int myListNo = ListsCursor.getInt(ListsCursor.getColumnIndex("_id"));

    }

    private String convertDateFromSQLLite( String dateToConvert) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(dateToConvert);
    }


    /**
     *  backPage returns to previous activity
     * @param view
     */
    public void backPage(View view) {
        Log.i(">> list_edit"," :: backPage");
        finish();
    }

    /**
     * deletes the current row
     * @param view
     */
    public void deleteRow(View view) {
        //Log.i(">> list_edit"," :: deleteRow");
        //mDbHelperLists.deleteListOfListsRow(listNumber);
        //finish();
        //ourTargetRowID = rowId;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder.setTitle("Delete List");

        // set dialog message
        alertDialogBuilder
                .setMessage("Are you sure you want to delete this list?")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        Log.i(">> ListOfLists"," :: delete = yes");
                        // if this button is clicked
                        // we have a confirmation so delete record
                        mDbHelperLists.deleteListOfListsRow(listNumber);
                        //view.finish();
                        mDbHelperItems.deleteItemsFromList(listNumber);
                        finish();
                        dialog.cancel();

                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        Log.i(">> ListOfLists"," :: delete = no");
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();

                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

        //return true; /// maybe change this later
    }

    /**
     * saves and updates changes to the selected row with values from the current screen
     * @param view
     */
    public void updateRow(View view) {
        String myEnteredName;
        String myEnteredDesc;
        String myEnteredDate;
        String myEnteredLastEdited;

        Log.i(">> list_edit"," :: updateRow");
        TextView nameTextView = (TextView)findViewById(R.id.nameofList);
        myEnteredName = nameTextView.getText().toString();

        TextView descTextView = (TextView)findViewById(R.id.description_text);
        myEnteredDesc = descTextView.getText().toString();

        TextView dateTextView = (TextView)findViewById(R.id.date_created);
        myEnteredDate = dateTextView.getText().toString();

        TextView lastEdTextView = (TextView)findViewById(R.id.Last_edited);
        myEnteredLastEdited = lastEdTextView.getText().toString();

        mDbHelperLists.updateListOfListsRow(listNumber, myEnteredName, myEnteredDesc, null, null
                ); //Last one is wrong
        finish();
    }

}
