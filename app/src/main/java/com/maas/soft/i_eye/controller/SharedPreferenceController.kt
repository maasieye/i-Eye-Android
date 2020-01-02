package com.maas.soft.i_eye.controller

import android.content.Context
import android.content.SharedPreferences
import android.content.Context.MODE_PRIVATE



object SharedPreferenceController {

    private val FIRST_RUN : String = "first_run"
    private val PHONE_NUM : String = "android_id"
    private val STATUS : String = "status"
    private val START_LAT : String = "start_lat"
    private val START_LNG : String = "start_lng"
    private val DESTINATION_LAT : String = "destination_lat"
    private val DESTINATION_LNG : String = "destination_lng"
    private val BUS_NUMBER : String = "bus_number"
    private val STATION_ID : String = "station_id"
    private val ROUTE_ID : String = "route_id"
    private val END_STATION_ID : String = "end_station_id"
    private val DESTINATION : String = "destination"
    private val CLEAR_ALL : String = "clear_all"

    /**
     * FIRST_RUN
     * Boolean
     */
    fun setFirstRun(ctx : Context, input_first_run : Boolean) {
        val preferences : SharedPreferences = ctx.getSharedPreferences(FIRST_RUN, MODE_PRIVATE)
        val editor : SharedPreferences.Editor = preferences.edit()
        editor.putBoolean(FIRST_RUN, input_first_run)
        editor.apply()
    }

    fun getFirstRun(ctx : Context) : Boolean {
        val preferences : SharedPreferences = ctx.getSharedPreferences(FIRST_RUN, MODE_PRIVATE)
        return preferences.getBoolean(FIRST_RUN, true)
    }


    /**
     * PHONE_NUM
     * String
     */
    fun setPhoneNum(ctx : Context, input_phone_num : String) {
        val preferences : SharedPreferences = ctx.getSharedPreferences(PHONE_NUM, MODE_PRIVATE)
        val editor : SharedPreferences.Editor = preferences.edit()
        editor.putString(PHONE_NUM, input_phone_num)
        editor.commit()
    }

    fun getPhoneNum(ctx : Context) : String {
        val preferences : SharedPreferences = ctx.getSharedPreferences(PHONE_NUM, MODE_PRIVATE)
        return preferences.getString(PHONE_NUM, "")
    }


    /**
     * STATUS -   0: 예약X,   1: 예약O,   2: 정류장 도착,   3: 탑승,   4: 하차
     * INT
     */
    fun setStatus(ctx : Context, input_status : Int) {
        val preferences : SharedPreferences = ctx.getSharedPreferences(STATUS, MODE_PRIVATE)
        val editor : SharedPreferences.Editor = preferences.edit()
        editor.putInt(STATUS, input_status)
        editor.apply()
    }

    fun getStatus(ctx : Context) : Int {
        val preferences : SharedPreferences = ctx.getSharedPreferences(STATUS, MODE_PRIVATE)
        return preferences.getInt(STATUS, 0)
    }

    /**
     * START_LAT
     * Double
     */
    fun setStartLat(ctx : Context, input_start_lat : Double) {
        val preferences : SharedPreferences = ctx.getSharedPreferences(START_LAT, MODE_PRIVATE)
        val editor : SharedPreferences.Editor = preferences.edit()
        editor.putFloat(START_LAT, input_start_lat.toFloat())
        editor.commit()
    }

    fun getStartLat(ctx : Context) : Double {
        val preferences : SharedPreferences = ctx.getSharedPreferences(START_LAT, MODE_PRIVATE)
        return preferences.getFloat(START_LAT, 0.0F).toDouble()
    }

    /**
     * START_LNG
     * Double
     */
    fun setStartLng(ctx : Context, input_start_lng : Double) {
        val preferences : SharedPreferences = ctx.getSharedPreferences(START_LNG, MODE_PRIVATE)
        val editor : SharedPreferences.Editor = preferences.edit()
        editor.putFloat(START_LNG, input_start_lng.toFloat())
        editor.commit()
    }

    fun getStartLng(ctx : Context) : Double {
        val preferences : SharedPreferences = ctx.getSharedPreferences(START_LNG, MODE_PRIVATE)
        return preferences.getFloat(START_LNG, 0.0F).toDouble()
    }

    /**
     * DESTINATION_LAT
     * Double
     */
    fun setDestinationLat(ctx : Context, input_des_lat : Double) {
        val preferences : SharedPreferences = ctx.getSharedPreferences(DESTINATION_LAT, MODE_PRIVATE)
        val editor : SharedPreferences.Editor = preferences.edit()
        editor.putFloat(DESTINATION_LAT, input_des_lat.toFloat())
        editor.commit()
    }

