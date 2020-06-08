package com.example.submission3.views.fragment.following

import com.example.submission3.model.User
import com.example.submission3.views.base.View

interface FollowingView : View {
    fun onShowLoading()
    fun onHideLoading()
    fun onSuccess(result: List<User>)
    fun onError(error: String)
}