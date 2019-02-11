package com.toshiki.shun.gnlivewallpaper

import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
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
         * ライブ壁紙起動時の時刻
         */
        private var mFirstTime = 0L
        /**
         * 計測開始時(fps調整用)
         */
        private var mStartTime = 0L
        /**
         * 計測終了時(fps調整用)
         */
        private var mEndTime = 0L
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
        /**
         * 表示する画像番号
         */
        private var mIndex = "0000"

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
            if (mFirstTime == 0L) {
                mFirstTime = System.currentTimeMillis()
            }
            mStartTime = System.currentTimeMillis()

            val canvas = surfaceHolder.lockCanvas()
            canvas?.let { canvas ->
                drawPicture(canvas)
                surfaceHolder.unlockCanvasAndPost(canvas)
            }

            mEndTime = System.currentTimeMillis()

            // 次の描画設定
            val delay = 1000 / FPS - (mEndTime - mStartTime)
            mHandler.removeCallbacks(mDrawRunnable)
            if (mVisible) {
                mHandler.postDelayed(mDrawRunnable, delay)
            }
        }

        /**
         * 画像をCanvasに描画します。
         *
         * @param canvas キャンバス
         */
        private fun drawPicture(canvas: Canvas) {
            try {
                // 現在表示すべき画像番号を計算
                val index = (mStartTime - mFirstTime) / (1000 / FPS) % 227
                val indexString = String.format("%04d", index)
                // assets から画像を読み込み、 canvas に描画
                assets.open("animation${File.separator}live_wallpaper_test$indexString.jpg").use { inputStream ->
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    canvas.drawBitmap(bitmap, null, Rect(0, 0, mWidth, mHeight), null)
                }
            } catch (e: Throwable) {
                Log.e("LiveWallpaperTest", e.message, e)
            }
        }

        /**
         * 画面書き込み用スレッド
         */
        private val mDrawRunnable = Runnable { drawFrame() }

    }

}