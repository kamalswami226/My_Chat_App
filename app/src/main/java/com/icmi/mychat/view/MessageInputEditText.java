package com.icmi.mychat.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.icmi.mychat.R;

public class MessageInputEditText extends androidx.appcompat.widget.AppCompatEditText {

    public MessageInputEditText(Context context) {
        super(context);
        initViews();
    }

    public MessageInputEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public MessageInputEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        this.setVisibility(GONE);
    }

}
