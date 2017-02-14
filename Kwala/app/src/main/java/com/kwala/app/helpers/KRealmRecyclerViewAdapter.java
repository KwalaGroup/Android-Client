package com.kwala.app.helpers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import io.realm.OrderedRealmCollection;
import io.realm.RealmModel;
import io.realm.RealmRecyclerViewAdapter;

/**
 * @author jacobamuchow@gmail.com
 */
public abstract class KRealmRecyclerViewAdapter<T extends RealmModel> extends RealmRecyclerViewAdapter<T, RecyclerView.ViewHolder> {

    public KRealmRecyclerViewAdapter(@NonNull Context context, @Nullable OrderedRealmCollection<T> data, boolean autoUpdate) {
        super(context, data, autoUpdate);
    }
}
