package ir.rasen.charsoo.classes;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import ir.rasen.charsoo.helper.FriendshipRelation;
import ir.rasen.charsoo.helper.Params;
import ir.rasen.charsoo.helper.Permission;
import ir.rasen.charsoo.helper.Sex;


/**
 * Created by A.Kaveh on 11/29/2014.
 */
public class User {

    public int id;
    public String userName;
    public String name;
    public String email;
    public boolean isEmailConfirmed;
    public String password;
    public String aboutMe;
    public Sex sex;
    public String birthDate;
    public String profilePicture;
    public int profilePictureId;
    public int coverPictureId;
    public String coverPicture;
    public Permission permissions;
    public FriendshipRelation.Status friendshipRelationStatus;
    public int friendRequestNumber;
    public int reviewsNumber;
    public int followedBusinessesNumber;
    public int friendsNumber;
    public ArrayList<UserBusinesses> businesses;

    public static class UserBusinesses{
        public int businessId;
        public String businessUserName;
    }

    public static ArrayList<UserBusinesses> getUserBusinesses(JSONArray jsonArray)throws Exception {
        ArrayList<UserBusinesses> businesses = new ArrayList<>();

        for (int j = 0; j < jsonArray.length(); j++) {
            JSONObject jsonObject = jsonArray.getJSONObject(j);
            UserBusinesses userBusinesses = new UserBusinesses();
            userBusinesses.businessId = Integer.valueOf(jsonObject.getString(Params.BUSINESS_ID));
            userBusinesses.businessUserName = jsonObject.getString(Params.BUSINESS_USER_NAME);
            businesses.add(userBusinesses);
        }

        return businesses;
    }
}
