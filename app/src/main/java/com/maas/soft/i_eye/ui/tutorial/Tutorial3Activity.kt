package com.maas.soft.i_eye.ui.tutorial

import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.maas.soft.i_eye.R
import com.maas.soft.i_eye.ui.reserve_before.NoReservedMainActivity
import kotlinx.android.synthetic.main.activity_tutorial3.*

class Tutorial3Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial3)
        changeStatusBarColor()
        setOnClickListener()
    }

    private fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = resources.getColor(R.color.black, theme)
        } else {
            window.statusBarColor = resources.getColor(R.color.black)
        }
    }

    private fun setOnClickListener() {
        btn_next_tutorial3.setOnClickListener {
            val intent = Intent(applicationContext, NoReservedMainActivity::class.java)
            startActivity(intent)
        }
    }

}
