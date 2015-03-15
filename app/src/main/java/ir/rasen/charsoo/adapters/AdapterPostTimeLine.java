package ir.rasen.charsoo.adapters;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import ir.rasen.charsoo.R;
import ir.rasen.charsoo.classes.Business;
import ir.rasen.charsoo.classes.Comment;
import ir.rasen.charsoo.classes.Post;
import ir.rasen.charsoo.classes.User;
import ir.rasen.charsoo.dialog.PopupReportPost;
import ir.rasen.charsoo.helper.Image_M;
import ir.rasen.charsoo.helper.PersianDate;
import ir.rasen.charsoo.helper.TestUnit;
import ir.rasen.charsoo.interfaces.IReportPost;
import ir.rasen.charsoo.ui.GridViewHeader;
import ir.rasen.charsoo.ui.TextViewFont;
import ir.rasen.charsoo.webservices.DownloadImages;
import ir.rasen.charsoo.webservices.post.CancelShare;
import ir.rasen.charsoo.webservices.post.Like;
import ir.rasen.charsoo.webservices.post.Share;
import ir.rasen.charsoo.webservices.post.Unlike;

/**
 * Created by android on 3/7/2015.
 */
public class AdapterPostTimeLine extends BaseAdapter implements IReportPost {

    private ArrayList<Post> items;
    private Context context;
    DownloadImages downloadImages;
    private int screedWidth;
    private IReportPost iReportPost;
    GridView gridView;


    public AdapterPostTimeLine(Context context, ArrayList<Post> items) {
        this.context = context;
        this.items = items;
        downloadImages = new DownloadImages(context);
        screedWidth = context.getResources().getDisplayMetrics().widthPixels;
        iReportPost = this;
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
        if(gridView == null) {
            gridView = (GridView) viewGroup;
            gridView.setSelector(new ColorDrawable(0x00ffffff));
        }
        final Holder holder;

        if (view == null) {
            holder = new Holder();
            view = LayoutInflater.from(context).inflate(R.layout.item_post_adapter_list, viewGroup, false);


            holder.imageViewProfileImage = (ImageView) view.findViewById(R.id.imageView_profile_picture);
            holder.textViewBusinessIdentifier = (TextViewFont) view.findViewById(R.id.textView_business_identifier);
            holder.textViewDate = (TextViewFont) view.findViewById(R.id.textView_date);

            //complete section
            holder.llCompleteSection = (LinearLayout) view.findViewById(R.id.ll_complete_post_section);
            holder.imageViewPost = (ImageView) view.findViewById(R.id.imageView_post);
            //to display post picture as square
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT,screedWidth);
            holder.imageViewPost.setLayoutParams(params);

            holder.imageViewLike = (ImageView) view.findViewById(R.id.imageView_like);
            holder.imageViewComment = (ImageView) view.findViewById(R.id.imageView_comment);
            holder.imageViewShare = (ImageView) view.findViewById(R.id.imageView_share);
            holder.imageViewMore = (ImageView) view.findViewById(R.id.imageView_more);
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

            //announcement parts
            holder.llAnnouncementSection = (LinearLayout) view.findViewById(R.id.ll_announcement);
            holder.textViewAnnouncementUserIdentifier = (TextViewFont) view.findViewById(R.id.textView_announcement_userIdentifier);
            holder.textViewAnnouncementBusinessStaticPart = (TextViewFont) view.findViewById(R.id.textView_announcement_business_static_part);

            view.setTag(holder);

        } else
            holder = (Holder) view.getTag();

        //all post's types have these three fields
        downloadImages.download(items.get(position).businessProfilePictureId, Image_M.SMALL, holder.imageViewProfileImage);
        holder.textViewDate.setText(PersianDate.getCreationDate(context, items.get(position).creationDate));
        holder.textViewBusinessIdentifier.setText(items.get(position).businessUserName);

