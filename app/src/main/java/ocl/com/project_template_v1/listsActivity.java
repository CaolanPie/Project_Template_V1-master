package ocl.com.project_template_v1;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import ocl.com.project_template_v1.DBfunctions.ListOfLists;

public class listsActivity extends AppCompatActivity {

    private ListOfLists mDbHelperLists;
    private ListView MyListsView;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);

        Intent intent = getIntent();
        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_lists);

        MyListsView = (ListView) findViewById(R.id.list_of_lists);

        mDbHelperLists = new ListOfLists(this);
        mDbHelperLists.open();
        GetAllLists();  // Get all records from my List of Lists table
    }

    private void GetAllLists() {
        Log.i(">> MainActivity"," :: GetAllLists");

        Cursor ListsCursor = mDbHelperLists.fetchAllListOfLists();
        startManagingCursor(ListsCursor);
        // Cursor c = mDbHelper.rawQuery("select * from your_table_name",null);
        Log.i("Number of List Records"," :: "+ListsCursor.getCount());


        if (ListsCursor.getCount() >= 1) {
            String[] from = new String[]{ListOfLists.KEY_Name};

            int[] to = new int[]{R.id.mytext};

            SimpleCursorAdapter ListOfLists =
                    new SimpleCursorAdapter(this, R.layout.list_single_row,
                            ListsCursor, from, to);
            MyListsView.setAdapter(ListOfLists);
        }


    }


    public void listEntryPage(View view) {

        Intent intent = new Intent(this, ListEntryActivity.class);
        startActivity(intent);
    }



    public void backPage(View view) {
// nothing in the function yet .. just finish
        finish();
    }

}
