package com.example.assignmentiwizards.repository.client;

import com.example.assignmentiwizards.repository.model.JobsModel;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author Dnyaneshwar Patil <dnyaneshwar1926@gmail.com>
 * @version 1.0, $date 2021/23/03 08:02 PM
 * */
public interface RetrofitDataService {
    @GET(RetrofitClient.POSITIONS)
    Call<List<JobsModel>> getJobs();

    @GET(RetrofitClient.POSITIONS)
    Call<List<JobsModel>> getSearchedJobs(
            @Query("page") int page,
            @Query("search") String jobSearch
    );
}
