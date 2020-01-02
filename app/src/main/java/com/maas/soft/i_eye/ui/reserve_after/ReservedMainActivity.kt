package com.maas.soft.i_eye.ui.reserve_after

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.maas.soft.i_eye.R
import com.maas.soft.i_eye.controller.SharedPreferenceController
import com.maas.soft.i_eye.ui.DirectionsTestActivity
import com.maas.soft.i_eye.ui.reserve_before.NoReservedMainActivity
import kotlinx.android.synthetic.main.activity_reserved_main.*

class ReservedMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserved_main)
        setClickListener()
        tv_bus_number_reserved_main.text = SharedPreferenceController.getBusNumber(this)+"번 버스"
    }

    private fun setClickListener() {
        btn_cancel_reserved_main.setOnClickListener {
            SharedPreferenceController.setStatus(this, 0)
            val intent = Intent(applicationContext, NoReservedMainActivity::class.java)
            startActivity(intent)
            finish()
        }
        btn_directions_reserved_main.setOnClickListener {
            Log.d("@@@@@@", "길찾기 버튼 클릭")
            val intent = Intent(applicationContext, DirectionsActivity::class.java)
            startActivity(intent)
        }
//        relative_reserved_main.setOnClickListener {
//            startActivity(Intent(this, DirectionsTestActivity::class.java))
//        }
    }
}
