package com.example.bookman.ui.book_search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.bookman.R
import com.example.bookman.data.SearchCriteria
import com.example.bookman.ui.MainActivityDelegate

class BookSearchFragment : Fragment() {
    private lateinit var viewModel: BookSearchViewModel

    private lateinit var mainActivityDelegate: MainActivityDelegate

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mainActivityDelegate = context as MainActivityDelegate
        } catch (e: ClassCastException) {
            throw ClassCastException()
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        println("I am in book search fragment")
        return inflater.inflate(R.layout.fragment_book_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(BookSearchViewModel::class.java)

        viewModel.updateSearchCriteria(SearchCriteria.ALL)

       /* initToolbar(toolbar, R.string.book_search, false)
        mainActivityDelegate.setupNavDrawer(toolbar)*/
        mainActivityDelegate.enableNavDrawer(true)

        initCriteriaSpinner()
        initAdapter()

        /*tvSearch.setOnEditorActionListener { textView, actionId, _ ->

            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    hideKeyboard(textView)
                    viewModel.updateSearchTerm(textView.text.toString())

                    true
                }
                EditorInfo.IME_ACTION_DONE -> {
                    viewModel.updateSearchTerm(textView.text.toString())
                    true
                }
                else -> false
            }
        }*/

    }

    private fun hideKeyboard(textView: Any) {


    }

    private fun initAdapter() {


    }

    private fun initCriteriaSpinner() {


    }

    private fun initToolbar(toolbar: Any, bookSearch: Any, b: Boolean) {


    }

}