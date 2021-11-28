package com.example.myfileexplorer.ui.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.myfileexplorer.R;
import com.example.myfileexplorer.utils.FileUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FolderViewHolder extends BaseViewHolder {

    @BindView(R.id.iv_folder)
    ImageView folderIcon;

    @BindView(R.id.tv_folder_name)
    TextView folderNameText;

    @BindView(R.id.tv_items)
    TextView numberOfItemsText;


    public FolderViewHolder(@NonNull  View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

    @Override
    public void postPerformBind() {
        folderNameText.setText(currentFileInViewHolder.getName());
        numberOfItemsText.setText(FileUtils.getDisplayTextForNumberOfItemsInFolder(currentFileInViewHolder));
    }

    @Override
    public void onClick(View v) {
        communicationInterface.someEvent(currentFileInViewHolder, "folder_clicked");
    }

}
