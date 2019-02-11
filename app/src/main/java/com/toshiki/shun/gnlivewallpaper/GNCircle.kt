package com.toshiki.shun.gnlivewallpaper

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class GNCircle(x : Float, y :Float, radius : Float) : GNObject() {
    private val paint = Paint()
    private val x = x
    private val y = y
    private val radius = radius
    init {
        this.paint.color = Color.BLACK
        this.paint.style = Paint.Style.STROKE
        this.paint.strokeWidth = 10f
    }
    override fun draw(canvas: Canvas) {
        canvas.drawCircle(this.x, this.y, this.radius, paint)
    }
}