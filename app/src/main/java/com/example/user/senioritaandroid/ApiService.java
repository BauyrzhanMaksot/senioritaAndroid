package com.example.user.senioritaandroid;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("person/{person_id}")
    Single<Student> getPersonData(@Path("person_id") int personId,
                                  @Query("api_key") String apiKey);

    @GET("/bake/getAndroidUser")
    Single<User> getUser();
}
