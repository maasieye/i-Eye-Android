package com.maas.soft.i_eye.ui

import android.Manifest
import android.content.Context
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.maas.soft.i_eye.R
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import com.maas.soft.i_eye.controller.SharedPreferenceController
import com.maas.soft.i_eye.ui.tutorial.Tutorial1Activity
import android.telephony.TelephonyManager
import android.util.Log
import com.maas.soft.i_eye.ui.reserve_after.AfterBoardingActivity
import com.maas.soft.i_eye.ui.reserve_after.ArriveAtStopActivity
import com.maas.soft.i_eye.ui.reserve_after.DirectionsActivity
import com.maas.soft.i_eye.ui.reserve_after.ReservedMainActivity
import com.maas.soft.i_eye.ui.reserve_before.NoReservedMainActivity


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

//        SharedPreferenceController.clearAll(this)
        checkFirstRun()
        changeStatusBarColor()
    }

    private fun checkFirstRun() {
        var firstRun = SharedPreferenceController.getFirstRun(this)
        lateinit var intent : Intent
        if(firstRun) { //첫 실행
            while (ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION), 100)
            }
            var phoneNum: String
            val telManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            phoneNum = telManager.line1Number
            if (phoneNum.startsWith("+82")) {
                phoneNum = phoneNum.replace("+82", "0")
            }
            Log.d("SplashActivity", "폰 번호: $phoneNum")
            SharedPreferenceController.setPhoneNum(this, phoneNum)

            //TODO 서버로 휴대폰 번호 전송
            SharedPreferenceController.setFirstRun(this, false)

            intent = Intent(applicationContext, Tutorial1Activity::class.java)
        }
        else {
            Log.d("@@@@@@", "현재 status ${SharedPreferenceController.getStatus(this)}")
            intent = when (SharedPreferenceController.getStatus(this)) {
                // 예약 X
                0 -> Intent(applicationContext, NoReservedMainActivity::class.java)
                // 예약 O
                1 -> Intent(applicationContext, ReservedMainActivity::class.java)
                // 정류장 도착
                2 -> Intent(applicationContext, ArriveAtStopActivity::class.java)
                // 탑승
                3 -> Intent(applicationContext, AfterBoardingActivity::class.java)
                // 하차
                else -> Intent(applicationContext, DirectionsActivity::class.java)
            }
        }
        moveToNextActivity(intent)
    }

    private fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = resources.getColor(R.color.black, theme)
        } else {
            window.statusBarColor = resources.getColor(R.color.black)
        }
    }

    private fun moveToNextActivity(intent : Intent) {
        val handler = Handler()
        handler.postDelayed({
            startActivity(intent)
            finish()
        }, 1500)
    }
}