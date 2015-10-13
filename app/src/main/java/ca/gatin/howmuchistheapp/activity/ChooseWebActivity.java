package ca.gatin.howmuchistheapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ca.gatin.howmuchistheapp.R;

/**
 * @author RGatin
 * @since 12-Oct-2015
 *
 */
public class ChooseWebActivity extends AppCompatActivity {

    private TextView tvLoggedInBy;
    private TextView tvPlatform;
    private Button btnWeb, btnNoWeb;

    private boolean withWeb = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_web);
        init();
    }

    private void init() {
        tvLoggedInBy = (TextView) findViewById(R.id.tvLoggedInBy);
        tvLoggedInBy.setText(getSavedData("email"));
        tvPlatform = (TextView) findViewById(R.id.tvPlatform);
        tvPlatform.setText(getSavedData("platform"));

        btnWeb = (Button) findViewById(R.id.btnWeb);
        btnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                withWeb = true;
                goNextScreen();
            }
        });

        btnNoWeb = (Button) findViewById(R.id.btnNoWeb);
        btnNoWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goNextScreen();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_choose_web, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private String getSavedData(String key) {
        SharedPreferences sharedPreferences = getSharedPreferences("X", MODE_PRIVATE);
        return sharedPreferences.getString(key, "default value");
    }

    private void saveWeb(boolean withWeb) {
        SharedPreferences sharedPreferences = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("withweb", withWeb);
        editor.commit();
    }

    private void goNextScreen() {
        saveWeb(withWeb);
        startActivity(new Intent(ChooseWebActivity.this, SummaryActivity.class));
    }
}
