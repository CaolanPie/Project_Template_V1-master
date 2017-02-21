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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

}
