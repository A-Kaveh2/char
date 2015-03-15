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
import ir.rasen.charsoo.adapters.AdapterPostBusiness;
import ir.rasen.charsoo.adapters.AdapterPostGrid;
import ir.rasen.charsoo.adapters.AdapterPostShared;
import ir.rasen.charsoo.classes.Post;
import ir.rasen.charsoo.webservices.DownloadCoverImage;

/**
 * Created by android on 3/14/2015.
 */
public class GridViewBusiness {
    static GridViewHeader gridViewHeader;
    static AdapterPostGrid adapterPostGrid;
    static AdapterPostBusiness adapterPostBusiness;
    static private boolean isThreeColumn = true;

    static ImageView  imageViewMore, imageViewSwitch, imageViewCover;
    static TextViewFont textViewFollowers, textViewContactInfo, textViewReviews;

    static View viewHeader;

    public static void InitialGridViewBusiness(final Context context,int profilePictureId, ArrayList<Post> posts, final GridViewHeader gridViewHeader, final DrawerLayout drawerLayout) {


        adapterPostGrid = new AdapterPostGrid(context,posts);
        adapterPostBusiness = new AdapterPostBusiness(context,posts);




        viewHeader = ((Activity) context).getLayoutInflater().inflate(R.layout.layout_business_grid_header, null);
        imageViewMore = (ImageView) viewHeader.findViewById(R.id.imageView_more);

        imageViewSwitch = (ImageView) viewHeader.findViewById(R.id.imageView_switch);
        imageViewCover = (ImageView) viewHeader.findViewById(R.id.imageView_cover);
        textViewContactInfo = (TextViewFont) viewHeader.findViewById(R.id.textView_contact_info);
        textViewFollowers = (TextViewFont) viewHeader.findViewById(R.id.textView_followers);
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



        textViewContactInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Open  business contact info page", Toast.LENGTH_LONG).show();
            }
        });
        textViewFollowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Open business followers page", Toast.LENGTH_LONG).show();
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
                    gridViewHeader.setAdapter(adapterPostBusiness);
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
