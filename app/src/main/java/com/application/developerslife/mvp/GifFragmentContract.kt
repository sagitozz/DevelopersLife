package com.application.developerslife.mvp

import com.application.developerslife.model.network.NetworkState
import com.application.developerslife.mvp.base.BaseView
import com.application.developerslife.ui.CategoryPagerAdapter

/**
 * @autor d.snytko
 */
interface GifFragmentContract {

    interface Presenter {
        fun loadData(category: CategoryPagerAdapter.GifCategory)
        fun preFetchData(category: CategoryPagerAdapter.GifCategory)
        fun getNext()
        fun getPrevious()
    }

    interface View :
        BaseView<NetworkState> {
        fun updateUi(url: String, desc: String)
        fun onStateReceive(state: NetworkState)
        fun showEmpty()
        fun showIdle(show: Boolean)
        fun nextButtonSetClickable(isClickable: Boolean)
    }
}
