package com.maas.soft.i_eye.ui.tutorial

import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.maas.soft.i_eye.R
import kotlinx.android.synthetic.main.activity_tutorial1.*

class Tutorial1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial1)
        changeStatusBarColor()
        setClickListener()
    }

    private fun setClickListener() {
        btn_next_tutorial1.setOnClickListener {
            val intent = Intent(applicationContext, Tutorial2Activity::class.java)
            startActivity(intent)
        }
    }

    private fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = resources.getColor(R.color.black, theme)
        } else {
            window.statusBarColor = resources.getColor(R.color.black)
        }
    }

}
