package com.application.developerslife.mvp.base

import com.application.developerslife.model.network.NetworkState

/**
 * @autor d.snytko
 */
abstract class BasePresenter<VIEW : BaseView<NetworkState>> : IPresenter<VIEW> {

    var view: VIEW? = null;

    override fun attach(view: VIEW) {
        this.view = view
    }

    override fun detach() {
        view = null
    }
}
