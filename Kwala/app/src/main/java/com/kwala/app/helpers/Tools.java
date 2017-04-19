package com.kwala.app.helpers;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.kwala.app.R;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.Map;

/**
 * @author jacobamuchow@gmail.com
 */

public class Tools {
    private static final String TAG = Tools.class.getSimpleName();

    /*
     * Reference: https://karanbalkar.com/2013/05/tutorial-28-implement-sha1-and-md5-hashing-in-android/
     */
    private static String convertToHex(byte[] data) {
        StringBuffer sb = new StringBuffer();
        String hex;

        hex = Base64.encodeToString(data, 0, data.length, Base64.DEFAULT);

        sb.append(hex);

        return sb.toString();
    }

    @Nullable
    public static String computeSHAHash(String string) {
        MessageDigest mdSha1 = null;
        try {
            mdSha1 = MessageDigest.getInstance("SHA-1");
            mdSha1.update(string.getBytes("UTF-8"));

            byte[] data = mdSha1.digest();
            return convertToHex(data);

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            Log.e(TAG, "Error initializing SHA1 message digest");
            return null;
        }
    }

    @ColorInt
    public static int hexToColorInt(String hexString) {
        return Long.valueOf(hexString, 16).intValue();
    }

    public static String colorIntToHex(@ColorInt int colorInt) {
        return Integer.toHexString(colorInt);
    }

    public static void applyProfileColorToView(View view, @ColorInt int profileColor) {
        if (view.getBackground() == null) {
            view.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.white_oval));
        }

        view.getBackground().mutate()
                .setColorFilter(profileColor, PorterDuff.Mode.SRC_ATOP);
    }

    public static JSONObject dataSnapshotToJSONObject(@Nullable DataSnapshot dataSnapshot) {
        if (dataSnapshot == null) {
            return null;
        }

        Object value = dataSnapshot.getValue();
        return value == null ? null : new JSONObject((Map) value);
    }

    public static String formatString(Context context, @StringRes int stringId, Object... args) {
        String string = context.getString(stringId);
        return String.format(Locale.US, string, args);
    }
}
