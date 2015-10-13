package ca.gatin.howmuchistheapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
public class SummaryActivity extends AppCompatActivity {

    private TextView tvLoggedInBy;
    private TextView tvPlatform;
    private TextView tvWeb;
    private TextView tvResult;
    private Button btnPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        init();
    }

    private void init() {
        tvLoggedInBy = (TextView) findViewById(R.id.tvLoggedInBy);
        tvLoggedInBy.setText(getSavedData("email"));
        tvPlatform = (TextView) findViewById(R.id.tvPlatform);
        tvPlatform.setText(getSavedData("platform"));
        tvWeb = (TextView) findViewById(R.id.tvWeb);
        tvWeb.setText(getSavedData("withweb"));
        tvResult = (TextView) findViewById(R.id.tvResult);
        tvResult.setText(calculateResult() + " $");


        btnPay = (Button) findViewById(R.id.btnPay);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goNextScreen();
            }
        });
    }

    private String calculateResult() {
        int platform = (tvPlatform.getText().toString().equals(AppConstants.PLATFORM_ANDROID_AND_IOS)) ? 21753 : 9815;
        int web = (tvWeb.getText().toString().equals("Yes")) ? 17000 : 0;
        int result = platform + web;
        String strResult = Integer.toString(result);
        return strResult;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_summary, menu);
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
        if (key.equals("withweb")) {
            boolean withweb = sharedPreferences.getBoolean(key, false);
            return (withweb) ? "Yes" : "No";
        }
        return sharedPreferences.getString(key, "default value");
    }

    private void saveWeb(boolean withWeb) {
        SharedPreferences sharedPreferences = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("withweb", withWeb);
        editor.commit();
    }

    private void goNextScreen() {
        //saveWeb(withWeb);
        startActivity(new Intent(SummaryActivity.this, PayActivity.class));

        Intent i = new Intent(SummaryActivity.this, PayActivity.class);
        i.putExtra("amount_total", calculateResult());
        startActivity(i);
    }

}
