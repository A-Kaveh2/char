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
import ir.rasen.charsoo.classes.Comment;
import ir.rasen.charsoo.classes.Post;
import ir.rasen.charsoo.classes.User;
import ir.rasen.charsoo.helper.BaseAdapterItem;
import ir.rasen.charsoo.helper.Image_M;
import ir.rasen.charsoo.ui.ImageViewCircle;
import ir.rasen.charsoo.ui.TextViewFont;
import ir.rasen.charsoo.webservices.DownloadImages;

/**
 * Created by android on 3/7/2015.
 */
public class AdapterPostList extends BaseAdapter {

    private ArrayList<Post> items;
    private Context context;
    DownloadImages downloadImages;
    ListView listView;


    public AdapterPostList(Context context, ArrayList<Post> items) {
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

        if (view == null) {
            holder = new Holder();
            view = LayoutInflater.from(context).inflate(R.layout.item_post_adapter_list, viewGroup, false);
            //dynamic images
            holder.imageViewProfileImage = (ImageViewCircle)view.findViewById(R.id.imageView_profile_picture);
            holder.imageViewPost = (ImageView)view.findViewById(R.id.imageView_post);
            //static images
            holder.imageViewLike = (ImageView)view.findViewById(R.id.imageView_like);
            holder.imageViewComment = (ImageView)view.findViewById(R.id.imageView_comment);
            holder.imageViewShare = (ImageView)view.findViewById(R.id.imageView_share);
            //texts
            holder.textViewUserIdentifier = (TextViewFont) view.findViewById(R.id.textView_base_adapter_item_title);
            holder.textViewDate = (TextViewFont)view.findViewById(R.id.textView_date);
            holder.textViewLikeNumber = (TextViewFont)view.findViewById(R.id.textView_like_number);
            holder.textViewCommentNumber = (TextViewFont)view.findViewById(R.id.textView_comment_number);
            holder.textViewShareNumber = (TextViewFont)view.findViewById(R.id.textView_share_number);
            holder.textViewDescription = (TextViewFont)view.findViewById(R.id.textView_description);
            holder.textViewComment1 = (TextViewFont)view.findViewById(R.id.textView_comment1);
            holder.textViewComment1UserIdentifier = (TextViewFont)view.findViewById(R.id.textView_comment1_user_identifier);
            holder.textViewComment2 = (TextViewFont)view.findViewById(R.id.textView_comment2);
            holder.textViewComment2UserIdentifier = (TextViewFont)view.findViewById(R.id.textView_comment2_user_identifier);
            holder.textViewComment3 = (TextViewFont)view.findViewById(R.id.textView_comment3);
            holder.textViewComment3UserIdentifier = (TextViewFont)view.findViewById(R.id.textView_comment3_user_identifier);

            view.setTag(holder);
        } else
            holder = (Holder) view.getTag();

        //download image with customized class via imageId
        downloadImages.download(items.get(position).businessProfilePictureId, Image_M.SMALL, holder.imageViewProfileImage);
        downloadImages.download(items.get(position).pictureId, Image_M.LARGE, holder.imageViewPost);

        //set texts
        holder.textViewUserIdentifier.setText(items.get(position).businessID);
        holder.textViewDate.setText(items.get(position).creationDate);
        holder.textViewLikeNumber.setText(items.get(position).likeNumber);
        holder.textViewCommentNumber.setText(items.get(position).commentNumber);
        holder.textViewShareNumber.setText(items.get(position).shareNumber);
        holder.textViewDescription.setText(items.get(position).description);
        holder.textViewComment1UserIdentifier.setText(items.get(position).lastThreeComments.get(0).username);
        holder.textViewComment2UserIdentifier.setText(items.get(position).lastThreeComments.get(1).username);
        holder.textViewComment3UserIdentifier.setText(items.get(position).lastThreeComments.get(2).username);
        holder.textViewComment1.setText(items.get(position).lastThreeComments.get(0).text);
        holder.textViewComment2.setText(items.get(position).lastThreeComments.get(1).text);
        holder.textViewComment3.setText(items.get(position).lastThreeComments.get(2).text);

        if(items.get(position).isLiked)
            holder.imageViewLike.setBackgroundResource(R.drawable.ic_favorite_red);
        if(items.get(position).isShared)
            holder.imageViewShare.setBackgroundResource(R.drawable.ic_reply_blue);

        holder.imageViewLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (items.get(position).isLiked){
                    //unlike the post
                }
                else{
                    //like the post
                }

            }
        });
        holder.imageViewComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Comment.openCommentActivity(items.get(position).id);
            }
        });
        holder.imageViewShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(items.get(position).isShared){
                    //cancel share the post
                }
                else {
                    //share the post
                }
            }
        });
        holder.textViewComment1UserIdentifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User.goUserHomeInfoPage(items.get(position).lastThreeComments.get(0).userID);
            }
        });
        holder.textViewComment2UserIdentifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User.goUserHomeInfoPage(items.get(position).lastThreeComments.get(1).userID);
            }
        });
        holder.textViewComment3UserIdentifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User.goUserHomeInfoPage(items.get(position).lastThreeComments.get(2).userID);
            }
        });
        return view;
    }



    private class Holder {
        ImageViewCircle imageViewProfileImage;
        TextViewFont textViewUserIdentifier;
        TextViewFont textViewDate;
        ImageView imageViewPost;
        TextViewFont textViewLikeNumber;
        TextViewFont textViewCommentNumber;
        TextViewFont textViewShareNumber;
        TextViewFont textViewDescription;
        TextViewFont textViewComment1UserIdentifier;
        TextViewFont textViewComment2UserIdentifier;
        TextViewFont textViewComment3UserIdentifier;
        TextViewFont textViewComment1;
        TextViewFont textViewComment2;
        TextViewFont textViewComment3;
        ImageView imageViewLike;
        ImageView imageViewComment;
        ImageView imageViewShare;

    }
}
