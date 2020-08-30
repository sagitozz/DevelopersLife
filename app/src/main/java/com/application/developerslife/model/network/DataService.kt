package com.application.developerslife.model.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @autor d.snytko
 */
interface DataService {

    @GET("{category}/{pageNumber}?json=true&pageSize=50")
    fun getImages(
        @Path("pageNumber") pageNumber: Int,
        @Path("category") category: String
    ): Call<GifResponse>
}

class DataServiceImpl(private val gifRestApi: GifRestApi) :
    DataService {

    @GET("{category}/{pageNumber}?json=true&pageSize=50")
    override fun getImages(
        @Path("pageNumber") pageNumber: Int,
        @Path("category") category: String
    ): Call<GifResponse> {

        return gifRestApi.getEndPoint().getImages(pageNumber, category)
    }
}
