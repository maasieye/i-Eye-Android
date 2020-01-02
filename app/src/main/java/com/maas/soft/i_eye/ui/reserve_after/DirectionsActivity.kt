package com.maas.soft.i_eye.ui.reserve_after

import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.maas.soft.i_eye.R
import com.maas.soft.i_eye.controller.SharedPreferenceController
import com.maas.soft.i_eye.model.Point
import com.maas.soft.i_eye.network.ApplicationController
import com.maas.soft.i_eye.network.NetworkService
import com.skt.Tmap.TMapView
import kotlinx.android.synthetic.main.activity_directions.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapPolyLine
import android.location.LocationManager
import android.content.pm.PackageManager
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.support.v4.app.ActivityCompat
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationListener
import android.os.Vibrator
import android.speech.tts.TextToSpeech
import com.maas.soft.i_eye.model.PathResDto
import com.maas.soft.i_eye.model.Type
import com.maas.soft.i_eye.ui.SplashActivity
import com.skt.Tmap.TMapMarkerItem
import com.maas.soft.i_eye.ui.reserve_before.NoReservedMainActivity
import java.util.*
import kotlin.collections.ArrayList


class DirectionsActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var mSensorManager : SensorManager
    private lateinit var mAccelerometer : Sensor
    private lateinit var mMagnetometer : Sensor
    private var mLastAccelerometer : FloatArray = floatArrayOf(0f, 0f, 0f)
    private var mLastMagnetometer : FloatArray = floatArrayOf(0f, 0f, 0f)
    private var mLastAccelerometerSet : Boolean = false
    private var mLastMagnetometerSet : Boolean = false
    private var mR : FloatArray = floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)
    private var mOrientation : FloatArray = floatArrayOf(0f, 0f, 0f)
    private var mCurrentDegree  : Double = 0.0
    private lateinit var vibrator : Vibrator

    private lateinit var tts : TextToSpeech
    private lateinit var tMapView : TMapView
    private lateinit var locationManager : LocationManager
    private var status = 1

    private var latitude : Double = 0.0
    private var longitude : Double = 0.0
    private var desLatitude : Double = 0.0
    private var desLongitude : Double = 0.0

    private var busStopLat : Double = 0.0
    private var busStopLng : Double= 0.0

    private var offBusStopLat : Double = 0.0
    private var offBusStopLng : Double= 0.0

    private var finalDesLat : Double = 0.0
    private var finalDesLng : Double= 0.0

    private var paths : ArrayList<Point> = ArrayList()
    private var pathCnt = 0

    private var networkService: NetworkService = ApplicationController.instance.networkService

    var locationListener : LocationListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("@@@@@@", "길찾기 액티비티 진입")
        setContentView(R.layout.activity_directions)

        mSensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        tts = TextToSpeech(applicationContext, TextToSpeech.OnInitListener {
            tts.language = Locale.KOREAN
        })
        locationManager = this.getSystemService(LOCATION_SERVICE) as LocationManager
        status = SharedPreferenceController.getStatus(this)
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        getLatLng()
        getTMap()
        getPathResponse()
        changeStatusBarColor()
        setLocationListener()

        relative_directions.setOnClickListener {

//            tts.speak("직진하세요.", TextToSpeech.QUEUE_FLUSH, null, this.hashCode().toString())
            var status : Int = SharedPreferenceController.getStatus(this)
            if(status == 4) {
                SharedPreferenceController.setStatus(this, 0)
                val intent = Intent(this, SplashActivity::class.java)
                startActivity(intent)
            }

            else {
                SharedPreferenceController.setStatus(this, 2)
                val intent = Intent(this, ArriveAtStopActivity::class.java)
                startActivity(intent)
            }
        }

        var lm : LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (ActivityCompat.checkSelfPermission(applicationContext, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(applicationContext, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION), 1)
        }else{
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, // 등록할 위치제공자
                    1000, // 통지사이의 최소 시간간격 (miliSecond)
                    1f, // 통지사이의 최소 변경거리 (m)
                    locationListener)
        }
    }

    override fun onResume() {
        super.onResume()
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME)
        mSensorManager.registerListener(this, mMagnetometer, SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onPause() {
        super.onPause()
        mSensorManager.unregisterListener(this, mAccelerometer)
        mSensorManager.unregisterListener(this, mMagnetometer)

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        Log.d("@@@@@@", "onAccuracyChanged")
    }

    override fun onSensorChanged(event: SensorEvent?) {
        Log.d("@@@@@@", "onSensorChanged")
        if (event!!.sensor == mAccelerometer) {
            System.arraycopy(event.values, 0, mLastAccelerometer, 0, event.values.size)
            mLastAccelerometerSet = true
        } else if (event.sensor == mMagnetometer) {
            System.arraycopy(event.values, 0, mLastMagnetometer, 0, event.values.size)
            mLastMagnetometerSet = true;
        }
        if (mLastAccelerometerSet && mLastMagnetometerSet) {
            SensorManager.getRotationMatrix(mR, null, mLastAccelerometer, mLastMagnetometer)
            mCurrentDegree = (Math.toDegrees(SensorManager.getOrientation(mR, mOrientation)[0].toDouble()) + 360) % 360
            Log.d("@@@@@@@", "Heading: $mCurrentDegree degree")
        }
    }

    private fun setLocationListener() {
            locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location?) {
                if (location != null) {
                    latitude = location.latitude
                    longitude = location.longitude
                    tMapView.setLocationPoint(longitude, latitude)
                    tMapView.setCenterPoint(longitude, latitude)
                }
                Log.d("@@@@@@", "위치 변경 감지")
                Log.d("@@@@@@", "현재 좌표 $longitude, $latitude")
                Log.d("@@@@@@", "목적지 좌표 $desLongitude, $desLatitude")

                if(desLatitude-0.015 <= latitude && latitude <= desLatitude+0.015 && desLongitude-0.015 <= longitude && longitude <= desLongitude+0.015){
                    Log.d("@@@@@@", "도착")

                    if(status==1) {
                        Log.d("@@@@@@", "status 1, 버스 정류장 도착")

                        tts.speak("버스 정류장에 도착하였습니다.", TextToSpeech.QUEUE_FLUSH, null, this.hashCode().toString())
                        SharedPreferenceController.setStatus(applicationContext, 2)
                        Intent(applicationContext, ArriveAtStopActivity::class.java).let {
                            it.putExtra("BUS_NUM", 1125)
                            // TODO 버스 정보 등 버스 예약에 필요한 정보 넘기기
                            startActivity(it)
                            finish()
                        }
                    }else {
                        Log.d("@@@@@@", "status 1 아님, 목적지 도착")

                        tts.speak("목적지에 도착하였습니다. 하단의 안내 종료 버튼을 눌러서 안내를 종료하세요.", TextToSpeech.QUEUE_FLUSH, null, this.hashCode().toString())
                        SharedPreferenceController.setStatus(applicationContext, 0)
                        startActivity(Intent(applicationContext, NoReservedMainActivity::class.java))
                        finish()
                    }
                }

                chkPoint()
            }


            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            }

            override fun onProviderEnabled(provider: String?) {
            }

            override fun onProviderDisabled(provider: String?) {
            }
        }

        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0f, locationListener)
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0f, locationListener)
    }

     private fun getDegreeBetween(lat1:Double, lng1:Double, lat2:Double, lng2:Double) : Long{
         val PI = Math.PI
         val dTeta = Math.log(Math.tan((lat2/2)+(PI/4))/Math.tan((lat1/2)+(PI/4)))
         val dLon = Math.abs(lng1-lng2)
         val teta = Math.atan2(dLon,dTeta)
         return Math.round(Math.toDegrees(teta))
    }

    private fun chkPoint() {
        val clockKor = arrayOf("열두", "한", "두", "세", "네", "다섯", "여섯", "일곱", "여덟", "아홉", "열", "열한")
        for (i in pathCnt until paths.size){
            if(paths[i].type==Type.POINT && paths[i].turnType!="앞으로 가세요." && paths[i].y-0.0001 <= latitude && latitude <= paths[i].y+0.0001 && paths[i].x-0.0001 <= longitude && longitude <= paths[i].x+0.0001){
                tts.speak("여기서 ${paths[i].turnType} 하십시오.", TextToSpeech.QUEUE_FLUSH, null, this.hashCode().toString())
                pathCnt = i

                Log.d("ASDASD","${i} ${paths[i].turnType}")
                val pointDegree = getDegreeBetween(paths[i].y, paths[i].x, paths[i+2].y, paths[i+2].x)

                Log.d("ASDASD","${i} point degree: ${pointDegree}")
                var userDir = 0
                var roadDir = 0
                var clock = 0
                if(!(pointDegree-15 <= mCurrentDegree && mCurrentDegree < pointDegree+15)) {
//                    vibrator.vibrate(10)
                    // 시계 방위로 변경 (0~11시 방향)
                    userDir = ((mCurrentDegree + 15) % 360 / 30).toInt()
                    roadDir = ((pointDegree + 15) % 360 / 30).toInt()

                    // userDir을 12시 기준으로 roadDir 시계 방위 구하기
                    clock = if(userDir > roadDir)
                                12-userDir-roadDir
                            else
                                roadDir-userDir

                    tts.speak("${clockKor[clock]}시 방향으로 직진하세요.\n", TextToSpeech.QUEUE_FLUSH, null, this.hashCode().toString())

                    Log.d("ASDASD","${i} ${clock} ${userDir} ${roadDir}")
                }

                break
            }
        }
    }

    private fun getLatLng() {
        latitude = SharedPreferenceController.getStartLat(this)
        longitude = SharedPreferenceController.getStartLng(this)
        desLatitude = SharedPreferenceController.getDestinationLat(this)
        desLongitude = SharedPreferenceController.getDestinationLng(this)

        Log.d("길찾기 좌표 확인", "현재 ($longitude, $latitude) / 목적지 ($desLongitude, $desLatitude)")
    }

    private fun getTMap() {
        tMapView = TMapView(this)
        tMapView.setSKTMapApiKey("767dc065-35e7-4782-a787-202f73d8d976")
        tMapView.setLocationPoint(longitude!!,latitude!!)
        tMapView.setCenterPoint(longitude!!,latitude!!)
        tMapView.setCompassMode(true)
        tMapView.setIconVisibility(true)

        var d : Drawable = resources.getDrawable(R.drawable.current_loc_icon)
        var bitmap : Bitmap = (d as BitmapDrawable).bitmap
        tMapView.setIcon(bitmap)
        tMapView.zoomLevel = 30
        tMapView.mapType = TMapView.MAPTYPE_HYBRID  //일반지도
        tMapView.setLanguage(TMapView.LANGUAGE_KOREAN)
        tMapView.setTrackingMode(true)
        tMapView.setSightVisible(true)
        tMapView.contentDescription = "지도 영역입니다"
    }

    private fun drawLine(pathResDtoList : List<Point>) {
        val alTMapPoint = ArrayList<TMapPoint>()
        paths = pathResDtoList as ArrayList<Point>

        if (status==1) {
            // 예약O, 탑승 전
            // 출발지부터 버스 정거장까지 draw
            for(i in pathResDtoList) {
                alTMapPoint.add(TMapPoint(i.y, i.x))

                if(i.type == Type.BUS_STOP) {
                    Log.d("BUS_STOP 좌표", "${i.x}, ${i.y}")
                    busStopLng = i.x
                    busStopLat = i.y
                    break
                }
            }

        } else {
            // 하차 후
            // 내린 정거장 부터 목적지까지 draw
            var flag = false

            finalDesLat = pathResDtoList[pathResDtoList.size-1].y
            finalDesLng = pathResDtoList[pathResDtoList.size-1].x

            for(i in pathResDtoList) {
                if (flag){
                    if(i.type == Type.BUS_STOP) {
                        offBusStopLng = i.x
                        offBusStopLat = i.y
                    }
                    alTMapPoint.add(TMapPoint( i.y, i.x))
                }
                if (i.type == Type.BUS_STOP)
                    flag = true

            }

        }

        val tMapPolyLine = TMapPolyLine()
        tMapPolyLine.lineColor = Color.parseColor("#484848")
        tMapPolyLine.lineAlpha = -100
        tMapPolyLine.lineWidth = 80f
        tMapPolyLine.outLineColor = Color.parseColor("#484848")
        tMapPolyLine.outLineAlpha = -100
        tMapPolyLine.outLineWidth = 80f
        for (i in 0 until alTMapPoint.size) {
            tMapPolyLine.addLinePoint(alTMapPoint[i])
        }
        drawMarker()

        tMapView.addTMapPolyLine("Line1", tMapPolyLine)
    }

    private fun drawMarker() {
        Log.d("drawMarker", "진입")
        var startMarker = TMapMarkerItem()
        var desMarker = TMapMarkerItem()
        var startMarkerPos : TMapPoint
        var desMarkerPos : TMapPoint

        // 마커 아이콘
        var startIcon : Bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.start_marker)
        var desIcon : Bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.dest_marker)

        if (status == 1) {
            startMarkerPos = TMapPoint(SharedPreferenceController.getStartLat(this), SharedPreferenceController.getStartLng(this))
            desMarkerPos = TMapPoint(busStopLat, busStopLng)
            Log.d("drawMarker", "도착지 좌표: $busStopLng, $busStopLat")
        }

        else {
            Log.d("drawMarker", "?????")
            startMarkerPos = TMapPoint(offBusStopLat, offBusStopLng)
            desMarkerPos = TMapPoint(finalDesLat, finalDesLng)
        }

        startMarker.icon = startIcon // 마커 아이콘 지정
        startMarker.setPosition(0.5f, 1.0f) // 마커의 중심점을 중앙, 하단으로 설정
        startMarker.tMapPoint = startMarkerPos // 마커의 좌표 지정

        desMarker.icon = desIcon // 마커 아이콘 지정
        desMarker.setPosition(0.5f, 1.0f) // 마커의 중심점을 중앙, 하단으로 설정
        desMarker.tMapPoint = desMarkerPos // 마커의 좌표 지정

        tMapView.addMarkerItem("startMarker", startMarker) // 지도에 마커 추가
        tMapView.addMarkerItem("desMarker", desMarker) // 지도에 마커 추가

