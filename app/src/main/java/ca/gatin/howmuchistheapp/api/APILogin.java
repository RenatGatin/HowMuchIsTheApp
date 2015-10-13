package ca.gatin.howmuchistheapp.api;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.util.ArrayList;

import ca.gatin.howmuchistheapp.activity.LoginActivity;
import ca.gatin.howmuchistheapp.model.ActionError;
import ca.gatin.howmuchistheapp.util.AppConstants;

/**
 * @author RGatin
 * @since 12-Oct-2015
 */
public class APILogin extends APIAbstractManager<String, String, String>{
    private static final String TAG="APILogin";

    private String mRestUrl;
    private ArrayList<String> mRequestBody;
    private String mResponse;

    // TODO: It is not ready, can't use it right now
    // private AccountController mUserController;
    // private PropertyController mPropertyController;

    public APILogin(String restUrl, ArrayList<String> requestBody, Context context){
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
                    .data("email", mRequestBody.get(0).toString())
                    .data("password", mRequestBody.get(1).toString())
                    .timeout(60*1000).ignoreContentType(true).method(Connection.Method.POST);

            Connection.Response resp = dconn.execute();

            String resebody = resp.body();
            JSONObject obj = new JSONObject(resebody);

            if (obj.has("result")) {
                JSONObject result = obj.getJSONObject("result");
                int resultCode = Integer.parseInt(result.getString("resultCode"));
                if (resultCode == 0) {

                    // TODO: Use it after finishing implementation of MySQLiteHelper class
                    // mUserController.saveUser(userObject.getString("first_name"), userObject.getString("last_name"), userObject.getString("email"));
                    // mPropertyController.setValue("username",userObject.getString("email"));

                    mResponse = obj.getString("token");
                } else {
                    mError = new ActionError(result.getString("resultCode"), result.getString("resultMessage"));
                }
            } else {
                mError = new ActionError(Integer.toString(AppConstants.FAILURE), AppConstants.MESSAGE_INVALID_JSON_RESPONSE);
            }
        }
        catch(Exception e){
            Log.d("GCT", e.getMessage());
            e.printStackTrace();
            mError = new ActionError("", e.getMessage());
        }

        return mRequestBody.get(0).toString();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (mContext instanceof LoginActivity) {
            LoginActivity act = (LoginActivity) mContext;

            act.closeProgressDialog();
            if (mError == null) {
                //mPropertyController.setValue("token", mResponse);
                act.postExecuteAPISuccess(ActionType.LOGIN, mResponse);
            } else {
                act.postExecuteAPIFailure(ActionType.LOGIN, mError);
            }
        }
    }



}
