package ocl.com.project_template_v1;

/*
 * Change log
 * 22-Feb-17 Caolan     List messages created
 * 21-Feb-17 Caolan     Created menu in top right of screen
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

//     String[] myStrings = { "First", "Second", "Third hard coded String"};
  //  String[] myStrings;                        // My Array of strings

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        ListView ll=(ListView) findViewById(R.id.list_messages);

/*
        ArrayList<String> myArrayList = new ArrayList<String>(
                Arrays.asList(myStrings));
                */
        ArrayList<String> myArrayList = new ArrayList<String> (Arrays.asList("Empty"));
        myArrayList.clear();        // Clear our list
        myArrayList.add("Fourth");  // Add some value
        myArrayList.add("Fifth");
        myArrayList.add("Last");

        // The next line called the routing which will populate our list of messages
        populateMyList(myArrayList);

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
    } // end of onCreate

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

    /*
     * Routing to populate our ArrayList of Message for the bottom of the screen
     *      Currently there are only hard coded messages
     */
    private void populateMyList(ArrayList<String> myArrayList) {

        myArrayList.add("Added by PopulateMyList");
        myArrayList.add("Also Added by PopulateMyList");

    } // End of populateMyList


} // End of Class



