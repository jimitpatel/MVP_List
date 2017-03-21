package com.wellthy.www.custom_views.font;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.wellthy.www.R;

/**
 * Created by Jimit Patel on 24/07/15.
 */
public class CustomFontHelper {
    /**
     * Sets a font on a textview based on the custom com.my.package:font attribute
     * If the custom font attribute isn't found in the attributes nothing happens
     *
     * @param textview
     * @param context
     * @param attrs
     */
    public static void setCustomFont(TextView textview, Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextFont);
        String font = typedArray.getString(R.styleable.TextFont_customFont);
        setCustomFont(textview, font, context);
        typedArray.recycle();
    }

    /**
     * Sets a font on a com.stylabs.styfi.custom.FontTextView
     *
     * @param textview view for which font mentioned needs to be changed
     * @param font path of the font where it has been saved (Generally within asset folder)
     * @param context Context where view has been used
     */
    public static void setCustomFont(TextView textview, String font, Context context) {
        if (font == null) {
            return;
        }
        Typeface typeface = FontCache.get(font, context);
        if (typeface != null) {
            textview.setTypeface(typeface);
        }
    }

    /**
     * Sets a font on a paint based on the custom com.my.package:font attribute
     * If the custom font attribute isn't found in the attributes nothing happens
     *
     * @param paint
     * @param context
     * @param attrs
     */
    public static void setCustomFont(Paint paint, Context context, AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextFont);
        String font = typedArray.getString(R.styleable.TextFont_customFont);
        setCustomFont(paint, font, context);
        typedArray.recycle();
    }

    /**
     * Changing font of Paint element
     * @param paint text element of which font needs to be changed
     * @param font
     * @param context
     */
    public static void setCustomFont(Paint paint, String font, Context context) {
        if (font == null) {
            return;
        }
        Typeface typeface = FontCache.get(font, context);
        if (typeface != null) {
            paint.setTypeface(typeface);
        }
    }
}
