package com.elementarylogics.imagesliderapp.network

import com.elementarylogics.imagesliderapp.dataclases.Product
import com.elementarylogics.imagesliderapp.dataclases.User
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface Apis {

    @FormUrlEncoded
    @POST("forgot_password")
    public fun userForgotPassword(@Field("email") email: String?): Call<Response<Product>>

    @FormUrlEncoded
    @POST("login")
    public fun loginUser(@Field("email") email: String?, @Field("password") password: String?): Call<Response<ResultLogin>>


    @Headers("Accept:application/json")
    @GET("api/v1/customer/profile/{id}")
    fun getUser(
        @Header("Authorization") authHeader: String?, @Path("id") id: String?
    ): Call<ResponseResult<User>>



    @Headers("Accept:application/json")
    @Multipart
    @POST("api/v1/customer/profile/update")
    public fun saveOrUpdate(
        @Header("Authorization") authHeader: String,
        @Part("first_name") first_name: RequestBody,
        @Part("last_name") last_name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("latitude") latitude: RequestBody,
        @Part("longitude") longitude: RequestBody,
        @Part("address") address: RequestBody,
        @Part("phone_number") phone_number: RequestBody,
        @Part photo: MultipartBody.Part,
        @Part("city") city: RequestBody

    ): Call<ResponseResult<User>>


}