package com.example.submission3.views.fragment.following

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.submission3.adapter.UserAdapter
import com.example.submission3.model.User

import com.example.submission3.R
import com.example.submission3.rest.ApiInterface
import com.example.submission3.views.fragment.followers.FollowersFragment
import kotlinx.android.synthetic.main.activity_main.rvUser
import kotlinx.android.synthetic.main.fragment_followers.*

class FollowingFragment : Fragment(), FollowingView {

    private lateinit var mApiInterface: ApiInterface
    private lateinit var adapter: UserAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var presenter: FollowingPresenter

    private var username: String? = null

    companion object {
        const val EXTRA_USERNAME = "username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        username = arguments?.getString(FollowersFragment.EXTRA_USERNAME) ?: ""
        rvUser.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        progressBar = view.findViewById(R.id.progressBar)
        presenter = FollowingPresenter()

        onAttachView()
    }

    override fun onShowLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun onHideLoading() {
        progressBar.visibility = View.GONE
    }

    override fun onSuccess(result: List<User>) {
        if(result.size == 0 ){
            tvEmptyList.visibility = View.VISIBLE
        }

        adapter = UserAdapter(result)
        rvUser.adapter = adapter
    }

    override fun onError(error: String) {
        Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
        Log.e("error : ", error)
    }

    override fun onAttachView() {
        presenter.onAttach(this)
        presenter.getFollowing(username?:"")
    }

    override fun onDetachView() {
        presenter.onDetach()
    }

    override fun onDestroy() {
        onDetachView()
        super.onDestroy()
    }

}
