package com.msa.supervisor.tool

import android.graphics.*
import android.graphics.drawable.shapes.Shape


/**
 * create by Ali Soleymani.
 */
class LatifiShape(
    private val colorTopStart: Int,
    private val colorTopEnd: Int,
    private val colorBottom: Int,
    private val marginTopLeft: Float
) : Shape() {
    private val pathBack = Path()
    private val path = Path()

    override fun onResize(width: Float, height: Float) {
        path.reset()
        path.moveTo(0f, (height * marginTopLeft / 100))
        path.lineTo(0f, height)
        path.lineTo(width, height)
        path.lineTo(0f, (height * marginTopLeft / 100))
        path.close()
        pathBack.reset()
        pathBack.moveTo(0f, 0f)
        pathBack.lineTo(0f, height)
        pathBack.lineTo(width, height)
        pathBack.lineTo(width, 0f)
        pathBack.close()
    }

    override fun draw(canvas: Canvas, paint: Paint) {
        val paintTest = Paint()
        paintTest.shader = LinearGradient(
            0f, 0f, 0f,
            height, colorTopStart, colorTopEnd, Shader.TileMode.MIRROR
        )
        canvas.drawPath(pathBack, paintTest)
        paint.color = colorBottom
        canvas.drawPath(path, paint)
    }
}