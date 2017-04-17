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
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import ocl.com.project_template_v1.DBfunctions.ListOfLists;

public class listsActivity extends AppCompatActivity {

    private ListOfLists mDbHelperLists;
    private ListView MyListsView;
    private String currentList;
    private int currentListNo;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        Log.i(">> listsActivity"," :: onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);

        Intent intent = getIntent();
        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_lists);

        MyListsView = (ListView) findViewById(R.id.list_of_lists);

        mDbHelperLists = new ListOfLists(this);
        mDbHelperLists.open();
        GetAllLists();  // Get all records from my List of Lists table


        MyListsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
              int position, long id)
            {
                Log.i(">> listsActivity"," :: onItemClick");
               // ... do something based on position ...
                //Intent intent = new Intent(this, list_edit.class);
                //putExtraData();

                Intent list_edit_intent = new Intent(listsActivity.this, list_edit.class);
                currentListNo = (int)id; //fix this later
                list_edit_intent.putExtra("selectedList", "fred");
                list_edit_intent.putExtra("selectedListNo", currentListNo);
                startActivity(list_edit_intent);

                Toast.makeText(getApplicationContext(), "We chose " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();

            }
        });
    }

    private void GetAllLists() {
        Log.i(">> listsActivity"," :: GetAllLists");

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
        Log.i(">> listsActivity"," :: listEntryPage");
        Intent intent = new Intent(this, ListEntryActivity.class);
        startActivity(intent);
    }

    public void onResume(){
        Log.i(">> listsActivity"," :: onResume");
        super.onResume();
        GetAllLists();
    }

    public void backPage(View view) {
        Log.i(">> listsActivity"," :: backPage");
// nothing in the function yet .. just finish
        finish();
    }

}
