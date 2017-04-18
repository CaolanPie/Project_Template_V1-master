package ocl.com.project_template_v1;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import ocl.com.project_template_v1.DBfunctions.ListOfLists;

/**
 * Created by Caolan on 15/04/2017.
 */

public class list_edit extends AppCompatActivity {

    private ListOfLists mDbHelperLists;
    private String listName;
    private String ListDescription;
    private int listNumber;


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
        Cursor ListsCursor = mDbHelperLists.fetchListOfListsRow(listNumber);

        //Get List name from row returned
        String listName = ListsCursor.getString(ListsCursor.getColumnIndex("Name"));
        TextView nameTextView = (TextView)findViewById(R.id.nameofList);
        nameTextView.setText(listName);

        //Get List Description from row returned
        String listDescription = ListsCursor.getString(ListsCursor.getColumnIndex("Description"));
        TextView descTextView = (TextView)findViewById(R.id.description_text);
        descTextView.setText(listDescription);

        // int myListNo = ListsCursor.getInt(ListsCursor.getColumnIndex("_id"));

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
        Log.i(">> list_edit"," :: deleteRow");
        mDbHelperLists.deleteListOfListsRow(listNumber);
        finish();
    }

    /**
     * saves and updates changes to the selected row with values from the current screen
     * @param view
     */
    public void updateRow(View view) {
        String myEnteredName;
        String myEnteredDesc;

        Log.i(">> list_edit"," :: updateRow");
        TextView nameTextView = (TextView)findViewById(R.id.nameofList);
        myEnteredName = nameTextView.getText().toString();

        TextView descTextView = (TextView)findViewById(R.id.description_text);
        myEnteredDesc = descTextView.getText().toString();

        mDbHelperLists.updateListOfListsRow(listNumber, myEnteredName, myEnteredDesc,
                "Edited"); //Last one is wrong
        finish();
    }

}
