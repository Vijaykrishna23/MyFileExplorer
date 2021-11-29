package com.example.myfileexplorer.ui;

import android.os.Build;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfileexplorer.R;
import com.example.myfileexplorer.schema.CustomVolume;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;

/**
 * The opening screen of the application.
 * Lists the volumes user has in his/her device.
 */
public class VolumesListingActivity extends AbstractAppCompatActivity {

    @BindView(R.id.volume_listing_recycler_view)
    public RecyclerView volumeListingRecyclerView;

    private List<CustomVolume> volumesInDevice;

    @Override
    public int getActivityLayout() {
        return R.layout.activity_volume_listing;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        volumesInDevice = getVolumesInDeviceListBasedOnAndroidVersion();
        loadRecyclerView(volumesInDevice);


    }

    /**
     * Given the list of volumes of the custom class `VolumeSchema`, displays
     * buttons with the names associated.
     */
    private void loadRecyclerView(List<CustomVolume> volumeList) {
        VolumesListingAdapter volumesListingAdapter = new VolumesListingAdapter(
                this,
                volumeList
        );

        volumeListingRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        volumeListingRecyclerView.setAdapter(volumesListingAdapter);
    }

    /**
     * Gets all the volumes in the device and maps it to the custom class
     * `VolumeSchema`. Two different approaches are used to get the volume list.
     * If device's android version is greater than Nougat, storage manager is used.
     * <p>
     * It is used for readable names of the volume.
     * Or `getExternalFilesDirs` method is used.
     * <p>
     * The path is used as the name of the volume, since the file object doesn't have
     * a name attribute.
     */
    public List<CustomVolume> getVolumesInDeviceListBasedOnAndroidVersion() {

        List<CustomVolume> volumeList = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StorageManager storageManagerService = (StorageManager) getSystemService(STORAGE_SERVICE);
            List<StorageVolume> storageVolumes = storageManagerService.getStorageVolumes();
            volumeList = storageVolumes
                    .stream()
                    .map((storageVolume) -> CustomVolume.generateVolumeObjectFromStorageVolume(this, storageVolume))
                    .collect(Collectors.toList());
        } else {
            File[] externalDirectories = getExternalFilesDirs(null);
            for (File externalDirectory : externalDirectories) {
                volumeList.add(CustomVolume.generateVolumeObjectFromExternalDirectoryFile(externalDirectory));
            }
        }

        return volumeList;

    }

}
