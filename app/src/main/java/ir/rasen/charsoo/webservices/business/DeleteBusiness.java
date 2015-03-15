package ir.rasen.charsoo.webservices.business;

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
public class DeleteBusiness extends AsyncTask<Void, Void, ResultStatus> {
    private static final String TAG = "DeleteBusiness";

    private IWebserviceResponse delegate = null;
    private int businessID;
    private int userID;
    private ServerAnswer serverAnswer;

    public DeleteBusiness(int userID, int businessID) {
        //this.delegate = delegate;
        this.businessID = businessID;
        this.userID = userID;
    }

    @Override
    protected ResultStatus doInBackground(Void... voids) {
        WebserviceGET webserviceGET = new WebserviceGET(URLs.DELETE_BUSINESS, new ArrayList<>(
                Arrays.asList(String.valueOf(businessID),String.valueOf( userID))));

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

        /*if (serverAnswer == null) {
            delegate.getError(ServerAnswer.EXECUTION_ERROR);
            return;
        }
        if (serverAnswer.getSuccessStatus())
            delegate.getResult(result);
        else
            delegate.getError(serverAnswer.getErrorCode());*/
    }
}
