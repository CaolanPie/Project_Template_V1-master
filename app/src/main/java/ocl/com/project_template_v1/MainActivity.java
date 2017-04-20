package ocl.com.project_template_v1;

/*
 * Change log
 * 22-Feb-17 Caolan     List messages created
 * 21-Feb-17 Caolan     Created menu in top right of screen
 *
 */


import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import ocl.com.project_template_v1.DBfunctions.ListOfItems;
import ocl.com.project_template_v1.DBfunctions.ListOfLists;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ListOfLists mDbHelperLists;
    private ListOfItems mDbHelperItems;
    //private ListView MyItemsView;
    //private ListView MyListsView;
    private String currentList;
    private int currentListNo;
    private ArrayList<String> spinnerArray;
    private ArrayAdapter<String> spinnerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(">> MainActivity"," :: onCreate");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ListView ll=(ListView) findViewById(R.id.list_messages);

        ArrayList<String> myArrayList = new ArrayList<String> (Arrays.asList("Empty"));
        myArrayList.clear();        // Clear our list
        myArrayList.add("Fourth");  // Add some value
        myArrayList.add("Fifth");
        myArrayList.add("Last");

        // The next line called the routing which will populate our list of messages
        populateMyList(myArrayList);

        //This is for my spinner
        spinnerArray = new ArrayList<String> (Arrays.asList("Default"));
        spinnerArray.clear(); //remove this line for testing

        mDbHelperLists = new ListOfLists(this);
        mDbHelperLists.open();

        populateSpinnerLists(spinnerArray);
        // get ID's of our two lists
        //MyListsView = (ListView) findViewById(R.id.list_of_lists);
        //MyItemsView = (ListView) findViewById(R.id.list_of_items);

        // Ths next section takes our myArrayList and puts them in our ListView
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, myArrayList);
        ll.setAdapter(adapter);

        // this code waits for one of the items in our ListView and just says what was clicked
        ll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    //TODO Auto-generated method stub
                    TextView txt = (TextView) arg1;
                    System.out.println(txt.getText().toString());
                }
            }
        );
        /*
         * mDbHelperLists below is creating and opening the database for ListOfLIsts
         */
        // Read my ListOfLists table
        /*
        mDbHelperLists = new ListOfLists(this);
        mDbHelperLists.open();
        GetAllLists();  // Get all records from my List of Lists table
        */
        /*
         * mDbHelperItems below is creating and opening the database for ListOfItems
         */
        /*
        mDbHelperItems = new ListOfItems(this);
        mDbHelperItems.open();
        GetAllItems(); // Get all records from my List of Items table
        */

        // GetAllLists();  // Get all records from my List of Items table

        Spinner spinner = (Spinner) findViewById(R.id.lists_spinner);
        spinner.setOnItemSelectedListener(this);
// Create an ArrayAdapter using the string array and a default spinner layout
        //ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                //R.array.planets_array, android.R.layout.simple_spinner_item);
        spinnerAdapter = new ArrayAdapter<String>(this,
        android.R.layout.simple_spinner_item, spinnerArray);
