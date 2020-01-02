package com.maas.soft.i_eye.ui.reserve_after

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.maas.soft.i_eye.R
import com.maas.soft.i_eye.controller.SharedPreferenceController
import com.maas.soft.i_eye.model.RemainBusResDto
import com.maas.soft.i_eye.network.ApplicationController
import com.maas.soft.i_eye.network.NetworkService
import kotlinx.android.synthetic.main.activity_after_boarding.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AfterBoardingActivity : AppCompatActivity() {

    private var networkService: NetworkService = ApplicationController.instance.networkService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after_boarding)
        Log.d("@@@@@@@", "AfterBoardingActivity")
        getBusResponse()
        btn_get_off_after_boarding.setOnClickListener {
            val intent = Intent(this, RequestToGetOffActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getBusResponse() {
        var jsonObject = JSONObject()
        jsonObject.put("busId", SharedPreferenceController.getStationId(this))
        jsonObject.put("busRouteId", SharedPreferenceController.getRouteId(this))
        jsonObject.put("endStationId", SharedPreferenceController.getStationId(this))
        jsonObject.put("mobileNumber", SharedPreferenceController.getPhoneNum(this))

        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        val getRemainBus = networkService.getRemainBus(gsonObject)

        getRemainBus!!.enqueue(object : Callback<RemainBusResDto> {
            override fun onFailure(call: Call<RemainBusResDto>, t: Throwable) {
                Log.d("getRemainBus 호출: ","onFailure")
                Log.d("getRemainBus 에러: ", t.message)
            }
            override fun onResponse(call: Call<RemainBusResDto>, response: Response<RemainBusResDto>) {
                response?.let {
                    when (it.code()) {
                        200 -> {
                            Log.d("getRemainBus 상태 코드: ","200")
                            Log.d("getRemainBus 결과: ", response.body().toString())

                            tv_next_stop_after_boarding.text = response.body()!!.nextStationName
                            tv_remain_stop_after_boarding.text = response.body()!!.remainStationAmount
                            tv_this_stop_after_boarding.text = response.body()!!.nowStationName
                        }
                        403 -> {
                            Log.d("getRemainBus 상태 코드: ","403")

                        }
                        500 -> {
                            Log.d("getRemainBus 상태 코드: ","500")

                        }
                        else -> {
                            Log.d("getRemainBus 상태 코드: ", it.code().toString())
                        }
                    }
                }
            }

        })
    }
}
