package com.jundat95.tinhngo.sharelocation.until;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.jundat95.tinhngo.sharelocation.R.id.imageBase64;

/**
 * Created by tinhngo on 12/2/16.
 */

public class Until {

    public static String IdUser;

    public static double tryParse(Object obj) {
        double retVal;
        try {
            retVal = Double.parseDouble((String) obj);
        } catch (NumberFormatException nfe) {
            retVal = 0; // or null if that is your preference
        }
        return retVal;
    }

    public static String bitmapToBase64(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap =  bm;
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] imageBytes = baos.toByteArray();
        String imageString = Base64.encodeToString(imageBytes,Base64.DEFAULT);
        return imageString;
    }

    public static Bitmap base64ToBitmap(String imageString){
        // Decode base64 to image
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] imageBytes = baos.toByteArray();
        imageBytes = Base64.decode(imageString,Base64.DEFAULT);
        Bitmap decodeImage  = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
        return decodeImage;
    }

}
