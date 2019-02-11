package com.toshiki.shun.gnlivewallpaper

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.Paint
import android.os.Handler
import android.service.wallpaper.WallpaperService
import android.util.Log
import android.view.SurfaceHolder
import java.io.File



/**
 * ライブ壁紙メインクラスです。
 *
 * Created by Daisuke on 2017/06/20.
 */
class GNLiveWallpaper : WallpaperService() {

    companion object {
        /**
         * 描画間隔
         */
        const val FPS = 30
    }

    private val mHandler = Handler()

    override fun onCreateEngine(): Engine {
        return LiveWallpaperEngine()
    }

    inner class LiveWallpaperEngine : Engine() {

        /**
         * 描画領域(幅)
         */
        private var mWidth = 0
        /**
         * 描画領域(高さ)
         */
        private var mHeight = 0
        /**
         * ライブ壁紙が表示されているか
         */
        private var mVisible = false
        private val mPaint = Paint()


        init {
        }

        override fun onSurfaceCreated(holder: SurfaceHolder?) {
            super.onSurfaceCreated(holder)
        }

        override fun onSurfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
            super.onSurfaceChanged(holder, format, width, height)
            mWidth = width
            mHeight = height
            drawFrame()
        }

        override fun onSurfaceDestroyed(holder: SurfaceHolder?) {
            super.onSurfaceDestroyed(holder)
            mVisible = false
            mHandler.removeCallbacks(mDrawRunnable)
        }

        override fun onVisibilityChanged(visible: Boolean) {
            super.onVisibilityChanged(visible)
            mVisible = visible
            if (visible) {
                drawFrame()
            } else {
                mHandler.removeCallbacks(mDrawRunnable)
            }
        }

        /**
         * 画面に書き込みます。
         */
        private fun drawFrame() {
            val canvas = surfaceHolder.lockCanvas()
            // 次の描画設定
            val delay = 1000L / FPS
            mHandler.removeCallbacks(mDrawRunnable)
            if (mVisible) {
                mHandler.postDelayed(mDrawRunnable, delay)
            }
        }
        /**
         * 画面書き込み用スレッド
         */
        private val mDrawRunnable = Runnable { drawFrame() }

    }

}