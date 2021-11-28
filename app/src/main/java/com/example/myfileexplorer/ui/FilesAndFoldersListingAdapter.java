package com.example.myfileexplorer.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfileexplorer.R;
import com.example.myfileexplorer.utils.AppUtils;
import com.example.myfileexplorer.utils.Constants;
import com.example.myfileexplorer.ui.viewholder.BaseViewHolder;
import com.example.myfileexplorer.ui.viewholder.FileViewHolder;
import com.example.myfileexplorer.ui.viewholder.FolderViewHolder;

import java.io.File;
import java.util.List;

public class FilesAndFoldersListingAdapter extends RecyclerView.Adapter<BaseViewHolder>  {

    private final Context activityContext;
    private List<File> filesAndFoldersToDisplay;
    private final FilesAndFoldersListingInterface communicationInterface;

    public FilesAndFoldersListingAdapter(Context activityContext, List<File> filesAndFoldersToDisplay, FilesAndFoldersListingInterface communicationInterface) {
        this.activityContext = activityContext;
        this.filesAndFoldersToDisplay = filesAndFoldersToDisplay;
        this.communicationInterface = communicationInterface;
    }

    public void setFilesAndFoldersToDisplay(List<File> filesAndFoldersToDisplay) {
        this.filesAndFoldersToDisplay = filesAndFoldersToDisplay;
    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case Constants.VIEW_TYPE_FOLDER:
                view = LayoutInflater.from(activityContext).inflate(R.layout.item_single_folder, parent, false);
                return new FolderViewHolder(view);
            case Constants.VIEW_TYPE_FILE:
                view = LayoutInflater.from(activityContext).inflate(R.layout.item_single_file,parent,false);
                return new FileViewHolder(view);
            default:
                throw new IllegalArgumentException();

        }


    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.performBind(filesAndFoldersToDisplay.get(position),activityContext, communicationInterface);
    }

    @Override
    public int getItemViewType(int position) {
        return AppUtils.getViewType(filesAndFoldersToDisplay.get(position));
    }

    @Override
    public int getItemCount() {
        return filesAndFoldersToDisplay.size();
    }


    public interface FilesAndFoldersListingInterface {
        void someEvent(File file,String type);
    }

}
