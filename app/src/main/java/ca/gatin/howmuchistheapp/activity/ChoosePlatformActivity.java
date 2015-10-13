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
import ca.gatin.howmuchistheapp.util.AppConstants;

/**
 * @author RGatin
 * @since 12-Oct-2015
 *
 */
public class ChoosePlatformActivity extends AppCompatActivity {

    private TextView tvLoggedInBy;
    private Button btnAndroid, btnIos, btnBoth;
    private String platform = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_platforms);
        init();
    }

    private void init() {
        tvLoggedInBy = (TextView) findViewById(R.id.tvLoggedInBy);
        tvLoggedInBy.setText(getSavedData("email"));

        btnAndroid = (Button)findViewById(R.id.btnAndroid);
        btnAndroid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                platform = AppConstants.PLATFORM_ANDROID;
                goNextScreen();
            }
        });

        btnIos = (Button)findViewById(R.id.btnIos);
        btnIos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                platform = AppConstants.PLATFORM_IOS;
                goNextScreen();
            }
        });

        btnBoth = (Button)findViewById(R.id.btnBoth);
        btnBoth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                platform = AppConstants.PLATFORM_ANDROID_AND_IOS;
                goNextScreen();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_choose_platforms, menu);
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

    private void savePlatform(String platform) {
        SharedPreferences sharedPreferences = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("platform", platform);
        editor.commit();
    }

    private void goNextScreen() {
        savePlatform(platform);
        startActivity(new Intent(ChoosePlatformActivity.this, ChooseWebActivity.class));
    }
}
