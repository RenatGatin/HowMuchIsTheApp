package ca.gatin.howmuchistheapp.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import ca.gatin.howmuchistheapp.R;
import ca.gatin.howmuchistheapp.api.APIConnectionManager;
import ca.gatin.howmuchistheapp.api.APIResponseHandler;
import ca.gatin.howmuchistheapp.api.ActionType;
import ca.gatin.howmuchistheapp.model.ActionError;

/**
 * @author RGatin
 * @since 12-Oct-2015
 *
 */
public class LoginActivity extends AppCompatActivity implements APIResponseHandler {

    private Button btnLogin, btnCreate;
    private EditText etEmail, etPassword;
    private ProgressDialog progressDialog;
    protected APIConnectionManager apiCM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    private void init() {
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);

        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        btnCreate = (Button)findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, CreateAccountActivity.class));
            }
        });

        apiCM = new APIConnectionManager(this);
    }

    private void loginUser() {
        ArrayList<String> args = new ArrayList<String>();
        args.add(etEmail.getText().toString());
        args.add(etPassword.getText().toString());
        openProgressDialog("Signing you in ...");
        apiCM.postAccountLogin(args);
    }

    @Override
    public void postExecuteAPISuccess(ActionType callType, Object data) {
        switch (callType) {
            case LOGIN:
                String email = etEmail.getText().toString();
                String token = (String) data;
                showMessage("Success", "You have successfuling loged in as " + email + "!\n(token: " + token + ")");
                saveTokenAndEmail(token, email);
                startActivity(new Intent(LoginActivity.this, ChoosePlatformActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void postExecuteAPIFailure(ActionType callType, ActionError error) {
        switch (callType) {
            case LOGIN:
                showMessage("Error", error.getMessage());
                break;
            default:
                break;
        }
    }

    public void openProgressDialog(String text){
        progressDialog = ProgressDialog.show(this,
                "Please wait ...", text);
    }

    public void closeProgressDialog(){
        if(progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public void showMessage(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(
                        message)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {

                            }
                        }).setIcon(R.drawable.ipad).show();

    }

    private void saveTokenAndEmail(String token, String email) {
        SharedPreferences sharedPreferences = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.putString("email", email);
        editor.commit();
    }

}
