package com.example.mainproject.Api;


import com.example.mainproject.Constant.Const;
import com.example.mainproject.Model.*;
import com.example.mainproject.ResponeAPI.*;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIService {
    @FormUrlEncoded
    @POST("login")
    Call<ResponseLogin> login(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("register")
    Call<ResponseRegister> register(@Field("username") String username, @Field("password") String password,
                                    @Field("name") String name,  @Field("gender_id") int gender_id,
                                    @Field("email") String email, @Field("phone") String phone, @Field("addresses") String addresses);

    @GET("selectAvailableNewPet")
    Call<List<Pets>> selectAvailableNewPet();

    @FormUrlEncoded
    @GET("showListImagePet")
    Call<List<ImagesPet>> showListImagePet(@Field("pets_id") int pets_id);

    @FormUrlEncoded
    @POST("showListStylePet")
    Call<List<Style>> showListStylePet(@Field("pets_id") int pets_id);


    @GET("selectAvailableCategoryPet")
    Call<List<ResponseCategoryPet>> selectAvailableCategoryPet();

    @FormUrlEncoded
    @POST("selectInformationPet")
    Call<Pets> selectInformationPet(@Field("pets_id") int pets_id);

    @Multipart
    @POST("uploadAvatar")
    Call<ResponseUpload> upload(@Part(Const.MY_USERNAME) RequestBody username, @Part MultipartBody.Part avatar);

    @FormUrlEncoded
    @POST("getCartToUser")
    Call<Cart> getCartToUser(@Field("user_id") int user_id);

    @FormUrlEncoded
    @POST("addProductToCart")
    Call<ResponseToAPI> addProductToCart(@Field("user_id") int user_id, @Field("pets_id") int pets_id, @Field("count_SP") int count_SP);

    @FormUrlEncoded
    @POST("checkProductToCart")
    Call<ResponseToAPI> checkProductToCart(@Field("user_id") int user_id, @Field("pets_id") int pets_id);

    @FormUrlEncoded
    @POST("updateCountOfItem")
    Call<ResponseToAPI> updateCountOfItem(@Field("count_SP") int count_SP, @Field("cartdetail_id") int cartdetail_id);

    @FormUrlEncoded
    @POST("getOrderToUser")
    Call<List<Order>> getOrderToUser(@Field("user_id") int user_id);

    @FormUrlEncoded
    @POST("deleteCartDetail")
    Call<ResponseToAPI> deleteCartDetail(@Field("cartdetail_id") int cartdetail_id);

    @FormUrlEncoded
    @POST("addOrderToUser")
    Call<ResponseToAPI> addOrderToUser(@Field("user_id") int user_id, @Field("address") String address, @Field("phone") String phone);

    @FormUrlEncoded
    @POST("editInforUser")
    Call<ResponseUpload> editInforUser(@Field("name") String name, @Field("email") String email, @Field("addresses") String addresses, @Field("phone") String phone, @Field("user_id") int user_id);

    @GET("getAllCategory")
    Call<List<Category>> getAllCategory();

    @GET("getAllOrder")
    Call<List<Order>> getAllOrder();

    @FormUrlEncoded
    @POST("updateStatusOrder")
    Call<ResponseToAPI> updateStatusOrder(@Field("status_order") int status_order, @Field("order_id") int order_id);

    @GET("getSales")
    Call<List<ResponseSales>> getSales();

    @POST("filterPetToStyle")
    Call<List<Pets>> filterPetToStyle(@Body RequestPet requestPet);
}
