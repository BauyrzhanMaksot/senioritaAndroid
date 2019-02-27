package com.example.user.senioritaandroid;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @POST("/oauth/token")
    @FormUrlEncoded
    @Headers({
            "Accept: application/json"
    })
    Single<Token> loginAccount(@Field("grant_type") String grantType,
                               @Field("username") String username,
                               @Field("password") String password,
                               @Header("Authorization") String authKey,
                               @Header("Content-Type") String contentType,
                               @Header("noToken") Boolean noToken);

    @GET("/bake/getAndroidUser")
    Single<User> getUser();
}
