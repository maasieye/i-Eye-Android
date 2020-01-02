package com.maas.soft.i_eye.model

enum class Type(private val typeMessage : String, private val typeValue : Int) {
    POINT("Point", 0),
    LINE("LineString", 1),
    BUS_STOP("BusStop",2);
}