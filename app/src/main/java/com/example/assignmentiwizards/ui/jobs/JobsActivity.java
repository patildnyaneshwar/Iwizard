package com.example.assignmentiwizards.ui.jobs;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.assignmentiwizards.R;

/**
 * @author Dnyaneshwar Patil <dnyaneshwar1926@gmail.com>
 * @version 1.0, $date 2021/23/03 07:14 PM
 */
public class JobsActivity extends AppCompatActivity {

    private NavController navController;
    JobsViewModel jobsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }
        jobsViewModel = new ViewModelProvider(this).get(JobsViewModel.class);
        NavigationUI.setupActionBarWithNavController(this, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }

    /**
     * Check internet connectivity
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    /**
     * OnBackPressed() check weather data is searched jobs/all jobs,
     * if searched jobs navigate to JobsFragment(using same fragment, model class & adapter
     *  just to show like SearchFragment is different), so using jobsViewModel.isSearched
     * */
    @Override
    public void onBackPressed() {
        if (navController.getCurrentDestination().getId() == R.id.mainFragment)
            if (jobsViewModel.isSearched) {
                jobsViewModel.isSearched = false;
                jobsViewModel.getAllJobs();
                return;
            }
        super.onBackPressed();
    }
}