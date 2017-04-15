package ocl.com.project_template_v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ocl.com.project_template_v1.DBfunctions.ListOfItems;

/**
 * Created by Caolán on 01/04/2017.
 */

public class ItemEntryActivity extends AppCompatActivity {

    private ListOfItems mDbHelperItems;
    private Button mSaveButton;
    private Button mCancelButton;
    private EditText mNameBox;
    private EditText mSerialBox;
    private EditText mDateBox;
    private String targetListname;
    private int myListNumber;

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

        //this detects when the button itself is pressed
        // this calls a routine which will setup the detection of the buttons
        registerButtonsListenersAndSetDefaultText();
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
                        // this is called when our button is actually pressed
                        Toast.makeText(ItemEntryActivity.this,
                                "Clicked Save button Name = " + mNameBox.getText().toString() +
                                ", Serial Number = " + mSerialBox.getText().toString() +
                                ", Date Purchased = " + mDateBox.getText().toString() ,
                                Toast.LENGTH_SHORT).show();

                        // This line actually calls our createListOfListsRow which then
                        //		inserts the new row in our database
                        mDbHelperItems.createListOfItemsRow(
                                myListNumber, //List number
                                1, //Item number
                                mNameBox.getText().toString(),
                                mSerialBox.getText().toString(),
                                mDateBox.getText().toString(),
                                "Y",
                                "forever");
                        //Fix these later ^
                        Toast.makeText(ItemEntryActivity.this,
                                "Details saved.",
                                Toast.LENGTH_SHORT).show();
                        finish(); // now exit this screen
//						showDialog(DATE_PICKER_DIALOG);
                    } // end of setClick(view...
                } // end of new View...
        ); // this is the end of setOnClickListener

        mCancelButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ItemEntryActivity.this, "we clicked the cancel button", Toast.LENGTH_SHORT).show();
                        finish(); // now exit this screen
                    }
                }
        );
    }

    public void backPage(View view) {
// nothing in the function yet .. just finish
        finish();
    }


}
