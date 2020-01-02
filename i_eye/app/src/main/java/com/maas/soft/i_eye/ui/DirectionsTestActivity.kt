package com.maas.soft.i_eye.ui

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
import android.os.Handler
import android.os.Vibrator
import android.speech.tts.TextToSpeech
import com.maas.soft.i_eye.model.PathResDto
import com.maas.soft.i_eye.model.Type
import com.maas.soft.i_eye.ui.reserve_after.ArriveAtStopActivity
import com.skt.Tmap.TMapMarkerItem
import com.maas.soft.i_eye.ui.reserve_before.NoReservedMainActivity
import java.util.*
import kotlin.collections.ArrayList


class DirectionsTestActivity : AppCompatActivity(){
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

        tts = TextToSpeech(applicationContext, TextToSpeech.OnInitListener {
            tts.language = Locale.KOREAN
        })
        locationManager = this.getSystemService(LOCATION_SERVICE) as LocationManager
        status = SharedPreferenceController.getStatus(this)

        relative_directions.setOnClickListener {
            startActivity(Intent(applicationContext, ArriveAtStopActivity::class.java))
        }

        getLatLng()
        getTMap()
        getPathResponse()
        changeStatusBarColor()
        setLocation()

        var lm : LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (ActivityCompat.checkSelfPermission(applicationContext, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(applicationContext, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION), 1)
        }else{
//            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, // 등록할 위치제공자
//                    1000, // 통지사이의 최소 시간간격 (miliSecond)
//                    1f, // 통지사이의 최소 변경거리 (m)
//                    locationListener)
        }
    }

    private fun setLocation() {
//        for (i in 0 until paths.size){
        Handler().postDelayed({
            tMapView.setCenterPoint(127.03121516595668, 37.50563215168024)
            Handler().postDelayed({
                tMapView.setCenterPoint(127.03114781150711, 37.50561340264893)
                Handler().postDelayed({
                    tMapView.setCenterPoint(127.03111413428232, 37.505604028133284)
                    Handler().postDelayed({
                        tMapView.setCenterPoint(127.03109729566992, 37.50559934087546)
                        Handler().postDelayed({
                            tMapView.setCenterPoint(127.03108887636373, 37.50559699724655)
                            Handler().postDelayed({
                                tMapView.setCenterPoint(127.03108466671063, 37.505595825432096)
                                Handler().postDelayed({
                                    tMapView.setCenterPoint(127.03108256188409, 37.505595239524865)
                                    Handler().postDelayed({
                                        tMapView.setCenterPoint(127.03108150947082, 37.50559494657125)
                                        Handler().postDelayed({
                                            tMapView.setCenterPoint(127.03108098326419, 37.50559480009444)
                                            Handler().postDelayed({
                                                tMapView.setCenterPoint(127.03108072016087, 37.50559472685604)
                                                Handler().postDelayed({
                                                    tMapView.setCenterPoint(127.0310805886092, 37.50559469023683)
                                                    Handler().postDelayed({
                                                        tMapView.setCenterPoint(127.03108052283338, 37.505594671927234)
                                                        Handler().postDelayed({
                                                            tMapView.setCenterPoint(127.03108048994545, 37.50559466277244)
                                                            Handler().postDelayed({
                                                                tMapView.setCenterPoint(127.0310804735015, 37.50559465819504)
                                                                Handler().postDelayed({
                                                                    tMapView.setCenterPoint(127.03108046527953, 37.505594655906336)
                                                                    Handler().postDelayed({
                                                                        tMapView.setCenterPoint(127.03108046116853, 37.505594654761985)
                                                                        Handler().postDelayed({
                                                                            tMapView.setCenterPoint(127.03108045911304, 37.50559465418981)
                                                                            Handler().postDelayed({
                                                                                tMapView.setCenterPoint(127.03108045808528, 37.50559465390372)
                                                                                Handler().postDelayed({
                                                                                    tMapView.setCenterPoint(127.03108045757142, 37.50559465376068)
                                                                                    Handler().postDelayed({
                                                                                        tMapView.setCenterPoint(127.03108045731449, 37.50559465368916)
                                                                                        Handler().postDelayed({
                                                                                            tMapView.setCenterPoint(127.03108045718602, 37.5055946536534)
                                                                                            Handler().postDelayed({
                                                                                                tMapView.setCenterPoint(127.03108045712179, 37.50559465363551)
                                                                                                Handler().postDelayed({
                                                                                                    tMapView.setCenterPoint(127.03108045708967, 37.505594653626574)
                                                                                                    Handler().postDelayed({
                                                                                                        tMapView.setCenterPoint(127.03108045707361, 37.505594653622104)
                                                                                                        Handler().postDelayed({
                                                                                                            tMapView.setCenterPoint(127.03108045706557, 37.505594653619866)
                                                                                                            Handler().postDelayed({
                                                                                                                tMapView.setCenterPoint(127.03108045706156, 37.50559465361875)
                                                                                                                Handler().postDelayed({
                                                                                                                    tMapView.setCenterPoint(127.03108045705955, 37.50559465361819)
                                                                                                                    Handler().postDelayed({
                                                                                                                        tMapView.setCenterPoint(127.03108045705855, 37.50559465361791)
                                                                                                                        Handler().postDelayed({
                                                                                                                            tMapView.setCenterPoint(127.03108045705804, 37.50559465361778)
                                                                                                                            Handler().postDelayed({
                                                                                                                                tMapView.setCenterPoint(127.03108045705754, 37.505594653617635)
                                                                                                                                Handler().postDelayed({
                                                                                                                                    tMapView.setCenterPoint(127.03101310260797, 37.50557590458633)
                                                                                                                                    Handler().postDelayed({
                                                                                                                                        tMapView.setCenterPoint(127.03097942538318, 37.50556653007068)
                                                                                                                                        Handler().postDelayed({
                                                                                                                                            tMapView.setCenterPoint(127.03096258677078, 37.50556184281285)
                                                                                                                                            Handler().postDelayed({
                                                                                                                                                tMapView.setCenterPoint(127.03095416746459, 37.50555949918394)
                                                                                                                                                Handler().postDelayed({
                                                                                                                                                    tMapView.setCenterPoint(127.03094995781149, 37.505558327369485)
                                                                                                                                                    Handler().postDelayed({
                                                                                                                                                        tMapView.setCenterPoint(127.03094785298495, 37.505557741462255)
                                                                                                                                                        Handler().postDelayed({
                                                                                                                                                            tMapView.setCenterPoint(127.03094680057168, 37.50555744850864)
                                                                                                                                                            Handler().postDelayed({
                                                                                                                                                                tMapView.setCenterPoint(127.03094627436505, 37.50555730203183)
                                                                                                                                                                Handler().postDelayed({
                                                                                                                                                                    tMapView.setCenterPoint(127.03094601126173, 37.50555722879343)
                                                                                                                                                                    Handler().postDelayed({
                                                                                                                                                                        tMapView.setCenterPoint(127.03094587971006, 37.50555719217422)
                                                                                                                                                                        Handler().postDelayed({
                                                                                                                                                                            tMapView.setCenterPoint(127.03094581393424, 37.505557173864624)
                                                                                                                                                                            Handler().postDelayed({
                                                                                                                                                                                tMapView.setCenterPoint(127.03094578104631, 37.50555716470983)
                                                                                                                                                                                Handler().postDelayed({
                                                                                                                                                                                    tMapView.setCenterPoint(127.03094576460236, 37.505557160132426)
                                                                                                                                                                                    Handler().postDelayed({
                                                                                                                                                                                        tMapView.setCenterPoint(127.0309457481584, 37.505557155555024)
                                                                                                                                                                                        Handler().postDelayed({
                                                                                                                                                                                            tMapView.setCenterPoint(127.03081103925926, 37.505519657492414)
                                                                                                                                                                                            Handler().postDelayed({
                                                                                                                                                                                                tMapView.setCenterPoint(127.03074368480969, 37.50550090846111)
                                                                                                                                                                                                Handler().postDelayed({
                                                                                                                                                                                                    tMapView.setCenterPoint(127.0307100075849, 37.50549153394546)
                                                                                                                                                                                                    Handler().postDelayed({
                                                                                                                                                                                                        tMapView.setCenterPoint(127.0306931689725, 37.50548684668763)
                                                                                                                                                                                                        Handler().postDelayed({
                                                                                                                                                                                                            tMapView.setCenterPoint(127.0306847496663, 37.50548450305872)
                                                                                                                                                                                                            Handler().postDelayed({
                                                                                                                                                                                                                tMapView.setCenterPoint(127.03068054001321, 37.505483331244264)
                                                                                                                                                                                                                Handler().postDelayed({
                                                                                                                                                                                                                    tMapView.setCenterPoint(127.03067633036012, 37.5054821594298)
                                                                                                                                                                                                                    Handler().postDelayed({
                                                                                                                                                                                                                        tMapView.setCenterPoint(127.03042911461058, 37.50604875583092)
                                                                                                                                                                                                                        Handler().postDelayed({
                                                                                                                                                                                                                            tMapView.setCenterPoint(127.0304485556351, 37.50610708273702)
                                                                                                                                                                                                                            Handler().postDelayed({
                                                                                                                                                                                                                                tMapView.setCenterPoint(127.03048744090573, 37.50610986089032)
                                                                                                                                                                                                                                Handler().postDelayed({
                                                                                                                                                                                                                                    tMapView.setCenterPoint(127.03065131354163, 37.506157080569366)
                                                                                                                                                                                                                                    Handler().postDelayed({
                                                                                                                                                                                                                                        tMapView.setCenterPoint(127.03096794887077, 37.506245964817865)
                                                                                                                                                                                                                                        Handler().postDelayed({
                                                                                                                                                                                                                                            tMapView.setCenterPoint(127.0310375644094, 37.50608116628093)},1000)
                                                                                                                                                                                                                                    },1000)
                                                                                                                                                                                                                                },1000)
                                                                                                                                                                                                                            },1000)
                                                                                                                                                                                                                        },1000)
                                                                                                                                                                                                                    },1000)
                                                                                                                                                                                                                },1000)
                                                                                                                                                                                                            },1000)
                                                                                                                                                                                                        },1000)
                                                                                                                                                                                                    },1000)
                                                                                                                                                                                                },1000)
                                                                                                                                                                                            },1000)
                                                                                                                                                                                        },1000)
                                                                                                                                                                                    },1000)
                                                                                                                                                                                },1000)
                                                                                                                                                                            },1000)
                                                                                                                                                                        },1000)
                                                                                                                                                                    },1000)
                                                                                                                                                                },1000)
                                                                                                                                                            },1000)
                                                                                                                                                        },1000)
                                                                                                                                                    },1000)
                                                                                                                                                },1000)
                                                                                                                                            },1000)
                                                                                                                                        },1000)
                                                                                                                                    },1000)
                                                                                                                                },1000)
                                                                                                                            },1000)
                                                                                                                        },1000)
                                                                                                                    },1000)
                                                                                                                },1000)
                                                                                                            },1000)
                                                                                                        },1000)
                                                                                                    },1000)
                                                                                                },1000)
                                                                                            },1000)
                                                                                        },1000)
                                                                                    },1000)
                                                                                },1000)
                                                                            },1000)
                                                                        },1000)
                                                                    },1000)
                                                                },1000)
                                                            },1000)
                                                        },1000)
                                                    },1000)
                                                },1000)
                                            },1000)
                                        },1000)
                                    },1000)
                                },1000)
                            },1000)
                        },1000)
                    },1000)
                },1000)
            },1000)
        },1000)

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
        tMapView.setTrackingMode(false)
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
        tMapView.addTMapPolyLine("Line1", tMapPolyLine)
    }

    private fun drawMarker() {
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
        }

        else {
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
                            drawMarker()
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