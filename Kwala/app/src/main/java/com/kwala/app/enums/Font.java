package com.kwala.app.enums;

import android.content.Context;
import android.graphics.Typeface;

/**
 * @author jacobamuchow@gmail.com
 */

public enum Font {
    MEDIUM(0, "fonts/AvenirNext-Medium.ttf"),
    REGULAR(1, "fonts/AvenirNext-Regular.ttf"),
    BOLD(2, "fonts/AvenirNext-Bold.ttf"),
    ITALIC(3, "fonts/AvenirNext-Italic.ttf");

    private int id;
    private String filepath;
    private Typeface typeface = null;

    Font(int id, String filepath) {
        this.id = id;
        this.filepath = filepath;
    }

    public static Font fromId(int id) {
        for (Font font : values()) {
            if (font.id == id) {
                return font;
            }
        }
        return MEDIUM;
    }

    public int getId() {
        return id;
    }

    //Lazily loading typefaces
    public synchronized Typeface getTypeface(Context context) {
        if (typeface == null) {
            typeface = Typeface.createFromAsset(context.getAssets(), filepath);
        }
        return typeface;
    }
}
