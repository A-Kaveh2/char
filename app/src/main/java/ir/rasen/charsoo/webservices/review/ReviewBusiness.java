package ir.rasen.charsoo.webservices.review;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

import ir.rasen.charsoo.helper.ResultStatus;
import ir.rasen.charsoo.helper.ServerAnswer;
import ir.rasen.charsoo.helper.URLs;
import ir.rasen.charsoo.interfaces.IWebserviceResponse;
import ir.rasen.charsoo.webservices.WebserviceGET;


/**
 * Created by android on 12/16/2014.
 */
public class ReviewBusiness extends AsyncTask<Void, Void, ResultStatus> {
    private static final String TAG = "ReviewBusiness";

    private IWebserviceResponse delegate = null;
    private int userID;
    private int businessID;
    private String review;
    private ServerAnswer serverAnswer;
    private  int rate;

    public ReviewBusiness(int userID, int businessID, String review,int rate, IWebserviceResponse delegate) {
        this.userID = userID;
        this.businessID = businessID;
        this.review = review;
        this.delegate = delegate;
        this.rate = rate;
    }

    @Override
    protected ResultStatus doInBackground(Void... voids) {
        WebserviceGET webserviceGET = new WebserviceGET(URLs.REVIEW_BUSINESS, new ArrayList<>(
                Arrays.asList(String.valueOf(userID), String.valueOf(businessID),
                        review,String.valueOf(rate))));

        try {
            serverAnswer = webserviceGET.execute();
            if (serverAnswer.getSuccessStatus())
                return ResultStatus.getResultStatus(serverAnswer);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            serverAnswer = null;
        }
        return null;
    }

    @Override
    protected void onPostExecute(ResultStatus result) {
      /*  if (result == null)
            delegate.getError(serverAnswer.getErrorCode());
        else
            delegate.getResult(result);*/

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
