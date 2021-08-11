
package com.example.bookman.sources


import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.bookman.api.OpenLibraryApi
import com.example.bookman.data.Book
import com.example.bookman.data.Work
import com.example.bookman.sources.BookDataSource


class BookDataSourceFactory(
    private val openLibraryApi: OpenLibraryApi
) : DataSource.Factory<Int, Book>() {

  val work = MutableLiveData<Work>()

  val sourceLiveData = MutableLiveData<BookDataSource>()

  override fun create(): DataSource<Int, Book> {
    val source = BookDataSource(openLibraryApi, work.value)
    sourceLiveData.postValue(source)
    return source
  }
}