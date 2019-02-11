package com.toshiki.shun.gnlivewallpaper

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import kotlin.math.PI
import kotlin.math.sin

class GNRadarCircle(x : Float, y : Float, radius : Float) : GNObject() {
    private val x = x
    private val y = y
    private val radius = radius
    private var height = radius
    private val paint = Paint()
    private var count = 0f
    private var radian = 0f
    /** 回転速度 マイナス値を指定すると逆回転 */
    private var v = 1f

    init {
        paint.strokeWidth = 2f
        paint.style  = Paint.Style.STROKE
        paint.color = Color.BLACK
    }

    override fun draw(canvas: Canvas) {
        canvas.rotate(this.radian + this.count, x, y)
        canvas.drawOval(x - radius,
            y - height,
            x + radius,
            y + height, paint)
        this.step()
    }
    private fun step() {
        this.count += this.v
        if(this.count > 180){
            this.count -= 360
        }
        if(this.count < -180) {
            this.count += 360
        }
        this.height = (sin(this.count * PI / 180f) * this.radius).toFloat()
    }

    fun setV(v : Float) {
        this.v = v
    }
    fun setRadian(radian : Float) {
        this.radian = radian
    }
}