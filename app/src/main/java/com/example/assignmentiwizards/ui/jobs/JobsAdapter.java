package com.example.assignmentiwizards.ui.jobs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;
import com.example.assignmentiwizards.R;
import com.example.assignmentiwizards.databinding.ItemJobsBinding;
import com.example.assignmentiwizards.repository.model.JobsModel;
import java.util.List;

/**
 * @author Dnyaneshwar Patil <dnyaneshwar1926@gmail.com>
 * @version 1.0, $date 2021/24/03 7:36 PM
 */
public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.ViewHolder> {

    private final List<JobsModel> jobsModelList;
    private final Context context;
    private final OnItemClickListener onItemClickListener;

    /**
     * These interface updates data in
     * {@link JobsFragment}
     * */
    interface OnItemClickListener {
        void onItemClick(View root, int position, JobsModel jobsModel);
        void onApplyClick(int position, JobsModel jobsModel);
    }

    public JobsAdapter(
            List<JobsModel> jobsModelList,
            Context context,
            OnItemClickListener onItemClickListener
    ) {
        this.jobsModelList = jobsModelList;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_jobs, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JobsModel jobsModel = jobsModelList.get(position);
        holder.bind(jobsModel);
    }

    @Override
    public int getItemCount() {
        return jobsModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemJobsBinding binding;

        public ViewHolder(@NonNull ItemJobsBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
//            binding.viewImgGradientBg.setAlpha(0.5f);
        }

        void bind(JobsModel jobsModel) {
            binding.setVariable(BR.jobs, jobsModel);
            binding.executePendingBindings();

            // If job is already applied set green color to button & change text to Applied
            // If not button color will be purple & text will be apply
            // once user applied then make button non-clickable
            if (jobsModel.isApplied()) {
                isApplied(context.getResources().getString(R.string.applied),
                        android.R.color.holo_green_dark);
                binding.btnApply.setClickable(false);
            } else {
                isApplied(context.getResources().getString(R.string.apply), R.color.purple_500);
                binding.btnApply.setClickable(true);
            }

            binding.btnApply.setOnClickListener(view -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION)
                    onItemClickListener.onApplyClick(getAdapterPosition(), jobsModel);
            });
            binding.getRoot().setOnClickListener(view -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION)
                    onItemClickListener.onItemClick(
                            binding.getRoot(),
                            getAdapterPosition(),
                            jobsModel
                    );
            });
        }

        /**
         * @implNote these will change the text & color of the item accordingly
         * @param text, set's the Apply/Applied to item,
         * @param color, set's the color to item
         * */
        private void isApplied(String text, int color) {
            binding.btnApply.setText(text);
            binding.btnApply.setBackgroundColor(
                    ResourcesCompat.getColor(context.getResources(), color, null)
            );
        }
    }

}
