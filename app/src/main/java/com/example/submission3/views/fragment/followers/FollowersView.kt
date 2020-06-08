package com.example.submission3.views.fragment.followers

import com.example.submission3.model.User
import com.example.submission3.views.base.View

interface FollowersView : View {
    fun onShowLoading()
    fun onHideLoading()
    fun onSuccess(result: List<User>)
    fun onError(error: String)
}