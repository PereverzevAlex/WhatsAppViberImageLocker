package com.apps.pereverzev.alexander.whatsappviberimagelocker;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.apps.pereverzev.alexander.whatsappviberimagelocker.adapters.components.DisplaySize;

import java.io.File;

/**
 * Created by alexander on 28.01.15.
 */
public class BitmapLoader {


    public Bitmap getImage(String imagePath, int imageWidth, int imageHeight) {
        File file = new File(imagePath);

        if(file.exists()){
            return decodeSampledBitmapFromFile(file, imageWidth, imageHeight);
        }

        return null;
    }

    public DisplaySize.Size getImageSize(String imagePath){
        File file = new File(imagePath);

        int width = 0;
        int height = 0;

        if(file.exists()){
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(file.getAbsolutePath(), options);

            width = options.outWidth;
            height = options.outHeight;
        }

        return new DisplaySize().new Size(height, width);
    }

    private Bitmap decodeSampledBitmapFromFile(File file, int width, int height){
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);

        if(width>0 && height>0) {
            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, width, height);
        }

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
    }

    private int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
