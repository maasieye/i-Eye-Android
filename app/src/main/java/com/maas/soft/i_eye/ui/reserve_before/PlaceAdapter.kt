package com.maas.soft.i_eye.ui.reserve_before

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.*
import android.view.accessibility.AccessibilityEvent
import com.maas.soft.i_eye.R
import kotlinx.android.synthetic.main.item_list.view.*
import java.lang.reflect.AccessibleObject


class PlaceAdapter(var placeItems: ArrayList<String>) : RecyclerView.Adapter<PlaceViewHolder>() {
    private lateinit var onItemClick:View.OnClickListener

    fun setOnItemClickListener(l: View.OnClickListener){
        onItemClick = l
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val mainView : View = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list, parent, false)
        mainView.setOnClickListener(onItemClick)


        mainView.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                view.setBackgroundColor(Color.parseColor("#D6D7D7"))
                mainView.tv_name_place.setTextColor(Color.parseColor("#242424"))
            } else if (motionEvent.action == MotionEvent.ACTION_CANCEL || motionEvent.action == MotionEvent.ACTION_UP) {
                view.setBackgroundColor(Color.parseColor("#171717"))
                mainView.tv_name_place.setTextColor(Color.parseColor("#FFFFFF"))
            }

            return@setOnTouchListener false
        }

        return PlaceViewHolder(mainView)
    }

    override fun getItemCount(): Int = placeItems.size

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        if (placeItems[position].length > 10)
            holder.placeName.text = placeItems[position].substring(0,8) + "..."
        else
            holder.placeName.text = placeItems[position]
        holder.placeName.contentDescription = placeItems[position]
    }

}