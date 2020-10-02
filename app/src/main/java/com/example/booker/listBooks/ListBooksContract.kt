package com.example.booker.listBooks

import com.example.booker.arch.BasePresenter
import com.example.booker.arch.BaseView
import com.example.booker.data.Books

interface ListBooksContract {

    interface ListBooksView : BaseView {
        fun showViewError(error: ListBooksError)
        fun showViewSuccess(listBooks: ArrayList<Books>)
    }

    interface ListBooksPresenter : BasePresenter<ListBooksView> {
        fun loadData()
    }

    interface ListBooksModel {

        fun getAllBooks(callback: Callback)

        interface Callback {
            fun onLoadDataError(error: ListBooksError)
            fun onLoadDataSuccess(listBooks: ArrayList<Books>)
        }
    }
}