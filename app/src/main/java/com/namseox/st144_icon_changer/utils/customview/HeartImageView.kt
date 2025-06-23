package com.namseox.st144_icon_changer.utils.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatImageView

class HeartImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatImageView(context, attrs) {

    private val path = Path()
    private val rect = RectF()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        rect.set(0f, 0f, w.toFloat(), h.toFloat())
        path.reset()

        val midX = w / 2f
        val midY = h / 2f
        val min = kotlin.comparisons.minOf(w, h) / 1.4f

        path.moveTo(midX, midY + min / 1.5f)
        path.cubicTo(
            -min / 1.4f,
            midY - min / 2,
            midX,
            midY - min,
            midX,
            midY - min / 2)

        path.cubicTo(
            midX-dpToPx(0f,context),
            midY - min,
            w + min / 1.4f,
            midY - min / 2,
            midX,
            midY + min / 1.5f)
        path.close()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.save()
        canvas.clipPath(path)
        super.onDraw(canvas)
        canvas.restore()
    }
    fun dpToPx(dp: Float, context: Context): Float {
        val metrics = context.resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics)
    }
}
