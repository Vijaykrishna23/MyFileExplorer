package com.example.myfileexplorer.ui.viewholder;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfileexplorer.ui.FilesAndFoldersListingAdapter;

import java.io.File;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {

    protected File currentFileInViewHolder;
    protected FilesAndFoldersListingAdapter.FilesAndFoldersListingInterface communicationInterface;
    protected Context activityContext;

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        activityContext = itemView.getContext();
    }

    public void performBind(File currentFileInViewHolder, Context activityContext, FilesAndFoldersListingAdapter.FilesAndFoldersListingInterface communicationInterface) {
        this.currentFileInViewHolder = currentFileInViewHolder;
        this.communicationInterface= communicationInterface;
        this.activityContext = activityContext;
        postPerformBind();
    }

    public abstract void postPerformBind();


    @Override
    public void onClick(View v) {
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
