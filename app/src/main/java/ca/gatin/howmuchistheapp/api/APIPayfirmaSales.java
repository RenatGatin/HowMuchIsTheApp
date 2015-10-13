package ca.gatin.howmuchistheapp.api;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.util.ArrayList;

import ca.gatin.howmuchistheapp.activity.CreateAccountActivity;
import ca.gatin.howmuchistheapp.activity.PayActivity;
import ca.gatin.howmuchistheapp.model.ActionError;
import ca.gatin.howmuchistheapp.model.Transaction;
import ca.gatin.howmuchistheapp.util.AppConstants;

/**
 * @author RGatin
 * @since 12-Oct-2015
 *
 */
public class APIPayfirmaSales extends APIAbstractManager<String, String, String> {
    private static final String TAG = "APIPayfirmaSales";

    private String mRestUrl;
    private ArrayList<String> mRequestBody;
    private String mResponse;
    private Transaction transaction;

    // TODO: It is not ready, can't use it right now
    // private AccountController mUserController;
    // private PropertyController mPropertyController;

    public APIPayfirmaSales(String restUrl, ArrayList<String> requestBody, Context context) {
        super(restUrl, requestBody, context);
        this.mRestUrl = restUrl;
        this.mRequestBody = requestBody;

        // TODO: Use it after finishing implementation of MySQLiteHelper class
        // mUserController = new UserController(context);
        // mPropertyController = new PropertyController(context);
    }

    @Override
    protected String doInBackground(String... arg0) {
        try {
            Connection dconn = Jsoup.connect(mRestUrl)
                    .data("key", mRequestBody.get(0).toString())
                    .data("merchant_id", mRequestBody.get(1).toString())
                    .data("amount", mRequestBody.get(2).toString())
                    .data("card_number", mRequestBody.get(3).toString())
                    .data("card_expiry_month", mRequestBody.get(4).toString())
                    .data("card_expiry_year", mRequestBody.get(5).toString())
                    .data("test_mode", mRequestBody.get(6).toString())
                    .timeout(60 * 1000).ignoreContentType(true).method(Connection.Method.POST);

            Connection.Response resp = dconn.execute();

            String resebody = resp.body();
            JSONObject obj = new JSONObject(resebody);

            if (obj.has("result")) {
                String result = obj.getString("result");
                if (result.equals("approved")) {
                    transaction = new Transaction();
                    transaction.setTransaction_id(Long.parseLong(obj.getString("transaction_id")));
                    transaction.setResult(obj.getString("result"));
                    transaction.setAmount(Double.parseDouble(obj.getString("amount")));
                    transaction.setAuth_code(obj.getString("auth_code"));
                    transaction.setCvv2(obj.getString("cvv2"));
                    transaction.setType(obj.getString("type"));
                } else {
                    mError = new ActionError(Integer.toString(AppConstants.FAILURE), "Transaction is " + obj.getString("result") + ".\ntransaction_id: " + obj.getString("transaction_id") + ", auth_code: " + obj.getString("auth_code"));
                }
            } else {
                mError = new ActionError(Integer.toString(AppConstants.FAILURE), AppConstants.MESSAGE_INVALID_JSON_RESPONSE);
            }
        } catch (Exception e) {
            Log.d("GCT", e.getMessage());
            e.printStackTrace();
            mError = new ActionError("", e.getMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (mContext instanceof PayActivity) {
            PayActivity act = (PayActivity) mContext;

            act.closeProgressDialog();
            if (mError == null) {
                act.postExecuteAPISuccess(ActionType.PAYMENT, transaction);
            } else {
                act.postExecuteAPIFailure(ActionType.PAYMENT, mError);
            }
        }
    }


}