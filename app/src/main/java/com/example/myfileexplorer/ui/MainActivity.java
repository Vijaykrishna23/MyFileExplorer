package com.example.myfileexplorer.ui;

import android.content.Intent;

import com.example.myfileexplorer.R;

import butterknife.OnClick;

public class MainActivity extends AbstractAppCompatActivity{

    @Override
    public int getActivityLayout() {
        return R.layout.activity_main;
    }

    @OnClick(R.id.btn_internal_storage)
    public void openInternalStorage() {
        moveToActivity(InternalStorageActivity.class);
    }

}
