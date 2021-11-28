package com.example.myfileexplorer.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

/**
 * Util Class for checking and requesting permissions.
 * requestPermissions method can be called anywhere
 * and the rest is handled by this class.
 */
public class PermissionUtils {

    /**
     * Request code to let android know we're requesting permissions
     * Can be any random integer
     */
    public static final int PERMISSIONS_REQUEST_CODE = 1;

    /**
     * Default permission list that is passed when calling
     * requestPermissions. Modify this to add/remove permissions.
     */
    public static final String[] PERMISSIONS_MAX_SDK_29 = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    public static final String[] PERMISSIONS_SDK_30 = {
            Manifest.permission.MANAGE_EXTERNAL_STORAGE
    };

    /**
     * Requests android for permissions with the default permission list provided
     * in this class.
     */
    public static void requestPermissions(Activity activity) {

        requestPermissions(activity, PERMISSIONS_MAX_SDK_29);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requestPermissions(activity, PERMISSIONS_SDK_30);
        }
    }

    /**
     * Requests android for permissions with the permissions list
     * passed by another class. If permission is already not granted,
     * requests for permissions or it does nothing.
     */
    public static void requestPermissions(Activity activity, String... permissions) {
        if (!hasPermissions(activity, permissions)) {
            ActivityCompat.requestPermissions(activity, permissions, PERMISSIONS_REQUEST_CODE);
        }
    }

    /**
     * Internal method to check if the permission is already granted or not.
     */
    private static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


}
