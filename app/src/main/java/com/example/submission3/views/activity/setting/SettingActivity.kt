package com.example.submission3.views.activity.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.submission3.R
import com.example.submission3.views.fragment.setting.SettingFragment
import kotlinx.android.synthetic.main.activity_detail.*

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        supportFragmentManager.beginTransaction().add(R.id.setting_holder, SettingFragment()).commit()
        toolbar()
    }

    private fun toolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(R.string.setting)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
