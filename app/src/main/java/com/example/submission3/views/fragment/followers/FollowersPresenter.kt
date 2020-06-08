package com.example.submission3.views.fragment.followers

import com.example.submission3.model.User
import com.example.submission3.rest.ApiClient
import com.example.submission3.rest.ApiInterface
import com.example.submission3.views.base.Presenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersPresenter : Presenter<FollowersView> {
    private var mView: FollowersView? = null
    override fun onAttach(view: FollowersView?) {
        mView = view
    }

    override fun onDetach() {
        mView = null
    }

    fun getFollowers(username: String) {
        val mApiInterface: ApiInterface = ApiClient.providesHttpAdapter().create(ApiInterface::class.java)

        mView?.onShowLoading()
        mApiInterface.getFollowerList(username).enqueue(object : Callback<List<User>> {
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                mView?.onHideLoading()
                mView?.onError(t.toString())
            }

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                val result = response.body() ?: emptyList()
                mView?.onHideLoading()
                mView?.onSuccess(result)
            }

        })
    }

}