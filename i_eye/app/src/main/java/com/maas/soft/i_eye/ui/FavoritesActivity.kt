package com.maas.soft.i_eye.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.maas.soft.i_eye.R
import kotlinx.android.synthetic.main.activity_favorites.*

class FavoritesActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {

    }

    lateinit var placeAdapter: PlaceAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        val placeItems = ArrayList<String>()
        placeItems.add("광화문")
        placeItems.add("광화문")
        placeItems.add("광화문")
        placeAdapter = PlaceAdapter(placeItems)
        placeAdapter.setOnItemClickListener(this)

        rv_bookmarks_favorite.layoutManager = LinearLayoutManager(this)
        rv_bookmarks_favorite.adapter = placeAdapter

    }
}