package ir.rasen.charsoo.webservices.post;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import ir.rasen.charsoo.classes.Post;
import ir.rasen.charsoo.helper.ServerAnswer;
import ir.rasen.charsoo.helper.URLs;
import ir.rasen.charsoo.interfaces.IWebserviceResponse;
import ir.rasen.charsoo.webservices.WebserviceGET;


/**
 * Created by android on 12/16/2014.
 */
public class GetBusinessPosts extends AsyncTask<Void, Void, ArrayList<Post>> {
    private static final String TAG = "GetPosts";
    private IWebserviceResponse delegate = null;
    private int businessID,userId;
    private int afterThisID,limitation;
    private ServerAnswer serverAnswer;

    public GetBusinessPosts(int userId,int businessID, int afterThisID, int limitation,IWebserviceResponse delegate) {
        this.businessID = businessID;
        this.userId = userId;
        this.afterThisID = afterThisID;
        this.limitation = limitation;
        this.delegate = delegate;
    }

    @Override
    protected ArrayList<Post> doInBackground(Void... voids) {
        ArrayList<Post> list = new ArrayList<Post>();
        WebserviceGET webserviceGET = new WebserviceGET(URLs.GET_POSTS,new ArrayList<>(
                Arrays.asList(String.valueOf(userId),String.valueOf(businessID),
                        String.valueOf(afterThisID),
                        String.valueOf(limitation))));


        try {
            serverAnswer = webserviceGET.executeList();
            if (serverAnswer.getSuccessStatus()) {
                JSONArray jsonArray = serverAnswer.getResultList();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    list.add(Post.getFromJSONObjectBusiness(businessID,jsonObject));
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
    protected void onPostExecute(ArrayList<Post> result) {


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
