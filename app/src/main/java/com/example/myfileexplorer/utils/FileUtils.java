package com.example.myfileexplorer.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.format.Formatter;
import android.webkit.MimeTypeMap;

import com.example.myfileexplorer.R;
import com.example.myfileexplorer.schema.FileAndFolder;

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
     * Given the currentFile path, gets the children
     * and converts each child to corresponding FileAndFolder object
     * and returns new List made of FileAndFolder object
     */
    public static List<FileAndFolder> getChildrenFileAndFolderListForRootFileAndFolder(Context activityContext, File currentFile) {
        List<File> childrenFiles = getChildrenForFile(currentFile);

        List<FileAndFolder> childrenFileAndFolderList = new ArrayList<>();

        for (File childrenFile : childrenFiles) {
            FileAndFolder fileAndFolder = FileAndFolder.fromJavaIoFile(activityContext, childrenFile);
            childrenFileAndFolderList.add(fileAndFolder);
        }


        return childrenFileAndFolderList;
    }


    public static String getFileAndFolderDescriptionBasedOnType(Context activityContext, File currentFile) {
        return currentFile.isDirectory() ? getDescriptionForFolder(currentFile)
                : getDescriptionForFile(activityContext, currentFile);
    }

    /**
     * Just a helper function that concatenates the number of children
     * and the string "items" to display to the user.
     */
    public static String getDescriptionForFolder(File currentFile) {
        return FileUtils.getChildrenForFile(currentFile).size() + " items";
    }

    /**
     * Formats the bytes in human readable format
     * such as KB, MB and GB with two decimal places.
     * e.g. 12.39KB
     */
    public static String getDescriptionForFile(Context activityContext, File currentFile) {
        return Formatter.formatFileSize(activityContext, currentFile.length());
    }

    /**
     * Given a file, returns the mimetype of the file.
     * Uses ContentResolver to get the mimeType if it exists
     * or uses fileExtension and MimeTypeMap to get the type.
     */
    public static String getMimeTypeFromFile(Context activityContext, File currentFile) {
        Uri currentFileUri = Uri.fromFile(currentFile);
        String mimeType;
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
            AppUtils.showToastMessage(activityContext, e.getMessage());
        }
    }

    public static File getFileFromVolumeUuid(String volumeUuid) {

        //Default case returns internal storage | /storage/emulated/0/
        if (volumeUuid == null)
            return Environment.getExternalStorageDirectory();

        //appends uuid to the storage directory
        return new File("/storage/", volumeUuid);
    }

    public static String getRootDirectoryFileNameFromExternalDirectoryFile(File externalDirectoryFile) {
        return externalDirectoryFile.toString().split("Android")[0];
    }

    public static int getFileAndFolderIconBasedOnTypeAndExtension(Context activityContext, File file) {
        if (file.isDirectory())
            return R.drawable.ic_baseline_folder_24;

        return R.drawable.ic_baseline_image_24;

    }


    public static File getParentFile(File currentFile) {
        if (currentFile == null)
            return null;

        return currentFile.getParentFile();
    }


}
