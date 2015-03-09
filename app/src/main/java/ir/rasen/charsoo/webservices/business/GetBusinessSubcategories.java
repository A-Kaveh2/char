package ir.rasen.charsoo.webservices.business;


import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import ir.rasen.charsoo.classes.SubCategory;
import ir.rasen.charsoo.helper.Params;
import ir.rasen.charsoo.helper.ServerAnswer;
import ir.rasen.charsoo.helper.URLs;
import ir.rasen.charsoo.interfaces.IWebserviceResponse;
import ir.rasen.charsoo.webservices.WebserviceGET;


/**
 * Created by android on 12/16/2014.
 */
public class GetBusinessSubcategories extends AsyncTask<Void, Void, ArrayList<SubCategory>> {
    private static final String TAG = "GetBusinessSubcategories";
    private IWebserviceResponse delegate = null;
    private int categoryID;
    private ServerAnswer serverAnswer;

    public GetBusinessSubcategories(int categoryID,IWebserviceResponse delegate) {
        this.categoryID = categoryID;
        this.delegate = delegate;
    }

    @Override
    protected ArrayList<SubCategory> doInBackground(Void... voids) {
        ArrayList<SubCategory> list = new ArrayList<>();

        WebserviceGET webserviceGET = new WebserviceGET(URLs.GET_BUSINESS_SUBCATEGORIES,new ArrayList<>(
                Arrays.asList(String.valueOf(categoryID))));

        try {
            serverAnswer = webserviceGET.executeList();
            if (serverAnswer.getSuccessStatus()) {
                JSONArray jsonArray = serverAnswer.getResultList();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    list.add(new SubCategory(Integer.valueOf(jsonObject.getString(Params.SUB_CATEGORY_ID)),jsonObject.getString(Params.SUBCATEGORY)));
                }
                return list;
            }

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            serverAnswer = null;
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<SubCategory> result) {


        //if webservice.execute() throws exception
        if (serverAnswer == null) {
            delegate.getError(ServerAnswer.EXECUTION_ERROR);
            return;
        }
        if (serverAnswer.getSuccessStatus())
            delegate.getResult(result);
        else
            delegate.getError(serverAnswer.getErrorCode());
    }
}
