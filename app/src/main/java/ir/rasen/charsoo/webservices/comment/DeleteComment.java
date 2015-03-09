package ir.rasen.charsoo.webservices.comment;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

import ir.rasen.charsoo.helper.ResultStatus;
import ir.rasen.charsoo.helper.ServerAnswer;
import ir.rasen.charsoo.helper.URLs;
import ir.rasen.charsoo.interfaces.ICommentChange;
import ir.rasen.charsoo.interfaces.IWebserviceResponse;
import ir.rasen.charsoo.webservices.WebserviceGET;


/**
 * Created by android on 12/16/2014.
 */
public class DeleteComment extends AsyncTask<Void, Void, ResultStatus> {
    private static final String TAG = "DeleteComment ";

    private IWebserviceResponse delegate = null;
    private ICommentChange iCommentChange = null;
    private int businessID;
    private int commentID;
    private ServerAnswer serverAnswer;

    //if userID = comment.userID delete the comment which write the user with id = userID
    //if userID != comment.userID (user is not the writer) and userID == comment.businessID delete the comment which user with id=userID is owner of the business which is owner of the post

    public DeleteComment(int businessID, int commentID, IWebserviceResponse delegate,ICommentChange iCommentChange) {
        this.delegate = delegate;
        this.businessID = businessID;
        this.commentID = commentID;
        this.iCommentChange = iCommentChange;
    }

    @Override
    protected ResultStatus doInBackground(Void... voids) {
        WebserviceGET webserviceGET = new WebserviceGET(URLs.DELETE_COMMENT,new ArrayList<>(
                Arrays.asList(String.valueOf(businessID), String.valueOf(commentID))));

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
        if (serverAnswer.getSuccessStatus()) {
            delegate.getResult(result);
            iCommentChange.notifyDeleteComment(commentID);
        }
        else
            delegate.getError(serverAnswer.getErrorCode());

    }
}
