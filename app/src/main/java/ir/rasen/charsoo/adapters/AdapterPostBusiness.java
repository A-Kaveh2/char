package ir.rasen.charsoo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import ir.rasen.charsoo.R;
import ir.rasen.charsoo.classes.Business;
import ir.rasen.charsoo.classes.Comment;
import ir.rasen.charsoo.classes.Post;
import ir.rasen.charsoo.classes.User;
import ir.rasen.charsoo.dialog.DialogDeletePostConfirmation;
import ir.rasen.charsoo.dialog.PopupReportPost;
import ir.rasen.charsoo.helper.Image_M;
import ir.rasen.charsoo.helper.PersianDate;
import ir.rasen.charsoo.helper.TestUnit;
import ir.rasen.charsoo.interfaces.IDeletePost;
import ir.rasen.charsoo.interfaces.IReportPost;
import ir.rasen.charsoo.ui.TextViewFont;
import ir.rasen.charsoo.webservices.DownloadImages;
import ir.rasen.charsoo.webservices.post.CancelShare;
import ir.rasen.charsoo.webservices.post.Like;
import ir.rasen.charsoo.webservices.post.Share;
import ir.rasen.charsoo.webservices.post.Unlike;

/**
 * Created by android on 3/7/2015.
 */
public class AdapterPostBusiness extends BaseAdapter implements IDeletePost {

    private ArrayList<Post> items;
    private Context context;
    DownloadImages downloadImages;
    private int screedWidth;
    private IDeletePost iDeletePost;


    public AdapterPostBusiness(Context context, ArrayList<Post> items) {
        this.context = context;
        this.items = items;
        downloadImages = new DownloadImages(context);
        screedWidth = context.getResources().getDisplayMetrics().widthPixels;
        iDeletePost = this;
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

        if (view == null) {
            holder = new Holder();
            view = LayoutInflater.from(context).inflate(R.layout.item_post_adapter_list_business_post, viewGroup, false);
            holder.llAnnouncementSection = (LinearLayout) view.findViewById(R.id.ll_announcement);
            holder.imageViewProfileImage = (ImageView) view.findViewById(R.id.imageView_profile_picture);
            holder.textViewBusinessIdentifier = (TextViewFont) view.findViewById(R.id.textView_business_identifier);
            holder.textViewDate = (TextViewFont) view.findViewById(R.id.textView_date);

            holder.imageViewPost = (ImageView) view.findViewById(R.id.imageView_post);
            //to display post picture as square
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, screedWidth);
            holder.imageViewPost.setLayoutParams(params);

            holder.imageViewEdit = (ImageView) view.findViewById(R.id.imageView_edit);
            holder.imageViewComment = (ImageView) view.findViewById(R.id.imageView_comment);
            holder.imageViewDelete = (ImageView) view.findViewById(R.id.imageView_delete);
            holder.textViewLikeNumber = (TextViewFont) view.findViewById(R.id.textView_like_number);
            holder.textViewCommentNumber = (TextViewFont) view.findViewById(R.id.textView_comment_number);
            holder.textViewShareNumber = (TextViewFont) view.findViewById(R.id.textView_share_number);
            holder.textViewDescription = (TextViewFont) view.findViewById(R.id.textView_description);
            holder.textViewComment1 = (TextViewFont) view.findViewById(R.id.textView_comment1);
            holder.textViewComment1UserIdentifier = (TextViewFont) view.findViewById(R.id.textView_comment1_user_identifier);
            holder.textViewComment2 = (TextViewFont) view.findViewById(R.id.textView_comment2);
            holder.textViewComment2UserIdentifier = (TextViewFont) view.findViewById(R.id.textView_comment2_user_identifier);
            holder.textViewComment3 = (TextViewFont) view.findViewById(R.id.textView_comment3);
            holder.textViewComment3UserIdentifier = (TextViewFont) view.findViewById(R.id.textView_comment3_user_identifier);

            view.setTag(holder);

        } else
            holder = (Holder) view.getTag();

        holder.llAnnouncementSection.setVisibility(View.GONE);

        //all post's types have these three fields
        downloadImages.download(items.get(position).businessProfilePictureId, Image_M.SMALL, holder.imageViewProfileImage);
        holder.textViewDate.setText(PersianDate.getCreationDate(context, items.get(position).creationDate));
        holder.textViewBusinessIdentifier.setText(items.get(position).businessUserName);

        downloadImages.download(items.get(position).pictureId, Image_M.LARGE, holder.imageViewPost);
        holder.textViewLikeNumber.setText(String.valueOf(items.get(position).likeNumber));
        holder.textViewCommentNumber.setText(String.valueOf(items.get(position).commentNumber));
        holder.textViewShareNumber.setText(String.valueOf(items.get(position).shareNumber));
        holder.textViewDescription.setText(items.get(position).description);

        if (items.get(position).lastThreeComments.size() > 0) {
            holder.textViewComment1UserIdentifier.setText(items.get(position).lastThreeComments.get(0).username);
            holder.textViewComment2UserIdentifier.setText(items.get(position).lastThreeComments.get(1).username);
            holder.textViewComment3UserIdentifier.setText(items.get(position).lastThreeComments.get(2).username);
            holder.textViewComment1.setText(items.get(position).lastThreeComments.get(0).text);
            holder.textViewComment2.setText(items.get(position).lastThreeComments.get(1).text);
            holder.textViewComment3.setText(items.get(position).lastThreeComments.get(2).text);
        }


        holder.textViewComment1UserIdentifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User.goUserHomeInfoPage(items.get(position).lastThreeComments.get(0).userID);
                Toast.makeText(context, "Go user home info", Toast.LENGTH_LONG).show();
            }
        });
        holder.textViewComment2UserIdentifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User.goUserHomeInfoPage(items.get(position).lastThreeComments.get(1).userID);
                Toast.makeText(context, "Go user home info", Toast.LENGTH_LONG).show();
            }
        });
        holder.textViewComment3UserIdentifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User.goUserHomeInfoPage(items.get(position).lastThreeComments.get(2).userID);
                Toast.makeText(context, "Go user home info", Toast.LENGTH_LONG).show();
            }
        });

        holder.imageViewComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Comment.openCommentActivity(items.get(position).id);
                Toast.makeText(context, "Go comment activity", Toast.LENGTH_LONG).show();
            }
        });
        holder.imageViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Go edit post activity", Toast.LENGTH_LONG).show();
            }
        });

        holder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogDeletePostConfirmation d= new DialogDeletePostConfirmation(context,items.get(position).businessID,items.get(position).id,iDeletePost);
                d.show();
            }
        });


        return view;
    }


    @Override
    public void notifyDeletePost(int postId) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).id == postId)
                items.remove(i);
        }
        notifyDataSetChanged();
    }

    private class Holder {

        ImageView imageViewProfileImage;
        TextViewFont textViewBusinessIdentifier;
        TextViewFont textViewDate;

        //complete post section
        LinearLayout llCompleteSection;
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
        ImageView imageViewEdit;
        ImageView imageViewComment;
        ImageView imageViewDelete;
        LinearLayout llAnnouncementSection;
    }
}
