package ocl.com.project_template_v1;

// this can be removed as it has been replaced by BarcodeCaptureActivity.java

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

public class scanActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        Intent intent = getIntent();
        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_scan);
    }

    public void backPage(View view) {
// nothing in the function yet .. just finish
        finish();
    }

}