// Specify the layout to use when the list of choices appears
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(spinnerAdapter);

    } // end of onCreate

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        Log.i(">> MainActivity"," :: onItemSelected");
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)

        //Spinner spinner = (Spinner) findViewById(R.id.planets_spinner);
        //spinner.setOnItemSelectedListener(this);
        Toast.makeText(getApplicationContext(), "We chose " + parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();

        currentList = parent.getItemAtPosition(pos).toString();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        Log.i(">> MainActivity"," :: onNothingSelected");
        // Another interface callback
    }

    /**
     * The scanPage starts the activity to open the barcode scanner
     * @param view
     */
    public void scanPage(View view) {
        Log.i(">> MainActivity"," :: scanPage");
        Intent intent = new Intent(this, BarcodeCaptureActivity.class);
        startActivity(intent);
    }

    /**
     * The invetoryPage starts the activity to open the inventory page,
     * although I am not sure it is needed anymore.
     * @param view
     */
    public void inventoryPage(View view) {
        Log.i(">> MainActivity"," :: inventoryPage");
        Intent intent = new Intent(this, inventoryActivity.class);
        intent.putExtra("selectedList", currentList);
        intent.putExtra("selectedListNo", mDbHelperLists.fetchListOfListsRowByName(currentList));
        startActivity(intent);
    }

    /**
     * The listsPage calls the activity responsible for the ListOfLists page
     * @param view
     */
    public void listsPage(View view) {
        Log.i(">> MainActivity"," :: listsPage");
        Intent intent = new Intent(this, listsActivity.class);
        startActivity(intent);
    }

    /**
     * The settingsPage calls the activity to open the settings page,
     * although this may be removed later.
     * @param view
     */
    public void settingsPage(View view) {
        Log.i(">> MainActivity"," :: settingsPage");
        Intent intent = new Intent(this, settingsActivity.class);
        startActivity(intent);
    }


    /**
     * The listEntryPage is currently designed to open an entire page to allow you to enter details
     * for a new list, it may later be turned into a pop up entry page
     * @param view
     */
    /*
    public void listEntryPage(View view) {

        Intent intent = new Intent(this, ListEntryActivity.class);
        startActivity(intent);
    }
*/

    /**
     * The menu created through this is the one accessed at the top right of the screen,
     * with "about" and "settings" options
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(">> MainActivity"," :: onCreateOptionsMenu");
        super.onCreateOptionsMenu(menu);
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Currently not using this
     * @param featureId
     * @param menu
     * @return
     */
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        Log.i(">> MainActivity"," :: onMenuOpened");
        return false;
    }

    /**
     * Deals with options chosen on the menu
     * @param item - the menu item you chose
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Log.i(">> MainActivity"," :: onOptionsItemSelected");
        // Handle item selection
        switch (item.getItemId())
        {
            case R.id.menu_settings:
            {
                Log.i(">> onOptnsSelect"," :: menu_settings");
                //ShowLoginUI();
                return true;
            }
            case R.id.menu_about:
            {
                Log.i(">> onOptnsSelect"," :: menu_about");
                //ShowGoodbyeUI();
                return true;
            }
            case R.id.menu_Demo:
            {
                Log.i(">> onOptnsSelect"," :: menu_Demo");
                createDemoData();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Routine to populate our ArrayList of Message for the bottom of the screen
     *      Currently there are only hard coded messages
     *      @param myArrayList
     */
    private void populateMyList(ArrayList<String> myArrayList) {
        Log.i(">> MainActivity"," :: populateMyList");
        myArrayList.add("Added by PopulateMyList");
        myArrayList.add("Also Added by PopulateMyList");
        myArrayList.add("moo");

    } // End of populateMyList

    public void onResume(){
        Log.i(">> MainActivity"," :: onResume");
        super.onResume();
        spinnerArray.clear();
        populateSpinnerLists(spinnerArray);
        //Spinner spinner = (Spinner) findViewById(R.id.lists_spinner);
        spinnerAdapter.notifyDataSetChanged();
    }

    private void populateSpinnerLists(ArrayList<String> spinnerArray) {
        Log.i(">> MainActivity"," :: populateSpinnerLists");

        Cursor ListsCursor = mDbHelperLists.fetchAllListOfLists();
        startManagingCursor(ListsCursor);

        if(ListsCursor != null) {
            Log.i("Number of List Records"," :: "+ListsCursor.getCount());
            if(ListsCursor.getCount() != 0){
                ListsCursor.moveToFirst();

                //String term = c.getString(c.getColumnIndex("term")));
                do {
                    String listName = ListsCursor.getString(ListsCursor.getColumnIndex("Name"));
                    int myListNo = ListsCursor.getInt(ListsCursor.getColumnIndex("_id"));
                    spinnerArray.add(listName);
                } while (ListsCursor.moveToNext());
            } else {
                spinnerArray.add("No Lists Currently");
            }
        }
    }

    /**
     * this routine will create sample data when the menuOption demoData is chosen
     */
    private void createDemoData() {

        Log.i(">> Main Activity"," :: createDemoData");
        // List table
        mDbHelperLists = new ListOfLists(this);
        mDbHelperLists.open();
        // Items Table
        mDbHelperItems = new ListOfItems(this);
        mDbHelperItems.open();
        long rowID;

        // Create List and then items withing that List
        rowID = mDbHelperLists.createListOfListsRow("Living Room 1", "Items in Living Room at Front", "2015-01-01","2017-01-31");
        // template for items(int)rowID, 1, Description, Serial Number, Date Purchaed, Warranty Tick,Warranty Expiry Date
        mDbHelperItems.createListOfItemsRow( (int)rowID, 1, "Samsung TV", "SR-127-3736-12", "2015-01-01", "X","2016-12-31");
        mDbHelperItems.createListOfItemsRow( (int)rowID, 2, "Denon AV2301", "DE-551-37", "2015-01-01", "X","2017-12-31");
        mDbHelperItems.createListOfItemsRow( (int)rowID, 3, "Mini Fridge", "FR-123-98", "2015-01-01", "X","2023-12-31");
        mDbHelperItems.createListOfItemsRow( (int)rowID, 4, "AV Speakers", "AV-837367-7", "2015-01-01", "X","2017-12-31");
        mDbHelperItems.createListOfItemsRow( (int)rowID, 5, "Hub", "HUB-01-04", "2015-01-01", "X","2025-12-31");

        rowID = mDbHelperLists.createListOfListsRow("Living Room 2", "Small Living Room at Front", "2015-01-02","2016-01-31");
        mDbHelperItems.createListOfItemsRow( (int)rowID, 1, "Hitachi TV", "HI-127-3736-12", "2012-01-01", "X","2015-12-31");
        mDbHelperItems.createListOfItemsRow( (int)rowID, 2, "Sony DVD Player", "SO-551-37", "2016-01-01", "X","2017-12-31");
        mDbHelperItems.createListOfItemsRow( (int)rowID, 3, "Sound Bar", "Sound-123-98", "2014-01-01", "X","2023-12-31");
        mDbHelperItems.createListOfItemsRow( (int)rowID, 4, "Disco Ball", "Disc-837367-7", "2011-01-01", "X","2017-12-31");
        mDbHelperItems.createListOfItemsRow( (int)rowID, 5, "Mini Fridge", "FRIG-01-04", "2009-01-01", "X","2025-12-31");

        rowID = mDbHelperLists.createListOfListsRow("Bathroom", "Downstairs Bathroom", "2015-01-02","2016-07-31");
        mDbHelperItems.createListOfItemsRow( (int)rowID, 1, "Japaneese Toilet", "JT-P00-1", "2026-06-01", "X","2018-06-31");
        mDbHelperItems.createListOfItemsRow( (int)rowID, 2, "Hair Dryer", "Hair-01-04", "2009-01-01", "X","");

        rowID = mDbHelperLists.createListOfListsRow("Bathroom Family", "Main bathroom", "2015-01-02","2016-09-31");
        mDbHelperItems.createListOfItemsRow( (int)rowID, 1, "Hot Tub", "HT-P00-1", "2026-06-01", "X","2018-06-31");
        mDbHelperItems.createListOfItemsRow( (int)rowID, 2, "Steam Cubical", "ST-01-04", "2009-01-01", "X","");
        mDbHelperItems.createListOfItemsRow( (int)rowID, 3, "Hair Curlers", "HC-P00-1", "2026-02-01", "X","2018-06-31");
        mDbHelperItems.createListOfItemsRow( (int)rowID, 4, "Philips Shaver", "PS-01-04", "2009-01-01", "X","2014-06-31");

        // finished with out lists so closing DBs - we should really close these, but I had probelms
        //mDbHelperLists.close();
        //mDbHelperItems.close();
    }
} // End of Class



