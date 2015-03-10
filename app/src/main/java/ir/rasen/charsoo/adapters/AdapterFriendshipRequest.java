package ir.rasen.charsoo.adapters;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import ir.rasen.charsoo.R;
import ir.rasen.charsoo.classes.User;
import ir.rasen.charsoo.helper.BaseAdapterItem;
import ir.rasen.charsoo.helper.Image_M;
import ir.rasen.charsoo.helper.LoginInfo;
import ir.rasen.charsoo.helper.TestUnit;
import ir.rasen.charsoo.ui.ImageViewCircle;
import ir.rasen.charsoo.ui.TextViewFont;
import ir.rasen.charsoo.webservices.DownloadImages;
import ir.rasen.charsoo.webservices.friend.AnswerRequestFriendship;

/**
 * Created by android on 3/7/2015.
 */
public class AdapterFriendshipRequest extends BaseAdapter {

    private ArrayList<BaseAdapterItem> items;
    private Context context;
    DownloadImages downloadImages;
    ListView listView;
    ArrayList<BaseAdapterItem> acceptedUsers;


    public AdapterFriendshipRequest(Context context, ArrayList<BaseAdapterItem> items) {
        this.context = context;
        this.items = items;
        downloadImages = new DownloadImages(context);
        acceptedUsers = new ArrayList<>();
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
        final Holder holder;

        if (listView == null) {
            listView = (ListView) viewGroup;
            listView.setSelector(new ColorDrawable(0x00ffffff));
        }

        if (view == null) {
            holder = new Holder();
            view = LayoutInflater.from(context).inflate(R.layout.item_friendship_requests, viewGroup, false);
            holder.imageViewImage = (ImageViewCircle) view.findViewById(R.id.imageView_base_adapter_item_image);
            holder.textViewUserIdentifier = (TextViewFont) view.findViewById(R.id.textView_base_adapter_item_title);
            holder.imageViewYes = (ImageView) view.findViewById(R.id.imageView_yes);
            holder.imageViewNo = (ImageView) view.findViewById(R.id.imageView_no);
            view.setTag(holder);
        } else
            holder = (Holder) view.getTag();

        holder.textViewUserIdentifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User.goUserHomeInfoPage(items.get(position).getId());
            }
        });
        holder.imageViewYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerYes(position, holder);
            }
        });
        holder.imageViewNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerNo(position);
            }
        });
        //download image with customized class via imageId
        downloadImages.download(items.get(position).getImageId(), Image_M.SMALL, holder.imageViewImage);
        holder.textViewUserIdentifier.setText(items.get(position).getTitle());

        return view;
    }


    private void answerNo(int position) {
        items.remove(position);
        notifyDataSetChanged();
        if (!TestUnit.isTesting)
            new AnswerRequestFriendship(LoginInfo.getUserId(context), items.get(position).getId(), false).execute();
    }

    private void answerYes(int position, Holder holder) {
        holder.imageViewYes.setImageResource(R.drawable.ic_check_green);
        holder.imageViewNo.setVisibility(View.GONE);
        acceptedUsers.add(items.get(position));
        if (!TestUnit.isTesting)
            new AnswerRequestFriendship(LoginInfo.getUserId(context), items.get(position).getId(), true).execute();
    }

    public ArrayList<BaseAdapterItem> getAcceptedUsers() {
        return acceptedUsers;
    }

    private class Holder {
        ImageViewCircle imageViewImage;
        TextViewFont textViewUserIdentifier;
        ImageView imageViewYes;
        ImageView imageViewNo;
    }
}
