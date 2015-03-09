package ir.rasen.charsoo.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.widget.EditText;

public class EditTextFont extends EditText {
    public EditTextFont(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public EditTextFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextFont(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/b_yekan.ttf");
            setTypeface(tf);
        }
    }

    public void setErrorC(String error) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD) {
            ForegroundColorSpan fgcspan = new ForegroundColorSpan(getResources().getColor(android.R.color.black));
            SpannableStringBuilder ssbuilder = new SpannableStringBuilder(error);
            ssbuilder.setSpan(fgcspan, 0, error.length(), 0);
            setError(ssbuilder);
        } else {
            setError(error);
        }
    }
}