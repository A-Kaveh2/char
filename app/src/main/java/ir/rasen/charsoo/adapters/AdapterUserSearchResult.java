package ir.rasen.charsoo.adapters;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ir.rasen.charsoo.R;
import ir.rasen.charsoo.classes.User;
import ir.rasen.charsoo.helper.BaseAdapterItem;
import ir.rasen.charsoo.helper.Image_M;
import ir.rasen.charsoo.interfaces.IUnfollowBusiness;
import ir.rasen.charsoo.ui.ImageViewCircle;
import ir.rasen.charsoo.ui.TextViewFont;
import ir.rasen.charsoo.webservices.DownloadImages;

/**
 * Created by android on 3/7/2015.
 */
public class AdapterUserSearchResult extends BaseAdapter {

    private ArrayList<BaseAdapterItem> items;
    private Context context;
    DownloadImages downloadImages;
    ListView listView;


    public AdapterUserSearchResult(Context context, ArrayList<BaseAdapterItem> items) {
        this.context = context;
        this.items = items;
        downloadImages = new DownloadImages(context);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        Holder holder;

        if(listView == null) {
            listView = (ListView) viewGroup;
            listView.setSelector(new ColorDrawable(0x00ffffff));
        }

        if (view == null) {
            holder = new Holder();
            view = LayoutInflater.from(context).inflate(R.layout.item_base_adapter_cirecle_image_un_selectable, viewGroup, false);
            holder.imageViewImage = (ImageViewCircle) view.findViewById(R.id.imageView_base_adapter_item_image);
            holder.textViewUserIdentifier = (TextViewFont) view.findViewById(R.id.textView_base_adapter_item_title);
            view.setTag(holder);
        } else
            holder = (Holder) view.getTag();

        holder.textViewUserIdentifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User.goUserHomeInfoPage(items.get(position).getId());
                Toast.makeText(context, "GO to user home info", Toast.LENGTH_LONG).show();
            }
        });
        //download image with customized class via imageId
        downloadImages.download(items.get(position).getImageId(), Image_M.SMALL, holder.imageViewImage);
        holder.textViewUserIdentifier.setText(items.get(position).getTitle());

        return view;
    }



    private class Holder {
        ImageViewCircle imageViewImage;
        TextViewFont textViewUserIdentifier;
    }
}
