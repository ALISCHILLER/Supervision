package com.msa.supervisor.view.custom

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Shader
import android.graphics.drawable.shapes.Shape
import com.msa.supervisor.R


/**
 * create by Ali Soleymani.
 */
class Latifi(private val colorTop: Int, private val colorBottom: Int, private val radius: Float): Shape() {
    private val pathBack = Path()
    private val path = Path()

    override fun onResize(width: Float, height: Float) {
        path.reset()
        path.moveTo(0f, height - (height * radius / 100))
        path.lineTo(0f, height)
        path.lineTo(width, height)
        path.lineTo(width, (height * (radius * 4) / 100))
        path.close()
        pathBack.reset()
        pathBack.moveTo(0f, 0f)
        pathBack.lineTo(0f, height)
        pathBack.lineTo(width, height)
        pathBack.lineTo(width, 0f)
        pathBack.close()
    }

    @SuppressLint("ResourceAsColor")
    override fun draw(canvas: Canvas, paint: Paint) {
        paint.shader = LinearGradient(
            0f, 0f, 0f,
            height, colorTop, R.color.datePickerConfirmButtonBackColor, Shader.TileMode.MIRROR
        )
        canvas.drawPath(pathBack, paint)

        val paint2 = Paint()
        paint2.shader = LinearGradient(
            0f, 0f, 0f,
            height,  R.color.datePickerConfirmButtonBackColor, colorBottom, Shader.TileMode.MIRROR
        )
        canvas.drawPath(path, paint2)
    }
}