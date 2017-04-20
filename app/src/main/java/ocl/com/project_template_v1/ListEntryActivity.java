package ocl.com.project_template_v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import ocl.com.project_template_v1.DBfunctions.ListOfLists;

/**
 * Created by Caolán on 01/04/2017.
 */

public class ListEntryActivity extends AppCompatActivity {

    private ListOfLists mDbHelperLists;
    private Button mSaveButton;
    private Button mCancelButton;
    private EditText mNameBox;
    private EditText mDescriptionBox;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lists_entry);

        Intent intent = getIntent();
        ViewGroup layout = (ViewGroup) findViewById(R.id.lists_entry);

        /*
         * Opening my database table again
         */
        mDbHelperLists = new ListOfLists(this);
        mDbHelperLists.open();

        // My Buttons / fields
        //this is where you define that the "save button" actually saves
        mSaveButton = (Button) findViewById(R.id.save_button);
        mCancelButton = (Button) findViewById(R.id.cancel_button);
        mNameBox = (EditText) findViewById (R.id.list_name);
        mDescriptionBox = (EditText) findViewById(R.id.description_text);

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
                        Toast.makeText(ListEntryActivity.this,
                                "Clicked Save button Name = " + mNameBox.getText().toString(),
                                Toast.LENGTH_SHORT).show();

                        // This line actually calls our createDetailRow which then
                        //		inserts the new row in our database
                        mDbHelperLists.createListOfListsRow(
                                mNameBox.getText().toString(),
                                mDescriptionBox.getText().toString(),
                                null,
                                null
                        );
                        //Fix these later ^
                        Toast.makeText(ListEntryActivity.this,
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
                        Toast.makeText(ListEntryActivity.this, "we clicked the cancel button", Toast.LENGTH_SHORT).show();
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
