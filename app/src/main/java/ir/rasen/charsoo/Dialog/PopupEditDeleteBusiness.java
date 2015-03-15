package ir.rasen.charsoo.dialog;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import ir.rasen.charsoo.R;
import ir.rasen.charsoo.classes.Comment;
import ir.rasen.charsoo.interfaces.ICommentChange;
import ir.rasen.charsoo.interfaces.IDeleteBusiness;
import ir.rasen.charsoo.interfaces.IWebserviceResponse;
import ir.rasen.charsoo.ui.TextViewFont;


public class PopupEditDeleteBusiness extends MyPopup {
    Context context;


    @SuppressLint("NewApi")
    public PopupEditDeleteBusiness(final Context context, final int businessId, final IDeleteBusiness iDeleteBusiness) {
        super(context);

        this.context = context;

        int screenHeight = context.getResources().getDisplayMetrics().heightPixels;
        int screenWidth = context.getResources().getDisplayMetrics().widthPixels;

        int height = getRowHeight();
        int width = getRowWidth();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
        TextViewFont textViewEdit = new TextViewFont(context);
        textViewEdit.setGravity(Gravity.CENTER);
        textViewEdit.setLayoutParams(params);
        textViewEdit.setText(context.getResources().getString(R.string.edit_profile));
        textViewEdit.setBackgroundResource(R.drawable.selector_popup_top_item);

        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(width, 1);
        TextViewFont textViewDevider = new TextViewFont(context);
        textViewDevider.setLayoutParams(params2);
        textViewDevider.setBackgroundColor(Color.GRAY);


        TextViewFont textViewDelete = new TextViewFont(context);
        textViewDelete.setGravity(Gravity.CENTER);
        textViewDelete.setLayoutParams(params);
        textViewDelete.setText(context.getResources().getString(R.string.delete));
        textViewDelete.setBackgroundResource(R.drawable.selector_popup_bottom_item);

        LinearLayout ll_body = getBody();
        ll_body.addView(textViewEdit);
        ll_body.addView(textViewDevider);
        ll_body.addView(textViewDelete);

        textViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Go to edit business profile", Toast.LENGTH_LONG).show();
                dismiss();
            }
        });
        textViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogDeleteBusinessConfirmation d =  new DialogDeleteBusinessConfirmation(context,businessId,iDeleteBusiness);
                d.show();
                dismiss();
            }
        });
    }


}
