package ir.rasen.charsoo.ui;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import ir.rasen.charsoo.R;
import ir.rasen.charsoo.helper.Image_M;
import ir.rasen.charsoo.helper.LoginInfo;
import ir.rasen.charsoo.webservices.DownloadImages;

/**
 * Created by android on 3/15/2015.
 */
public class DrawerLayoutUser {


    public static void Initial(final Context context, DrawerLayout drawerLayout, int userId, String userIdentifier, String userName, int userProfilePictureId) {


        DownloadImages downloadImages = new DownloadImages(context);
        downloadImages.download(userProfilePictureId, Image_M.MEDIUM, (ImageViewCircle) drawerLayout.findViewById(R.id.imageView_user_picture));

        ((ImageView) drawerLayout.findViewById(R.id.imageView_drawer_edit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Open user edit profile page", Toast.LENGTH_LONG).show();
            }
        });

        ((TextViewFont) drawerLayout.findViewById(R.id.textView_drawer_identifier)).setText(userIdentifier);
        ((TextViewFont) drawerLayout.findViewById(R.id.textView_drawer_user_name)).setText(userName);

        ((LinearLayout) drawerLayout.findViewById(R.id.ll_drawer_businesses)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Open user's businesses page", Toast.LENGTH_LONG).show();
            }
        });
        ((LinearLayout) drawerLayout.findViewById(R.id.ll_drawer_setting)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Open user's setting page", Toast.LENGTH_LONG).show();
            }
        });
        ((LinearLayout) drawerLayout.findViewById(R.id.ll_drawer_exit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Log out", Toast.LENGTH_LONG).show();
                LoginInfo.logout(context);
            }
        });
        ((LinearLayout) drawerLayout.findViewById(R.id.ll_drawer_contact_us)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Open contact us page", Toast.LENGTH_LONG).show();
            }
        });
        ((LinearLayout) drawerLayout.findViewById(R.id.ll_drawer_guide)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Open guide page", Toast.LENGTH_LONG).show();
            }
        });
    }




}
