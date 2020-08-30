package com.application.developerslife.ui

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import com.application.developerslife.R
import com.application.developerslife.common.ImageLoaderImpl
import com.application.developerslife.model.network.NetworkState
import com.application.developerslife.model.network.NetworkState.*
import com.application.developerslife.mvp.FragmentPresenter
import com.application.developerslife.mvp.GifFragmentContract
import com.application.developerslife.mvp.base.BaseFragment
import com.application.developerslife.ui.CategoryPagerAdapter.GifCategory
import com.application.developerslife.ui.CategoryPagerAdapter.GifCategory.*
import kotlinx.android.synthetic.main.empty_category.*
import kotlinx.android.synthetic.main.error_layout.*
import kotlinx.android.synthetic.main.fragment_gif_page.*
import kotlinx.android.synthetic.main.loading_layout.*
import org.koin.android.ext.android.inject

/**
 * @autor d.snytko
 */
class FragmentGifPage : BaseFragment(R.layout.fragment_gif_page), GifFragmentContract.View {

    private val presenter: FragmentPresenter by inject()
    private val imageLoader: ImageLoaderImpl by inject()

    private val category by lazy { (requireArguments().getSerializable(ARG_ARTICLE_CATEGORY) as GifCategory) }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.attach(this)
        checkIfCurrentImageIsFirst()
        nextButton.setOnClickListener {
            previousButton.isEnabled = true
            previousButton.backgroundTintMode = PorterDuff.Mode.SRC_IN
            if (somethingWentWrong()) return@setOnClickListener
            if (isOnline(requireContext())) {
                if (presenter.position == presenter.list.size - 1) {
                    nextButtonSetClickable(false)
                } else presenter.getNext()
                if (presenter.position == presenter.list.size - PREFETCH_POINT) {
                    presenter.preFetchData(category)
                }
            } else {
                if (!(presenter.list[presenter.position + 1].isLoaded)) {
                    presenter.position = presenter.position + 1
                    showConnectionProblem(true)
                } else {
                    presenter.getNext()
                }
            }
        }
        previousButton.setOnClickListener {
            error_layout.isVisible = false
            presenter.getPrevious()
            checkIfCurrentImageIsFirst()
            nextButtonSetClickable(true)
        }

        retryButton.setOnClickListener {
            if (isOnline(requireContext())) {
                presenter.loadData(category)
            } else {
                Toast.makeText(requireContext(), "CHECK INTERNET CONNECTION", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        when (category.name) {
            TOP.name -> presenter.loadData(TOP)
            LATEST.name -> presenter.loadData(LATEST)
            HOT.name -> presenter.loadData(HOT)
        }
    }

    override fun onStateReceive(state: NetworkState) {
        when (state) {
            is ImageLoaded -> onImageLoaded(state)
            is Loading -> showIdle(true)
            is Error -> showConnectionProblem(true)
        }
    }

    override fun updateUi(url: String, desc: String) {
        showLoader(true)
        imageLoader.loadImage(
            url,
            imageView,
            onLoad = { showLoader(false) },
            onFail = { showConnectionProblem(true) },
            isCached = { true.gifIsCached() })
        imageDescription.text = desc
    }

    override fun showEmpty() {
        showIdle(false)
        error_layout.isVisible = false
        emptyCategory.isVisible = true
        nextButton.isVisible = false
        previousButton.isVisible = false
        imageDescription.isVisible = false
    }

    override fun showIdle(show: Boolean) {
        showConnectionProblem(false)
        showLoader(show)
    }

    override fun nextButtonSetClickable(isClickable: Boolean) {
        nextButton.isClickable = isClickable
        when (isClickable) {
            true -> nextButton.backgroundTintMode = PorterDuff.Mode.SRC_IN
            false -> nextButton.backgroundTintMode = PorterDuff.Mode.OVERLAY
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }

    private fun checkIfCurrentImageIsFirst() {
        if (presenter.position == 0) {
            previousButton.isEnabled = false
            previousButton.backgroundTintMode = PorterDuff.Mode.OVERLAY
        }
    }

    private fun onImageLoaded(state: ImageLoaded) {
        showConnectionProblem(false)
        updateUi(
            state.gifResponse.gifURL,
            state.gifResponse.description
        )
    }

    private fun Boolean.gifIsCached() {
        presenter.list[presenter.position].isLoaded = this
    }

    private fun showConnectionProblem(show: Boolean) {
        error_layout.isVisible = show
        nextButtonSetClickable(!show)
        imageDescription.isVisible = !show
        showLoader(false)
    }

    private fun somethingWentWrong() =
        emptyCategory.isVisible or error_layout.isVisible

    private fun showLoader(hide: Boolean) {
        gif_loading_layout.isVisible = hide
    }

    companion object {

        private const val PREFETCH_POINT = 40
        private const val ARG_ARTICLE_CATEGORY = "article_category"

        fun newInstance(category: GifCategory) =
            FragmentGifPage().apply(fun FragmentGifPage.() {
                arguments = bundleOf(
                    ARG_ARTICLE_CATEGORY to category
                )
            })
    }
}
