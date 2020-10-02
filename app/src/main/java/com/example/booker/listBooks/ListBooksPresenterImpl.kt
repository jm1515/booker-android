package com.example.booker.listBooks

import com.example.booker.data.Books

class ListBooksPresenterImpl (model: ListBooksContract.ListBooksModel, view: ListBooksContract.ListBooksView)
    : ListBooksContract.ListBooksPresenter, ListBooksContract.ListBooksModel.Callback {

    private var view: ListBooksContract.ListBooksView? = view;
    private val listBooksModel: ListBooksContract.ListBooksModel = model;

    override fun loadData() {
        listBooksModel.getAllBooks(this)
    }

    override fun onViewAttach(v: ListBooksContract.ListBooksView?) {
        this.view = v;
    }

    override fun onViewDetach() {
        this.view = null;
    }

    override fun onDestroy() {
        onViewDetach();
    }

    override fun onLoadDataError(error: ListBooksError) {
        this.view?.showViewError(error)
    }

    override fun onLoadDataSuccess(listBooks: ArrayList<Books>) {
        this.view?.showViewSuccess(listBooks)
    }


}