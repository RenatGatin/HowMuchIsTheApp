package ca.gatin.howmuchistheapp.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Date;

import ca.gatin.howmuchistheapp.R;
import ca.gatin.howmuchistheapp.api.APIConnectionManager;
import ca.gatin.howmuchistheapp.api.APIResponseHandler;
import ca.gatin.howmuchistheapp.api.ActionType;
import ca.gatin.howmuchistheapp.model.ActionError;
import ca.gatin.howmuchistheapp.model.Transaction;

/**
 * @author RGatin
 * @since 12-Oct-2015
 *
 */
public class PayActivity extends AppCompatActivity implements APIResponseHandler {

    private EditText etCardNumber, edCardExMonth, edCardExYear, edAmount;
    private Button btnSubmit;
    private ProgressDialog progressDialog;
    protected APIConnectionManager apiCM;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        init();
    }

    private void init() {
        etCardNumber = (EditText) findViewById(R.id.etCardNumber);
        edCardExMonth = (EditText) findViewById(R.id.edCardExMonth);
        edCardExYear = (EditText) findViewById(R.id.edCardExYear);

        edAmount = (EditText) findViewById(R.id.edAmount);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("amount_total");
            edAmount.setText(value);
        }

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processTheOrder();
            }
        });

        apiCM = new APIConnectionManager(this);
    }

    private void processTheOrder() {
        if (validFields()) {
            makeOrder();
        }
    }

    private void makeOrder() {
        ArrayList<String> args = new ArrayList<String>();
        args.add(getApplicationContext().getResources().getString(R.string.payfirma_key)); // key
        args.add(getApplicationContext().getResources().getString(R.string.payfirma_merchant_id)); // merchant_id
        args.add(edAmount.getText().toString()); // amount
        args.add(etCardNumber.getText().toString()); // card_number
        args.add(edCardExMonth.getText().toString()); //card_expiry_month
        args.add(edCardExYear.getText().toString()); // card_expiry_year
        args.add("true"); // test_mode

        openProgressDialog("Processing payment ...");
        apiCM.postMakePayment(args);
    }

    private boolean validFields() {
        boolean result = false;

        if (etCardNumber.getText().toString().isEmpty() || edCardExMonth.getText().toString().isEmpty() || edCardExYear.getText().toString().isEmpty()) {
            showMessage("Error", "Some required field(s) are empty");
            return result;
        }

        long cardNumber = Long.parseLong(etCardNumber.getText().toString());
        int month = Integer.parseInt(edCardExMonth.getText().toString());
        int year = Integer.parseInt(edCardExYear.getText().toString());


        if (cardNumber <= 0) {
            showMessage("Error", "Invalid card number");
        } else if (month < 1 || month > 12) {
            showMessage("Error", "Invalid card expiry month");
        } else if (year < 2015 || year > 2030) {
            showMessage("Error", "Invalid card expiry year");
        } else {
            result = true;
        }

        return result;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pay, menu);
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

    @Override
    public void postExecuteAPISuccess(ActionType callType, Object data) {
        switch (callType) {
            case PAYMENT:
                Transaction  tr = (Transaction) data;
                String successMessage = "Your order successfully processed!" +
                        "\n result: " + tr.getResult() +
                        "\n transaction_id: " + tr.getTransaction_id() +
                        "\n type: " + tr.getType() +
                        "\n auth_code: " + tr.getAuth_code() +
                        "\n cvv2: " + tr.getCvv2() +
                        "\n amaunt: " + tr.getAmount();

                showMessage("Payment Processed", successMessage);
                break;
            default:
                break;
        }
    }

    @Override
    public void postExecuteAPIFailure(ActionType callType, ActionError error) {
        switch (callType) {
            case PAYMENT:
                showMessage("Error", error.getMessage());
                break;
            default:
                break;
        }
    }
}
