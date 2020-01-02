package com.maas.soft.i_eye.ui.reserve_before

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.util.Log
import com.bumptech.glide.Glide
import com.maas.soft.i_eye.R
import kotlinx.android.synthetic.main.activity_searching_for_location.*
import java.io.IOException
import java.util.*

class SearchingForLocationActivity : AppCompatActivity() {

    var locationManager : LocationManager? = null
    private val REQUEST_CODE_LOCATION : Int = 2
    var currentLocation : String = ""
    var latitude : Double? = null
    var longitude : Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searching_for_location)

        Glide.with(this).asGif().load(R.raw.loading).into(iv_loading_searching)

        getCurrentLoc()
        moveToNextActivity()
    }

    private fun getCurrentLoc() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        var userLocation: Location? = getLatLng()
        userLocation?.let {
            latitude = userLocation.latitude
            longitude = userLocation.longitude
            Log.d("CheckCurrentLocation", "현재 내 위치 값: $latitude, $longitude")

            var mGeocoder = Geocoder(applicationContext, Locale.KOREAN)
            var mResultList: List<Address>? = null
            try {
                mResultList = mGeocoder.getFromLocation(
                        latitude!!, longitude!!, 1
                )
            } catch (e: IOException) {
                e.printStackTrace()
            }
            mResultList?.let {
                Log.d("CheckCurrentLocation", mResultList[0].getAddressLine(0))
                currentLocation = mResultList[0].getAddressLine(0)
                currentLocation = currentLocation.substring(11)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLatLng() : Location? {
        var currentLatLng: Location?
        val locationProvider = LocationManager.GPS_PROVIDER
        currentLatLng = locationManager?.getLastKnownLocation(locationProvider)

        return currentLatLng
    }

    private fun moveToNextActivity() {
        val intent = Intent(applicationContext, CheckCurrentLocationActivity::class.java)
        intent.putExtra("currentLocation", currentLocation)
        intent.putExtra("latitude", latitude)
        intent.putExtra("longitude", longitude)
        startActivity(intent)
        finish()
    }
}