package com.maas.soft.i_eye.ui.reserve_before

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.maas.soft.i_eye.R

class PlaceViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
    var placeName : TextView = itemView!!.findViewById(R.id.tv_name_place) as TextView
}