package com.maas.soft.i_eye.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.maas.soft.i_eye.R
import com.maas.soft.i_eye.controller.SharedPreferenceController
import com.maas.soft.i_eye.ui.reserve_before.NoReservedMainActivity
import kotlinx.android.synthetic.main.activity_rating.*

class RatingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rating)
        setClickListener()
    }

    private fun setClickListener() {
        btn_rating.setOnClickListener {
            SharedPreferenceController.setStatus(this, 0)

            val intent = Intent(applicationContext, NoReservedMainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
