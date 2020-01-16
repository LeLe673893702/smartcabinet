package com.llc.smartcabinet.page.askfinish

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.llc.smartcabinet.R
import com.llc.smartcabinet.page.order.ReturnMoneyActivity
import kotlinx.android.synthetic.main.activity_ask_finish.*

class AskFinishActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ask_finish)
        registerEvent()
    }

    private fun registerEvent() {
        btSure.setOnClickListener {
            val intent = Intent(this@AskFinishActivity, ReturnMoneyActivity::class.java)
            startActivity(intent)
        }

        btQuit.setOnClickListener {
            finish()
        }
    }
}
