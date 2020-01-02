package com.maas.soft.i_eye.ui.reserve_before

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_search_destination.*
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.maas.soft.i_eye.R
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPOIItem
import com.skt.Tmap.TMapView
import android.os.Handler
import android.support.v7.widget.RecyclerView
import com.maas.soft.i_eye.controller.SharedPreferenceController


class SearchDestinationActivity : AppCompatActivity() , View.OnClickListener {
    private val placeItems = ArrayList<String>()
    private lateinit var placeAdapter: PlaceAdapter
    private val tmapdata = TMapData()
    var item : ArrayList<TMapPOIItem> = ArrayList()
    val handler = Handler()

    override fun onClick(v: View?) {
        val intent = Intent(this, CheckDestinationActivity::class.java)
        intent.putExtra("Destination", placeItems[rv_search_dest_search.getChildAdapterPosition(v!!)])

        SharedPreferenceController.setDestinationLat(this, item[rv_search_dest_search.getChildAdapterPosition(v)].poiPoint.latitude)
        SharedPreferenceController.setDestinationLng(this, item[rv_search_dest_search.getChildAdapterPosition(v)].poiPoint.longitude)

        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_destination)
        val tMapView = TMapView(this)
        tMapView.setSKTMapApiKey("767dc065-35e7-4782-a787-202f73d8d976")

        chkAgain()
        hideKeyboard()
        setClickListener()
        setRecyclerView()
    }

    private fun chkAgain() {
        if (intent.getStringExtra("VOICE_DEST")!=null){
            searchPOI(intent.getStringExtra("VOICE_DEST"))
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(et_search_dest_search.windowToken, 0);
    }

    private fun setRecyclerView() {
        placeAdapter = PlaceAdapter(placeItems)
        placeAdapter.setOnItemClickListener(this)
        rv_search_dest_search.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        rv_search_dest_search.adapter = placeAdapter
    }

    private fun setClickListener() {
        tv_voice_dest_search.setOnClickListener {
            val intent = Intent(applicationContext, DestinationVoiceInputActivity::class.java)
            startActivity(intent)
        }
        et_search_dest_search.setOnKeyListener { v, keyCode, event ->
            if ((event.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                hideKeyboard()
                searchPOI(et_search_dest_search.text.toString())
            }
            true
        }
    }

    private fun searchPOI(str: String) {
        object : Thread() {
            override fun run() {
                placeItems.clear()
                item = tmapdata.findAllPOI(str)?:ArrayList()
                for (i in 0 until item.size){
                    placeItems.add(item[i].poiName)
                }
                handler.post {
                    placeAdapter.notifyDataSetChanged()
                }
            }
        }.start()
    }
}
