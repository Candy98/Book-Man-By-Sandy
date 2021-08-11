package com.example.bookman.ui.work_details

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.bookman.api.OpenLibraryApi
import com.example.bookman.data.Work
import com.example.bookman.repository.FavoritesRepository
import com.example.bookman.sources.BookDataSourceFactory
import com.example.bookman.sources.NetworkState

class WorkDetailsViewModel(app: Application) : AndroidViewModel(app) {

    companion object {
        private const val WORK_ARGUMENT = "work"

        fun createArguments(work: Work): Bundle {
            val bundle = Bundle()
            bundle.putSerializable(WORK_ARGUMENT, work)

            return bundle
        }
    }

    private val favoritesRepository = FavoritesRepository(app)

    private val bookDataSourceFactory = BookDataSourceFactory(
        OpenLibraryApi.create()
    )

    private val pagingConfig = PagedList.Config.Builder()
        .setPageSize(10)
        .setPrefetchDistance(10)
        .setEnablePlaceholders(true)
        .build()

    val data = LivePagedListBuilder(bookDataSourceFactory, pagingConfig)
        .build()

    val networkState: LiveData<NetworkState> =
        Transformations.switchMap(bookDataSourceFactory.sourceLiveData) {
            it.networkState
        }

    val work: LiveData<Work> = Transformations.map(bookDataSourceFactory.work) { it }

    var favorite: LiveData<Work> = MutableLiveData()

    fun loadArguments(arguments: Bundle?) {
        if (arguments == null) {
            return
        }

        val work: Work? = arguments.get(WORK_ARGUMENT) as Work?

        if (work != null) {
            favorite = favoritesRepository.getFavorite(work.id)

            bookDataSourceFactory.work.postValue(work)
            bookDataSourceFactory.sourceLiveData.value?.invalidate()
        }
    }

    fun addAsFavorite(): Boolean {
        val work = this.work.value

        return if (work != null) {
            favoritesRepository.addFavorite(work)
            true
        } else {
            false
        }
    }

    fun removeFromFavorites(): Boolean {
        val work = this.work.value

        return if (work != null) {
            favoritesRepository.removeFavorite(work)
            true
        } else {
            false
        }
    }
}
