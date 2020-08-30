package com.application.developerslife.model.network

import com.google.gson.annotations.SerializedName

/**
 * @autor d.snytko
 */
class GifItem(

    @SerializedName("id") val id: Int,
    @SerializedName("description") val description: String,
    @SerializedName("gifURL") val gifURL: String,
    var isLoaded: Boolean = false
)
