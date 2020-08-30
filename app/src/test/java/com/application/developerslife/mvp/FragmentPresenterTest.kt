package com.application.developerslife.mvp

import com.application.developerslife.model.network.DataService
import com.application.developerslife.model.network.GifItem
import com.application.developerslife.model.network.GifResponse
import com.application.developerslife.model.network.NetworkState
import com.application.developerslife.ui.CategoryPagerAdapter
import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Call
import retrofit2.Response

/**
 * @autor d.snytko
 */
class FragmentPresenterTest {

    private val service: DataService = mock()
    private val view: GifFragmentContract.View = mock()
    private val presenter: FragmentPresenter = FragmentPresenter(service)

    @Before
    fun setUp() {
        presenter.attach(view)
    }

    @Test
    fun loadData_WhenSuccess_with_result() {
        val mockGifItem: GifItem = mock {
            on { id } doReturn 1
            on { description } doReturn "some text"
        }
        val response = GifResponse(listOf(mockGifItem))
        val call: Call<GifResponse> = mock {
            on { execute() } doReturn Response.success(response)
        }
        whenever(service.getImages(any(), any())).thenReturn(call)

        presenter.loadData(CategoryPagerAdapter.GifCategory.HOT)

        Mockito.verify(view).onStateReceive(NetworkState.Loading)
        Mockito.verifyNoMoreInteractions(view)
    }

    @Test
    fun loadData_WhenSuccess_without_result() {

        val response = GifResponse(emptyList())
        val call: Call<GifResponse> = mock {
            on { execute() } doReturn Response.success(response)
        }
        whenever(service.getImages(any(), any())).thenReturn(call)

        presenter.loadData(CategoryPagerAdapter.GifCategory.HOT)

        Mockito.verify(view).onStateReceive(NetworkState.Loading)
    }

    @Test
    fun getNext_with_list_more_than_zero() {
        val list: MutableList<GifItem> = getFakeList()
        presenter.list = list
        val position = presenter.position

        presenter.getNext()

        assert(presenter.position == position + 1)
        verify(view).updateUi(list[presenter.position].gifURL, list[presenter.position].description)
    }

    @Test
    fun getNext_with_list_equals_zero() {
        val position = presenter.position
        presenter.getNext()

        assert(presenter.position == position + 1)
    }

    @Test
    fun getPrevious_with_list_more_than_zero() {
        val list: MutableList<GifItem> = getFakeList()
        presenter.list = list
        presenter.position = 1
        val position: Int = presenter.position

        presenter.getPrevious()

        assert(presenter.position == position - 1)
        verify(view).updateUi(list[presenter.position].gifURL, list[presenter.position].description)
    }

    @Test
    fun getPrevious_with_list_equals_zero() {
        val position = presenter.position

        presenter.getPrevious()

        assert(presenter.position == position - 1)
    }

    private fun getFakeList(): MutableList<GifItem> {
        val list: MutableList<GifItem> = mutableListOf()
        list.add(GifItem(1, "sometext", "someUrl"))
        list.add(GifItem(2, "sometext", "someUrl"))
        return list
    }
}
