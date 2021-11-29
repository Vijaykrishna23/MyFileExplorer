package com.example.myfileexplorer.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.storage.StorageManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfileexplorer.R;
import com.example.myfileexplorer.utils.FileUtils;

import java.io.File;
import java.util.List;

import butterknife.BindView;

public class FilesAndFoldersListingActivity extends AbstractAppCompatActivity {

    @BindView(R.id.files_folders_listing_recycler_view)
    public RecyclerView filesAndFoldersRecyclerView;

    private List<File> filesToDisplay;

    private FilesAndFoldersListingAdapter filesAndFoldersListingAdapter;

    @Override
    public int getActivityLayout() {
        return R.layout.activity_files_and_folders_listing;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rootFolderForCurrentScreen = (File) getIntent().getSerializableExtra("rootDirectory");
        StorageManager storageManager = (StorageManager) getSystemService(STORAGE_SERVICE);
        initializeAndLoadRecyclerView();
    }

    public void initializeAndLoadRecyclerView() {
        filesAndFoldersListingAdapter = new FilesAndFoldersListingAdapter(this, filesToDisplay, (file, type) -> {

            if(type.equals("folder_clicked")) {
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
        filesToDisplay = FileUtils.getChildrenForFile(rootFolderForCurrentScreen.getAbsoluteFile());
        filesAndFoldersListingAdapter.setFilesAndFoldersToDisplay(filesToDisplay);
        filesAndFoldersListingAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        if (isParentFileOfRootFileInsideInternalStorage()) {
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
        String rootFilePathFromSavedInstanceState  = savedInstanceState.getString("rootFileForCurrentScreen");
        rootFolderForCurrentScreen = new File(rootFilePathFromSavedInstanceState);
        loadRecyclerViewWithRootFolderAsParent();
    }
}
