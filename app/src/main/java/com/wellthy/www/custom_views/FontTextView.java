package com.wellthy.www.custom_views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.wellthy.www.custom_views.font.CustomFontHelper;

/**
 * Created by Jimit Patel on 24/07/15.
 * A custom com.stylabs.styfi.custom.FontTextView for supporting different fonts kept in asset folder.
 * To implement this also use declare-styleable for set of custom attributes
 */
public class FontTextView extends AppCompatTextView {

    public FontTextView(Context context) {
        super(context);
    }

    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        CustomFontHelper.setCustomFont(this, context, attrs);
    }

    public FontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        CustomFontHelper.setCustomFont(this, context, attrs);
    }

    public void setFont(@NonNull String font) {
        CustomFontHelper.setCustomFont(this, font, getContext());
    }
}
