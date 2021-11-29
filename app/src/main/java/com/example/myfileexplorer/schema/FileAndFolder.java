package com.example.myfileexplorer.schema;

import android.content.Context;

import com.example.myfileexplorer.utils.FileUtils;

import java.io.File;

/**
 * Custom class for java generic file to represent that it can either be
 * a file or a folder.
 * Also used as a model class for displaying the ui in
 * `FilesAndFoldersListingViewHolder`,
 */
public class FileAndFolder {

    // java file object which provides values for other objects
    public File javaIoFile;

    // attributes used in ui
    public String fileAndFolderName;
    public int fileAndFolderIcon;
    public String fileAndFolderDescription;

    /**
     * Given a generic file object, converts to the instance of this class
     * Uses the helper functions in `FileUtils` and assigns value
     * respectively.
     * Used when rootDirectory is changed in `FilesAndFoldersListingActivity`
     * and the recyclerView is loaded.
     */
    public static FileAndFolder fromJavaIoFile(Context activityContext, File file) {
        FileAndFolder fileAndFolderObject = new FileAndFolder();

        fileAndFolderObject.javaIoFile = file.getAbsoluteFile();

        fileAndFolderObject.fileAndFolderName = file.getName();
        fileAndFolderObject.fileAndFolderDescription = FileUtils.getFileAndFolderDescriptionBasedOnType(activityContext,file);
        fileAndFolderObject.fileAndFolderIcon = FileUtils.getFileAndFolderIconBasedOnTypeAndExtension(activityContext, file);

        return fileAndFolderObject;
    }

    /**
     * Wrapper function that returns if the FileAndFolder object is a
     * folder or a file. Uses the generic java file object that was
     * assigned when creation.
     * Returns true if the file is a folder.
     */
    public boolean isFolder() {
        return this.javaIoFile.isDirectory();
    }

    /**
     * Wrapper function that returns if the FileAndFolder object is a
     * folder or a file. Uses the generic java file object that was
     * assigned when creation.
     * Returns true if the file is not a folder.
     */
    public boolean isFile() {
        return !this.javaIoFile.isDirectory();
    }

}
