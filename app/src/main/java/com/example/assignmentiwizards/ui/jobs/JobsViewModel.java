package com.example.assignmentiwizards.ui.jobs;

import android.app.Application;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.assignmentiwizards.repository.JobsRepository;
import com.example.assignmentiwizards.repository.database.JobsDao;
import com.example.assignmentiwizards.repository.database.JobsDatabase;
import com.example.assignmentiwizards.repository.model.JobsModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import retrofit2.Response;

/**
 * @author Dnyaneshwar Patil <dnyaneshwar1926@gmail.com>
 * @version 1.0, $date 2021/24/03 08:12 PM
 * These ViewModel is also called as SharedViewModel
 */
public class JobsViewModel extends AndroidViewModel implements JobsRepository.Service {

    private final MutableLiveData<List<JobsModel>> jobsModelMutableLiveData;
    public ObservableField<Boolean> loading;
    private final Application application;
    private final JobsRepository jobsRepository;
    public List<JobsModel> jobsModelList = new ArrayList<>();
    private final JobsDao jobsDao;
    // trace user typing if user type any keyword before 600ms then cancel timer & re-start
    private Timer timer;
    // upto pageNo 3 data is available, for pagination check data upto page 3
    // if still data not found then notify as "Jobs not found"
    private int pageNo = 1;
    // For search keywords upto page 3 for pagination & to survive orientation change
    public String query = "";
    // For handling BackPress() in Activity re-using same fragment & adapter
    // to show AllJobs & searched jobs
    public Boolean isSearched = false;
    // If searched query is already searching then not hitting the api again upto we get response
    private Boolean isAlreadySearching = false;
    // To survive orientation change, that after orientation toast will not trigger again
    public Boolean isShowToast = true;

    public JobsViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        jobsModelMutableLiveData = new MutableLiveData<>();
        loading = new ObservableField<>();
        jobsRepository = JobsRepository.getInstance();
        loading.set(true);
        jobsDao = JobsDatabase.getInstance(application).dbDao();
    }

    /**
     * allJobs will hit service & gives all jobs from service
     * */
    public void allJobs() {
        AsyncTask.execute(() -> {
            // To survive orientation change, only if data = null then hit api otherwise
            // return the previous data
            if (jobsModelMutableLiveData.getValue() == null
                    && JobsActivity.isNetworkAvailable(application)
            ) {
                jobsRepository.allJobs(this);
            }
        });
    }

    /**
     * if internet is not available & onBackPress()
     * show data from local database
     * */
    public void getAllJobs() {
        AsyncTask.execute(() -> {
            query = "";
            loading.set(false);
            jobsModelMutableLiveData.postValue(jobsDao.getAllJobs());
        });
    }

    /**
     * Only hit search api when query is notEmpty & is not already performing search
     * */
    public void searchQuery(String s) {
        AsyncTask.execute(() -> {
            if (timer != null)
                timer.cancel();

            timer = new Timer();
            isSearched = true;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (!s.isEmpty() && !isAlreadySearching) {
                        query = s;
                        loading.set(true);
                        jobsRepository.searchedJobs(JobsViewModel.this, pageNo, query);
                    }
                }
            }, 600);
        });
    }

    protected LiveData<List<JobsModel>> getJobs() {
        return jobsModelMutableLiveData;
    }

    /**
     * Update job applied = true in local database
     * */
    public void updateJobs(JobsModel jobsModel) {
        AsyncTask.execute(() -> {
            jobsDao.update(jobsModel);
        });
    }

    /**
     * onSuccess response from jobs api insert all data into local database as cache for offline work
     * Once new data comes from api, it will replace with old data & keep's it as cache
     * */
    @Override
    public void onSuccess(Response<List<JobsModel>> response) {
        AsyncTask.execute(() -> {
            jobsDao.insertAll(response.body());
            jobsModelMutableLiveData.postValue(jobsDao.getAllJobs());
            loading.set(false);
        });
    }

    /**
     * onFailed to get data from service fetch data from local database/cache
     * */
    @Override
    public void onFailed(Throwable t) {
        AsyncTask.execute(() -> {
            jobsModelMutableLiveData.postValue(jobsDao.getAllJobs());
            loading.set(false);
        });
    }

    /**
     * Not storing searched data in local database,
     * As search queries always perform from api's,
     * If didn't found data in 1st page check with 2nd page by calling same api with pageNo increment
     * and with same query, still not found data then notify as "Job not found"
     *
     * Performed data search upto 3 pages, because data is available upto pageNo: 3
     * & check weather the search query is same if not then start from 1st page again
     * as we made isShowToast = false to survive orientation change will make here
     * isShowToast = true to trigger toast
     * */
    @Override
    public void onSearchQuerySuccess(Response<List<JobsModel>> response, String query) {

        isAlreadySearching = false;
        if (response.body().isEmpty()
                && pageNo <= 2
                && JobsActivity.isNetworkAvailable(application)
        ) {
            loading.set(true);
            if (this.query.equalsIgnoreCase(query)) {
                ++pageNo;
            } else {
                pageNo = 1;
            }
            jobsRepository.searchedJobs(this, pageNo, query);
            return;
        }
        if (response.body().isEmpty()) {
            isShowToast = true;
        }
        pageNo = 1;
        jobsModelMutableLiveData.postValue(response.body());
        loading.set(false);
    }

    /**
     * If search query failed then show data from local database/cache
     * */
    @Override
    public void onSearchQueryFailed(Throwable t) {
        AsyncTask.execute(() -> {
            isAlreadySearching = false;
            jobsModelMutableLiveData.postValue(jobsDao.getAllJobs());
        });
    }
}
