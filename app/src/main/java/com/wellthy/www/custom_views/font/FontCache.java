package com.wellthy.www.custom_views.font;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Hashtable;

/**
 * Created by Jimit Patel on 24/07/15.
 */
public class FontCache {

    private static Hashtable<String, Typeface> fontCache = new Hashtable<>();

    /**
     * Gets the typeface from Asset folder
     * @param name path to the font within asset folder
     * @param context context of the view
     * @return
     */
    public static Typeface get(String name, Context context) {
        Typeface tf = fontCache.get(name);
        if (tf == null) {
            try {
                tf = Typeface.createFromAsset(context.getAssets(), name);
            } catch (Exception e) {
                return null;
            }
            fontCache.put(name, tf);
        }
        return tf;
    }
}
