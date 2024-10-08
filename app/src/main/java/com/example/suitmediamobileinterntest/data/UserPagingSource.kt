package com.example.suitmediamobileinterntest.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.suitmediamobileinterntest.data.api.ApiService
import com.example.suitmediamobileinterntest.retrofit.response.DataItem

class UserPagingSource (
    private val apiService: ApiService) : PagingSource<Int, DataItem>() {


    override fun getRefreshKey(state: PagingState<Int, DataItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getUsers(position, params.loadSize)

            LoadResult.Page(
                data = responseData.data,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (position >= responseData.totalPages) null else position + 1
            )
        } catch (exception: Exception){
            LoadResult.Error(exception)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}