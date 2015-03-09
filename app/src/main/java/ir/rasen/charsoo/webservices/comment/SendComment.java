package ir.rasen.charsoo.webservices.comment;

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
public class SendComment extends AsyncTask<Void, Void, ResultStatus> {
    private static final String TAG = "SendComment";

    private IWebserviceResponse delegate = null;
    private int userID;
    private int postID;
    private String comment;
    private ServerAnswer serverAnswer;

    public SendComment(int userID, int postID, String comment, IWebserviceResponse delegate) {
        this.userID = userID;
        this.postID = postID;
        this.comment = comment;
        this.delegate = delegate;
    }

    @Override
    protected ResultStatus doInBackground(Void... voids) {
        WebserviceGET webserviceGET = new WebserviceGET(URLs.SEND_COMMENT, new ArrayList<>(
                Arrays.asList(String.valueOf(userID), String.valueOf(postID), comment)));


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
