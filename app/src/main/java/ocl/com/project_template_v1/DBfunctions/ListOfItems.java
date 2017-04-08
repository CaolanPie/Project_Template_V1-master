package ocl.com.project_template_v1.DBfunctions;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Caolán on 28/03/2017.
 */

public class ListOfItems {

    private static final String DATABASE_NAME = "myItemsDB";  // Our DB will be called myItemsDB
    private static final String DATABASE_TABLE = "Items"; // our table inside the db is called Details
    private static final int DATABASE_VERSION = 1; // initial DB Version

    // These are the names of the columns in our table
    public static final String KEY_ROWID = "ListNo";
    public static final String item_Number = "ItemNumber";
    public static final String item_Name = "ItemName";
    public static final String item_serial = "SerialNumber";
    public static final String date_purchased = "DatePurchased";
    public static final String warranty = "Warranty";
    public static final String warranty_date = "WarrantyExpiration";


    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_CREATE =
            "create table " + DATABASE_TABLE + " ("
                    + KEY_ROWID + " integer not null, "
                    + item_Number + " integer not null, "
                    + item_Name + " text not null, "
                    + item_serial + " text not null, "
                    + date_purchased + " text not null, "
                    + warranty + " text not null, "
                    + warranty_date + " text,"
                    + "PRIMARY KEY ( " + KEY_ROWID + ", " + item_Number + "))";

    // above SQL statment translates to
    // create table ListOfItems ( _id integer primary key autoincrement,
    // 							Name text not null,
    //                          DateCreated text not null,
    //							LastEdited text not null);
	/*
	The table would look like this (column names on top)
	   ListNo	ItemNumber	ItemName  SerialNumber  DatePurchased  Warranty WarrantyExpiration
	   ======	===========	========= ============  =============  ======== ==================
		1	    1		    T.V.	  931904193     22-Feb-2017    Y        22-Feb-2018
		1	    2	        Drone	  834873912     24-Feb-2017    N        N/A
		1	    3	        Headset	  293129304     19-Feb-2017    N        N/A

	 */

    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion) {
            // Not used, but you could upgrade the database with ALTER
            // Scripts
        }
    }

    /**
     * info for database
     * >>> get more information on what this actually does <<<<<<<<<<<<
     * input parameters Context ctx - interface to global information about an application environment
     *
     * @return .mCtx
     */

    public ListOfItems(Context ctx) {
        this.mCtx = ctx;
    }

    /**
     * opens the database
     *
     * input parameters NONE
     *
     * @return this(DB adaptor)
     * throws SQLException - if it fails to open a DB it returns an exception.
     */

    public ListOfItems open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    /**
     * Closes the database
     *
     * input parameters NONE
     *
     * @return NONE
     */

    public void close() {
        mDbHelper.close();
    }

    /**
     * Create a new ListOfItems record using the supplied details (name, address etc...)
     *
     * If the row is successfully created then the .insert will return the new rowId
     * for that row, otherwise return a -1 to indicate failure.
     *
     * @param ListNo = the number of the list that the item belongs to
     * @param ItemName = the name of the item
     * @param SerialNumber = serial number of item
     * @param DatePurchased = date it was purchased
     * @param Warranty = does it have a warranty? Y/N
     * @param WarrantyExpiration = If so, when does it's warranty expire?
     *
     * @return rowId (if successful) or -1 if failed
     */
    public long createListOfItemsRow(int ListNo,
                                     String ItemName,
                                     String SerialNumber,
                                     String DatePurchased,
                                     String Warranty,
                                     String WarrantyExpiration) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_ROWID, ListNo);
        initialValues.put(item_Name, ItemName);
        initialValues.put(item_serial, SerialNumber);
        initialValues.put(date_purchased, DatePurchased);
        initialValues.put(warranty, Warranty);
        initialValues.put(warranty_date, WarrantyExpiration);

        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }

    /**
     * deletes selected ListOfItems row
     *
     * input parameters rowId - the Lists to delete (which row it is on)
     *
     * @return rowId
     */

    public boolean deleteListOfItemsRow(long rowId) {
        return
                mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    /**
     * fetches ALL the ListOfItems from the table
     *
     * input parameters NONE
     *
     * @return string[] - a list of all the Lists in our table
     */

    public Cursor fetchAllListOfItems() {
        return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, item_Name,
                item_serial, date_purchased, warranty, warranty_date}, null, null, null, null, null);
    }

    /**
     * Retrieves a SINGLE row of ListOfItems based on the rowid
     *
     * input parameters rowId - description not found
     *
     * @return mCursor - a cursor pointing to the required ListOfLists Row
     */

    public Cursor fetchListOfItemsRow(long rowId) throws SQLException {
        Cursor mCursor =
                mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                                item_Name, item_serial, date_purchased, warranty, warranty_date}, KEY_ROWID + "=" + rowId,
                        null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    /**
     * saves changes to the ListOfItems row
     *
     * input parameters  ListNo = the number of the list that the item belongs to
     *                   ItemName = the name of the item
     *                   SerialNumber = serial number of item
     *                   DatePurchased = date it was purchased
     *                   Warranty = does it have a warranty? Y/N
     *                   WarrantyExpiration = If so, when does it's warranty expire?
     *
     * @return true if update was successful
     */

    public boolean updateListOfItemsRow(long rowId,
                                        String ListNo,
                                        String ItemName,
                                        String SerialNumber,
                                        String DatePurchased,
                                        String Warranty,
                                        String WarrantyExpiration) {
        ContentValues args = new ContentValues();
        args.put(KEY_ROWID, ListNo);
        args.put(item_Name, ItemName);
        args.put(item_serial, SerialNumber);
        args.put(date_purchased, DatePurchased);
        args.put(warranty, Warranty);
        args.put(warranty_date, WarrantyExpiration);

        return
                mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

}
