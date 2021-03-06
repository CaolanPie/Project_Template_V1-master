package ocl.com.project_template_v1.DBfunctions;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AlertDialog;
import android.util.EventLogTags;
import android.util.Log;
import android.view.View;

import java.util.Date;
import java.util.Locale;

import ocl.com.project_template_v1.list_edit;

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
    public static final String KEY_Description = "Description";
    public static final String KEY_DateCreated = "DateCreated";
    public static final String KEY_LastEdited = "LastEdited";


    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    private int ourTargetRowID;

    private static final String DATABASE_CREATE =
            "create table " + DATABASE_TABLE + " ("
                    + KEY_ROWID + " integer primary key autoincrement, "
                    + KEY_Name + " text not null, "
                    + KEY_Description + " text not null, "
                    + KEY_DateCreated + " DATETIME, "
                    + KEY_LastEdited + " DATETIME DEFAULT CURRENT_TIMESTAMP);";

    // above SQL statement translates to
                                     // create table ListOfLists ( _id integer primary key autoincrement,
                                     // 							Name text not null,
                                     //                          Description text not null,
                                     //                          DateCreated text not null,
                                     //							LastEdited text not null);
	/*
	The table would look like this (column names on top)
	   _id	Name		Description DateCreated	LastEdited
	   ===	===========	=========== =========   ===========
		1	Work		work stuff  02-Jan-2017	02-Jan-2017
		2	LivingRoom	TV stuff    02-Feb-2017	02-Feb-2017
		3	Upstairs	up stuff    02-Apr-2017	02-Apr-2017

	 */

                                     private final Context mCtx;

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }


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
        Log.i(">> ListOfLists"," :: open");
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
     * @param Description = a brief description of the lists contents
     * @param DateCreated = null or datecreated
     * @param LastEdited = null or lastedited
     *
     * @return rowId (if successful) or -1 if failed
     */
    public long createListOfListsRow(String name,
                                     String Description,
                                     String DateCreated,
                                     String LastEdited) {
    ContentValues initialValues = new ContentValues();
    initialValues.put(KEY_Name, name);
    initialValues.put(KEY_Description, Description);
    //initialValues.put(KEY_DateCreated, getDateTime());
    // initialValues.put(KEY_LastEdited, LastEdited);
        if( DateCreated == null)
        {
            initialValues.put(KEY_DateCreated, getDateTime());
        } else {
            initialValues.put(KEY_DateCreated, DateCreated);
        }
        if( LastEdited == null)
        {
            initialValues.put(KEY_LastEdited, getDateTime());
        } else {
            initialValues.put(KEY_LastEdited, LastEdited);
        }

    return mDb.insert(DATABASE_TABLE, null, initialValues);
    }

    /**
     * deletes selected ListOfLists row
     *
     * @param rowId rowId - the row in the list to delete (which row it is on)
     *
     * @return rowId
     */
    public boolean deleteListOfListsRow(int rowId) {
        return
                mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public boolean updateListOfListsRowEditDate(int rowId) {
        ContentValues newValue = new ContentValues();
        newValue.put(KEY_LastEdited, getDateTime());
        return mDb.update(DATABASE_TABLE, newValue, KEY_ROWID + "=" + rowId, null) > 0;
                //mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    /**
     * fetches ALL the ListOfLists from the table
     *
     * input parameters NONE
     *
     * @return string[] - a list of all the Lists in our table
     */
    public Cursor fetchAllListOfLists() {
        return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_Name, KEY_Description,
                KEY_DateCreated, KEY_LastEdited}, null, null, null, null, null);
    }

    /**
     * Retrieves a SINGLE row of ListOfLists based on the rowid
     *
     * @param rowId - the SQL rowId
     *
     * @return mCursor - a cursor pointing to the required ListOfLists Row
     */
    public Cursor fetchListOfListsRow(int rowId) throws SQLException {
        Cursor mCursor =
                mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                                KEY_Name, KEY_Description, KEY_DateCreated, KEY_LastEdited}, KEY_ROWID + "=" + rowId,
                        null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    /**
     * Returns the list number of a given list name
     *
     * @param listName - the name of the selected list
     *
     * @return myListNo - the list number based on the list name
     */
    public int fetchListOfListsRowByName(String listName) throws SQLException {
        int myListNo;
        Cursor mCursor =
                mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                                KEY_Name}, KEY_Name + "=" + "'" + listName + "'",
                        null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        if ( mCursor.getCount() >= 1) {
            myListNo = mCursor.getInt(mCursor.getColumnIndex(KEY_ROWID));
        } else {
            myListNo = 0; // no rows returned
        }

        return myListNo;
    }

    /**
     * saves changes to the ListOfLists row
     *
     * @param rowId - the id of the row we want to update
     * @param name - name of the list
     * @param Description - a brief description of the lists contents
     * @param dateCreated - null or datecreated
     * @param lastEdited - null or lastedited
     *
     * @return true if update was successful
     */
    public boolean updateListOfListsRow(int rowId,
                                   String name,
                                   String Description,
                                        String dateCreated,
                                        String lastEdited) {
        ContentValues args = new ContentValues();
        args.put(KEY_Name, name);
        args.put(KEY_Description, Description);
        if( dateCreated == null)
        {
            // args.put(KEY_DateCreated, getDateTime());
        } else {
            args.put(KEY_DateCreated, dateCreated);
        }
        if( lastEdited == null)
        {
            args.put(KEY_LastEdited, getDateTime());
        } else {
            args.put(KEY_LastEdited, lastEdited);
        }

        return
                mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
}
