package com.kwala.app.helpers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

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

    public void setProfileImageId(String profileImageId) {
        Glide.with(context)
                .load(getImageUrl(profileImageId))
                .bitmapTransform(new CropCircleTransformation(context))
                .into(imageView);
    }

    private String getImageUrl(String imageId) {
        return KwalaConstants.Network.S3_BUCKET_URL + imageId;
    }
}
