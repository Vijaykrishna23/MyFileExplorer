package com.example.myfileexplorer.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfileexplorer.R;
import com.example.myfileexplorer.schema.FileAndFolder;
import com.example.myfileexplorer.utils.Constants;
import com.example.myfileexplorer.utils.FileUtils;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The adapter class to lists the files and folders of the root Directory.
 * Given the list, it displays the folders and files and sets the onClickListener
 * respectively.
 */
public class FilesAndFoldersListingAdapter extends RecyclerView.Adapter<FilesAndFoldersListingAdapter.FilesAndFoldersListingViewHolder> {

    private final Context activityContext;
    private final FilesAndFoldersListingInterface communicationInterface;
    private List<FileAndFolder> filesAndFoldersToDisplay;

    public FilesAndFoldersListingAdapter(Context activityContext, List<FileAndFolder> filesAndFoldersToDisplay, FilesAndFoldersListingInterface communicationInterface) {
        this.activityContext = activityContext;
        this.filesAndFoldersToDisplay = filesAndFoldersToDisplay;
        this.communicationInterface = communicationInterface;
    }

    public void setFilesAndFoldersToDisplay(List<FileAndFolder> filesAndFoldersToDisplay) {
        this.filesAndFoldersToDisplay = filesAndFoldersToDisplay;
    }


    @NonNull
    @Override
    public FilesAndFoldersListingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.component_folder,
                parent,
                false
        );
        return new FilesAndFoldersListingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FilesAndFoldersListingViewHolder holder, int position) {
        FileAndFolder currentFileAndFolder = filesAndFoldersToDisplay.get(position);

        holder.fileFolderName.setText(currentFileAndFolder.fileAndFolderName);
        holder.fileFolderDescription.setText(currentFileAndFolder.fileAndFolderDescription);
        holder.fileFolderIcon.setImageResource(currentFileAndFolder.fileAndFolderIcon);

        holder.itemView.setOnClickListener(v -> {
            if (currentFileAndFolder.isFolder()) {
                communicationInterface.someEvent(currentFileAndFolder.javaIoFile, Constants.FOLDER_CLICKED);
            } else {
                FileUtils.openAppChooserBasedOnFileExtension(activityContext, currentFileAndFolder.javaIoFile);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filesAndFoldersToDisplay.size();
    }


    public interface FilesAndFoldersListingInterface {
        void someEvent(File file, String eventType);
    }

    /**
     * ViewHolder class for file and folder.
     * contains the name, the items or the size and the icon
     * that is configurable in the adapter.
     */
    static class FilesAndFoldersListingViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_folder)
        ImageView fileFolderIcon;

        @BindView(R.id.tv_folder_name)
        TextView fileFolderName;

        @BindView(R.id.tv_items)
        TextView fileFolderDescription;

        public FilesAndFoldersListingViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
