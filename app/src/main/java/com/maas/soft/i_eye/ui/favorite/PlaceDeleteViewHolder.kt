package com.maas.soft.i_eye.ui.favorite

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import com.maas.soft.i_eye.R

class PlaceDeleteViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
    var placeWhole : ConstraintLayout = itemView!!.findViewById(R.id.const_whole_delete_place) as ConstraintLayout
    var placeRadio : RadioButton = itemView!!.findViewById(R.id.radio) as RadioButton
    var placeName : TextView = itemView!!.findViewById(R.id.tv_name_delete_place) as TextView
}