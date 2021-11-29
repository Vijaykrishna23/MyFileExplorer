package com.example.myfileexplorer.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfileexplorer.R;
import com.example.myfileexplorer.schema.FileAndFolder;
import com.example.myfileexplorer.utils.FileUtils;

import java.io.File;
import java.util.List;

import butterknife.BindView;

public class FilesAndFoldersListingActivity extends AbstractAppCompatActivity {

    @BindView(R.id.files_folders_listing_recycler_view)
    public RecyclerView filesAndFoldersRecyclerView;

    private File currentVolumeFile;
    private File rootFolderForCurrentScreen;

    private List<FileAndFolder> fileAndFoldersToDisplay;
    private FilesAndFoldersListingAdapter filesAndFoldersListingAdapter;


    @Override
    public int getActivityLayout() {
        return R.layout.activity_files_and_folders_listing;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentVolumeFile = (File) getIntent().getSerializableExtra("rootDirectory");
        rootFolderForCurrentScreen = currentVolumeFile;
        initializeAndLoadRecyclerView();
    }

    public void initializeAndLoadRecyclerView() {
        filesAndFoldersListingAdapter = new FilesAndFoldersListingAdapter(this, fileAndFoldersToDisplay, (file, type) -> {

            if (type.equals("folder_clicked")) {
                rootFolderForCurrentScreen = file;
                loadRecyclerViewWithRootFolderAsParent();
            }

        });

        filesAndFoldersRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        filesAndFoldersRecyclerView.setAdapter(filesAndFoldersListingAdapter);

        loadRecyclerViewWithRootFolderAsParent();

    }


    @SuppressLint("NotifyDataSetChanged")
    public void loadRecyclerViewWithRootFolderAsParent() {
        fileAndFoldersToDisplay = FileUtils.getChildrenFileAndFolderListForRootFileAndFolder(this, rootFolderForCurrentScreen.getAbsoluteFile());
        filesAndFoldersListingAdapter.setFilesAndFoldersToDisplay(fileAndFoldersToDisplay);
        filesAndFoldersListingAdapter.notifyDataSetChanged();
    }


    /**
     * If the current file path starts with currentVolumeFilePath,
     * this returns true.
     * Used to check if the user is on main page or interior page
     * on back pressed.
     */
    public boolean isParentFileInsideCurrentVolume() {

        if (rootFolderForCurrentScreen == null)
            return false;

        if (rootFolderForCurrentScreen.getParentFile() == null)
            return false;

        return rootFolderForCurrentScreen.getParentFile().getAbsolutePath()
                .startsWith(currentVolumeFile.getAbsolutePath());
    }


    @Override
    public void onBackPressed() {
        if (isParentFileInsideCurrentVolume()) {
            rootFolderForCurrentScreen = rootFolderForCurrentScreen.getParentFile();
            loadRecyclerViewWithRootFolderAsParent();
        } else {
            finish();
        }
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("rootFileForCurrentScreen", rootFolderForCurrentScreen.toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String rootFilePathFromSavedInstanceState = savedInstanceState.getString("rootFileForCurrentScreen");
        rootFolderForCurrentScreen = new File(rootFilePathFromSavedInstanceState);
        loadRecyclerViewWithRootFolderAsParent();
    }


}
