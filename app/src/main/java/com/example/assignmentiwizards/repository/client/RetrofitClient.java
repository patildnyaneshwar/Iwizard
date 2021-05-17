package com.example.assignmentiwizards.repository.client;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Dnyaneshwar Patil <dnyaneshwar1926@gmail.com>
 * @version 1.0, $date 2021/23/03 07:44 PM
 */
public class RetrofitClient {
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://jobs.github.com/";
    public static final String POSITIONS = "positions.json";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            synchronized (RetrofitClient.class) {
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(getHttpClient())
                            .build();
                }
            }
        }
        return retrofit;
    }

    /**
     * To avoid SSLHandshakeException protocol for below api < 20
     * supporting only a specific protocol version which client is unaware of.
     */
    public static OkHttpClient getHttpClient() {
        OkHttpClient client = new OkHttpClient();
        try {
            TLSSocketFactory tlsSocketFactory = new TLSSocketFactory();
            if (tlsSocketFactory.getTrustManager() != null) {
                client = new OkHttpClient.Builder()
                        .sslSocketFactory(tlsSocketFactory, tlsSocketFactory.getTrustManager())
                        .build();
            }
        } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
            e.printStackTrace();
        }
        return client;
    }
}
