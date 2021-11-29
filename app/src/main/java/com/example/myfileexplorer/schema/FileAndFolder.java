package com.example.myfileexplorer.schema;

import android.content.Context;

import com.example.myfileexplorer.utils.FileUtils;

import java.io.File;

public class FileAndFolder {

    public File javaIoFile;

    public String fileAndFolderName;
    public int fileAndFolderIcon;
    public String fileAndFolderDescription;


    public static FileAndFolder fromJavaIoFile(Context activityContext, File file) {
        FileAndFolder fileAndFolderObject = new FileAndFolder();
        fileAndFolderObject.javaIoFile = file.getAbsoluteFile();
        fileAndFolderObject.fileAndFolderName = file.getName();
        fileAndFolderObject.fileAndFolderDescription = FileUtils.getFileAndFolderDescriptionBasedOnType(activityContext,file);
        fileAndFolderObject.fileAndFolderIcon = FileUtils.getFileAndFolderIconBasedOnTypeAndExtension(activityContext, file);

        return fileAndFolderObject;
    }


    public boolean isFolder() {
        return this.javaIoFile.isDirectory();
    }

    public boolean isFile() {
        return !this.javaIoFile.isDirectory();
    }

}
