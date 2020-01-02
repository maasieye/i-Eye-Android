package com.maas.soft.i_eye.ui.favorite

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.maas.soft.i_eye.R
import kotlinx.android.synthetic.main.activity_delete_favorites.*

class DeleteFavoritesActivity : AppCompatActivity(), View.OnClickListener {
    private val placeItems = ArrayList<PlaceDeleteData>()
    private lateinit var placeAdapter: PlaceDeleteAdapter

    override fun onClick(v: View?) {
        var idx = rv_bookmarks_delete_favorite.getChildAdapterPosition(v!!)
        placeItems[0].boolean = false
        placeItems[1].boolean = false
        placeItems[2].boolean = false
        placeItems[idx].boolean = true
        placeAdapter.notifyDataSetChanged()
        btn_yes_delete_favorites.contentDescription = placeItems[idx].name + "를 즐겨찾기에서 삭제하시겠습니까?"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_favorites)
        setClickListenrs()

        placeItems.add(PlaceDeleteData(false, "광화문역"))
        placeItems.add(PlaceDeleteData(false, "시각장애인센터"))
        placeItems.add(PlaceDeleteData(false, "장애인 복지관"))
        placeAdapter = PlaceDeleteAdapter(placeItems)
        placeAdapter.setOnItemClickListener(this)

        rv_bookmarks_delete_favorite.layoutManager = LinearLayoutManager(this)
        rv_bookmarks_delete_favorite.adapter = placeAdapter
    }

    private fun setClickListenrs(){
        btn_yes_delete_favorites.setOnClickListener {
            startActivity(Intent(this, FavoritesActivity::class.java))
            finish()
        }
        btn_no_delete_favorites.setOnClickListener {
            startActivity(Intent(this, FavoritesActivity::class.java))
            finish()
        }
    }
}
