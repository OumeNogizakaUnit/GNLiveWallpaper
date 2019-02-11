package com.toshiki.shun.gnlivewallpaper

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import kotlin.math.PI
import kotlin.math.sin

class GNRadarCircle(x : Float, y : Float, radius : Float) : GNObject() {
    private val x = x
    private val y = y
    private val radius = radius
    private var height = radius
    private val paint = Paint()
    private var count = 0
    init {
        paint.strokeWidth = 2f
        paint.style  = Paint.Style.STROKE
        paint.color = Color.BLACK
    }

    override fun draw(canvas: Canvas) {
        canvas.drawOval(x - radius,
            y - height,
            x + radius,
            y + height, paint)
        this.step()
    }
    private fun step() {
        this.count++
        if(this.count > 360) {
            this.count = 0
        }
        this.height = (sin(this.count * PI / 180f) * this.radius).toFloat()
        Log.d("Radar", this.height.toString())
    }
}