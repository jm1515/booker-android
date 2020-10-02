package com.example.booker.data;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIInterface {

    @Headers("Content-Type: application/json")
    @POST("/users")
    Call<Users> createUser(@Body Users user);

    @Headers("Content-Type: application/json")
    @POST("/users/login")
    Call<JsonObject> getUser(@Body JsonObject body);

    @Headers("Content-Type: application/json")
    @GET("/users/{email}")
    Call<JsonPrimitive> getUserExist(@Path("email") String email);

    @Headers("Content-Type: application/json")
    @POST("/books")
    Call<Users> addOneBook(@Body Books book);

    @Headers("Content-Type: application/json")
    @GET("/books")
    Call<ArrayList<Books>> getAllBooks();

}
