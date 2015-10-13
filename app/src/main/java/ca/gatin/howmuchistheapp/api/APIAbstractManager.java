package ca.gatin.howmuchistheapp.api;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

import ca.gatin.howmuchistheapp.model.ActionError;

/**
 * @author RGatin
 * @since 12-Oct-2015
 *
 * An AsyncTask abstract implementation that every API caller will extend
 */
public abstract class APIAbstractManager <String0, String1, String2> extends AsyncTask<String, String, String> {

    //instance variables for the APIAbstractManager abstract class
    private String mRestUrl;
    private ArrayList<String> mRequestBody;
    protected ActionError mError = null;
    private String mResponse;
    protected Context mContext;

    /**
     * Creates a new instance of PostTask with the specified URL, callback, and
     * request body.
     *
     * @param restUrl The URL for the REST API.
     * @param requestBody The body of the POST request.
     *
     */
    public APIAbstractManager(String restUrl, ArrayList<String> requestBody, Context context){
        this.mRestUrl = restUrl;
        this.mRequestBody = requestBody;
        this.mContext = context;
    }

    /**
     * Operations to be done in the background
     * blank here since this is an abstract class
     *
     */
    @Override
    protected String doInBackground(String... arg0) {
        return mRequestBody.get(0).toString();

    }

    /**
     * Operations to be done once doInBackground has finished
     * blank here since this is an abstract class
     */
    @Override
    protected void onPostExecute(String result) {


    }

    protected String getAppVersion() throws PackageManager.NameNotFoundException {
        PackageInfo pInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
        Log.d("VERSION", pInfo.versionName);
        return pInfo.versionName;
    }

}