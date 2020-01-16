package com.llc.smartcabinet.utils

import android.content.Context
import android.hardware.usb.UsbManager
import com.hoho.android.usbserial.driver.UsbSerialDriver
import com.hoho.android.usbserial.driver.UsbSerialPort
import com.hoho.android.usbserial.driver.UsbSerialProber
import com.llc.smartcabinet.App

/**
 *
 * @what
 * @author newler
 * @date 2020/1/11
 *
 */
class UsbController {
    companion object {
        val instance: UsbController by lazy {
            UsbController()
        }
    }
    private var port:Int = 0
    private var bauRate:Int = 0

    fun port(port: Int):UsbController {
        this.port = port
        return this
    }

    fun bauRate(bauRate:Int) : UsbController {
        this.bauRate = bauRate
        return this
    }

    private val usbManager:UsbManager by lazy {
        App.getContext().getSystemService(Context.USB_SERVICE) as UsbManager
    }

    private val availableDrivers by lazy {
        UsbSerialProber.getDefaultProber().findAllDrivers(usbManager)
    }

    private var usbSerialPort : UsbSerialPort?=null

    fun open() {
        if (availableDrivers.isNotEmpty()) {
            val driver = availableDrivers[0]
            val connection = usbManager.openDevice(driver.device)
            connection?.let {
                usbSerialPort = driver.ports[port]
            }
            usbSerialPort?.open(connection)
            usbSerialPort?.setParameters(bauRate, UsbSerialPort.DATABITS_8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE)
        }
    }

    fun write() {

    }

    fun close() {

    }
}