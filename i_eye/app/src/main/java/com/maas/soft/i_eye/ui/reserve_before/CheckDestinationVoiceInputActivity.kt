package com.maas.soft.i_eye.ui.reserve_before

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.maas.soft.i_eye.R
import kotlinx.android.synthetic.main.activity_check_destination_voice_input.*

class CheckDestinationVoiceInputActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_destination_voice_input)

        tv_dest_check_dest_voice.text = "${intent.getStringExtra("VOICE_DEST")}\n맞습니까?"
        tv_dest_check_dest_voice.contentDescription = "${intent.getStringExtra("VOICE_DEST")} 맞습니까?"

        setClickListener()
    }

    private fun setClickListener() {
        btn_yes_dest_voice.setOnClickListener {
            // 스트링 값 전달하여 POI 검색
            val mIntent = Intent(this, SearchDestinationActivity::class.java)
            mIntent.putExtra("VOICE_DEST", intent.getStringExtra("VOICE_DEST"))
            startActivity(mIntent)
            finish()
        }
        btn_no_dest_voice.setOnClickListener {
            val mIntent = Intent(this, DestinationVoiceInputActivity::class.java)
            startActivity(mIntent)
            finish()
        }
    }
}
