package ir.rasen.charsoo.webservices.business;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import ir.rasen.charsoo.classes.Business;
import ir.rasen.charsoo.helper.Params;
import ir.rasen.charsoo.helper.ServerAnswer;
import ir.rasen.charsoo.helper.URLs;
import ir.rasen.charsoo.interfaces.IWebserviceResponse;
import ir.rasen.charsoo.webservices.WebserviceGET;


/**
 * Created by android on 12/16/2014.
 */
public class GetBusinessContactInfo extends AsyncTask<Void, Void, Business> {
    private static final String TAG = "GetBusinessContactInfo";
    private IWebserviceResponse delegate = null;
    private int businessID;
    private ServerAnswer serverAnswer;

    public GetBusinessContactInfo(int businessID, IWebserviceResponse delegate) {
        this.businessID = businessID;
        this.delegate = delegate;
    }

    @Override
    protected Business doInBackground(Void... voids) {


        Business business = new Business();
        WebserviceGET webserviceGET = new WebserviceGET(URLs.GET_BUSINESS_CONTACT_INFO,new ArrayList<>(
                Arrays.asList(String.valueOf(businessID))));

        try {
            serverAnswer = webserviceGET.execute();

            if (serverAnswer.getSuccessStatus()) {
                JSONObject jsonObject = serverAnswer.getResult();
                business.id = businessID;
                String lat = jsonObject.getString(Params.LATITUDE);
                String lon = jsonObject.getString(Params.LONGITUDE);

                business.location_m.setLatitude(lat);
                business.location_m.setLongitude(lon);
                business.workTime.setTimeWorkOpenFromString(jsonObject.getString(Params.WORK_TIME_OPEN));
                business.workTime.setTimeWorkCloseFromString(jsonObject.getString(Params.WORK_TIME_CLOSE));
                business.webSite = jsonObject.getString(Params.WEBSITE);
                business.email = jsonObject.getString(Params.EMAIL);
                business.phone = jsonObject.getString(Params.PHONE);
                business.mobile = jsonObject.getString(Params.MOBILE);

                return business;
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            serverAnswer = null;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Business result) {
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
