@file:Suppress("RemoveExplicitTypeArguments")

package com.application.developerslife.core.di

import com.application.developerslife.model.network.DataService
import com.application.developerslife.model.network.DataServiceImpl
import com.application.developerslife.model.network.GifRestApi
import com.application.developerslife.mvp.FragmentPresenter
import org.koin.dsl.module
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @autor d.snytko
 */
val dataModule = module {
    factory<GifRestApi> { GifRestApi(GsonConverterFactory.create()) }
    factory<DataService> { DataServiceImpl(get<GifRestApi>()) }
    factory<FragmentPresenter> { FragmentPresenter(get<DataService>()) }
}
