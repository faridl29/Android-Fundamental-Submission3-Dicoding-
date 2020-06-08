package com.example.submission3.views.activity.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.submission3.adapter.SectionsPagerAdapter
import com.example.submission3.model.User
import com.example.submission3.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var sectionsPagerAdapter : SectionsPagerAdapter

    companion object{
        const val EXTRA_USER = "extra_user"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val user = intent.getParcelableExtra(EXTRA_USER) as User

        initToolbar()
        setupAdapter(user)
        addData(user)
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle("Detail User")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupAdapter(user : User) {
        sectionsPagerAdapter =
            SectionsPagerAdapter(
                this,
                supportFragmentManager,
                user.username
            )
        viewPager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(viewPager)
        supportActionBar?.elevation = 0f
    }

    private fun addData(user: User) {
        Picasso.get()
            .load(user.avatar)
            .resize(300,300)
            .into(image)

        tvRepository.text = user.repository
        tvFollowers.text = user.followers
        tvFollowing.text = user.following
        tvNama.text = user.name
        tvUsername.text = resources.getString(R.string.username, user.username)
        tvCompany.text = resources.getString(R.string.company, user.company)
        tvLocation.text = resources.getString(R.string.location, user.location)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}
