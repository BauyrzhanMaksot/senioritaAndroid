package com.example.user.senioritaandroid;

import com.example.user.senioritaandroid.Client.Request;
import com.example.user.senioritaandroid.Driver.Offer;
import com.example.user.senioritaandroid.User.Street;
import com.example.user.senioritaandroid.User.Token;
import com.example.user.senioritaandroid.User.User;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

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

    @GET("/getUser")
    @Headers({
            "Accept: application/json"
    })
    Single<User> getUser();

    @POST("/bake/register")
    Single<String> register(@Body User user);

    @GET("/bake/images/{location}")
    Single<ResponseBody> getImage(@Path("location") String location);

    @GET("/getRequests")
    @Headers({
            "Accept: application/json"
    })
    Single<ArrayList<Request>> getRequests();

    @GET("/getOffers")
    @Headers({
            "Accept: application/json"
    })
    Single<ArrayList<Offer>> getOffers();

    @GET("/getAcceptedHistoryDriver")
    @Headers({
            "Accept: application/json"
    })
    Single<List<Order>> getAcceptedRequests();

    @GET("/getAcceptedHistoryClient")
    @Headers({
            "Accept: application/json"
    })
    Single<List<Order>> getAcceptedOffers();

    @GET("/getHistoryDriver")
    @Headers({
            "Accept: application/json"
    })
    Single<List<Order>> getHistory();

    @POST("/putOffer")
    Single<String> putOffer(@Body Offer offer);

    @GET("/acceptRequest/{requestId}")
    Single<String> acceptRequest(@Path("requestId") Long requestId);

    @GET("/acceptOffer/{offerId}")
    Single<String> acceptOffer(@Path("offerId") Long offerdId);

    @GET("finishRequest/{requestId}")
    Single<String> finishRequest(@Path("requestId") Long requestId);

    @POST("/updateUser")
    Single<String> updateUser(@Body User user);

    @POST("/putRequest")
    Single<String> putRequest(@Body Request request);

    @GET("/getStreets")
    Single<List<Street>> getStreets();

    @GET("/getOffer")
    Single<List<Offer>> getMyOffers();

    @GET("getDriver/{id}")
    Single<User> getDriver(@Path("id") Long id);

    @GET("/getRequests")
    Single<List<Request>> getMyRequests();
}
