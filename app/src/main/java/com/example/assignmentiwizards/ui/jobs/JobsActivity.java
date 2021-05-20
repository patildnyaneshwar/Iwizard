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
import com.example.assignmentiwizards.utils.Constants;
import com.google.firebase.crashlytics.CustomKeysAndValues;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

/**
 * @author Dnyaneshwar Patil <dnyaneshwar1926@gmail.com>
 * @version 1.0, $date 2021/23/03 07:14 PM
 */
public class JobsActivity extends AppCompatActivity {
    private static final String TAG = JobsActivity.class.getSimpleName();

    private NavController navController;
    JobsViewModel jobsViewModel;

    /**
     * This Crash will occur only in debug Build Variant
     * */
    private void crashDebug(){
        throw new IllegalStateException("Debug Crash");
    }

    /**
     * This Crash will occur only in QA Build Variant
     * */
    private void crashQa(){
        throw new IllegalStateException("QA Crash");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs);
        //Depending on the different Build Variant we get the BUILD_NAME
        //from there respective Constants.class
        Log.d(TAG, "onCreate:Build Variant "+ Constants.BUILD_NAME);
        /*if (Constants.BUILD_NAME.equalsIgnoreCase("debug")){
            crashDebug();
        } else {
            crashQa();
        }*/

        /*CustomKeysAndValues keysAndValues = new CustomKeysAndValues.Builder()
                .putString("string key", "Test")
                .putString("string key 2", "string  value 2")
                .putBoolean("boolean key", true)
                .putBoolean("boolean key 2", false)
                .putFloat("float key", 1.01f)
                .putFloat("float key 2", 2.02f)
                .build();

        FirebaseCrashlytics.getInstance().setCustomKeys(keysAndValues);
        FirebaseCrashlytics.getInstance().setUserId("12345");

        try {
            //
        } catch (Exception e){
            FirebaseCrashlytics.getInstance().recordException(e);
        }*/

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