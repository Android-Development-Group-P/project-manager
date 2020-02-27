package com.example.projectmanager.util

import android.graphics.Bitmap
import android.graphics.Color
import com.google.gson.annotations.SerializedName
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.io.Serializable

object QRGenerator {

    private val QR_WIDTH = 512
    private val QR_HEIGHT = 512
    private val writer = QRCodeWriter()

    fun generate(qr: QRObject) : Bitmap {
        val matrix = writer.encode(qr.content, BarcodeFormat.QR_CODE, qr.width!!, qr.height!!)
        val bitmap = Bitmap.createBitmap(qr.width!!, qr.height!!, Bitmap.Config.RGB_565)

        for (x in 0 until qr.width!!) {
            for (y in 0 until qr.height!!) {
                bitmap.setPixel(x, y, if (matrix.get(x, y)) Color.BLACK else Color.WHITE)
            }
        }

        return bitmap
    }

    fun  generate2(qr: QRObject) : Bitmap {
        val barcodeEncoder = BarcodeEncoder()
        return barcodeEncoder.encodeBitmap(qr.content, BarcodeFormat.QR_CODE,
            qr.width ?: QR_WIDTH,
            qr.height ?: QR_HEIGHT)
    }

    data class QRObject (
        var content: String? = null,
        var width: Int? = null,
        var height: Int? = null
    ) : Serializable
}