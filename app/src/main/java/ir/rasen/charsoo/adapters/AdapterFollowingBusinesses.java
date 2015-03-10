package ir.rasen.charsoo.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import ir.rasen.charsoo.R;
import ir.rasen.charsoo.classes.Business;
import ir.rasen.charsoo.classes.User;
import ir.rasen.charsoo.dialog.PopupCancelFriendship;
import ir.rasen.charsoo.dialog.PopupUnfollowBusiness;
import ir.rasen.charsoo.helper.BaseAdapterItem;
import ir.rasen.charsoo.helper.Image_M;
import ir.rasen.charsoo.interfaces.ICancelFriendship;
import ir.rasen.charsoo.interfaces.IUnfollowBusiness;
import ir.rasen.charsoo.interfaces.IWebserviceResponse;
import ir.rasen.charsoo.ui.ImageViewCircle;
import ir.rasen.charsoo.ui.TextViewFont;
import ir.rasen.charsoo.webservices.DownloadImages;

/**
 * Created by android on 3/7/2015.
 */
public class AdapterFollowingBusinesses extends BaseAdapter implements IUnfollowBusiness {

    private ArrayList<BaseAdapterItem> items;
    private Context context;
    DownloadImages downloadImages;
    IUnfollowBusiness iUnfollowBusiness;
    IWebserviceResponse iWebserviceResponse;
    ProgressDialog progressDialog;


    public AdapterFollowingBusinesses(Context context, ArrayList<BaseAdapterItem> items, IWebserviceResponse iWebserviceResponse, ProgressDialog progressDialog) {
        this.context = context;
        this.items = items;
        downloadImages = new DownloadImages(context);
        iUnfollowBusiness = this;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_base_adapter_squar_image_selectable, viewGroup, false);
            holder.imageViewImage = (ImageView) view.findViewById(R.id.imageView_base_adapter_item_image);
            holder.textViewUserIdentifier = (TextViewFont) view.findViewById(R.id.textView_base_adapter_item_title);
            view.setTag(holder);
        } else
            holder = (Holder) view.getTag();

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupUnfollowBusiness p = new PopupUnfollowBusiness(context,items.get(position).getId(),iWebserviceResponse,progressDialog,iUnfollowBusiness);
                p.show();
                return false;
            }
        });

        holder.textViewUserIdentifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Business.goBusinessHomeInfoPage(items.get(position).getId());
            }
        });

        //download image with customized class via imageId
        downloadImages.download(items.get(position).getImageId(), Image_M.SMALL, holder.imageViewImage);
        holder.textViewUserIdentifier.setText(items.get(position).getTitle());

        return view;
    }



    @Override
    public void notifyUnfollowBusiness(int businessId) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() == businessId)
                items.remove(i);
        }
        notifyDataSetChanged();
    }

    //Each item in this adapter has a picture and a title

    private class Holder {
        ImageView imageViewImage;
        TextViewFont textViewUserIdentifier;
    }
}
