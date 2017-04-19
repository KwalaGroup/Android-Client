package com.kwala.app.helpers;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.kwala.app.R;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * @author muchow@hello.com
 */
public class KwalaImages {
    private static final String TAG = KwalaImages.class.getSimpleName();

    private static final String BASE_IMAGE_URL = "https://s3.amazonaws.com/kwala-uploads/";

    @NonNull private ImageView imageView;
    @NonNull private Context context;

    private KwalaImages(@NonNull ImageView imageView) {
        this.imageView = imageView;
        this.context = imageView.getContext();
    }

    public static KwalaImages with(@NonNull ImageView imageView) {
        return new KwalaImages(imageView);
    }

    public void setProfileImageId(String profileImageId, @ColorInt final int profileColor) {
        imageView.setBackground(null);

        Glide.with(context)
                .load(getImageUrl(profileImageId))
                .placeholder(R.drawable.kwala_head_placeholder)
                .bitmapTransform(new CropCircleTransformation(context))
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        Tools.applyProfileColorToView(imageView, profileColor);
                        return false;
                    }
                })
                .into(imageView);
    }

    public void setProfileImageId(String profileImageId) {
        imageView.setBackground(null);

        Glide.with(context)
                .load(getImageUrl(profileImageId))
                .bitmapTransform(new CropCircleTransformation(context))
                .into(imageView);
    }

    public void setProfileImageUri(Uri imageUri) {
        Glide.with(context)
                .load(imageUri)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .bitmapTransform(new CropCircleTransformation(context))
                .into(imageView);
    }

    private String getImageUrl(String imageId) {
        return KwalaConstants.Network.S3_BUCKET_URL + imageId;
    }
}
