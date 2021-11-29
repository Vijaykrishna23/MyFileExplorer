package com.example.myfileexplorer.schema;

import android.content.Context;
import android.os.Build;
import android.os.storage.StorageVolume;

import androidx.annotation.RequiresApi;

import com.example.myfileexplorer.utils.FileUtils;

import java.io.File;

/**
 * CustomVolume class for storing the name and the file associated.
 * Used for displaying volumes in the volumeListingScreen
 * Acts as model class for VolumeListingViewHolder.
 */
public class CustomVolume {

    public String volumeName;
    public File volumeFileWithPath;


    /**
     * Given the storage volume, returns instance of the customVolume class.
     * Storage volume has a `getDescription` method which gives a human
     * readable name. This function only works with android devices
     * with version since nougat.The caller of this function must be made
     * sure to check the condition or this function will throw an Exception.
     *
     * For versions below the requirement. the other static constructor
     * method `generateVolumeObjectFromExternalDirectoryFile` is used.
     *
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static CustomVolume generateVolumeObjectFromStorageVolume(Context activityContext, StorageVolume storageVolume) {
        CustomVolume tempVolume = new CustomVolume();
        tempVolume.volumeName = storageVolume.getDescription(activityContext);
        tempVolume.volumeFileWithPath = FileUtils.getFileFromVolumeUuid(storageVolume.getUuid());
        return tempVolume;
    }

    /**
     * Given a file object, generates a customVolume instance.
     * This function can be used in all android versions.
     * This app uses it when android device version is below nougat.
     *
     * Since the generic file object doesn't give any readable name,
     * the path name is used as volume name.
     */
    public static CustomVolume generateVolumeObjectFromExternalDirectoryFile(File externalDirectoryFile) {
        CustomVolume tempVolume = new CustomVolume();
        tempVolume.volumeName = FileUtils.getRootDirectoryFileNameFromExternalDirectoryFile(externalDirectoryFile);
        tempVolume.volumeFileWithPath = new File(tempVolume.volumeName);
        return tempVolume;
    }
}
