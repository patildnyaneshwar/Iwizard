package com.example.assignmentiwizards.repository;

import androidx.annotation.NonNull;
import com.example.assignmentiwizards.repository.client.RetrofitClient;
import com.example.assignmentiwizards.repository.client.RetrofitDataService;
import com.example.assignmentiwizards.repository.model.JobsModel;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Dnyaneshwar Patil <dnyaneshwar1926@gmail.com>
 * @version 1.0, $date 2021/23/03 11:05 PM
 */
public class JobsRepository {
    private static JobsRepository instance;
    private Service service;

    /**
     * interface used to update response in
     * {@link com.example.assignmentiwizards.ui.jobs.JobsViewModel}
     */
    public interface Service {
        void onSuccess(Response<List<JobsModel>> response);
        void onFailed(Throwable t);
        void onSearchQuerySuccess(Response<List<JobsModel>> response, String query);
        void onSearchQueryFailed(Throwable t);
    }

    public static JobsRepository getInstance() {
        if (instance == null) {
            synchronized (JobsRepository.class) {
                if (instance == null) {
                    instance = new JobsRepository();
                }
            }
        }
        return instance;
    }

    /**
     * @apiNote These API will provides all jobs available in server
     * */
    public void allJobs(Service service) {
        this.service = service;
        RetrofitDataService retrofit = RetrofitClient.getRetrofitInstance()
                .create(RetrofitDataService.class);
        retrofit.getJobs().enqueue(new Callback<List<JobsModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<JobsModel>> call,
                                   @NonNull Response<List<JobsModel>> response) {
                if (response.body() != null) {
                    service.onSuccess(response);
                } else {
                    service.onFailed(new Exception("Response null"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<JobsModel>> call,
                                  @NonNull Throwable t) {
                service.onFailed(t);
            }
        });
    }

    /**
     * @apiNote These API will provides only searched key jobs
     * */
    public void searchedJobs(Service service, int pageNo, String query) {
        this.service = service;
        RetrofitDataService retrofit = RetrofitClient.getRetrofitInstance()
                .create(RetrofitDataService.class);
        retrofit.getSearchedJobs(pageNo, query).enqueue(new Callback<List<JobsModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<JobsModel>> call,
                                   @NonNull Response<List<JobsModel>> response) {
                if (response.body() != null) {
                    service.onSearchQuerySuccess(response, query);
                } else {
                    service.onSearchQueryFailed(new Exception("searchQuery Response null"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<JobsModel>> call,
                                  @NonNull Throwable t) {
                t.printStackTrace();
                service.onSearchQueryFailed(t);
            }
        });
    }

}
