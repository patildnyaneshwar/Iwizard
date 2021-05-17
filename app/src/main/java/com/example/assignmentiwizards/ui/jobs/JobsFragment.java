package com.example.assignmentiwizards.ui.jobs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.assignmentiwizards.R;
import com.example.assignmentiwizards.databinding.FragmentJobsBinding;
import com.example.assignmentiwizards.repository.model.JobsModel;

/**
 * @author Dnyaneshwar Patil <dnyaneshwar1926@gmail.com>
 * @version 1.0, $date 2021/23/03 07:18 PM
 */
public class JobsFragment extends Fragment implements JobsAdapter.OnItemClickListener {

    private FragmentJobsBinding binding;
    private JobsViewModel jobsViewModel;
    private JobsAdapter jobsAdapter;

    public JobsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_jobs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentJobsBinding.bind(view);

        jobsViewModel = new ViewModelProvider(
                requireActivity(),
                getDefaultViewModelProviderFactory()
        ).get(JobsViewModel.class);
        // If internet not available notify
        if (JobsActivity.isNetworkAvailable(requireContext())) {
            jobsViewModel.allJobs();
        } else {
            Toast.makeText(
                    requireContext(),
                    getResources().getString(R.string.no_net_connection),
                    Toast.LENGTH_SHORT
            ).show();
            jobsViewModel.getAllJobs();
        }
        initialiseUiElements();

        jobsViewModel.getJobs().observe(getViewLifecycleOwner(), jobsModels -> {
            if (jobsModels.isEmpty()) {
                // to survive from orientation change make isShowToast = true
                if (jobsViewModel.isShowToast) {
                    jobsViewModel.isShowToast = false;
                    Toast.makeText(
                            requireContext(),
                            getResources().getString(R.string.jobs_not_found),
                            Toast.LENGTH_SHORT
                    ).show();
                }
                return;
            }
            jobsViewModel.jobsModelList.clear();
            jobsViewModel.jobsModelList.addAll(jobsModels);
            // notify JobsAdapter about changes
            jobsAdapter.notifyDataSetChanged();
        });

        setHasOptionsMenu(true);
    }

    private void initialiseUiElements() {
        binding.setViewModel(jobsViewModel);

        jobsAdapter = new JobsAdapter(
                jobsViewModel.jobsModelList,
                requireContext(),
                this
        );
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(jobsAdapter);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        // Survive orientation change with ViewModel & set the old query in searchView
        if (!jobsViewModel.query.isEmpty()) {
            searchItem.expandActionView();
            searchView.clearFocus();
            searchView.setQuery(jobsViewModel.query, false);
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (JobsActivity.isNetworkAvailable(requireContext())) {
                    jobsViewModel.searchQuery(s);
                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    /**
     * On complete Item click navigating to show Complete JobDetail
     * */
    @Override
    public void onItemClick(View root, int position, JobsModel jobsModel) {
        Navigation.findNavController(root)
                .navigate(JobsFragmentDirections.actionMainFragmentToJobsDetailFragment(jobsModel));
    }

    /**
     * OnApplied() updating data in local database/cache,
     * and notify adapter about changes
     * */
    @Override
    public void onApplyClick(int position, JobsModel jobsModel) {
        jobsModel.setApplied(true);
        jobsViewModel.updateJobs(jobsModel);
        jobsAdapter.notifyItemChanged(position);
    }
}