package com.example.myfileexplorer.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfileexplorer.R;
import com.example.myfileexplorer.schema.CustomVolume;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter class for the `VolumeSchema` object. Displays the name of the volume in a button.
 * And when user clicks the button, redirects to the `FileAndFolderListingActivity`.
 */
public class VolumesListingAdapter extends RecyclerView.Adapter<VolumesListingAdapter.VolumesListingViewHolder> {

    private final List<CustomVolume> volumeList;
    private final Context activityContext;

    public VolumesListingAdapter(Context activityContext, List<CustomVolume> volumeList) {
        this.volumeList = volumeList;
        this.activityContext = activityContext;
    }


    @NonNull
    @Override
    public VolumesListingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.component_button,
                parent,
                false
        );
        return new VolumesListingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VolumesListingViewHolder holder, int position) {

        CustomVolume currentVolume = volumeList.get(position);
        holder.volumeNameButton.setText(currentVolume.volumeName);

        holder.volumeNameButton.setOnClickListener((v -> {
            Intent intent = new Intent(activityContext, FilesAndFoldersListingActivity.class);
            intent.putExtra("rootDirectory", currentVolume.volumeFileWithPath);
            activityContext.startActivity(intent);
        }));
    }

    @Override
    public int getItemCount() {
        return volumeList.size();
    }

    /**
     * ViewHolder class for the single button where the name of the volume
     * is displayed.
     */
    static class VolumesListingViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.volume_name_button)
        public TextView volumeNameButton;

        public VolumesListingViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
