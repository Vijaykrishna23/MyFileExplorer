package com.example.myfileexplorer.ui;

import android.os.Build;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfileexplorer.R;
import com.example.myfileexplorer.schema.VolumeSchema;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;

public class VolumesListingActivity extends AbstractAppCompatActivity {

    @BindView(R.id.volume_listing_recycler_view)
    public RecyclerView volumeListingRecyclerView;

    private List<VolumeSchema> volumesInDevice;

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

    private void loadRecyclerView(List<VolumeSchema> volumeList) {
        VolumesListingAdapter volumesListingAdapter = new VolumesListingAdapter(
                this,
                volumeList
        );

        volumeListingRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        volumeListingRecyclerView.setAdapter(volumesListingAdapter);
    }


    public List<VolumeSchema> getVolumesInDeviceListBasedOnAndroidVersion() {

        List<VolumeSchema> volumeList = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StorageManager storageManagerService = (StorageManager) getSystemService(STORAGE_SERVICE);
            List<StorageVolume> storageVolumes = storageManagerService.getStorageVolumes();
            volumeList = storageVolumes
                    .stream()
                    .map((storageVolume) -> VolumeSchema.generateVolumeObjectFromStorageVolume(this, storageVolume))
                    .collect(Collectors.toList());
        } else {
            File[] externalDirectories = getExternalFilesDirs(null);
            for (File externalDirectory : externalDirectories) {
                volumeList.add(VolumeSchema.generateVolumeObjectFromExternalDirectoryFile(externalDirectory));
            }
        }

        return volumeList;

    }

}
