package com.maas.soft.i_eye.ui.favorite

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maas.soft.i_eye.R

class PlaceDeleteAdapter(var placeItems: ArrayList<PlaceDeleteData>) : RecyclerView.Adapter<PlaceDeleteViewHolder>() {
    private lateinit var onItemClick:View.OnClickListener

    fun setOnItemClickListener(l: View.OnClickListener){
        onItemClick = l
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceDeleteViewHolder {
        val mainView : View = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_delete_list, parent, false)
        mainView.setOnClickListener(onItemClick)
        return PlaceDeleteViewHolder(mainView)
    }

    override fun getItemCount(): Int = placeItems.size

    override fun onBindViewHolder(holder: PlaceDeleteViewHolder, position: Int) {
        holder.placeWhole.contentDescription = placeItems[position].name+" 선택"
        holder.placeRadio.isChecked = placeItems[position].boolean
        holder.placeName.text = placeItems[position].name
    }

}