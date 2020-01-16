package com.llc.smartcabinet.page.order

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.llc.smartcabinet.R
import com.llc.smartcabinet.constants.Constants
import com.llc.smartcabinet.data.enumtype.OperationWay
import com.llc.smartcabinet.page.face.FaceActivity
import com.llc.smartcabinet.page.password.PasswordActivity
import kotlinx.android.synthetic.main.activity_returen_money.*

class ReturnMoneyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_returen_money)
        registerEvent()
    }

    private fun registerEvent() {
        btPasswordOrder.setOnClickListener {
            val intent = Intent(this@ReturnMoneyActivity, PasswordActivity::class.java)
            intent.putExtra(Constants.OPERATION_TYPE, OperationWay.FINISH_ORDER)
            startActivity(intent)
        }

        btFaceOrder.setOnClickListener {
            val intent = Intent(this@ReturnMoneyActivity, FaceActivity::class.java)
            intent.putExtra(Constants.OPERATION_TYPE, OperationWay.FINISH_ORDER)
            startActivity(intent)
        }
    }
}
