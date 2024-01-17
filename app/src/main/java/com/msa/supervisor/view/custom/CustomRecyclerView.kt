package com.msa.supervisor.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import java.lang.Math.abs


/**
 * create by A-Soleymani on 5/8/2023
 */
class CustomRecyclerView  : RecyclerView {

    private var lastX: Float = 0f
    private var lastY: Float = 0f

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    override fun onInterceptTouchEvent(e: MotionEvent): Boolean {
        when (e.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = e.x
                lastY = e.y
            }
            MotionEvent.ACTION_MOVE -> {
                val deltaX = abs(e.x - lastX)
                val deltaY = abs(e.y - lastY)
                if (deltaX > deltaY) {
                    // User is scrolling horizontally
                    return true
                }
            }
        }
        return super.onInterceptTouchEvent(e)
    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        when (e.action) {
            MotionEvent.ACTION_MOVE -> {
                val deltaX = e.x - lastX
                val deltaY = e.y - lastY
                scrollBy(-deltaX.toInt(), -deltaY.toInt())
                lastX = e.x
                lastY = e.y
                return true
            }
            MotionEvent.ACTION_DOWN -> {
                lastX = e.x
                lastY = e.y
                return true
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> return true
        }
        return super.onTouchEvent(e)
    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int
    ) {
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed)
        scrollBy(dxUnconsumed, dyUnconsumed)
    }
}