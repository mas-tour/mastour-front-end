package com.mastour.mastour.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mastour.mastour.data.remote.HistoryData
import com.mastour.mastour.data.remote.MasTourApiService

class HistoryPagingSource(
    private val masTourApiService: MasTourApiService,
    private val bearer: String
) : PagingSource<Int, HistoryData>() {
    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, HistoryData>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HistoryData> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData =
                masTourApiService.getHistory(bearer, page = position, size = params.loadSize)

            LoadResult.Page(
                data = responseData.data,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.data.isEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}