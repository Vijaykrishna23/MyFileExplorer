package com.example.myfileexplorer.utils;

import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppUtils {

    /**
     * Short hand utility function to show toast message to the user.
     * Can be used across all classes, must pass in activityContext.
     */
    public static void showToastMessage(Context activityContext,String message) {
        Toast.makeText(activityContext, message, Toast.LENGTH_SHORT).show();
    }

}
