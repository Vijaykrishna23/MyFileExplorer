package com.example.myfileexplorer.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfileexplorer.R;
import com.example.myfileexplorer.schema.FileAndFolder;
import com.example.myfileexplorer.utils.Constants;
import com.example.myfileexplorer.utils.FileUtils;

import java.io.File;
import java.util.List;

import butterknife.BindView;

/**
 * Single main screen that shows the folders and files.
 * The data is changed when user clicks on directory
 * and when user presses back.
 * <p>
 * Multiple screens are avoided for performance.
 */
public class FilesAndFoldersListingActivity extends AbstractAppCompatActivity {

    @BindView(R.id.files_folders_listing_recycler_view)
    public RecyclerView filesAndFoldersRecyclerView;

    //file objects
    private File currentVolumeFile;
    private File rootFolderForCurrentScreen;

    // recyclerview stuff
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

    /**
     * One time function to be called for initializing the
     * recycler, adapter, and communicationInterface.
     */
    public void initializeAndLoadRecyclerView() {
        filesAndFoldersListingAdapter = new FilesAndFoldersListingAdapter(this, fileAndFoldersToDisplay, (file, type) -> {

            if (type.equals(Constants.FOLDER_CLICKED)) {
                rootFolderForCurrentScreen = file;
                loadRecyclerViewWithRootFolderAsParent();
            }

        });

        filesAndFoldersRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        filesAndFoldersRecyclerView.setAdapter(filesAndFoldersListingAdapter);

        loadRecyclerViewWithRootFolderAsParent();

    }

    /**
     * Loads recyclerview with root directory's children as elements.
     * Whenever we change the `rootFolderForCurrentScreen` variable,
     * this method should be called to ensure the Ui is updated
     * accordingly.
     */
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

    /**
     * Android calls this method when the user presses
     * back button.Since we change the data in the recycler view
     * and give the user virtual feeling of new screens though
     * there's only one screen, when user presses back this activity ends
     * and VolumeListingActivity is shown.
     * <p>
     * So we override this method and have a custom implementation.
     * When user presses back button, we check if the rootDirectory
     * of the page is the inside the specific volume.
     * e.g. rootDirectory='storage/emulated/0/Android is inside
     * VolumeRoot='storage/emulated/0'.
     * <p>
     * If so, we move the user to parent path by changing
     * the `rootFolderForCurrentScreen` variable.
     * <p>
     * If the root directory is not inside the volume, we finish this screen
     * and move the user back to volume listing screen.
     */
    @Override
    public void onBackPressed() {
        if (isParentFileInsideCurrentVolume()) {
            rootFolderForCurrentScreen = rootFolderForCurrentScreen.getParentFile();
            loadRecyclerViewWithRootFolderAsParent();
        } else {
            finish();
        }
    }


    /**
     * This method is called by android on configuration change started.
     * i.e when screen rotates etc..to save the state of the app
     * and restore it later.
     * The `rootFolderForCurrentScreen` variable is saved in
     * the `outstate` in the form of string.
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("rootFileForCurrentScreen", rootFolderForCurrentScreen.toString());
    }

    /**
     * This method is called by android on configuration change ended.
     * i.e when screen rotates etc..to save the state of the app
     * and restore it later.
     * The stored variable in string format is restored and
     * assigned to the existing variable by converting the
     * string to file format.
     */
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String rootFilePathFromSavedInstanceState = savedInstanceState.getString("rootFileForCurrentScreen");
        rootFolderForCurrentScreen = new File(rootFilePathFromSavedInstanceState);
        loadRecyclerViewWithRootFolderAsParent();
    }


}
