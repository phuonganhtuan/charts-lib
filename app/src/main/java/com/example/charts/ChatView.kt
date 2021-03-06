package com.example.charts

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.graphics.blue
import androidx.core.graphics.green
import java.util.*


class ChartView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    // Configuration params
    var pointDotEnable = true
    var fadeEffectEnable = true // Fade effect supports black or white background
    var pointDotStrokeEnable = true
    var pointDotStrokeColor = Color.WHITE
    var lineWidth = 8f
    var dotRadius = 4f
    var dotStrokeSize = 3f
    var isDarkMode = false
    var itemDistance = 0f
    var chartHeight = 0f
    private var path = Path()
    var unit = 0
    private val charts = mutableListOf<ChartObject>()
    private var unitVertical = 0

    private var touchAreaDot = mutableListOf<RectF>()

    private val chartPaint by lazy {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = pointDotStrokeColor
        paint.strokeWidth = dotStrokeSize + dotRadius
        paint.strokeCap = Paint.Cap.ROUND
        paint.style = Paint.Style.STROKE
        paint.setShadowLayer(lineWidth * 1.5f, 0f, 4f, Color.argb(50, 0, 0, 0))
        paint
    }
    private val shadowPaint by lazy {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.BLUE
        paint.strokeWidth = 1f
        paint.style = Paint.Style.STROKE
        paint
    }

    private val chartPaintSmall by lazy {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.argb(10, 0, 0, 0)
        paint.strokeWidth = 4f
        paint.strokeCap = Paint.Cap.ROUND
        paint.style = Paint.Style.STROKE
        paint
    }

    private val chartPaintSmallDash by lazy {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.argb(10, 0, 0, 0)
        paint.strokeWidth = 4f
        paint.strokeCap = Paint.Cap.ROUND
        paint.style = Paint.Style.STROKE
        paint.pathEffect = DashPathEffect(floatArrayOf(5f, 5f, 5f, 5f), 0f)
        paint
    }

    private val shadowChartPaint by lazy {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.strokeWidth = lineWidth - 1f
        paint.style = Paint.Style.STROKE
        paint
    }

    private val shadowChartPaintTop by lazy {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.strokeWidth = 12f
        paint.style = Paint.Style.STROKE
        paint
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val startTime = Calendar.getInstance().timeInMillis
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        charts.forEach {
            if (it.data.size >= 2) {
                path.reset()
                path.moveTo(it.dots[0].x, it.dots[0].y)

                if (fadeEffectEnable) {
                    for (i in 0 until it.dots.size - 1) {
                        val xMid = (it.dots[i].x + it.dots[i + 1].x) / 2
                        val yMid = (it.dots[i].y + it.dots[i + 1].y) / 2
                        val controlx1 = (xMid + it.dots[i].x) / 2
                        val controlx2 = (xMid + it.dots[i + 1].x) / 2
                        path.quadTo(controlx1, it.dots[i].y, xMid, yMid)
                        path.quadTo(
                            controlx2,
                            it.dots[i + 1].y,
                            it.dots[i + 1].x,
                            it.dots[i + 1].y
                        )
                    }
                    val points = getPoints()
                    points.forEach { point ->
                        for (i in point.y.toInt() until height step lineWidth.toInt() - 2) {
                            Color.colorToHSV(it.chartColor, it.hsv)
                            if (isDarkMode) {
                                it.hsv[2] = it.hsv[2] - 0.5f - ((it.hsv[2] - 0.5f) / height) * i
                            } else {
                                it.hsv[1] = it.hsv[1] - 0.5f - ((it.hsv[1] - 0.5f) / height) * i
                            }
                            shadowChartPaint.color = Color.HSVToColor(it.hsv)
                            canvas?.drawPoint(point.x, i.toFloat(), shadowChartPaint)
                        }
                    }
                }
                path.moveTo(it.dots[0].x, it.dots[0].y)
                for (i in 0 until it.dots.size - 1) {
                    val xMid = (it.dots[i].x + it.dots[i + 1].x) / 2
                    val yMid = (it.dots[i].y + it.dots[i + 1].y) / 2
                    val controlx1 = (xMid + it.dots[i].x) / 2
                    val controlx2 = (xMid + it.dots[i + 1].x) / 2
                    path.quadTo(controlx1, it.dots[i].y, xMid, yMid)
                    canvas?.drawPath(path, it.paint)
                    path.quadTo(
                        controlx2,
                        it.dots[i + 1].y,
                        it.dots[i + 1].x,
                        it.dots[i + 1].y
                    )
                    canvas?.drawPath(path, it.paint)
                }
//                touchAreaDot.clear()
                if (pointDotEnable) {
                    it.dots.forEach { dot ->
                        if (pointDotStrokeEnable) {
                            canvas?.drawCircle(dot.x, dot.y, dotRadius + dotStrokeSize, chartPaint)
                        }
                        canvas?.drawCircle(dot.x, dot.y, dotRadius, it.paint)
//                    touchAreaDot.add(RectF(dot.x - 12f, dot.y - 12f, dot.x + 12f, dot.y + 12f))
                    }
                }
            }
        }
        Log.i("timedraw", (Calendar.getInstance().timeInMillis - startTime).toString())
    }

