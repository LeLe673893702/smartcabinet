package com.llc.smartcabinet

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.llc.smartcabinet.constants.Constants
import com.llc.smartcabinet.data.enumtype.OperationWay
import com.llc.smartcabinet.data.worker.ConnectWorker
import com.llc.smartcabinet.data.worker.FaceWork
import com.llc.smartcabinet.page.face.FaceActivity
import com.llc.smartcabinet.page.order.ReturnMoneyActivity
import com.llc.smartcabinet.page.password.PasswordActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        register()
        startWorkManager()
    }

    private fun register() {
        btPasswordOpen.setOnClickListener {
            val intent = Intent(this@MainActivity, PasswordActivity::class.java)
            intent.putExtra(Constants.OPERATION_TYPE, OperationWay.OPEN_DOOR)
            startActivity(intent)
        }

        btFaceOpen.setOnClickListener {
            val intent = Intent(this@MainActivity, FaceActivity::class.java)
            intent.putExtra(Constants.OPERATION_TYPE, OperationWay.OPEN_DOOR)
            startActivity(intent)
        }

        btRefunds.setOnClickListener {
            val intent = Intent(this@MainActivity, ReturnMoneyActivity::class.java)
            startActivity(intent)
        }
    }

    private fun startWorkManager() {
        val faceWorkRequest = OneTimeWorkRequest.Builder(FaceWork::class.java).build()
        val connectWorkRequest = OneTimeWorkRequest.Builder(ConnectWorker::class.java).build()
        WorkManager.getInstance(this).enqueue(listOf(faceWorkRequest, connectWorkRequest))
    }
}
