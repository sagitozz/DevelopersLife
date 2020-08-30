package com.application.developerslife.mvp.base

import com.application.developerslife.model.network.NetworkState

/**
 * @autor d.snytko
 */
interface IPresenter<VIEW : BaseView<NetworkState>> {

    fun attach(view: VIEW)

    fun detach()
}
