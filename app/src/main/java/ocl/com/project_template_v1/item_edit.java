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
import android.widget.TextView;

import java.sql.RowId;
import java.util.Locale;

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
    private int ourTargetRowID;

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
        TextView warrantyTextView = (TextView)findViewById(R.id.warrenty_checkbox);
        warrantyTextView.setText(warranty);

        //Get the date of warranty expiration
        String warrantyDate = ItemsCursor.getString(ItemsCursor.getColumnIndex("WarrantyExpiration"));
        TextView warrantyDTextView = (TextView)findViewById(R.id.warranty_date);
        warrantyDTextView.setText(warrantyDate);

        // int myItemNo = ItemsCursor.getInt(ItemsCursor.getColumnIndex("_id"));

    }

    private String convertDateFromSQLLite( String dateToConvert) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(dateToConvert);
    }

    public void scanPage(View view) {
        Log.i(">> item_edit"," :: scanPage");
        Intent intent = new Intent(this, BarcodeCaptureActivity.class);
        startActivity(intent);
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
        //Log.i(">> item_edit"," :: deleteRow");
        //mDbHelperItems.deleteListOfItemsRow(itemNumber);
        //finish();
        // ourTargetRowID = rowId;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder.setTitle("Delete Item");

        // set dialog message
        alertDialogBuilder
                .setMessage("Are you sure you want to delete this item?")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        Log.i(">> ListOfItems"," :: delete = yes");
                        // if this button is clicked
                        // we have a confirmation so delete record
                        mDbHelperItems.deleteListOfItemsRow(itemNumber);
                        //view.finish();
                        finish();
                        dialog.cancel();

                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        Log.i(">> ListOfItems"," :: delete = no");
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

        TextView warrantyTextView = (TextView)findViewById(R.id.warrenty_checkbox);
        myEnteredWarranty = warrantyTextView.getText().toString();

        TextView warrantyDTextView = (TextView)findViewById(R.id.warranty_date);
        myEnteredWarrantyDate = warrantyDTextView.getText().toString();

        //mDbHelperItems.updateListOfItemsRow(the_key, myEnteredListNo, myEnteredItemNo, myEnteredName, myEnteredSerial,
                //myEnteredDate, myEnteredWarranty, myEnteredWarrantyDate); //Last three are wrong

        mDbHelperItems.updateListOfItemsRow(the_key, myEnteredListNo, myEnteredItemNo, myEnteredName, myEnteredSerial,
                null, myEnteredWarranty, null);
        finish();
    }

}