//        tMapView.setCenterPoint( 126.985302, 37.570841 );
        mapview_directions.addView(tMapView)

    }

    private fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = resources.getColor(R.color.black, theme)
        }
        else {
            window.statusBarColor = resources.getColor(R.color.black)
        }
    }

    private fun getPathResponse() {
        var jsonObject = JSONObject()
        jsonObject.put("endX", desLongitude)
        jsonObject.put("endY", desLatitude)
        jsonObject.put("startX", longitude)
        jsonObject.put("startY", latitude)

        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        val getPathResponse = networkService.getPathResponse(gsonObject)

        getPathResponse!!.enqueue(object : Callback<PathResDto> {
            override fun onFailure(call: Call<PathResDto>, t: Throwable) {
                Log.d("pathResponse 호출: ","onFailure")
                Log.d("pathResponse 에러: ", t.message)
            }
            override fun onResponse(call: Call<PathResDto>, response: Response<PathResDto>) {
                response?.let {
                    when (it.code()) {
                        200 -> {
                            Log.d("pathResponse 상태 코드: ","200")
                            Log.d("pathResponse 결과: ", response.body().toString())

                            drawLine(response.body()!!.points)
                        }
                        403 -> {
                            Log.d("pathResponse 상태 코드: ","403")

                        }
                        500 -> {
                            Log.d("pathResponse 상태 코드: ","500")

                        }
                        else -> {
                            Log.d("pathResponse 상태 코드: ", it.code().toString())
                        }
                    }
                }
            }

        })
    }
}