//    @SuppressLint("ClickableViewAccessibility")
//    override fun onTouchEvent(event: MotionEvent): Boolean {
//        val touchX = event.x
//        val touchY = event.y
//
//        when (event.action) {
//            MotionEvent.ACTION_DOWN -> {
//
//            }
//            MotionEvent.ACTION_MOVE -> {
//
//            }
//            MotionEvent.ACTION_UP -> {
//
//            }
//            else -> return false
//        }
//        invalidate()
//        return true
//    }

    private fun getPoints(): List<PointF> {
        val pointArray = mutableListOf<PointF>()
        val pm = PathMeasure(path, false)
        val length = pm.length
        var distance = 0f
        val speed = lineWidth - 2f
        var counter = 0
        val aCoordinates = FloatArray(2)
        while (distance < length && counter < width) {
            pm.getPosTan(distance, aCoordinates, null)
            pointArray.add(
                PointF(
                    aCoordinates[0],
                    aCoordinates[1]
                )
            )
            counter++
            distance += speed
        }
        return pointArray
    }

    fun reset() {
        charts.clear()
        invalidate()
    }

    fun setup(unit: Int) {
        this.unit = unit + unit / 7
    }

    fun displayChart(
        data: List<Float>,
        color: Int
    ) {
        this.chartHeight = height.toFloat() - CHART_LINE_WIDTH
        itemDistance = (width.toFloat() - CHART_LINE_WIDTH * 6) / (data.size - 1)
        unitVertical = height / itemDistance.toInt()
        val chartObject = ChartObject()
        chartObject.apply {
            this.data = data.toMutableList()
            chartColor = color
            dots.clear()
            for (i in data.indices) {
                val xOffset = i * itemDistance + CHART_LINE_WIDTH * 3
                val yOffset = chartHeight - (chartHeight * data[i] / unit)
                dots.add(Dot(x = xOffset, y = yOffset))
            }
            paint = Paint(Paint.ANTI_ALIAS_FLAG)
            paint.color = color
            paint.strokeWidth = lineWidth
            paint.style = Paint.Style.STROKE
            paint.strokeCap = Paint.Cap.ROUND
            Color.colorToHSV(color, hsv)
//            paint.setShadowLayer(12f, 0f, 48f, Color.argb(10, 200, 200, 200))
        }
        charts.add(chartObject)
    }

    companion object {
        const val CHART_LINE_WIDTH = 12f
    }
}

data class Dot(
    var x: Float = 0f,
    var y: Float = 0f
)

data class ChartObject(
    var data: MutableList<Float> = mutableListOf(),
    var chartColor: Int = Color.BLUE,
    var dots: MutableList<Dot> = mutableListOf(),
    var paint: Paint = Paint(),
    var hsv: FloatArray = FloatArray(3),
)
