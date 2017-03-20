package com.kwala.app.helpers;

import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
    public static String computeSHAHash(String password) {
        MessageDigest mdSha1 = null;
        try {
            mdSha1 = MessageDigest.getInstance("SHA-1");
            mdSha1.update(password.getBytes("UTF-8"));

            byte[] data = mdSha1.digest();
            return convertToHex(data);

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            Log.e(TAG, "Error initializing SHA1 message digest");
            return null;
        }
    }
}
