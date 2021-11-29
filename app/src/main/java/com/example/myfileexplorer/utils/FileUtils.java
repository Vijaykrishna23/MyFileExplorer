package com.example.myfileexplorer.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.format.Formatter;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtils {

    /**
     * Given the root path, returns all the children files.
     * Returns null in any other case.
     */
    public static List<File> getChildrenForFile(File root) {
        if (root == null)
            return null;

        File[] childrenFiles = root.listFiles();

        if (childrenFiles == null)
            return new ArrayList<>();

        return Arrays.asList(childrenFiles);

    }

    /**
     * Just a helper function that concatenates the number of children
     * and the string "items" to display to the user.
     */
    public static String getDisplayTextForNumberOfItemsInFolder(File currentFile) {
        return FileUtils.getChildrenForFile(currentFile).size() + " items";
    }

    /**
     * Formats the bytes in human readable format
     * such as KB, MB and GB with two decimal places.
     * e.g. 12.39KB
     */
    public static String getDisplayTextForFileSize(Context activityContext, File currentFile) {
        return Formatter.formatFileSize(activityContext, currentFile.length());
    }

    /**
     * Given a file, returns the mimetype of the file.
     * Uses ContentResolver to get the mimeType if it exists
     * or uses fileExtension and MimeTypeMap to get the type.
     */
    public static String getMimeTypeFromFile(Context activityContext, File currentFile) {
        Uri currentFileUri = Uri.fromFile(currentFile);
        String mimeType = null;
        if (ContentResolver.SCHEME_CONTENT.equals(currentFileUri.getScheme())) {
            ContentResolver cr = activityContext.getContentResolver();
            mimeType = cr.getType(currentFileUri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(currentFileUri
                    .toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        return mimeType;
    }

    /**
     * Open the chooser bottom bar based on the mimetype.
     * If app doesn't exist, shows message.
     */
    public static void openAppChooserBasedOnFileExtension(Context activityContext, File currentFile) {
        Uri currentFileUri = Uri.fromFile(currentFile);
        String mimeTypeForCurrentFile = getMimeTypeFromFile(activityContext, currentFile);

        Intent openAppChooserIntent = new Intent()
                .setAction(Intent.ACTION_VIEW)
                .setDataAndType(currentFileUri, mimeTypeForCurrentFile);

        try {
            activityContext.startActivity(openAppChooserIntent);
        } catch (Exception e) {
            AppUtils.showToastMessage(activityContext,e.getMessage());
        }
    }

    public static File getFileFromVolumeUuid(String volumeUuid) {

        //Default case returns internal storage | /storage/emulated/0/
        if(volumeUuid == null)
            return Environment.getExternalStorageDirectory();

        //appends uuid to the storage directory
        return new File("/storage/",volumeUuid);
    }

    public static String getRootDirectoryFileNameFromExternalDirectoryFile(File externalDirectoryFile) {
        return externalDirectoryFile.toString().split("Android")[0];
    }




    public static File getParentFile(File currentFile) {
        if (currentFile == null)
            return null;

        return currentFile.getParentFile();
    }

    public static File getGrandParentFile(File root) {
        if (root == null)
            return null;

        if (root.getParentFile() == null)
            return null;
        return root.getParentFile().getParentFile();
    }

    public static List<File> getFolders(File root) {
        List<File> allFiles = getChildrenForFile(root);
        List<File> folders = new ArrayList<>();

        for (File file : allFiles) {
            if (file.isDirectory()) folders.add(file);
        }
        return folders;

    }

    public static List<File> getFiles(File root) {
        List<File> allFiles = getChildrenForFile(root);
        List<File> files = new ArrayList<>();

        for (File file : allFiles) {
            if (!file.isDirectory()) files.add(file);
        }
        return files;

    }

}
