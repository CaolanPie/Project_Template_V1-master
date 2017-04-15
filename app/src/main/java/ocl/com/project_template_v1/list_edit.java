package ocl.com.project_template_v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import ocl.com.project_template_v1.DBfunctions.ListOfLists;

/**
 * Created by Tony on 15/04/2017.
 */

public class list_edit extends AppCompatActivity {
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        Log.i(">> list_edit"," :: onCreate");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_edit);

        Intent intent = getIntent();
        ViewGroup layout = (ViewGroup) findViewById(R.id.list_edit);
        /*
        MyListsView = (ListView) findViewById(R.id.list_of_lists);

        mDbHelperLists = new ListOfLists(this);
        mDbHelperLists.open();
        GetAllLists();  // Get all records from my List of Lists table

        */
    }

    public void backPage(View view) {
        Log.i(">> list_edit"," :: backPage");
// nothing in the function yet .. just finish
        finish();
    }

}
