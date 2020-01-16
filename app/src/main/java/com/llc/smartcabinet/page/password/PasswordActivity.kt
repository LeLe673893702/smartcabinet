package com.llc.smartcabinet.page.password

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.llc.smartcabinet.R
import com.llc.smartcabinet.constants.Constants
import com.llc.smartcabinet.data.enumtype.OperationWay

class PasswordActivity : AppCompatActivity() {
    @OperationWay
    private val  operationType:Int by lazy {
        intent.getIntExtra(Constants.OPERATION_TYPE, 0)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)

        supportFragmentManager.beginTransaction()
            .add(R.id.content, PasswordFragment.newInstance(operationType))
            .commit()
    }
}
