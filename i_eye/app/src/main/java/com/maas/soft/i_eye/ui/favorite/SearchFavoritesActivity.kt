package com.maas.soft.i_eye.ui.favorite

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.maas.soft.i_eye.R
import kotlinx.android.synthetic.main.activity_search_favorites.*

class SearchFavoritesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_favorites)

        setClickListener()
    }

    private fun setClickListener() {
        tv_voice_favorites_search.setOnClickListener {
            val intent = Intent(applicationContext, FavoritesVoiceInputActivity::class.java)
            startActivity(intent)
        }
    }
}
