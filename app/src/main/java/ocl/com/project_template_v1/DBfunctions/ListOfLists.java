package ocl.com.project_template_v1.DBfunctions;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Caolán on 26/03/2017.
 */

/*
 * This Class will hold the majority if not all the functions for the ListOfLists database
 *      which will be used for accessing our data base
 *
 * Change Log
 * 26-Mar-17 Initial version, this may be all the DB functions we need.
 */

public class ListOfLists {

    private static final String DATABASE_NAME = "myListsDB";  // Our DB will be called myListsDB
    private static final String DATABASE_TABLE = "Lists"; // our table inside the db is called Details
    private static final int DATABASE_VERSION = 1; // initial DB Version

    // These are the names of the columns in our table
    public static final String KEY_ROWID = "_id";
    public static final String KEY_Name = "Name";
    public static final String KEY_DateCreated = "DateCreated";
    public static final String KEY_LastEdited = "LastEdited";


    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_CREATE =
            "create table " + DATABASE_TABLE + " ("
                    + KEY_ROWID + " integer primary key autoincrement, "
                    + KEY_Name + " text not null, "
                    + KEY_DateCreated + " text not null, "
                    + KEY_LastEdited + " text not null);";

    // above SQL statment translates to
    // create table ListOfLists ( _id integer primary key autoincrement,
    // 							Name text not null,
    //                          DateCreated text not null,
    //							LastEdited text not null);
	/*
	The table would look like this (column names on top)
	   _id	Name		DateCreated	LastEdited
	   ===	===========	=========== =========
		1	Work		02-Jan-2017	02-Jan-2017
		2	LivingRoom	02-Feb-2017	02-Feb-2017
		3	Upstairs	02-Apr-2017	02-Apr-2017

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
     * >>> Caolan get more information on what this actually does <<<<<<<<<<<<
     * input parameters Context ctx - interface to global information about an application environment
     *
     * @return .mCtx
     */

    public ListOfLists(Context ctx) {
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

    public ListOfLists open() throws SQLException {
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
     * Create a new ListOfLists record using the supplied details (name, address etc...)
     *
     * If the row is successfully created then the .insert will return the new rowId
     * for that row, otherwise return a -1 to indicate failure.
     *
     * @param name = name
     * @param DateCreated = date when the list was created
     * @param LastEdited = date of when the user last edited this list
     *
     * @return rowId (if successful) or -1 if failed
     */

    public long createListOfListsRow(String name,
                                String DateCreated,
                                String LastEdited) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_Name, name);
        initialValues.put(KEY_DateCreated, DateCreated);
        initialValues.put(KEY_LastEdited, LastEdited);

        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }

    /**
     * deletes selected ListOfLists row
     *
     * input parameters rowId - the Lists to delete (which row it is on)
     *
     * @return rowId
     */

    public boolean deleteListOfListsRow(long rowId) {
        return
                mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    /**
     * fetches ALL the ListOfLists from the table
     *
     * input parameters NONE
     *
     * @return string[] - a list of all the Lists in our table
     */

    public Cursor fetchAllListOfLists() {
        return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_Name,
                KEY_DateCreated, KEY_LastEdited}, null, null, null, null, null);
    }

    /**
     * Retrieves a SINGLE row of ListOfLists based on the rowid
     *
     * input parameters rowId - description not found
     *
     * @return mCursor - a cursor pointing to the required ListOfLists Row
     */

    public Cursor fetchListOfListsRow(long rowId) throws SQLException {
        Cursor mCursor =
                mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                                KEY_Name, KEY_DateCreated, KEY_LastEdited}, KEY_ROWID + "=" + rowId,
                        null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    /**
     * saves changes to the ListOfLists row
     *
     * input parameters rowId - the id of the row we want to update
     * 				  name - name of the list
     * 				  DateCreated - date the list was created
     * 				  LastEdited - date that the list was last edited
     *
     * @return true if update was successful
     */

    public boolean updateListOfListsRow(long rowId,
                                   String name,
                                   String DateCreated,
                                   String LastEdited) {
        ContentValues args = new ContentValues();
        args.put(KEY_Name, name);
        args.put(KEY_DateCreated, DateCreated);
        args.put(KEY_LastEdited, LastEdited);

        return
                mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
}