package com.example.bookman.ui.book_details

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bookman.data.Book

class BookDetailsViewModel : ViewModel() {

    companion object {
        private const val BOOK_ARGUMENT = "book"

        fun createArguments(book: Book): Bundle {
            val bundle = Bundle()
            bundle.putSerializable(BOOK_ARGUMENT, book)

            return bundle
        }
    }

    val book: MutableLiveData<Book> = MutableLiveData()

    fun loadArguments(arguments: Bundle?) {
        if (arguments == null) {
            return
        }

        val book: Book? = arguments.get(BOOK_ARGUMENT) as Book?
        this.book.postValue(book)
    }
}
