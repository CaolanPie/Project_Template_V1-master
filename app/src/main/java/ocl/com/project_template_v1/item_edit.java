package ocl.com.project_template_v1;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.RowId;

import ocl.com.project_template_v1.DBfunctions.ListOfItems;
import ocl.com.project_template_v1.DBfunctions.ListOfLists;

import static java.lang.Byte.valueOf;

/**
 * Created by Caolán on 18/04/2017.
 */

public class item_edit extends AppCompatActivity {

    private ListOfItems mDbHelperLists;
    private ListOfItems mDbHelperItems;
    private String itemName;
    private String ItemSerial;
    private int itemNumber;
    private int listNumber;
    private int the_key;
    private String warranty;
    private String warrantyDate;
    private String datePurchased;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        Log.i(">> item_edit"," :: onCreate");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.item_edit);

        Intent intent = getIntent();

        itemName = intent.getStringExtra("selectedItem");
        TextView mTextView = (TextView) findViewById(R.id.item_name);
        mTextView.setText(itemName);
        itemNumber = intent.getIntExtra("selectedItemNo", 0);

        ViewGroup layout = (ViewGroup) findViewById(R.id.item_edit);

        mDbHelperItems = new ListOfItems(this);
        mDbHelperItems.open();
        //Cursor ListsCursor = mDbHelperItems.fetchListOfItemsRow(listNumber);

        // get the row of data we are interested in
        Cursor ItemsCursor = mDbHelperItems.fetchListOfItemsRow(itemNumber);

        //Get Item name from row returned
        String itemName = ItemsCursor.getString(ItemsCursor.getColumnIndex("ItemName"));
        TextView nameTextView = (TextView)findViewById(R.id.item_name);
        nameTextView.setText(itemName);

        //get list number
        String listNumber = ItemsCursor.getString(ItemsCursor.getColumnIndex("ListNo"));
        TextView listTextView = (TextView)findViewById(R.id.list_number);
        listTextView.setText(listNumber);

        //get item number
        String itemNumber = ItemsCursor.getString(ItemsCursor.getColumnIndex("itemNumber"));
        TextView itemNTextView = (TextView)findViewById(R.id.item_number);
        itemNTextView.setText(itemNumber);

        //Get Item Description from row returned
        String ItemSerial = ItemsCursor.getString(ItemsCursor.getColumnIndex("SerialNumber"));
        TextView serialTextView = (TextView)findViewById(R.id.serial_number);
        serialTextView.setText(ItemSerial);

        //Get date of purchase
        String datePurchased = ItemsCursor.getString(ItemsCursor.getColumnIndex("DatePurchased"));
        TextView dateTextView = (TextView)findViewById(R.id.date_purchased);
        dateTextView.setText(datePurchased);

        //Get item warranty Y/N
        String warranty = ItemsCursor.getString(ItemsCursor.getColumnIndex("Warranty"));
        TextView warrantyTextView = (TextView)findViewById(R.id.warranty);
        warrantyTextView.setText(warranty);

        //Get the date of warranty expiration
        String warrantyDate = ItemsCursor.getString(ItemsCursor.getColumnIndex("WarrantyExpiration"));
        TextView warrantyDTextView = (TextView)findViewById(R.id.warranty_date);
        warrantyDTextView.setText(warrantyDate);

        // int myItemNo = ItemsCursor.getInt(ItemsCursor.getColumnIndex("_id"));

    }

    /**
     *  backPage returns to previous activity
     * @param view
     */
    public void backPage(View view) {
        Log.i(">> item_edit"," :: backPage");
        finish();
    }

    /**
     * deletes the current row
     * @param view
     */
    public void deleteRow(View view) {
        Log.i(">> item_edit"," :: deleteRow");
        mDbHelperItems.deleteListOfItemsRow(itemNumber, view);
        //finish();
    }

    /**
     * saves and updates changes to the selected row with values from the current screen
     * @param view
     */

    public void updateRow(View view) {
        int myEnteredListNo;
        int myEnteredItemNo;
        String myEnteredName;
        String myEnteredSerial;
        String myEnteredDate;
        String myEnteredWarranty;
        String myEnteredWarrantyDate;

        Log.i(">> item_edit"," :: updateRow");

        TextView listTextView = (TextView)findViewById(R.id.list_number);
        myEnteredListNo = valueOf(listTextView.getText().toString());

        TextView itemNTextView = (TextView)findViewById(R.id.item_number);
        myEnteredItemNo = valueOf(itemNTextView.getText().toString());

        TextView nameTextView = (TextView)findViewById(R.id.item_name);
        myEnteredName = nameTextView.getText().toString();

        TextView descTextView = (TextView)findViewById(R.id.serial_number);
        myEnteredSerial = descTextView.getText().toString();

        TextView dateTextView = (TextView)findViewById(R.id.date_purchased);
        myEnteredDate = dateTextView.getText().toString();

        TextView warrantyTextView = (TextView)findViewById(R.id.warranty);
        myEnteredWarranty = warrantyTextView.getText().toString();

        TextView warrantyDTextView = (TextView)findViewById(R.id.warranty_date);
        myEnteredWarrantyDate = warrantyDTextView.getText().toString();

        mDbHelperItems.updateListOfItemsRow(the_key, myEnteredListNo, myEnteredItemNo, myEnteredName, myEnteredSerial,
                myEnteredDate, myEnteredWarranty, myEnteredWarrantyDate); //Last three are wrong
        finish();
    }

}
