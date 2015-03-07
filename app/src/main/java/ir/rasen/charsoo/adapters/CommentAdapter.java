package ir.rasen.charsoo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import ir.rasen.charsoo.R;
import ir.rasen.charsoo.classes.Comment;
import ir.rasen.charsoo.helper.BaseAdapterItem;
import ir.rasen.charsoo.helper.Image_M;
import ir.rasen.charsoo.interfaces.IBaseAdapterOnClickTask;
import ir.rasen.charsoo.ui.ImageViewCircle;
import ir.rasen.charsoo.ui.TextViewFont;
import ir.rasen.charsoo.webservices.DownloadImages;

/**
 * Created by android on 3/7/2015.
 */
public class CommentAdapter extends BaseAdapter {

    private ArrayList<Comment> comments;
    private Context context;
    DownloadImages downloadImages;


    public CommentAdapter(Context context, ArrayList<Comment> comments) {
        this.context = context;
        this.comments = comments;
        downloadImages = new DownloadImages(context);
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int position) {
        return comments.get(position);
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
            view = LayoutInflater.from(context).inflate(R.layout.comment_adapter_item, viewGroup, false);
            holder.imageViewImage = (ImageViewCircle) view.findViewById(R.id.imageView_comment_adapter_item_image);
            holder.textViewTitle = (TextViewFont) view.findViewById(R.id.textView_comment_adapter_item_title);
            holder.textViewText = (TextViewFont) view.findViewById(R.id.textView_comment_adapter_item_text);
            holder.imageViewMore = (ImageView) view.findViewById(R.id.imageView_comment_adapter_more);
            view.setTag(holder);
        } else
            holder = (Holder) view.getTag();


        if(comments.get(position).isPostOwner){
            //getBusinessPosts is executed: the business owner is watching his post
            if()
        }
        else{

        }

        //download image with customized class via imageId
        downloadImages.download(comments.get(position).userProfilePictureID, Image_M.SMALL, holder.imageViewImage);
        holder.textViewTitle.setText(comments.get(position).username);

        return view;
    }
    //Each item in this adapter has a picture and a title

    private class Holder {
        ImageViewCircle imageViewImage;
        TextViewFont textViewTitle;
        TextViewFont textViewText;
        ImageView imageViewMore;
    }
}
