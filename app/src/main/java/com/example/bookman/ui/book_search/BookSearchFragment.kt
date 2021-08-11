package com.example.bookman.ui.book_search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.bookman.R
import com.example.bookman.data.SearchCriteria
import com.example.bookman.sources.NetworkState
import com.example.bookman.ui.MainActivityDelegate
import com.example.bookman.ui.work_details.WorkDetailsViewModel
import kotlinx.android.synthetic.main.fragment_book_search.*

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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_book_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(BookSearchViewModel::class.java)

        viewModel.updateSearchCriteria(SearchCriteria.ALL)

        //initToolbar(toolbar, R.string.book_search, false)
        //mainActivityDelegate.setupNavDrawer(toolbar)
        mainActivityDelegate.enableNavDrawer(true)
        onClicks()
        initCriteriaSpinner()
        initAdapter()

        etSearch.setOnEditorActionListener { textView, actionId, _ ->

            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    hideKeyboard(textView)
                    println("I am pressed")
                    viewModel.updateSearchTerm(textView.text.toString())

                    true
                }
                EditorInfo.IME_ACTION_DONE -> {
                    viewModel.updateSearchTerm(textView.text.toString())
                    true
                }
                else -> false
            }
        }
    }

    private fun onClicks() {
        tvSpinnerFilter.setOnClickListener {
            spnCriteria.performClick()
        }

    }

    private fun hideKeyboard(view: View) {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun initCriteriaSpinner() {
        val adapter = ArrayAdapter<String>(
            requireContext(),
            R.layout.item_search_criteria,
            SearchCriteria.values().map {
                it.name
            }
        )
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1)

        spnCriteria.adapter = adapter

        spnCriteria.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                viewModel.updateSearchCriteria(SearchCriteria.valueOf(spnCriteria.adapter.getItem(p2) as String))
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun initAdapter() {
        val adapter = WorksAdapter(Glide.with(this))

        rvBooks.adapter = adapter
        adapter.itemClickListener = {
            //TODO Setup Nav Graph

            findNavController().navigate(
                R.id.actionBookDetails,
                WorkDetailsViewModel.createArguments(it)
            )
        }

        viewModel.data.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        viewModel.networkState.observe(viewLifecycleOwner, Observer {
            progressBar.visibility = when (it) {
                NetworkState.LOADING -> View.VISIBLE
                else -> View.GONE
            }
        })
    }
}