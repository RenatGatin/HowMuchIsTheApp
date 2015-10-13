package ca.gatin.howmuchistheapp.api;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import ca.gatin.howmuchistheapp.R;

/**
 * @author RGatin
 * @since 12-Oct-2015
 */
public class APIConnectionManager {
    private static final String TAG="APIConnectionManager";

    public APIAbstractManager apiman;
    public Context mContext;
    private String mApiUrl = null;
    private String mApiPayfirmaUrl = null;
    //private String mApiUrl = "http://gatin.ca:8080/todoapi/webapi";

    public APIConnectionManager(Context context) {
        mContext = context;
        mApiUrl = context.getResources().getString(R.string.RESTfulAPIsURL);
        mApiPayfirmaUrl = context.getResources().getString(R.string.PayfirmaAPIsURL);
    }

    public void postAccountLogin(ArrayList<String> args) {
        String restUrl = mApiUrl + "/accounts/login";
        apiman = (APILogin) new APILogin(restUrl, args, mContext).execute();
        Log.i(TAG, restUrl);
    }

    public void postCreateAccount(ArrayList<String> args) {
        String restUrl = mApiUrl + "/accounts/create";
        apiman = (APICreateAccount) new APICreateAccount (restUrl, args, mContext).execute();
        Log.i(TAG, restUrl);
    }

    public void postMakePayment(ArrayList<String> args) {
        String restUrl = mApiPayfirmaUrl + "/sale";
        apiman = (APIPayfirmaSales) new APIPayfirmaSales (restUrl, args, mContext).execute();
        Log.i(TAG, restUrl);
    }

}
