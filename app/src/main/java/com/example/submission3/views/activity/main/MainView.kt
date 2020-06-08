package com.example.submission3.views.activity.main

import com.example.submission3.model.User
import com.example.submission3.views.base.View

interface MainView : View {
    fun onSuccess(result: List<User>)
    fun onError(error: String)
    fun onDetailSuccess(user: User)
    fun onShowLoading()
    fun onHideLoading()
}