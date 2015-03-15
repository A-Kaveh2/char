package ir.rasen.charsoo.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class LoginInfo {


    public int userID;
    public String accessToken;

    public static boolean isLoggedIn(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                context.getPackageName(), Context.MODE_PRIVATE);
        if (preferences.getInt(Params.USER_ID, 0)!= 0)
            return true;
        return false;
    }

    public static void logout(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                context.getPackageName(), Context.MODE_PRIVATE);
        Editor edit = preferences.edit();
        edit.putInt(Params.USER_ID, 0);
        edit.commit();

        //go to the welcome activity


    }

    public static void login(Context context, int userID, String accessToken) {
        SharedPreferences preferences = context.getSharedPreferences(
                context.getPackageName(), Context.MODE_PRIVATE);
        Editor edit = preferences.edit();
        edit.putInt(Params.USER_ID, userID);
        edit.putString(Params.ACCESS_TOKEN, accessToken);

        edit.commit();

    }

    public static int getUserId(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                context.getPackageName(), Context.MODE_PRIVATE);
        int i = preferences.getInt(Params.USER_ID, 0);
        return preferences.getInt(Params.USER_ID, 0);
    }
    public static String getAccessToken(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                context.getPackageName(), Context.MODE_PRIVATE);
        return preferences.getString(Params.ACCESS_TOKEN, "");
    }



}
