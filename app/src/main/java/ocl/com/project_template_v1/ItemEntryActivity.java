package ocl.com.project_template_v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import ocl.com.project_template_v1.DBfunctions.ListOfItems;
import ocl.com.project_template_v1.DBfunctions.ListOfLists;

/**
 * Created by Caolán on 01/04/2017.
 */

public class ItemEntryActivity extends AppCompatActivity {

    private ListOfItems mDbHelperItems;
    private ListOfLists mDbHelperLists;
    private Button mSaveButton;
    private Button mCancelButton;
    private EditText mNameBox;
    private EditText mSerialBox;
    private EditText mDateBox;
    private EditText mPurchasePrice;
    private CheckBox mPortableItem;
    private CheckBox mWarrantyBox;
    private EditText mWarrantyDateBox;
    private String targetListname;
    private int myListNumber;

    private static final int RC_BARCODE_CAPTURE = 9001;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_entry);

        Intent intent = getIntent();
        ViewGroup layout = (ViewGroup) findViewById(R.id.item_entry);

        targetListname = intent.getStringExtra("selectedList");
        TextView mTextView = (TextView) findViewById(R.id.nameofList);
        mTextView.setText(targetListname);
        myListNumber = intent.getIntExtra("selectedListNo", 0);

        mDbHelperItems = new ListOfItems(this);
        mDbHelperItems.open();

        mSaveButton = (Button) findViewById(R.id.save_button);
        mCancelButton = (Button) findViewById(R.id.cancel_button);
        mNameBox = (EditText) findViewById (R.id.item_name);
        mSerialBox = (EditText) findViewById (R.id.serial_number);
        mDateBox = (EditText) findViewById (R.id.date_purchased);
        setDate fromDate = new setDate(mDateBox, this);
        mPurchasePrice = (EditText) findViewById (R.id.purchase_price);
        mPortableItem = (CheckBox) findViewById(R.id.portable_checkbox);
        mWarrantyBox = (CheckBox) findViewById (R.id.warrenty_checkbox);
        mWarrantyDateBox = (EditText) findViewById (R.id.warranty_date);
        setDate fromDate2 = new setDate(mWarrantyDateBox, this);

        //this detects when the button itself is pressed
        // this calls a routine which will setup the detection of the buttons
        registerButtonsListenersAndSetDefaultText();
    }

    public void onResume(){
        Log.i(">> ItemEntryActivity"," :: onResume");
        super.onResume();

    }

    // This routine we will create all our listeners for our screen
    private void registerButtonsListenersAndSetDefaultText() {
        // TODO Auto-generated method stub

        //this is to detect when the save button specifically is pressed
        // this sets up a trigger which will tell us when the button is press and when it is it calls our onClick
        mSaveButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int nPortable;
                        int nWarranty;
                        // this is called when our button is actually pressed
                        /*
                        Toast.makeText(ItemEntryActivity.this,
                                "Clicked Save button Name = " + mNameBox.getText().toString() +
                                ", Serial Number = " + mSerialBox.getText().toString() +
                                ", Date Purchased = " + mDateBox.getText().toString() ,
                                Toast.LENGTH_SHORT).show();
                        */
                        // find out if we indicated this item as portable
                        // that is can be taken out of the house
                        if(mPortableItem.isChecked()) {
                            nPortable = 1;
                        } else {
                            nPortable = 0;
                        }
                        if(mWarrantyBox.isChecked()) {
                            nWarranty = 1;
                        } else {
                            nWarranty = 0;
                        }
                        // This line actually calls our createListOfListsRow which then
                        //		inserts the new row in our database
                        mDbHelperItems.createListOfItemsRow(
                                myListNumber, //List number
                                1, //Item number
                                mNameBox.getText().toString(),
                                mSerialBox.getText().toString(),
                                // mDateBox.getText().toString(), // Date Purchased
                                mDateBox.getText().toString(),  // temp date purchased
                                Float.valueOf(mPurchasePrice.getText().toString()), // Purchase Price -- fix this hard coding
                                nPortable, // PortableItem (blank is No, anything else is Yes)
                                nWarranty,
                                mWarrantyDateBox.getText().toString()); // temp warranty date
                               // mWarrantyDateBox.getText().toString()); // Warranty DAte
                        //Fix these later ^
                        Toast.makeText(ItemEntryActivity.this,
                                "Details Saved",
                                Toast.LENGTH_SHORT).show();
                        // need to update the List Edit Date Also
                        mDbHelperLists = new ListOfLists(ItemEntryActivity.this);
                        mDbHelperLists.open();
                        mDbHelperLists.updateListOfListsRowEditDate(myListNumber);

                        finish(); // now exit this screen
//						showDialog(DATE_PICKER_DIALOG);
                    } // end of setClick(view...
                } // end of new View...
        ); // this is the end of setOnClickListener

        mCancelButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ItemEntryActivity.this, "Edit Cancelled", Toast.LENGTH_SHORT).show();
                        finish(); // now exit this screen
                    }
                }
        );
    }

    public void scanPage(View view) {
        Log.i(">> ItemEntryActivity"," :: scanPage");
        //Intent intent = new Intent(this, BarcodeCaptureActivity.class);
        //startActivity(intent);
        Intent intent = new Intent(this, BarcodeCaptureActivity.class);
        intent.putExtra(BarcodeCaptureActivity.AutoFocus, true);
        intent.putExtra(BarcodeCaptureActivity.UseFlash, false);

        startActivityForResult(intent, RC_BARCODE_CAPTURE);
    }

    public void backPage(View view) {
// nothing in the function yet .. just finish
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
        Log.i(">> ItemEntryActivity", "onActivityResult");
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    mSerialBox.setText(barcode.displayValue);
                    Toast.makeText(ItemEntryActivity.this,
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

}
