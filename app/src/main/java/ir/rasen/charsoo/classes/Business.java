package ir.rasen.charsoo.classes;

import java.util.ArrayList;

import ir.rasen.charsoo.helper.Location_M;
import ir.rasen.charsoo.helper.WorkTime;


/**
 * Created by android on 12/1/2014.
 */
public class Business {
    public int id;
    public String businessUserName;
    public int userID;
    public String name;
    public String coverPicture;
    public int coverPictureId;
    public String profilePicture;
    public int profilePictureId;
    public String category;
    public String subcategory;
    public int categoryID;
    public int subCategoryID;
    public String description;
    public WorkTime workTime = new WorkTime();
    public String phone;
    public String state;
    public String city;
    public String address;
    public Location_M location_m = new Location_M();
    public String email;
    public String mobile;
    public String webSite;
    public ArrayList<String> hashtagList = new ArrayList<String>();
    public int reviewsNumber;
    public int followersNumber;
    public boolean isFollowing;
    public float rate;



}
