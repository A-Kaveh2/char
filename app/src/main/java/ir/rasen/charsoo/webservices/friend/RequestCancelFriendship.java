package ir.rasen.charsoo.webservices.friend;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

import ir.rasen.charsoo.helper.ResultStatus;
import ir.rasen.charsoo.helper.ServerAnswer;
import ir.rasen.charsoo.helper.URLs;
import ir.rasen.charsoo.interfaces.ICancelFriendship;
import ir.rasen.charsoo.interfaces.IWebserviceResponse;
import ir.rasen.charsoo.webservices.WebserviceGET;


/**
 * Created by android on 12/16/2014.
 */
public class RequestCancelFriendship extends AsyncTask<Void, Void, ResultStatus> {
    private static final String TAG = "RequestCancelFriendship";

    private IWebserviceResponse delegate = null;
    private int applicatorUserID;
    private int requestedUserID;
    private ServerAnswer serverAnswer;
    private ICancelFriendship iCancelFriendship;

    public RequestCancelFriendship(int applicatorUserID, int requestedUserID, IWebserviceResponse delegate,ICancelFriendship iCancelFriendship) {
        this.applicatorUserID = applicatorUserID;
        this.requestedUserID = requestedUserID;
        this.delegate = delegate;
        this.iCancelFriendship = iCancelFriendship;
    }

    @Override
    protected ResultStatus doInBackground(Void... voids) {
        WebserviceGET webserviceGET = new WebserviceGET(URLs.REQUEST_CANCEL_FRIENDSHIP, new ArrayList<>(
                Arrays.asList(String.valueOf(applicatorUserID),
                        String.valueOf(requestedUserID))));

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
        if (serverAnswer == null) {
            delegate.getError(ServerAnswer.EXECUTION_ERROR);
            return;
        }
        if (serverAnswer.getSuccessStatus()) {
            delegate.getResult(result);
            iCancelFriendship.notifyDeleteFriend(requestedUserID);
        }
        else
            delegate.getError(serverAnswer.getErrorCode());
    }
}
