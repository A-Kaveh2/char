package ir.rasen.charsoo.webservices.post;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.internal.id;

import ir.rasen.charsoo.classes.Post;
import ir.rasen.charsoo.helper.Hashtag;
import ir.rasen.charsoo.helper.Params;
import ir.rasen.charsoo.helper.ServerAnswer;
import ir.rasen.charsoo.helper.URLs;
import ir.rasen.charsoo.interfaces.IWebserviceResponse;
import ir.rasen.charsoo.webservices.WebservicePOST;

/**
 * Created by android on 12/16/2014.
 */
public class AddPost extends AsyncTask<Void, Void, Post> {
    private static final String TAG = "AddPost";

    private IWebserviceResponse delegate = null;
    private Post post;
    private ServerAnswer serverAnswer;

    public AddPost(Post post,IWebserviceResponse delegate) {
        this.post = post;
        this.delegate = delegate;
    }

    @Override
    protected Post doInBackground(Void... voids) {
        WebservicePOST webservicePOST = new WebservicePOST(URLs.ADD_POST);

        try {
            webservicePOST.addParam(Params.BUSINESS_ID, String.valueOf(post.businessID));
            webservicePOST.addParam(Params.TITLE, post.title);
            webservicePOST.addParam(Params.PICTURE, post.picture);
            webservicePOST.addParam(Params.DESCRIPTION, post.description);
            webservicePOST.addParam(Params.PRICE, post.price);
            webservicePOST.addParam(Params.CODE, post.code);
            webservicePOST.addParam(Params.HASHTAG_LIST, Hashtag.getStringFromList(post.hashtagList));

            serverAnswer = webservicePOST.execute();
            if (serverAnswer.getSuccessStatus()) {
                post.id = Integer.valueOf(serverAnswer.getResult().getString(Params.POST_ID));
                return post;
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            serverAnswer = null;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Post result) {


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
