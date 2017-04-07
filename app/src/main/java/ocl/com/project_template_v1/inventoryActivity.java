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

import ocl.com.project_template_v1.DBfunctions.ListOfItems;

public class inventoryActivity extends AppCompatActivity {

    private ListOfItems mDbHelperItems;
    private ListView MyItemsView;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        Intent intent = getIntent();
        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_inventory);

        MyItemsView = (ListView) findViewById(R.id.list_of_items);

        mDbHelperItems = new ListOfItems(this);
        mDbHelperItems.open();
        GetAllItems(); // Get all records from my List of Items table

    }

    private void GetAllItems() {
        Log.i(">> MainActivity"," :: GetAllItems");

        Cursor ItemsCursor = mDbHelperItems.fetchAllListOfItems();
        startManagingCursor(ItemsCursor);
        // Cursor c = mDbHelper.rawQuery("select * from your_table_name",null);
        Log.i("Number of Records"," :: "+ItemsCursor.getCount());

        if (ItemsCursor.getCount() >= 1) {
            String[] from = new String[]{ListOfItems.item_Name};

            int[] to = new int[]{R.id.mytext2};

            SimpleCursorAdapter ListOfItems =
                    new SimpleCursorAdapter(this, R.layout.item_single_row,
                            ItemsCursor, from, to);
            MyItemsView.setAdapter(ListOfItems);
        }


    }

    public void itemEntryPage(View view) {

        Intent intent = new Intent(this, ItemEntryActivity.class);
        startActivity(intent);
    }

    public void backPage(View view) {
// nothing in the function yet .. just finish
        finish();
    }

}
