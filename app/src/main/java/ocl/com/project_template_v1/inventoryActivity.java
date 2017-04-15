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
import android.widget.TextView;

import ocl.com.project_template_v1.DBfunctions.ListOfItems;

public class inventoryActivity extends AppCompatActivity {

    private ListOfItems mDbHelperItems;
    private ListView MyItemsView;
    private String targetListname;
    private int myListNumber;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        Log.i(">> inventorActivity"," :: onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        Intent intent = getIntent();

        //String firstKeyName = myIntent.getStringExtra("firstKeyName"); // will return "FirstKeyValue"
        targetListname = intent.getStringExtra("selectedList");
        TextView mTextView = (TextView) findViewById(R.id.nameofList);
        mTextView.setText(targetListname);
        myListNumber = intent.getIntExtra("selectedListNo", 0);


        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_inventory);

        MyItemsView = (ListView) findViewById(R.id.list_of_items);

        mDbHelperItems = new ListOfItems(this);
        mDbHelperItems.open();
        GetAllItems(myListNumber); // Get all records from my List of Items table
    }

    /**
     * GetAllItems for our selected inventory list number
     *
     * @param myListNo - the number of my list to get items from
     */
    private void GetAllItems(int myListNo) {
        Log.i(">> inventorActivity"," :: GetAllItems");

        Cursor ItemsCursor = mDbHelperItems.fetchAllListOfItems(myListNo);
        startManagingCursor(ItemsCursor);
        // Cursor c = mDbHelper.rawQuery("select * from your_table_name",null);
        Log.i("Number of Item Records"," :: "+ItemsCursor.getCount());

        if (ItemsCursor.getCount() >= 1) {
            String[] from = new String[]{ListOfItems.item_Name};
            //String[] from = new String[]{ListOfItems.the_Key};

            int[] to = new int[]{R.id.mytext2};

            SimpleCursorAdapter ListOfItems =
                    new SimpleCursorAdapter(this, R.layout.item_single_row,
                            ItemsCursor, from, to);
            MyItemsView.setAdapter(ListOfItems);
        }


    }

    public void itemEntryPage(View view) {
        Log.i(">> inventorActivity"," :: itemEntryPage");

        Intent intent = new Intent(this, ItemEntryActivity.class);
        intent.putExtra("selectedList", targetListname);
        intent.putExtra("selectedListNo", myListNumber);
        startActivity(intent);
    }

    public void onResume(){
        Log.i(">> inventorActivity"," :: onResume");
        super.onResume();
        GetAllItems(myListNumber);
    }

    public void backPage(View view) {
        Log.i(">> inventorActivity"," :: backPage");
// nothing in the function yet .. just finish
        finish();
    }

}
