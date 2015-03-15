package ir.rasen.charsoo.adapters;

/**
 * Created by android on 3/14/2015.
 */

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import ir.rasen.charsoo.classes.Post;
import ir.rasen.charsoo.helper.Image_M;
import ir.rasen.charsoo.ui.GridViewHeader;
import ir.rasen.charsoo.webservices.DownloadImages;

public class AdapterPostGrid extends BaseAdapter {
    private Context context;
    ArrayList<Post> items;
    private int screedWidth;
    DownloadImages downloadImages;
    GridViewHeader gridView;

    // Constructor
    public AdapterPostGrid(Context c, ArrayList<Post> posts) {
        context = c;
        items = posts;
        screedWidth = context.getResources().getDisplayMetrics().widthPixels;
        downloadImages = new DownloadImages(context);
    }

    public int getCount() {
        return items.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView imageView;
      /*  if(gridView == null){
            gridView = (GridViewHeader) parent;
            gridView.setVerticalSpacing(3);
            gridView.setHorizontalSpacing(9);
        }*/
        if (convertView == null) {
            imageView = new ImageView(context);
           /* LinearLayout linearLayout = new LinearLayout(context);
            ViewGroup.LayoutParams params = null;

            switch (position%3){
                case 0:
                    params =  new ViewGroup.LayoutParams(screedWidth/3, screedWidth/3);
                    break;
                case 1:
                    params =  new ViewGroup.LayoutParams(screedWidth/3, screedWidth/3);
                    break;
                case 2:
                    params =  new ViewGroup.LayoutParams((screedWidth/3)-4, screedWidth/3);
                    break;
            }

            linearLayout.setGravity(Gravity.TOP|Gravity.LEFT);
            linearLayout.setLayoutParams(params);*/
            imageView.setLayoutParams(new GridView.LayoutParams((screedWidth/3), (screedWidth/3)));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }

        downloadImages.download(items.get(position).pictureId, Image_M.MEDIUM,imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Post.goPostPage(context,items.get(position).id);
            }
        });

        return imageView;
    }
}
