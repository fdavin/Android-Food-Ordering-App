package com.majika.api

import com.majika.api.branch.BranchResponse
import com.majika.api.menu.MenuResponse
import retrofit2.Call
import retrofit2.http.GET

interface Api {
    @GET("v1/branch")
    fun getBranch(): Call<BranchResponse>

    @GET("v1/menu")
    fun getMenu(): Call<MenuResponse>

    @GET("v1/menu/food")
    fun getFoodMenu(): Call<MenuResponse>

    @GET("v1/menu/drink")
    fun getDrinkMenu(): Call<MenuResponse>
}