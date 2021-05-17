package com.example.assignmentiwizards.ui.jobs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.assignmentiwizards.BR;
import com.example.assignmentiwizards.R;
import com.example.assignmentiwizards.databinding.FragmentJobsDetailBinding;
import com.example.assignmentiwizards.repository.model.JobsModel;

/**
 * @author Dnyaneshwar Patil <dnyaneshwar1926@gmail.com>
 * @version 1.0, $date 2021/24/03 10:28 PM
 */
public class JobsDetailFragment extends Fragment {

    private FragmentJobsDetailBinding binding;
    private JobsViewModel jobsViewModel;
    private JobsModel jobsModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_jobs_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentJobsDetailBinding.bind(view);
        if (getArguments() != null) {
            jobsModel = JobsDetailFragmentArgs.fromBundle(getArguments()).getJobsDetails();
        }

        jobsViewModel = new ViewModelProvider(requireActivity()).get(JobsViewModel.class);
        binding.setVariable(BR.jobsDetail, jobsModel);
        binding.executePendingBindings();

        // If company url not present then not showing company_url layout
        if (jobsModel.getCompanyUrl() == null) {
            binding.companyUrlCard.setVisibility(View.GONE);
        }

        // If job is already applied show the green color to button & text as Applied,
        // which indicates user already applied to job he can't apply again
        // so make button non-clickable
        if (jobsModel.isApplied()) {
            applied();
            binding.btnApply.setClickable(false);
        }

        binding.btnApply.setOnClickListener(btnApply -> {
            jobsModel.setApplied(true);
            jobsViewModel.updateJobs(jobsModel);
            applied();
        });
    }

    /**
     * @implNote change the apply button color & text, once user applied to job
     */
    private void applied() {
        binding.btnApply.setText(getResources().getString(R.string.applied));
        binding.btnApply.setBackgroundColor(
                ResourcesCompat.getColor(
                        getResources(),
                        android.R.color.holo_green_dark,
                        null
                )
        );
    }
}