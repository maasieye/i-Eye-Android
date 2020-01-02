package com.maas.soft.i_eye.ui.favorite

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.maas.soft.i_eye.R
import com.maas.soft.i_eye.ui.reserve_before.CheckDestinationActivity
import com.maas.soft.i_eye.ui.reserve_before.PlaceAdapter
import kotlinx.android.synthetic.main.activity_favorites.*

class FavoritesActivity : AppCompatActivity(), View.OnClickListener {
    private val placeItems = ArrayList<String>()
    private lateinit var placeAdapter: PlaceAdapter

    override fun onClick(v: View?) {
        val intent = Intent(this, CheckDestinationActivity::class.java)
        intent.putExtra("Destination", placeItems[rv_bookmarks_favorite.getChildAdapterPosition(v!!)])
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        placeItems.add("광화문역")
        placeItems.add("시각장애인센터")
        placeItems.add("장애인 복지관")
        placeAdapter = PlaceAdapter(placeItems)
        placeAdapter.setOnItemClickListener(this)

        rv_bookmarks_favorite.layoutManager = LinearLayoutManager(this)
        rv_bookmarks_favorite.adapter = placeAdapter


        btn_register_favorites.setOnClickListener {
            startActivity(Intent(this, SearchFavoritesActivity::class.java))
        }

        btn_delete_favorites.setOnClickListener {
            startActivity(Intent(this, DeleteFavoritesActivity::class.java))
        }

    }
}
