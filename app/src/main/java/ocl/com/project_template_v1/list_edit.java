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
 * Created by Tony on 15/04/2017.
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

        /*
        MyListsView = (ListView) findViewById(R.id.list_of_lists);

        mDbHelperLists = new ListOfLists(this);
        mDbHelperLists.open();
        GetAllLists();  // Get all records from my List of Lists table

        */
        // above SQL statement translates to
        // create table ListOfLists ( _id integer primary key autoincrement,
        // 							Name text not null,
        //                          Description text not null,
        //                          DateCreated text not null,
        //							LastEdited text not null);

        mDbHelperLists = new ListOfLists(this);
        mDbHelperLists.open();
        Cursor ListsCursor = mDbHelperLists.fetchListOfListsRow(listNumber);

        //Get List name from row returned
        String listName = ListsCursor.getString(ListsCursor.getColumnIndex("Name"));
        TextView nameTextView = (TextView)findViewById(R.id.nameofList);
        nameTextView.setText(listName);

        String listDesciption = ListsCursor.getString(ListsCursor.getColumnIndex("Description"));
        TextView descTextView = (TextView)findViewById(R.id.description_text);
        descTextView.setText(listDesciption);

        // int myListNo = ListsCursor.getInt(ListsCursor.getColumnIndex("_id"));

    }

    public void backPage(View view) {
        Log.i(">> list_edit"," :: backPage");
// nothing in the function yet .. just finish
        finish();
    }

    public void deleteRow(View view) {
        Log.i(">> list_edit"," :: deleteRow");
// nothing in the function yet .. just finish
        mDbHelperLists.deleteListOfListsRow(listNumber);
        finish();
    }

}
