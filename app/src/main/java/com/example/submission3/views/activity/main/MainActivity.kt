package com.example.submission3.views.activity.main

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.submission3.R
import com.example.submission3.adapter.UserAdapter
import com.example.submission3.model.User
import com.example.submission3.receivers.NotificationReceiver
import com.example.submission3.views.activity.detail.DetailActivity
import com.example.submission3.views.activity.setting.SettingActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity(), MainView {

    private lateinit var adapter: UserAdapter
    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter()

        rvUser.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        onAttachView()
        setSupportActionBar(toolbar)

        showNotification()
    }

    private fun showNotification(){
        val myIntent = Intent(applicationContext, NotificationReceiver::class.java)
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = PendingIntent.getService(this, 0, myIntent, 0)

        val calendar = Calendar.getInstance()
        val now = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 9)
        calendar.set(Calendar.MINUTE, 52)
        calendar.set(Calendar.SECOND, 0)
        if (now.after(calendar)) {
            calendar.add(Calendar.DATE, 1)
        }

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            1000 * 60 * 60 * 24.toLong(),
            pendingIntent
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        val searchEditText = searchView.findViewById<EditText>(R.id.search_src_text)
        searchEditText.setTextColor(Color.WHITE)
        searchEditText.setHintTextColor(Color.WHITE)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(nextText: String): Boolean {
                if(nextText != ""){
                    presenter.searchUser(nextText)
                } else {
                    presenter.getData()
                }

                return true
            }
        })


        searchView.queryHint = resources.getString(R.string.search_user)


        MenuItemCompat.setOnActionExpandListener(
            searchItem,
            object : MenuItemCompat.OnActionExpandListener {
                override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                    return true
                }

                override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                    presenter.getData()
                    return true
                }
            })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_settings) {

            val mIntent = Intent(this, SettingActivity::class.java)
            startActivity(mIntent)

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSuccess(result: List<User>) {
        adapter = UserAdapter(result)
        rvUser.adapter = adapter

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                presenter.getDetailUser(data.username)
            }

        })
    }

    override fun onError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        Log.e(MainActivity::class.simpleName, error)
    }

    override fun onDetailSuccess(user: User) {
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_USER, user)
        startActivity(intent)
    }

    override fun onShowLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun onHideLoading() {
        progressBar.visibility = View.GONE
    }

    override fun onAttachView() {
        presenter.onAttach(this)
        presenter.getData()
    }
    
    override fun onDetachView() {
        presenter.onDetach()
        presenter.getData()
    }

    override fun onDestroy() {
        onDetachView()
        super.onDestroy()
    }


}
