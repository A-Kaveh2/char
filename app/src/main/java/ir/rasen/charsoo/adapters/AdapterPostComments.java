package ir.rasen.charsoo.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import ir.rasen.charsoo.R;
import ir.rasen.charsoo.classes.Comment;
import ir.rasen.charsoo.dialog.PopupEditDeleteComment;
import ir.rasen.charsoo.helper.Image_M;
import ir.rasen.charsoo.helper.LoginInfo;
import ir.rasen.charsoo.helper.TestUnit;
import ir.rasen.charsoo.interfaces.ICommentChange;
import ir.rasen.charsoo.interfaces.IWebserviceResponse;
import ir.rasen.charsoo.ui.ImageViewCircle;
import ir.rasen.charsoo.ui.TextViewFont;
import ir.rasen.charsoo.webservices.DownloadImages;

/**
 * Created by android on 3/7/2015.
 */
public class AdapterPostComments extends BaseAdapter implements ICommentChange {

    //This adapter will displays comments of a post
    //In this adapter user can not delete the comment or block the comment's writer (business who owns the post can)
    //In this adapter, if the user is comment's owner, he can edit or delete the comment

    private ArrayList<Comment> comments;
    private Context context;
    DownloadImages downloadImages;
    IWebserviceResponse IWebserviceResponse;
    ProgressDialog progressDialog;
    int postOwnerBusinessId;
    private ICommentChange iCommentChange;

    public AdapterPostComments(Context context, int postOwnerBusinessId, ArrayList<Comment> comments, IWebserviceResponse IWebserviceResponse, ProgressDialog progressDialog) {
        this.context = context;
        this.comments = comments;
        downloadImages = new DownloadImages(context);
        this.IWebserviceResponse = IWebserviceResponse;
        this.progressDialog = progressDialog;
        this.postOwnerBusinessId = postOwnerBusinessId;
        this.iCommentChange = this;
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
        final Holder holder;

        if (view == null) {
            holder = new Holder();
            view = LayoutInflater.from(context).inflate(R.layout.item_comment_adapter, viewGroup, false);
            holder.imageViewImage = (ImageViewCircle) view.findViewById(R.id.imageView_comment_adapter_item_image);
            holder.textViewUserIdentifier = (TextViewFont) view.findViewById(R.id.textView_comment_adapter_item_title);
            holder.textViewText = (TextViewFont) view.findViewById(R.id.textView_comment_adapter_item_text);
            view.setTag(holder);
        } else
            holder = (Holder) view.getTag();


        //download image with customized class via imageId
        downloadImages.download(comments.get(position).userProfilePictureID, Image_M.SMALL, holder.imageViewImage);
        holder.textViewUserIdentifier.setText(comments.get(position).username);
        holder.textViewText.setText(comments.get(position).text);


        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (!TestUnit.isTesting) {
                    if (comments.get(position).userID == LoginInfo.getUserId(context)) {
                        //the user is writer of the comment
                        PopupEditDeleteComment p = new PopupEditDeleteComment(context, comments.get(position), IWebserviceResponse, progressDialog, iCommentChange);
                        p.show();
                    }
                } else {
                    if (comments.get(position).userID == 1) {
                        //the user is writer of the comment
                        PopupEditDeleteComment p = new PopupEditDeleteComment(context, comments.get(position), IWebserviceResponse, progressDialog, iCommentChange);
                        p.show();
                    }
                }
                return false;
            }
        });

        holder.textViewUserIdentifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //GetUserHomeInfo via comment.userID
                Toast.makeText(context, "Open HomeInfo Activity", Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

    @Override
    public void notifyDeleteComment(int commentId) {
        for (int i = 0; i < comments.size(); i++) {
            if (comments.get(i).id == commentId)
                comments.remove(i);
        }
        notifyDataSetChanged();
    }

    @Override
    public void notifyUpdateComment(Comment comment) {
        for (int i = 0; i < comments.size(); i++) {
            if (comments.get(i).id == comment.id)
                comments.get(i).text = comment.text;
        }
        notifyDataSetChanged();
    }
    //Each item in this adapter has a picture and a title

    private class Holder {
        ImageViewCircle imageViewImage;
        TextViewFont textViewUserIdentifier;
        TextViewFont textViewText;
    }
}
