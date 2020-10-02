package com.example.booker.listBooks

import com.example.booker.data.APIClient
import com.example.booker.data.APIInterface
import com.example.booker.data.Books
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListBooksModelImpl : ListBooksContract.ListBooksModel {

    override fun getAllBooks(callback: ListBooksContract.ListBooksModel.Callback) {
        val apiInterface = APIClient.getClient().create(APIInterface::class.java)

        val call = apiInterface.allBooks

        var listBooks : java.util.ArrayList<Books> = java.util.ArrayList<Books>()

        call.enqueue(object: Callback<java.util.ArrayList<Books>> {

            override fun onResponse(call: Call<java.util.ArrayList<Books>>, response: Response<java.util.ArrayList<Books>>) {
                if (response.body()!!.isEmpty()){
                    callback.onLoadDataError(ListBooksError.LOAD_FAILED)
                    call.cancel()
                    return
                }
                listBooks = response.body()!!
                callback.onLoadDataSuccess(listBooks)
            }

            override fun onFailure(call: Call<java.util.ArrayList<Books>>, t: Throwable) {
                callback.onLoadDataError(ListBooksError.BOOKS_EMPTY)
                call.cancel()
            }

        })

    }
}