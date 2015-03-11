package ir.rasen.charsoo.dialog;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import ir.rasen.charsoo.R;
import ir.rasen.charsoo.interfaces.ICancelFriendship;
import ir.rasen.charsoo.interfaces.IReportPost;
import ir.rasen.charsoo.interfaces.IWebserviceResponse;
import ir.rasen.charsoo.ui.TextViewFont;
import ir.rasen.charsoo.webservices.post.Report;


public class PopupReportPost extends MyPopup {
    Context context;
    private IReportPost iReportPost;


    @SuppressLint("NewApi")
    public PopupReportPost(final Context context, final int userId,final int postId,final int reportedItemPosition,final ImageView reportedItemImageViewMore, final IReportPost iReportPost) {
        super(context);

        this.context = context;
        this.iReportPost = iReportPost;
        int height = getRowHeight();
        int width = getRowWidth();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
        TextViewFont textViewUnfriend = new TextViewFont(context);
        textViewUnfriend.setGravity(Gravity.CENTER);
        textViewUnfriend.setLayoutParams(params);
        textViewUnfriend.setText(context.getResources().getString(R.string.report));
        textViewUnfriend.setBackgroundResource(R.drawable.selector_popup_one_item);



        LinearLayout ll_body = getBody();
        ll_body.addView(textViewUnfriend);


        textViewUnfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Report(userId,postId).execute();
                dismiss();
                iReportPost.notifyReportPost(reportedItemPosition,reportedItemImageViewMore);
            }
        });

    }




}
