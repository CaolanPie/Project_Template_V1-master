package ocl.com.project_template_v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Caolán on 01/04/2017.
 */

public class ListEntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lists_entry);

        Intent intent = getIntent();
        ViewGroup layout = (ViewGroup) findViewById(R.id.lists_entry);
    }

    public void backPage(View view) {
// nothing in the function yet .. just finish
        finish();
    }


}
