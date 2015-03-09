package ir.rasen.charsoo.webservices.review;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

import ir.rasen.charsoo.classes.Review;
import ir.rasen.charsoo.helper.ResultStatus;
import ir.rasen.charsoo.helper.ServerAnswer;
import ir.rasen.charsoo.helper.URLs;
import ir.rasen.charsoo.interfaces.IReviewChange;
import ir.rasen.charsoo.interfaces.IWebserviceResponse;
import ir.rasen.charsoo.webservices.WebserviceGET;


/**
 * Created by android on 12/16/2014.
 */
public class UpdateReview extends AsyncTask<Void, Void, ResultStatus> {
    private static final String TAG = "UpdateReview";

    private IWebserviceResponse delegate = null;
    private ServerAnswer serverAnswer;
    private Review review;
    private IReviewChange iReviewChange;

    public UpdateReview(Review review,IWebserviceResponse delegate,IReviewChange iReviewChange) {
        this.delegate = delegate;
        this.review = review;
        this.iReviewChange = iReviewChange;
    }

    @Override
    protected ResultStatus doInBackground(Void... voids) {
        WebserviceGET webserviceGET = new WebserviceGET(URLs.UPDATE_REVIEW, new ArrayList<>(
                Arrays.asList(String.valueOf(review.userID), String.valueOf(review.id),String.valueOf(review.text),String.valueOf(review.rate))));

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
            iReviewChange.notifyUpdateReview(review);
        }
        else
            delegate.getError(serverAnswer.getErrorCode());
    }
}
