package ocl.com.project_template_v1;

/*
 * Change log
 * 22-Feb-17 Caolan     List messages created
 * 21-Feb-17 Caolan     Created menu in top right of screen
 *
 */


import android.app.AlertDialog;
import android.content.DialogInterface;
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
    private ArrayList<String> myArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(">> MainActivity"," :: onCreate");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ListView ll=(ListView) findViewById(R.id.list_messages);

        // ArrayList<String> myArrayList = new ArrayList<String> (Arrays.asList("Empty"));
        myArrayList = new ArrayList<String> (Arrays.asList("Empty"));

        // The next line called the routing which will populate our list of messages
        // populateMyList(myArrayList);
        populateMyList();;

        //This is for my spinner
        spinnerArray = new ArrayList<String> (Arrays.asList("Default"));
        spinnerArray.clear(); //remove this line for testing
        // Lists Table
        mDbHelperLists = new ListOfLists(this);
        mDbHelperLists.open();
        // below code is needed to stop crash if we do demo data First thing
        // Items Table
        mDbHelperItems = new ListOfItems(this);
        mDbHelperItems.open();

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
        Toast.makeText(getApplicationContext(), "Current List  -  " + parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();

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
        Log.i(">> MainActivity"," :: go to inventoryPage");
        int numberOfRow;
        Intent intent = new Intent(this, inventoryActivity.class);
        intent.putExtra("selectedList", currentList);
        numberOfRow = mDbHelperLists.fetchListOfListsRowByName(currentList);
        if ( numberOfRow >= 1) {
            intent.putExtra("selectedListNo", numberOfRow);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "No Data Currently" , Toast.LENGTH_LONG).show();
        }


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

    public void settingsPage() {
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
                settingsPage();
                return true;
            }
            case R.id.menu_about:
            {
                Log.i(">> onOptnsSelect"," :: menu_about");
                return true;
            }
            case R.id.menu_Demo:
            {
                Log.i(">> onOptnsSelect"," :: menu_Demo");


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

                // set title
                alertDialogBuilder.setTitle("Delete Current Data");

                // set dialog message to make sure delete is needed
                alertDialogBuilder
                        .setMessage("Would you like to clear all current Data before inserting the Demo Data?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                Log.i(">> MainActivity"," :: delete = yes");
                                // if this button is clicked
                                // we have a confirmation so delete record
                                formatDatabase();
                                createDemoData();
                                // This next code refreshes the spinner list
                                spinnerArray.clear();
                                populateSpinnerLists(spinnerArray);
                                //Spinner spinner = (Spinner) findViewById(R.id.lists_spinner);
                                spinnerAdapter.notifyDataSetChanged();
                                populateMyList();
                                //view.finish();
                                dialog.cancel();
                                Toast.makeText(getApplicationContext(), "Demo Data Created" , Toast.LENGTH_LONG).show();

                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                Log.i(">> MainActivity"," :: delete = no");
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                createDemoData();
                                // This next code refreshes the spinner list
                                spinnerArray.clear();
                                populateSpinnerLists(spinnerArray);
                                //Spinner spinner = (Spinner) findViewById(R.id.lists_spinner);
                                spinnerAdapter.notifyDataSetChanged();
                                populateMyList();
                                dialog.cancel();
                                Toast.makeText(getApplicationContext(), "Demo Data Created" , Toast.LENGTH_LONG).show();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Routine to populate our ArrayList of Message for the bottom of the screen
     *      Currently there are only hard coded messages
     *      @param
     */
    // private void populateMyList(ArrayList<String> myArrayList) {
    private void populateMyList() {
        Log.i(">> MainActivity"," :: populateMyList");

        int nOverGivenPrice = 0;
        int isPortable;
        float itemCost = 0;
        float givenCost = 2500;
        String stringgivenCost;
        myArrayList.clear();        // Clear our list
        myArrayList.add("[Item] Warranty Expires on [Date]");  // Add some value
        myArrayList.add("New model of [item] Available");
        myArrayList.add("Insurance for [List] exceeds [content insurance]");
        myArrayList.add("Insurance Renewal in [Days]");
        myArrayList.add("[Item] Added to [List], [Date]");
        myArrayList.add("[List] Created [Date Created]");

        // hard coding value but will be read from settings in future

        // This section check for items over amount set in settings
        // Items Table
        mDbHelperItems = new ListOfItems(this);
        mDbHelperItems.open();
        Cursor tempCursor = mDbHelperItems.fetchAllListOfItems();
        startManagingCursor(tempCursor);

        if(tempCursor != null) {
            Log.i(">> MainActivity", " :: populateMyList num items =" + tempCursor.getCount());
            if (tempCursor.getCount() != 0) {
                tempCursor.moveToFirst();

                do {
                    isPortable = tempCursor.getInt(tempCursor.getColumnIndex("PortableItem"));
                    if( isPortable != 0) {
                        itemCost = tempCursor.getFloat(tempCursor.getColumnIndex("PurchasePrice"));
                        if ( itemCost > givenCost) {
                            nOverGivenPrice = nOverGivenPrice +1; // add one to our total
                        }
                    }
                } while ( tempCursor.moveToNext());
            }
        }
        if ( nOverGivenPrice > 0) {
            myArrayList.add( nOverGivenPrice + " over limit of "+ givenCost);
        }
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
                currentList = ListsCursor.getString(ListsCursor.getColumnIndex("Name"));
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
     * deletes all lists and therefore removes all their items as well
     */
    private void formatDatabase() {
        Log.i(">> MainActivity"," :: formatDatabase");

        Cursor ListsCursor = mDbHelperLists.fetchAllListOfLists();
        startManagingCursor(ListsCursor);

        if(ListsCursor != null) {
            Log.i("No. of lists ", "to Delete "+ListsCursor.getCount());
            if(ListsCursor.getCount() != 0){
                // loop round our lists and call delete of all itesms withing them
                ListsCursor.moveToFirst();
                do {
                    int myListNo = ListsCursor.getInt(ListsCursor.getColumnIndex("_id"));
                    mDbHelperItems.deleteItemsFromList(myListNo);  // remove all items
                } while (ListsCursor.moveToNext());
                // Now loop on our list and remove the lists - we could actuall just call a delete all lists sql
                ListsCursor.moveToFirst();
                do {
                    int myListNo = ListsCursor.getInt(ListsCursor.getColumnIndex("_id"));
                    mDbHelperLists.deleteListOfListsRow(myListNo);  // remove this list row
                } while (ListsCursor.moveToNext());

            } else {
                Log.i(">> formatDatabase"," :: No deleting");
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
        rowID = mDbHelperLists.createListOfListsRow("Living Room 1", "Items in Living Room at Front", "Jan 01, 2015","Jan 31, 2017");
        // template for items(int)rowID, 1, Description, Serial Number, Date Purchaed, Purchase Price, Warranty Tick,Warranty Expiry Date
        mDbHelperItems.createListOfItemsRow( (int)rowID, 1, "Samsung TV", "SR-127-3736-12", "Apr 20, 2017", 500, 0, 1,"Dec 31, 2018");
        mDbHelperItems.createListOfItemsRow( (int)rowID, 2, "Denon AV2301", "DE-551-37", "Jan 01, 2015", 300, 0, 1,"Dec 31, 2018");
        mDbHelperItems.createListOfItemsRow( (int)rowID, 3, "Mini Fridge", "FR-123-98", "Jan 01, 2015", 80, 1, 1,"Dec 31, 2023");
        mDbHelperItems.createListOfItemsRow( (int)rowID, 4, "AV Speakers", "AV-837367-7", "Jan 01, 2015", 120, 0, 1,"Dec 31, 2017");
        mDbHelperItems.createListOfItemsRow( (int)rowID, 5, "Hub", "HUB-01-04", "Jan 01, 2015", 40, 0, 1,"Dec 31, 2025");

        rowID = mDbHelperLists.createListOfListsRow("Living Room 2", "Small Living Room at Front", "Jan 02, 2015","Jan 31, 2017");
        mDbHelperItems.createListOfItemsRow( (int)rowID, 1, "Hitachi TV", "HI-127-3736-12", "Jan 21, 2012", 450, 0, 1,"Dec 31, 2015");
        mDbHelperItems.createListOfItemsRow( (int)rowID, 2, "Sony DVD Player", "SO-551-37", "Jan 01, 2013", 200, 0, 1,"Dec 31, 2018");
        mDbHelperItems.createListOfItemsRow( (int)rowID, 3, "Sound Bar", "Sound-123-98", "Jan 01, 2013", 70, 0, 1,"Dec 31, 2018");
        mDbHelperItems.createListOfItemsRow( (int)rowID, 4, "Disco Ball", "Disc-837367-7", "Jan 01, 2013", 20, 1, 1,"Dec 31, 2018");
        mDbHelperItems.createListOfItemsRow( (int)rowID, 5, "Mini Fridge", "FRIG-01-04", "Jan 01, 2013", 40, 1, 1,"Dec 31, 2025");

        rowID = mDbHelperLists.createListOfListsRow("Bathroom", "Downstairs Bathroom", "Jan 02, 2015","Jul 31, 2017");
        mDbHelperItems.createListOfItemsRow( (int)rowID, 1, "Japaneese Toilet", "JT-P00-1", "Jan 01, 2026", 3000, 0, 1,"Jun 31, 2015");
        mDbHelperItems.createListOfItemsRow( (int)rowID, 2, "Hair Dryer", "Hair-01-04", "Jan 01, 2009", 40, 1, 0,"");

        rowID = mDbHelperLists.createListOfListsRow("Bathroom Family", "Main bathroom", "Jan 02, 2015","Sep 31, 2017");
        mDbHelperItems.createListOfItemsRow( (int)rowID, 1, "Hot Tub", "HT-P00-1", "Jun 01, 2026", 600, 0, 1,"Jun 31, 2018");
        mDbHelperItems.createListOfItemsRow( (int)rowID, 2, "Steam Cubical", "ST-01-04", "Jun 01, 2009", 350, 0, 0,"");
        mDbHelperItems.createListOfItemsRow( (int)rowID, 3, "Hair Curlers", "HC-P00-1", "Jun 01, 2026", 65, 1, 1,"Jun 31, 2018");
        mDbHelperItems.createListOfItemsRow( (int)rowID, 4, "Philips Shaver", "PS-01-04", "Jun 01, 2009", 60, 1, 1,"Jun 31, 2014");

        rowID = mDbHelperLists.createListOfListsRow("Garage", "Double Garage", "Jan 02, 2015","Sep 31, 2017");
        mDbHelperItems.createListOfItemsRow( (int)rowID, 1, "Mercedes", "BM-784-3736-12", "Jan 01, 2015", 15000, 0, 1,"Dec 31, 2019");
        mDbHelperItems.createListOfItemsRow( (int)rowID, 1, "Petrol Powered Lawnmower", "SR-127-3736-12", "Jan 01, 2015", 300, 1, 1,"Dec 31, 2016");
        mDbHelperItems.createListOfItemsRow( (int)rowID, 1, "Yamaha Motorbike", "SR-127-3736-12", "Jan 01, 2015", 3000, 0, 1,"Dec 31, 2016");
        mDbHelperItems.createListOfItemsRow( (int)rowID, 1, "Icebox Freezer", "SR-127-3736-12", "Jan 01, 2015", 500, 0, 1,"Dec 31, 2016");
        mDbHelperItems.createListOfItemsRow( (int)rowID, 1, "Ford Focus", "SR-127-3736-12", "Jan 01, 2015", 8000, 0, 1,"Dec 31, 2016");

        rowID = mDbHelperLists.createListOfListsRow("Attick", "Roof Storage", "Jan 02, 2015","Sep 31, 2017");
        mDbHelperItems.createListOfItemsRow( (int)rowID, 1, "Heirlooms", "SR-127-3736-12", "Jan 01, 2015", 200, 1, 1,"Dec 31, 2016");
        mDbHelperItems.createListOfItemsRow( (int)rowID, 1, "Replacement Electrics", "SR-127-3736-12", "Jan 01, 2015", 750, 0, 1,"Dec 31, 2016");

        rowID = mDbHelperLists.createListOfListsRow("Bedroom", "Master Bedroom", "Jan 02, 2015","Sep 31, 2017");
        mDbHelperItems.createListOfItemsRow( (int)rowID, 1, "Rolex Watch", "SR-127-3736-12", "Jan 01, 2015", 3000, 1, 1,"Dec 31, 2012");
        mDbHelperItems.createListOfItemsRow( (int)rowID, 1, "LG TV", "SR-127-3736-12", "Jan 01, 2015", 895, 0, 1,"Dec 31, 2016");
        mDbHelperItems.createListOfItemsRow( (int)rowID, 1, "MiniVault Safe", "SR-127-3736-12", "Jan 01, 2015", 359, 0, 1,"Dec 31, 2016");

        rowID = mDbHelperLists.createListOfListsRow("Kitchen", "Communal Eating Space", "Jan 02, 2015","Sep 31, 2017");
        mDbHelperItems.createListOfItemsRow( (int)rowID, 1, "Merrychef Eikon Oven", "SR-127-3736-12", "Jan 01, 2015", 6700, 0, 1,"Dec 31, 2016");
        mDbHelperItems.createListOfItemsRow( (int)rowID, 1, "Damascus Knives", "SR-127-3736-12", "Jan 01, 2015", 629, 1, 1,"Dec 31, 2016");
        mDbHelperItems.createListOfItemsRow( (int)rowID, 1, "Fracino Contempo Coffee Maker", "SR-127-3736-12", "Jan 01, 2015", 2100, 0, 1,"Dec 31, 2016");


        // finished with out lists so closing DBs - we should really close these, but I had probelms
        //mDbHelperLists.close();
        //mDbHelperItems.close();
    }
} // End of Class



