package com.elementarylogics.imagesliderapp.network

import com.elementarylogics.imagesliderapp.dataclases.AddressModel
import com.elementarylogics.imagesliderapp.dataclases.Product
import com.elementarylogics.imagesliderapp.dataclases.Register
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

//    @FormUrlEncoded
//    @POST("login")
//    public fun loginUser(@Field("email") email: String?, @Field("password") password: String?): Call<Response<ResultLogin>>


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
        @Part("customer_id") customer_id: RequestBody,
        @Part("first_name") first_name: RequestBody,
        @Part("last_name") last_name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("latitude") latitude: RequestBody,
        @Part("longitude") longitude: RequestBody,
        @Part("address") address: RequestBody,
        @Part("phone_number") phone_number: RequestBody,
        @Part photo: MultipartBody.Part,
        @Part("city") city: RequestBody,
        @Part("house_flate_number") house_flate_number: RequestBody,
        @Part("area_colony") area_colony: RequestBody

    ): Call<ResponseResult<User>>


    @Headers("Accept:application/json")
    @Multipart
    @POST("api/v1/customer/deliveryAddress/update")
    public fun saveOrUpdateAddress(
        @Header("Authorization") authHeader: String,
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("latitude") latitude: RequestBody,
        @Part("longitude") longitude: RequestBody,
        @Part("address") address: RequestBody,
        @Part image: MultipartBody.Part?,
        @Part("city") city: RequestBody,
        @Part("address_type") address_type: RequestBody,
        @Part("house_flate_number") house_flate_number: RequestBody,
        @Part("delivery_address_id") delivery_address_id: RequestBody,
        @Part("area_colony") area_colony: RequestBody

    ): Call<ResponseResult<AddressModel>>

    @Headers("Accept:application/json")
    @GET("api/v1/customer/deliveryAddress/{id}")
    fun getAllAddresses(
        @Header("Authorization") authHeader: String?, @Path("id") id: String?
    ): Call<ResponseListResult<AddressModel>>


    // regiser

    @FormUrlEncoded
    @Headers("Accept:application/json")
    @POST("api/v1/customer/register")
    public fun registerUser(@Header("Authorization") authHeader: String?, @Field("phone_number") phone_number: String?): Call<ResponseResult<Register>>

    @FormUrlEncoded
    @Headers("Accept:application/json")
    @POST("api/v1/customer/verifyCode")
    public fun verifyCode(
        @Header("Authorization") authHeader: String?, @Field("phone_number") phone_number: String?,
        @Field("code") code: String?
    ): Call<ResponseResult<User>>


    //address date and time
    @Headers("Accept:application/json")
    @GET("api/v1/customer/deliveryAddress/{id}")
    fun getAddress(
        @Header("Authorization") authHeader: String?, @Path("id") id: String?
    ): Call<ResponseListResult<AddressModel>>


}