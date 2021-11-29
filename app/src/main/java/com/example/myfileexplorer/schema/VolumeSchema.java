package com.example.myfileexplorer.schema;

import android.content.Context;
import android.os.Build;
import android.os.storage.StorageVolume;

import androidx.annotation.RequiresApi;

import com.example.myfileexplorer.utils.FileUtils;

import java.io.File;

public class VolumeSchema {

    public String volumeName;
    public File volumeFileWithPath;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static VolumeSchema generateVolumeObjectFromStorageVolume(Context activityContext, StorageVolume storageVolume) {
        VolumeSchema tempVolume = new VolumeSchema();
        tempVolume.volumeName = storageVolume.getDescription(activityContext);
        tempVolume.volumeFileWithPath = FileUtils.getFileFromVolumeUuid(storageVolume.getUuid());
        return tempVolume;
    }

    public static VolumeSchema generateVolumeObjectFromExternalDirectoryFile(File externalDirectoryFile) {
        VolumeSchema tempVolume = new VolumeSchema();
        tempVolume.volumeName = FileUtils.getRootDirectoryFileNameFromExternalDirectoryFile(externalDirectoryFile);
        tempVolume.volumeFileWithPath = new File(tempVolume.volumeName);
        return tempVolume;
    }
}
