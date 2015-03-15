package ir.rasen.charsoo.ui;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import ir.rasen.charsoo.R;
import ir.rasen.charsoo.adapters.AdapterPostGrid;
import ir.rasen.charsoo.adapters.AdapterPostShared;
import ir.rasen.charsoo.classes.Post;
import ir.rasen.charsoo.webservices.DownloadCoverImage;

/**
 * Created by android on 3/14/2015.
 */
public class GridViewUser {
    static GridViewHeader gridViewHeader;
    static AdapterPostGrid adapterPostGrid;
    static AdapterPostShared adapterPostShared;
    static private boolean isThreeColumn = true;

    static ImageView imageViewSearch, imageViewMore, imageViewSwitch, imageViewCover;
    static TextViewFont textViewFriends, textViewBusinesses, textViewReviews;

    static View viewHeader;

    public static void InitialGridViewUser(final Context context,int profilePictureId, ArrayList<Post> posts, final GridViewHeader gridViewHeader, final DrawerLayout drawerLayout) {


        adapterPostGrid = new AdapterPostGrid(context,posts);
        adapterPostShared = new AdapterPostShared(context,posts);




        viewHeader = ((Activity) context).getLayoutInflater().inflate(R.layout.layout_user_grid_header, null);
        imageViewMore = (ImageView) viewHeader.findViewById(R.id.imageView_more);
        imageViewSearch = (ImageView) viewHeader.findViewById(R.id.imageView_search);
        imageViewSwitch = (ImageView) viewHeader.findViewById(R.id.imageView_switch);
        imageViewCover = (ImageView) viewHeader.findViewById(R.id.imageView_cover);
        textViewBusinesses = (TextViewFont) viewHeader.findViewById(R.id.textView_businesses);
        textViewFriends = (TextViewFont) viewHeader.findViewById(R.id.textView_friends);
        textViewReviews = (TextViewFont) viewHeader.findViewById(R.id.textView_reviews);

        int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (screenWidth/3)*2);
        imageViewCover.setLayoutParams(params);
        DownloadCoverImage downloadCoverImage = new DownloadCoverImage(context);
        downloadCoverImage.download(profilePictureId,imageViewCover);

        imageViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerOpen(Gravity.RIGHT))
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                else
                    drawerLayout.openDrawer(Gravity.RIGHT);
            }
        });

        imageViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Open search page", Toast.LENGTH_LONG).show();
            }
        });

        textViewBusinesses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Open user following businesses page", Toast.LENGTH_LONG).show();
            }
        });
        textViewFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Open user friends page", Toast.LENGTH_LONG).show();
            }
        });
        textViewReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Open user reviews page", Toast.LENGTH_LONG).show();
            }
        });

        imageViewSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isThreeColumn){
                    gridViewHeader.setNumColumns(1);
                    gridViewHeader.setAdapter(adapterPostShared);
                    //now it has one column
                    isThreeColumn = false;
                    imageViewSwitch.setImageResource(R.drawable.selector_header_swtich_grid);
                }
                else {
                    prepareGridThreeColumn(gridViewHeader);
                    gridViewHeader.setAdapter(adapterPostGrid);
                    // now it has three column
                    isThreeColumn = true;
                    imageViewSwitch.setImageResource(R.drawable.selector_header_swtich_list);
                }
            }
        });

        gridViewHeader.addHeaderView(viewHeader);
        prepareGridThreeColumn(gridViewHeader);
        gridViewHeader.setAdapter(adapterPostGrid);

    }

    private static void prepareGridThreeColumn(GridViewHeader gridViewHeader){
        gridViewHeader.setNumColumns(3);
        gridViewHeader.setVerticalSpacing(3);
        gridViewHeader.setHorizontalSpacing(9);
    }
}
