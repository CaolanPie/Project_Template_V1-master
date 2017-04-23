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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import java.sql.RowId;
import java.util.Locale;

import ocl.com.project_template_v1.DBfunctions.ListOfItems;
import ocl.com.project_template_v1.DBfunctions.ListOfLists;

import static java.lang.Byte.valueOf;

/**
 * Created by Caolán on 18/04/2017.
 */

public class item_edit extends AppCompatActivity {

    private ListOfLists mDbHelperLists;
    private ListOfItems mDbHelperItems;
    private String itemName;
    private String ItemSerial;
    private int itemNumber;
    private int listNumber;
    private int the_key;
    private int warranty;
    private String warrantyDate;
    private String datePurchased;
    private float PurchasePrice;
    private int PortableItem;
    private int ourTargetRowID;

    private static final int RC_BARCODE_CAPTURE = 9001;

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
        EditText dateTextView = (EditText)findViewById(R.id.date_purchased);
        setDate fromDate = new setDate(dateTextView, this);
        dateTextView.setText(datePurchased);

        String PurchasePrice = ItemsCursor.getString(ItemsCursor.getColumnIndex("PurchasePrice"));
        TextView purchaseTextView = (TextView)findViewById(R.id.purchase_price);
        purchaseTextView.setText(PurchasePrice);

        int PortableItem = ItemsCursor.getInt(ItemsCursor.getColumnIndex("PortableItem"));
        CheckBox portableCheckBox = (CheckBox)findViewById(R.id.portable_checkbox);
        if(PortableItem != 0){
            portableCheckBox.setChecked(true);
        } else {
            portableCheckBox.setChecked(false);
        }

        //Get item warranty Y/N
        int warranty = ItemsCursor.getInt(ItemsCursor.getColumnIndex("Warranty"));
        CheckBox warrantyCheckBox = (CheckBox)findViewById(R.id.warrenty_checkbox);
        if(warranty != 0){
            warrantyCheckBox.setChecked(true);
        } else {
            warrantyCheckBox.setChecked(false);
        }

        //Get the date of warranty expiration
        String warrantyDate = ItemsCursor.getString(ItemsCursor.getColumnIndex("WarrantyExpiration"));
        EditText warrantyDTextView = (EditText)findViewById(R.id.warranty_date);
        setDate fromDate2 = new setDate(warrantyDTextView, this);
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
        //Intent intent = new Intent(this, BarcodeCaptureActivity.class);
        //startActivity(intent);
        Intent intent = new Intent(this, BarcodeCaptureActivity.class);
        intent.putExtra(BarcodeCaptureActivity.AutoFocus, true);
        intent.putExtra(BarcodeCaptureActivity.UseFlash, false);

        startActivityForResult(intent, RC_BARCODE_CAPTURE);
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
     * Called when an activity you launched exits, giving you the requestCode
     * you started it with, the resultCode it returned, and any additional
     * data from it.  The <var>resultCode</var> will be
     * {@link #RESULT_CANCELED} if the activity explicitly returned that,
     * didn't return any result, or crashed during its operation.
     * <p/>
     * <p>You will receive this call immediately before onResume() when your
     * activity is re-starting.
     * <p/>
     *
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode  The integer result code returned by the child activity
     *                    through its setResult().
     * @param data        An Intent, which can return result data to the caller
     *                    (various data can be attached to Intent "extras").
     * @see #startActivityForResult
     * @see #createPendingResult
     * @see #setResult(int)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        TextView serialTextView = (TextView)findViewById(R.id.serial_number);
        Log.i(">> item_edit", "onActivityResult");
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    serialTextView.setText(barcode.displayValue);
                    Toast.makeText(item_edit.this,
                            "Serial Number Added ",
                            Toast.LENGTH_SHORT).show();
                    //statusMessage.setText(R.string.barcode_success);
                    //barcodeValue.setText(barcode.displayValue);
                    //Log.d(TAG, "Barcode read: " + barcode.displayValue);
                } else {
                    //statusMessage.setText(R.string.barcode_failure);
                    //Log.d(TAG, "No barcode captured, intent data is null");
                }
            } else {
                //statusMessage.setText(String.format(getString(R.string.barcode_error),
                //        CommonStatusCodes.getStatusCodeString(resultCode)));
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
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

        // set dialog message to make sure delete is needed
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
        float myEnteredPurchasePrice;
        int myEnteredPortable;
        int myEnteredWarranty;
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

        TextView purchaseTextView = (TextView)findViewById(R.id.purchase_price);
        myEnteredPurchasePrice = Float.valueOf(purchaseTextView.getText().toString());

        CheckBox portableTextView = (CheckBox)findViewById(R.id.portable_checkbox);
        // find out if we indicated this item as portable
        // that is can be taken out of the house
        if(portableTextView.isChecked()) {
            myEnteredPortable = 1;
        } else {
            myEnteredPortable = 0;
        }

        CheckBox warrantyTextView = (CheckBox)findViewById(R.id.warrenty_checkbox);
        // find out if we indicated this item as portable
        // that is can be taken out of the house
        if(warrantyTextView.isChecked()) {
            myEnteredWarranty = 1;
        } else {
            myEnteredWarranty = 0;
        }

        TextView warrantyDTextView = (TextView)findViewById(R.id.warranty_date);
        myEnteredWarrantyDate = warrantyDTextView.getText().toString();


        //mDbHelperItems.updateListOfItemsRow(the_key, myEnteredListNo, myEnteredItemNo, myEnteredName, myEnteredSerial,
                //myEnteredDate, myEnteredWarranty, myEnteredWarrantyDate); //Last three are wrong

        mDbHelperItems.updateListOfItemsRow(itemNumber, myEnteredListNo, myEnteredItemNo, myEnteredName, myEnteredSerial,
                myEnteredDate,  // DatePurchased
                myEnteredPurchasePrice, // Purchase Price
                myEnteredPortable, // Portable (Blank = No, Anythng else = Yes)
                myEnteredWarranty,
                myEnteredWarrantyDate);  // Warranty Expiration Date
        // need to update the List Edit Date Also
        mDbHelperLists = new ListOfLists(this);
        mDbHelperLists.open();
        mDbHelperLists.updateListOfListsRowEditDate(myEnteredListNo);
        finish();
    }

}
