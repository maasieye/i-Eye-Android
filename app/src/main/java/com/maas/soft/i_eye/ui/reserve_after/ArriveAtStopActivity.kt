package com.maas.soft.i_eye.ui.reserve_after

import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.maas.soft.i_eye.R
import com.maas.soft.i_eye.controller.SharedPreferenceController
import com.maas.soft.i_eye.model.BusResDto
import com.maas.soft.i_eye.network.ApplicationController
import com.maas.soft.i_eye.network.NetworkService
import kotlinx.android.synthetic.main.activity_arrive_at_stop.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArriveAtStopActivity : AppCompatActivity() {

    private var networkService: NetworkService = ApplicationController.instance.networkService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arrive_at_stop)
        changeStatusBarColor()
        setListener()

        getBusResponse()
//        while(true) {
//            Handler().postDelayed({
//                getBusResponse()
//            }, 5000)
//        }

        //TODO 지금이 되면 tv_guide_arrive_at_stop 내용 바껴야함
    }

    private fun setListener() {
        btn_help_arrive_at_stop.setOnClickListener {
            startActivity(Intent(this, RequestToHelpActivity::class.java))
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
                            if(remainTime > 0) {
                                tv_minute_arrive_at_stop.text = remainTime.toString()+"분 후"
                                tv_guide_arrive_at_stop.text = "도착 후, 버스 앞 문에서\n안내방송이 나옵니다."
                            }
                            else {
                                tv_minute_arrive_at_stop.text = "지금"
                                tv_guide_arrive_at_stop.text = "안내방송이 나오는\n버스에 탑승해주세요."
                            }
                            tv_bus_num_arrive_at_stop.text = SharedPreferenceController.getBusNumber(applicationContext)+"번"
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
