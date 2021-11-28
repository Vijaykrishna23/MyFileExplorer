package com.example.myfileexplorer.ui.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.myfileexplorer.R;
import com.example.myfileexplorer.utils.FileUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FileViewHolder extends BaseViewHolder {

    @BindView(R.id.iv_file_icon)
    ImageView fileIcon;

    @BindView(R.id.tv_file_name)
    TextView fileNameText;

    @BindView(R.id.tv_file_size)
    TextView fileSizeText;

    public FileViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

    @Override
    public void postPerformBind() {
        fileNameText.setText(currentFileInViewHolder.getName());
        fileSizeText.setText(FileUtils.getDisplayTextForFileSize(activityContext,currentFileInViewHolder));
    }

    @Override
    public void onClick(View v) {
        FileUtils.openAppChooserBasedOnFileExtension(activityContext,currentFileInViewHolder);
//        fileNameText.setText(FileUtils.getMimeTypeFromFile(activityContext, currentFileInViewHolder));


    }
}
