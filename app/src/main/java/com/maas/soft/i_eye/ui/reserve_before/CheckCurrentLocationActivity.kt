package com.maas.soft.i_eye.ui.reserve_before

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.maas.soft.i_eye.controller.SharedPreferenceController
import kotlinx.android.synthetic.main.activity_check_current_location.*

class CheckCurrentLocationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(com.maas.soft.i_eye.R.layout.activity_check_current_location)
        setClickListener()
        setCurrentLocationText()
        tv_current_location.isClickable = false
    }

    private fun setCurrentLocationText() {
        var currentLocation : String = intent.getStringExtra("currentLocation")
        tv_current_location.text = currentLocation
    }

    private fun setClickListener() {
        btn_yes_check_current_location.setOnClickListener {
            var latitude : Double = intent.getDoubleExtra("latitude", 0.0)
            var longitude : Double = intent.getDoubleExtra("longitude", 0.0)

            SharedPreferenceController.setStartLat(this, latitude)
            SharedPreferenceController.setStartLng(this, longitude)

            val intent = Intent(applicationContext, SearchDestinationActivity::class.java)
            startActivity(intent)
            finish()
        }

        btn_no_check_current_location.setOnClickListener {
            val intent = Intent(applicationContext, SearchingForLocationActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}
