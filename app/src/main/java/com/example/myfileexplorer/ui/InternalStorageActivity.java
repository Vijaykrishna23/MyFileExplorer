package com.example.myfileexplorer.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfileexplorer.R;
import com.example.myfileexplorer.utils.FileUtils;

import java.io.File;
import java.util.List;

import butterknife.BindView;

public class InternalStorageActivity extends AbstractAppCompatActivity {

    @BindView(R.id.rv_list_files)
    public RecyclerView filesToDisplayRecyclerView;

    private List<File> filesToDisplay;

    private FilesToDisplayAdapter filedToDisplayRecyclerViewAdapter;

    @Override
    public int getActivityLayout() {
        return R.layout.activity_internal_storage;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rootFileForCurrentScreen = INTERNAL_STORAGE_ROOT;
        StorageManager storageManager = (StorageManager) getSystemService(STORAGE_SERVICE);
        initializeAndLoadRecyclerView();
    }

    public void initializeAndLoadRecyclerView() {
        filedToDisplayRecyclerViewAdapter = new FilesToDisplayAdapter(this, filesToDisplay, (file, type) -> {

            if(type.equals("folder_clicked")) {
                rootFileForCurrentScreen = file;
                loadRecyclerViewWithRootFileAsParent();
            }

        });

        filesToDisplayRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        filesToDisplayRecyclerView.setAdapter(filedToDisplayRecyclerViewAdapter);

        loadRecyclerViewWithRootFileAsParent();

    }


    @SuppressLint("NotifyDataSetChanged")
    public void loadRecyclerViewWithRootFileAsParent() {
        filesToDisplay = FileUtils.getChildrenForFile(rootFileForCurrentScreen.getAbsoluteFile());
        filedToDisplayRecyclerViewAdapter.setFilesToDisplay(filesToDisplay);
        filedToDisplayRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        if (isParentFileOfRootFileInsideInternalStorage()) {
            rootFileForCurrentScreen = rootFileForCurrentScreen.getParentFile();
            loadRecyclerViewWithRootFileAsParent();
        } else {
            finish();
        }
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("rootFileForCurrentScreen",rootFileForCurrentScreen.toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String rootFilePathFromSavedInstanceState  = savedInstanceState.getString("rootFileForCurrentScreen");
        rootFileForCurrentScreen = new File(rootFilePathFromSavedInstanceState);
        loadRecyclerViewWithRootFileAsParent();
    }
}
