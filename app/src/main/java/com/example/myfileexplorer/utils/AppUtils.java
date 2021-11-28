package com.example.myfileexplorer.utils;

import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppUtils {


    /**
     * Returns the viewType based on the type of
     * the file. i.e, if its a directory it returns
     * folder type or returns file type
     */
    public static int getViewType(File file) {
        if(file.isDirectory())
            return Constants.VIEW_TYPE_FOLDER;
        return Constants.VIEW_TYPE_FILE;
    }

    /**
     * Short hand utility function to show toast message to the user.
     * Can be used across all classes, must pass in activityContext.
     */
    public static void showToastMessage(Context activityContext,String message) {
        Toast.makeText(activityContext, message, Toast.LENGTH_SHORT).show();
    }

}
