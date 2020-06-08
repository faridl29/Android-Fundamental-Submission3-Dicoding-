package com.example.submission3.views.activity.main

import com.example.submission3.model.Search
import com.example.submission3.model.User
import com.example.submission3.rest.ApiClient
import com.example.submission3.rest.ApiInterface
import com.example.submission3.views.base.Presenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPresenter : Presenter<MainView> {
    private var mView: MainView? = null

    private lateinit var mApiInterface: ApiInterface
    override fun onAttach(view: MainView?) {
        mView = view
    }

    override fun onDetach() {
        mView = null
    }

    fun getData() {
        mApiInterface = ApiClient.providesHttpAdapter().create(ApiInterface::class.java)

        mView?.onShowLoading()
        mApiInterface.getUserList().enqueue(object : Callback<List<User>> {
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                mView?.onError(t.toString())
                mView?.onHideLoading()
            }

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {

                val result = response.body() ?: emptyList()
                mView?.onSuccess(result)
                mView?.onHideLoading()
            }

        })
    }


    fun getDetailUser(username : String) {
        mApiInterface.getDetailUser(username).enqueue(object : Callback<User>{
            override fun onFailure(call: Call<User>, t: Throwable) {
                mView?.onError(t.toString())
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                var result = response.body()

                val user = User (
                    result?.username ?: "",
                    result?.name ?: "",
                    result?.avatar ?: "",
                    result?.company ?: "",
                    result?.location ?: "",
                    result?.repository ?: "",
                    result?.followers ?: "",
                    result?.following ?: "",
                    result?.html_url?: ""
                )

                mView?.onDetailSuccess(user)

            }

        })
    }

    fun searchUser(user: String){
        mView?.onShowLoading()
        mApiInterface.searchUser(user).enqueue(object : Callback<Search>{
            override fun onFailure(call: Call<Search>, t: Throwable) {
                mView?.onHideLoading()
                mView?.onError(t.toString())
            }

            override fun onResponse(call: Call<Search>, response: Response<Search>) {
                val result = response.body()?.listUser ?: emptyList()
                mView?.onSuccess(result)
                mView?.onHideLoading()
            }

        })
    }

}