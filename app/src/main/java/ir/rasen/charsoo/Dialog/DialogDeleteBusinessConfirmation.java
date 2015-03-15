package ir.rasen.charsoo.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import ir.rasen.charsoo.R;
import ir.rasen.charsoo.helper.LoginInfo;
import ir.rasen.charsoo.helper.TestUnit;
import ir.rasen.charsoo.interfaces.IDeleteBusiness;
import ir.rasen.charsoo.interfaces.IDeletePost;
import ir.rasen.charsoo.ui.TextViewFont;
import ir.rasen.charsoo.webservices.business.DeleteBusiness;
import ir.rasen.charsoo.webservices.post.DeletePost;


public class DialogDeleteBusinessConfirmation extends MyDialogOkCancel {
    Context context;


    @SuppressLint("NewApi")
    public DialogDeleteBusinessConfirmation(final Context context, final int businessId, final IDeleteBusiness iDeleteBusiness) {
        super(context, context.getResources().getString(R.string.popup_warning),
                context.getResources().getString(R.string.cancel),
                context.getResources().getString(R.string.delete));


        LinearLayout.LayoutParams paramsWarning = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextViewFont textViewWarning = new TextViewFont(context);
        textViewWarning.setGravity(Gravity.CENTER);
        paramsWarning.setMargins(5, getRowHeight(), 5, getRowHeight());
        textViewWarning.setLayoutParams(paramsWarning);
        textViewWarning.setText(context.getResources().getString(R.string.confirmation_delete_business));


        LinearLayout ll_body = getBody();
        ll_body.addView(textViewWarning);

        //set onClickListener for the cancel button
        TextViewFont textViewOk = getOkButtonTextView();
        TextViewFont textViewCancel = getCancelButtonTextView();

        textViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        //set onClickListener for the ok button
        textViewOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TestUnit.isTesting)
                    new DeleteBusiness(LoginInfo.getUserId(context), businessId).execute();
                else {
                    iDeleteBusiness.notifyDeleteBusiness(businessId);
                }
                dismiss();
            }
        });

    }

}
