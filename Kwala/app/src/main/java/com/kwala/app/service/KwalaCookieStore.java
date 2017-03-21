package com.kwala.app.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.List;

/**
 * @author jacobamuchow@gmail.com
 */

public class KwalaCookieStore implements CookieStore {
    private static final String TAG = KwalaCookieStore.class.getSimpleName();

    private static final String SHARED_PREFS_KEY = "kwala_cookie_shared_prefs";

    private static final String COOKIE_NAME = "vapor-auth";

    //Use default cookie store underneath the hood
    private CookieStore cookieStore = new CookieManager().getCookieStore();

    private SharedPreferences sharedPreferences;
    private Gson gson = new Gson();

    public KwalaCookieStore(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE);

        String cookieRaw = sharedPreferences.getString(COOKIE_NAME, null);
        if (cookieRaw != null) {
            HttpCookie cookie = gson.fromJson(cookieRaw, HttpCookie.class);
            cookieStore.add(URI.create(cookie.getDomain()), cookie);
        }
    }

    @Override
    public void add(URI uri, HttpCookie cookie) {
        Log.d(TAG, "uri: " + uri.toString());
        Log.d(TAG, "cookie: " + cookie.toString());

        if (cookie.getName().equals(COOKIE_NAME)) {
            remove(URI.create(cookie.getDomain()), cookie);
            sharedPreferences.edit().putString(COOKIE_NAME, gson.toJson(cookie)).apply();
        }

        cookieStore.add(URI.create(cookie.getDomain()), cookie);
    }

    @Override
    public List<HttpCookie> get(URI uri) {
        return cookieStore.get(uri);
    }

    @Override
    public List<HttpCookie> getCookies() {
        return cookieStore.getCookies();
    }

    @Override
    public List<URI> getURIs() {
        return cookieStore.getURIs();
    }

    @Override
    public boolean remove(URI uri, HttpCookie cookie) {

        if (cookie.getName().equals(COOKIE_NAME)) {
            sharedPreferences.edit().remove(COOKIE_NAME).apply();
        }

        return cookieStore.remove(URI.create(cookie.getDomain()), cookie);
    }

    @Override
    public boolean removeAll() {
        sharedPreferences.edit().clear().apply();
        return cookieStore.removeAll();
    }
}
