package ir.rasen.charsoo.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import ir.rasen.charsoo.R;
import ir.rasen.charsoo.classes.User;
import ir.rasen.charsoo.dialog.PopupCancelFriendship;
import ir.rasen.charsoo.dialog.PopupEditDeleteReview;
import ir.rasen.charsoo.helper.BaseAdapterItem;
import ir.rasen.charsoo.helper.Image_M;
import ir.rasen.charsoo.interfaces.ICancelFriendship;
import ir.rasen.charsoo.interfaces.IWebserviceResponse;
import ir.rasen.charsoo.ui.ImageViewCircle;
import ir.rasen.charsoo.ui.TextViewFont;
import ir.rasen.charsoo.webservices.DownloadImages;

/**
 * Created by android on 3/7/2015.
 */
public class AdapterFriends extends BaseAdapter implements ICancelFriendship {

    private ArrayList<BaseAdapterItem> items;
    private Context context;
    DownloadImages downloadImages;
    ICancelFriendship iCancelFriendship;
    IWebserviceResponse iWebserviceResponse;
    ProgressDialog progressDialog;


    public AdapterFriends(Context context, ArrayList<BaseAdapterItem> items, IWebserviceResponse iWebserviceResponse, ProgressDialog progressDialog) {
        this.context = context;
        this.items = items;
        downloadImages = new DownloadImages(context);
        iCancelFriendship = this;
        this.iWebserviceResponse = iWebserviceResponse;
        this.progressDialog = progressDialog;
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

        if (view == null) {
            holder = new Holder();
            view = LayoutInflater.from(context).inflate(R.layout.item_base_adapter_cirecle_image_selectable, viewGroup, false);
            holder.imageViewImage = (ImageViewCircle) view.findViewById(R.id.imageView_base_adapter_item_image);
            holder.textViewUserIdentifier = (TextViewFont) view.findViewById(R.id.textView_base_adapter_item_title);
            view.setTag(holder);
        } else
            holder = (Holder) view.getTag();

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupCancelFriendship p = new PopupCancelFriendship(context,items.get(position).getId(),iWebserviceResponse,progressDialog,iCancelFriendship);
                p.show();
                return false;
            }
        });

        holder.textViewUserIdentifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User.goUserHomeInfoPage(items.get(position).getId());
            }
        });

        //download image with customized class via imageId
        downloadImages.download(items.get(position).getImageId(), Image_M.SMALL, holder.imageViewImage);
        holder.textViewUserIdentifier.setText(items.get(position).getTitle());

        return view;
    }

    @Override
    public void notifyDeleteFriend(int userId) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() == userId)
                items.remove(i);
        }
        notifyDataSetChanged();
    }
    //Each item in this adapter has a picture and a title

    private class Holder {
        ImageViewCircle imageViewImage;
        TextViewFont textViewUserIdentifier;
    }
}
