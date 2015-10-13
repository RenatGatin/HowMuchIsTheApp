package ca.gatin.howmuchistheapp.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
public class CreateAccountActivity extends AppCompatActivity implements APIResponseHandler {

    private EditText etEmail, edPassword, edFirstName, edLastName;
    private Button btnCreateAccount;
    private ProgressDialog progressDialog;
    protected APIConnectionManager apiCM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        init();
    }

    private void init() {
        etEmail = (EditText)findViewById(R.id.etEmail);
        edPassword = (EditText)findViewById(R.id.edPassword);
        edFirstName = (EditText)findViewById(R.id.edFirstName);
        edLastName = (EditText)findViewById(R.id.edLastName);
        btnCreateAccount = (Button)findViewById(R.id.btnCreateAccount);
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });

        apiCM = new APIConnectionManager(this);
    }

    private void createAccount() {
        ArrayList<String> args = new ArrayList<String>();
        args.add(etEmail.getText().toString());
        args.add(edPassword.getText().toString());
        args.add(edFirstName.getText().toString());
        args.add(edLastName.getText().toString());

        openProgressDialog("Creating account ...");
        apiCM.postCreateAccount(args);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_account, menu);
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

    @Override
    public void postExecuteAPISuccess(ActionType callType, Object data) {
        switch (callType) {
            case CREATE_ACCOUNT:
                showMessage("Success", "Your Account successfully created.\nGo back to Login page.");
                break;
            default:
                break;
        }
    }

    @Override
    public void postExecuteAPIFailure(ActionType callType, ActionError error) {
        switch (callType) {
            case CREATE_ACCOUNT:
                showMessage("Error", error.getMessage());
                break;
            default:
                break;
        }
    }
}
