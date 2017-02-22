package ocl.com.project_template_v1;

/*
 * Change log
 * 21-Feb-17 Caolan     Created menu in top right of screen
 *
 *
 */


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    String[] myStrings = { "First", "Second", "Third hard coded String"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        populateMyList();

        setContentView(R.layout.activity_main);
        ListView ll=(ListView) findViewById(R.id.list_messages);


        ArrayList<String> myArrayList = new ArrayList<String>(
                Arrays.asList(myStrings));

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, myArrayList);

        ll.setAdapter(adapter);
    }

    public void scanPage(View view) {

        Intent intent = new Intent(this, scanActivity.class);
        startActivity(intent);
    }

    public void inventoryPage(View view) {

        Intent intent = new Intent(this, inventoryActivity.class);
        startActivity(intent);
    }

    public void listsPage(View view) {

        Intent intent = new Intent(this, listsActivity.class);
        startActivity(intent);
    }

    public void settingsPage(View view) {

        Intent intent = new Intent(this, settingsActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_main, menu);
        return true;
    }

    private void populateMyList() {

/*
        ll.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //TODO Auto-generated method stub
                TextView txt = (TextView) arg1;
                System.out.println(txt.getText().toString());
            }
        }
        ); */

    } // End of populateMyList


} // End of Class



