package com.majika.api

import com.majika.api.branch.BranchResponse
import com.majika.api.menu.MenuResponse
import com.majika.api.payment.PaymentResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Api {
    @GET("v1/branch")
    fun getBranch(): Call<BranchResponse>

    @GET("v1/menu")
    fun getMenu(): Call<MenuResponse>

    @GET("v1/menu/food")
    fun getFoodMenu(): Call<MenuResponse>

    @GET("v1/menu/drink")
    fun getDrinkMenu(): Call<MenuResponse>

    @POST("/v1/payment/{code}")
    fun checkPayment(@Path("code") code: String): Call<PaymentResponse>
}