package com.llc.smartterminal.utils

import android.graphics.Bitmap
import android.util.Base64
import com.llc.smartcabinet.App
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 *
 * @what
 * @author newler
 * @date 2020/1/5
 *
 */
class FileUtil {
    companion object {
        fun saveBitmap(bitmap: Bitmap): String {
            var fos : FileOutputStream ?= null
            var pic : File ?= null
            try {
                pic = File(
                    "${App.getContext().filesDir.absolutePath}${File.separator}face-image",
                    "${System.currentTimeMillis()}.png"
                )
                if (!pic.exists()) {
                    pic.parentFile.mkdirs()
                    pic.createNewFile()
                }

                fos = FileOutputStream(pic)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
                fos.flush()
            } catch (e : IOException) {
                e.printStackTrace()
            } finally {
               fos?.close()
            }
            return pic?.absolutePath ?: ""
        }

        fun bitmapToBase64(bitmap : Bitmap) : String? {
            var result: String? = null
            var baos: ByteArrayOutputStream = ByteArrayOutputStream()

            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
                baos.flush()

                result = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT)
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                baos.close()
            }

            return result
        }
    }
}