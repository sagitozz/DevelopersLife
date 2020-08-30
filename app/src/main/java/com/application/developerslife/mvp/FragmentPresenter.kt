package com.application.developerslife.mvp

import com.application.developerslife.model.network.DataService
import com.application.developerslife.model.network.GifItem
import com.application.developerslife.model.network.GifResponse
import com.application.developerslife.model.network.NetworkState
import com.application.developerslife.mvp.base.BasePresenter
import com.application.developerslife.ui.CategoryPagerAdapter.GifCategory
import com.application.developerslife.ui.CategoryPagerAdapter.GifCategory.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 * @autor d.snytko
 */
class FragmentPresenter(private val dataService: DataService) :
    BasePresenter<GifFragmentContract.View>(),
    GifFragmentContract.Presenter {

    private var requestPageNumber: Int = 0

    var list: MutableList<GifItem> = mutableListOf()
    var prefetchList: MutableList<GifItem> = mutableListOf()
    var position: Int = 0

    override fun loadData(category: GifCategory) {
        view?.onStateReceive(NetworkState.Loading)
        val gifResponse = getImagesByCategory(category)

        gifResponse.enqueue(object : Callback<GifResponse?> {
            override fun onResponse(
                call: Call<GifResponse?>,
                response: Response<GifResponse?>
            ) {
                val responseBody = response.body()!!

                if (responseBody.result.isEmpty()) {
                    view?.showEmpty()
                } else {
                    onDataReceive(responseBody.result)
                }
            }

            override fun onFailure(call: Call<GifResponse?>, t: Throwable) {
                view?.onStateReceive(NetworkState.Error(Exception(t)))
            }
        })
    }

    override fun preFetchData(category: GifCategory) {
        view?.onStateReceive(NetworkState.Loading)
        requestPageNumber += 1
        val gifResponse = getImagesByCategory(category)
        gifResponse.enqueue(object : Callback<GifResponse?> {
            override fun onResponse(
                call: Call<GifResponse?>,
                response: Response<GifResponse?>
            ) {
                val responseBody = response.body()!!

                if (responseBody.result.isEmpty()) {
                    return
                } else {
                    prefetchList.addAll(responseBody.result)
                    list.addAll(prefetchList)
                    prefetchList.clear()
                    view?.showIdle(false)
                    view?.nextButtonSetClickable(true)
                }
            }

            override fun onFailure(call: Call<GifResponse?>, t: Throwable) {
                view?.onStateReceive(NetworkState.Error(Exception(t)))
            }
        })
    }

    override fun getNext() {
        position += 1
        if (list.size > 0) {
            view?.updateUi(
                list[position].gifURL,
                list[position].description
            )
        }
    }

    override fun getPrevious() {
        position--
        if (list.size > 0) {
            view?.updateUi(
                list[position].gifURL,
                list[position].description
            )
        }
    }

    private fun getImagesByCategory(category: GifCategory): Call<GifResponse> {
        return when (category) {
            TOP -> dataService.getImages(
                requestPageNumber,
                category.name.toLowerCase(Locale.getDefault())
            )
            HOT -> dataService.getImages(
                requestPageNumber,
                category.name.toLowerCase(Locale.getDefault())
            )
            LATEST -> dataService.getImages(
                requestPageNumber,
                category.name.toLowerCase(Locale.getDefault())
            )
        }
    }

    private fun onDataReceive(gifItem: List<GifItem>) {
        list.addAll(gifItem)
        view?.onStateReceive(NetworkState.ImageLoaded(list[position]))
    }
}