    fun getDestinationLat(ctx : Context) : Double {
        val preferences : SharedPreferences = ctx.getSharedPreferences(DESTINATION_LAT, MODE_PRIVATE)
        return preferences.getFloat(DESTINATION_LAT, 0.0F).toDouble()
    }

    /**
     * DESTINATION_LNG
     * Double
     */
    fun setDestinationLng(ctx : Context, input_des_lng : Double) {
        val preferences : SharedPreferences = ctx.getSharedPreferences(DESTINATION_LNG, MODE_PRIVATE)
        val editor : SharedPreferences.Editor = preferences.edit()
        editor.putFloat(DESTINATION_LNG, input_des_lng.toFloat())
        editor.commit()
    }

    fun getDestinationLng(ctx : Context) : Double {
        val preferences : SharedPreferences = ctx.getSharedPreferences(DESTINATION_LNG, MODE_PRIVATE)
        return preferences.getFloat(DESTINATION_LNG, 0.0F).toDouble()
    }

    /**
     * BUS_NUMBER
     * String
     */
    fun setBusNumber(ctx : Context, input_bus_num : String) {
        val preferences : SharedPreferences = ctx.getSharedPreferences(BUS_NUMBER, MODE_PRIVATE)
        val editor : SharedPreferences.Editor = preferences.edit()
        editor.putString(BUS_NUMBER, input_bus_num)
        editor.commit()
    }

    fun getBusNumber(ctx : Context) : String {
        val preferences : SharedPreferences = ctx.getSharedPreferences(BUS_NUMBER, MODE_PRIVATE)
        return preferences.getString(BUS_NUMBER, "")
    }

    /**
     * ROUTE_ID
     * String
     */
    fun setRouteId(ctx : Context, input_route_id : String) {
        val preferences : SharedPreferences = ctx.getSharedPreferences(ROUTE_ID, MODE_PRIVATE)
        val editor : SharedPreferences.Editor = preferences.edit()
        editor.putString(ROUTE_ID, input_route_id)
        editor.commit()
    }

    fun getRouteId(ctx : Context) : String {
        val preferences : SharedPreferences = ctx.getSharedPreferences(ROUTE_ID, MODE_PRIVATE)
        return preferences.getString(ROUTE_ID, "")
    }


    /**
     * STATION_ID
     * String
     */
    fun setStationId(ctx : Context, input_station_id : String) {
        val preferences : SharedPreferences = ctx.getSharedPreferences(STATION_ID, MODE_PRIVATE)
        val editor : SharedPreferences.Editor = preferences.edit()
        editor.putString(STATION_ID, input_station_id)
        editor.commit()
    }

    fun getStationId(ctx : Context) : String {
        val preferences : SharedPreferences = ctx.getSharedPreferences(STATION_ID, MODE_PRIVATE)
        return preferences.getString(STATION_ID, "")
    }

    /**
     * END_STATION_ID
     * String
     */
    fun setEndStationId(ctx : Context, input_end_station_id : String) {
        val preferences : SharedPreferences = ctx.getSharedPreferences(END_STATION_ID, MODE_PRIVATE)
        val editor : SharedPreferences.Editor = preferences.edit()
        editor.putString(END_STATION_ID, input_end_station_id)
        editor.commit()
    }

    fun getEndStationId(ctx : Context) : String {
        val preferences : SharedPreferences = ctx.getSharedPreferences(END_STATION_ID, MODE_PRIVATE)
        return preferences.getString(END_STATION_ID, "")
    }

    /**
     * DESTINATION
     * String
     */
    fun setDestination(ctx : Context, input_des : String) {
        val preferences : SharedPreferences = ctx.getSharedPreferences(DESTINATION, MODE_PRIVATE)
        val editor : SharedPreferences.Editor = preferences.edit()
        editor.putString(DESTINATION, input_des)
        editor.commit()
    }

    fun getDestination(ctx : Context) : String {
        val preferences : SharedPreferences = ctx.getSharedPreferences(DESTINATION, MODE_PRIVATE)
        return preferences.getString(DESTINATION, "")
    }


    /**
     * CLEAR_ALL
     */
    fun clearAll(ctx : Context) {
        val preferences : SharedPreferences = ctx.getSharedPreferences(CLEAR_ALL, MODE_PRIVATE)
        val editor : SharedPreferences.Editor = preferences.edit()
        editor.clear()
        editor.commit()
    }

}