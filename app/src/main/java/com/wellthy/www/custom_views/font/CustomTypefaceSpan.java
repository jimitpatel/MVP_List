package com.wellthy.www.custom_views.font;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;

/**
 * Created by jimitpatel on 10/09/16.
 */
public class CustomTypefaceSpan extends TypefaceSpan {

    private String font;
    private Context context;

    public CustomTypefaceSpan(@NonNull String font, @NonNull Context context) {
        super("");
        this.font = font;
        this.context = context;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        CustomFontHelper.setCustomFont(ds, font, context);
    }

    @Override
    public void updateMeasureState(TextPaint paint) {
        CustomFontHelper.setCustomFont(paint, font, context);
    }
}