        holder.textViewBusinessIdentifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Business.goBusinessHomeInfoPage(items.get(position).businessID);
                Toast.makeText(context,"Go business home info",Toast.LENGTH_LONG).show();
            }
        });

        if (items.get(position).type == Post.Type.Complete) {
            //this post is not an announcement
            holder.llAnnouncementSection.setVisibility(View.GONE);
            holder.llCompleteSection.setVisibility(View.VISIBLE);

            downloadImages.download(items.get(position).pictureId, Image_M.LARGE, holder.imageViewPost);
            holder.textViewLikeNumber.setText(String.valueOf(items.get(position).likeNumber));
            holder.textViewCommentNumber.setText(String.valueOf(items.get(position).commentNumber));
            holder.textViewShareNumber.setText(String.valueOf(items.get(position).shareNumber));
            holder.textViewDescription.setText(items.get(position).description);
            holder.textViewComment1UserIdentifier.setText(items.get(position).lastThreeComments.get(0).username);
            holder.textViewComment2UserIdentifier.setText(items.get(position).lastThreeComments.get(1).username);
            holder.textViewComment3UserIdentifier.setText(items.get(position).lastThreeComments.get(2).username);
            holder.textViewComment1.setText(items.get(position).lastThreeComments.get(0).text);
            holder.textViewComment2.setText(items.get(position).lastThreeComments.get(1).text);
            holder.textViewComment3.setText(items.get(position).lastThreeComments.get(2).text);

            if (items.get(position).isLiked)
                holder.imageViewLike.setImageResource(R.drawable.ic_favorite_red);
            if (items.get(position).isShared)
                holder.imageViewShare.setImageResource(R.drawable.ic_reply_blue);
            if (items.get(position).isReported)
                holder.imageViewMore.setVisibility(View.GONE);
            else
                holder.imageViewMore.setVisibility(View.VISIBLE);

            holder.textViewComment1UserIdentifier.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    User.goUserHomeInfoPage(items.get(position).lastThreeComments.get(0).userID);
                    Toast.makeText(context,"Go user home info",Toast.LENGTH_LONG).show();
                }
            });
            holder.textViewComment2UserIdentifier.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    User.goUserHomeInfoPage(items.get(position).lastThreeComments.get(1).userID);
                    Toast.makeText(context,"Go user home info",Toast.LENGTH_LONG).show();
                }
            });
            holder.textViewComment3UserIdentifier.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    User.goUserHomeInfoPage(items.get(position).lastThreeComments.get(2).userID);
                    Toast.makeText(context,"Go user home info",Toast.LENGTH_LONG).show();
                }
            });

            holder.imageViewComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Comment.openCommentActivity(items.get(position).id);
                    Toast.makeText(context,"Go comment activity",Toast.LENGTH_LONG).show();
                }
            });
            holder.imageViewLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (items.get(position).isLiked) {
                        //unlike the post
                        if (!TestUnit.isTesting)
                            new Unlike(items.get(position).userId, items.get(position).id).execute();
                        items.get(position).isLiked = false;
                        holder.imageViewLike.setImageResource(R.drawable.ic_favorite_grey);
                    } else {
                        //like the post
                        if (!TestUnit.isTesting)
                            new Like(items.get(position).userId, items.get(position).id).execute();
                        items.get(position).isLiked = true;
                        holder.imageViewLike.setImageResource(R.drawable.ic_favorite_red);
                    }

                }
            });

            holder.imageViewShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (items.get(position).isShared) {
                        //cancel share the post
                        if (!TestUnit.isTesting)
                            new CancelShare(items.get(position).userId, items.get(position).id).execute();

                        items.get(position).isShared = false;
                        holder.imageViewShare.setImageResource(R.drawable.ic_reply_grey);
                    } else {
                        //share the post
                        if (!TestUnit.isTesting)
                            new Share(items.get(position).userId, items.get(position).id).execute();

                        items.get(position).isShared = true;
                        holder.imageViewShare.setImageResource(R.drawable.ic_reply_blue);
                    }
                }
            });

            holder.imageViewMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupReportPost p = new PopupReportPost(context, items.get(position).userId, items.get(position).id,position,holder.imageViewMore,iReportPost);
                    p.show();
                }
            });

        } else {
            //this post is an announcement
            holder.llCompleteSection.setVisibility(View.GONE);
            holder.llAnnouncementSection.setVisibility(View.VISIBLE);

            holder.textViewAnnouncementUserIdentifier.setText(items.get(position).userName);
            holder.textViewAnnouncementUserIdentifier.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    User.goUserHomeInfoPage(items.get(position).userId);
                    Toast.makeText(context,"Go user home info",Toast.LENGTH_LONG).show();
                }
            });

            if (items.get(position).type == Post.Type.Follow)
                holder.textViewAnnouncementBusinessStaticPart.setText(context.getResources().getString(R.string.follow_announcement));
            else if (items.get(position).type == Post.Type.Review)
                holder.textViewAnnouncementBusinessStaticPart.setText(context.getResources().getString(R.string.review_announcement));

        }


        return view;
    }

    @Override
    public void notifyReportPost(int position, ImageView imageViewMore) {
        items.get(position).isReported = true;
        imageViewMore.setVisibility(View.GONE);
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
        ImageView imageViewLike;
        ImageView imageViewComment;
        ImageView imageViewShare;
        ImageView imageViewMore;

        //announcement parts
        LinearLayout llAnnouncementSection;
        TextViewFont textViewAnnouncementUserIdentifier;
        TextViewFont textViewAnnouncementBusinessStaticPart;

    }
}
