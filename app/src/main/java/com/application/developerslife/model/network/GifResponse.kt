package com.application.developerslife.model.network

import com.google.gson.annotations.SerializedName

/**
 * @autor d.snytko
 */
class GifResponse(

    @SerializedName("result") val result: List<GifItem>
)
