package com.maas.soft.i_eye.ui.reserve_after

import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.maas.soft.i_eye.R
import com.maas.soft.i_eye.controller.SharedPreferenceController
import com.maas.soft.i_eye.model.BusResDto
import com.maas.soft.i_eye.network.ApplicationController
import com.maas.soft.i_eye.network.NetworkService
import kotlinx.android.synthetic.main.activity_arrive_at_stop.*
import kotlinx.android.synthetic.main.activity_request_to_help.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class RequestToHelpActivity : AppCompatActivity() {

    private var networkService: NetworkService = ApplicationController.instance.networkService


    private lateinit var tts : TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_to_help)
        changeStatusBarColor()
        getBusResponse()
        setClickListener()

        tts = TextToSpeech(applicationContext, TextToSpeech.OnInitListener {
            tts.language = Locale.KOREAN
        })
        tts.speak("도움 요청을 완료했습니다.", TextToSpeech.QUEUE_FLUSH, null, this.hashCode().toString())

        tv_request_to_help.setOnClickListener { SharedPreferenceController.setStatus(this, 0) }
    }

    private fun setClickListener() {
        rl_request_to_help.setOnClickListener {
            SharedPreferenceController.setStatus(this, 3)
            val intent = Intent(this, AfterBoardingActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = resources.getColor(R.color.black, theme)
        } else {
            window.statusBarColor = resources.getColor(R.color.black)
        }
    }

    private fun getBusResponse() {
        var jsonObject = JSONObject()
        jsonObject.put("busRouteId", SharedPreferenceController.getRouteId(this))
        jsonObject.put("stationId", SharedPreferenceController.getStationId(this))

        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        val getBusResponse = networkService.getArriveBusTime(gsonObject)

        getBusResponse!!.enqueue(object : Callback<BusResDto> {
            override fun onFailure(call: Call<BusResDto>, t: Throwable) {
                Log.d("busResponse 호출: ","onFailure")
                Log.d("busResponse 에러: ", t.message)
            }
            override fun onResponse(call: Call<BusResDto>, response: Response<BusResDto>) {
                response?.let {
                    when (it.code()) {
                        200 -> {
                            Log.d("busResponse 상태 코드: ","200")
                            Log.d("busResponse 결과: ", response.body().toString())
                            var remainTime : Int = (response.body()!!.firstTime.toInt())/60
                            Log.d("@@@@@@@@@@", "remainTime : $remainTime")

                            tv_driver_help_request_to_help.text = "${SharedPreferenceController.getBusNumber(applicationContext)}번 버스 기사님이\n탑승을 도와드립니다."
                            tv_driver_help_request_to_help.contentDescription = "${SharedPreferenceController.getBusNumber(applicationContext)}번 버스 기사님이 탑승을 도와드립니다."
                            if(remainTime > 0) {
                                tv_remain_time_request_to_help.text = "도착 ${remainTime}분 전"
                            }
                            else {
                                tv_remain_time_request_to_help.text = "1분 이내 도착"
                            }
                        }
                        403 -> {
                            Log.d("busResponse 상태 코드: ","403")

                        }
                        500 -> {
                            Log.d("busResponse 상태 코드: ","500")

                        }
                        else -> {
                            Log.d("busResponse 상태 코드: ", it.code().toString())
                        }
                    }
                }
            }

        })
    }
}
