package com.maas.soft.i_eye.ui.reserve_after

import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.maas.soft.i_eye.R
import com.maas.soft.i_eye.controller.SharedPreferenceController
import kotlinx.android.synthetic.main.activity_check_to_get_off.*

class CheckToGetOffActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_to_get_off)

        tv_des_check_to_get_off.text = SharedPreferenceController.getDestination(this)

        btn_get_off_check_to_get_off.setOnClickListener {
            SharedPreferenceController.setStatus(this, 4)
            val intent = Intent(this, DirectionsActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
