package ir.rasen.charsoo.webservices.post;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.internal.id;

import ir.rasen.charsoo.classes.Post;
import ir.rasen.charsoo.helper.Hashtag;
import ir.rasen.charsoo.helper.Params;
import ir.rasen.charsoo.helper.ResultStatus;
import ir.rasen.charsoo.helper.ServerAnswer;
import ir.rasen.charsoo.helper.URLs;
import ir.rasen.charsoo.interfaces.IWebserviceResponse;
import ir.rasen.charsoo.webservices.WebservicePOST;


/**
 * Created by android on 12/16/2014.
 */
public class UpdatePost extends AsyncTask<Void, Void, ResultStatus> {
    private static final String TAG = "UpdatePost";

    private IWebserviceResponse delegate = null;
    private Post post;
    private ServerAnswer serverAnswer;

    public UpdatePost(Post post,IWebserviceResponse delegate) {
        this.post = post;
        this.delegate = delegate;
    }

    @Override
    protected ResultStatus doInBackground(Void... voids) {
        WebservicePOST webservicePOST = new WebservicePOST(URLs.UPDATE_POST);

        try {
            webservicePOST.addParam(Params.POST_ID, String.valueOf(post.id));
            webservicePOST.addParam(Params.TITLE, post.title);
            webservicePOST.addParam(Params.PICTURE, post.picture);
            webservicePOST.addParam(Params.DESCRIPTION, post.description);
            webservicePOST.addParam(Params.PRICE, post.price);
            webservicePOST.addParam(Params.CODE, post.code);
            webservicePOST.addParam(Params.HASHTAG_LIST, Hashtag.getStringFromList(post.hashtagList));
            //discount will be added  next phase
            webservicePOST.addParam(Params.DISCOUNT,"0");

            serverAnswer = webservicePOST.execute();
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
