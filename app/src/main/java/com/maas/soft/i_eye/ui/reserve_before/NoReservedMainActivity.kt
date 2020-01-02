package com.maas.soft.i_eye.ui.reserve_before

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.maas.soft.i_eye.R
import com.maas.soft.i_eye.ui.favorite.FavoritesActivity
import kotlinx.android.synthetic.main.activity_no_reserved_main.*

class NoReservedMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_reserved_main)
        setClickListener()
    }

    private fun setClickListener() {
        btn_reserve_no_reserved_main.setOnClickListener {
            val intent = Intent(applicationContext, SearchingForLocationActivity::class.java)
            startActivity(intent)
        }

        btn_favorites_no_reserved_main.setOnClickListener {
            val intent = Intent(applicationContext, FavoritesActivity::class.java)
            startActivity(intent)
        }
    }
}
