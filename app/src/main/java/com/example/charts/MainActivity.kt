package com.example.charts

import android.content.res.Configuration
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.charts.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val fakeData = listOf(3f, 6f, 5f, 7f, 8f, 4f, 1f, 5f, 3f)

        viewBinding.chartView.apply {
            reset()
            setup(8)
            pointDotEnable = false
            fadeEffectEnable = true
            pointDotStrokeEnable = true
            isDarkMode = resources.configuration.uiMode and
                    Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
            Handler(Looper.getMainLooper()).postDelayed({
                displayChart(
                    fakeData.shuffled(),
                    ContextCompat.getColor(this@MainActivity, R.color.color_chart)
                )
            }, 300)
        }
        viewBinding.chartView2.apply {
            reset()
            setup(8)
            pointDotEnable = false
            fadeEffectEnable = true
            pointDotStrokeEnable = true
            isDarkMode = resources.configuration.uiMode and
                    Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
            Handler(Looper.getMainLooper()).postDelayed({
                displayChart(
                    fakeData.shuffled(),
                    ContextCompat.getColor(this@MainActivity, R.color.color_chart2)
                )
            }, 300)
        }
        viewBinding.chartView3.apply {
            reset()
            setup(8)
            pointDotEnable = false
            fadeEffectEnable = true
            pointDotStrokeEnable = true
            isDarkMode = resources.configuration.uiMode and
                    Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
            Handler(Looper.getMainLooper()).postDelayed({
                displayChart(
                    fakeData.shuffled(),
                    ContextCompat.getColor(this@MainActivity, R.color.color_chart_4)
                )
            }, 300)
        }
        viewBinding.chartView4.apply {
            reset()
            setup(8)
            pointDotEnable = false
            fadeEffectEnable = true
            pointDotStrokeEnable = true
            isDarkMode = resources.configuration.uiMode and
                    Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
            Handler(Looper.getMainLooper()).postDelayed({
                displayChart(
                    fakeData.shuffled(),
                    ContextCompat.getColor(this@MainActivity, R.color.color_chart3)
                )
            }, 300)
        }
        viewBinding.apply {
            buttonDot.setOnClickListener {
                chartView.type = ChartType.LINE
                chartView2.type = ChartType.LINE
                chartView3.type = ChartType.LINE
                chartView4.type = ChartType.LINE
                chartView.invalidate()
                chartView2.invalidate()
                chartView3.invalidate()
                chartView4.invalidate()
//                chartView.pointDotEnable = !chartView.pointDotEnable
//                chartView2.pointDotEnable = !chartView2.pointDotEnable
//                chartView3.pointDotEnable = !chartView3.pointDotEnable
//                chartView4.pointDotEnable = !chartView4.pointDotEnable
            }
            buttonStroke.setOnClickListener {
                chartView.type = ChartType.COLUMN
                chartView2.type = ChartType.COLUMN
                chartView3.type = ChartType.COLUMN
                chartView4.type = ChartType.COLUMN
                chartView.invalidate()
                chartView2.invalidate()
                chartView3.invalidate()
                chartView4.invalidate()
//                chartView.pointDotStrokeEnable = !chartView.pointDotStrokeEnable
//                chartView2.pointDotStrokeEnable = !chartView2.pointDotStrokeEnable
//                chartView3.pointDotStrokeEnable = !chartView3.pointDotStrokeEnable
//                chartView4.pointDotStrokeEnable = !chartView4.pointDotStrokeEnable
            }
            buttonShadow.setOnClickListener {
                chartView.type = ChartType.CIRCLE
                chartView2.type = ChartType.CIRCLE
                chartView3.type = ChartType.CIRCLE
                chartView4.type = ChartType.CIRCLE
                chartView.invalidate()
                chartView2.invalidate()
                chartView3.invalidate()
                chartView4.invalidate()
//                chartView.fadeEffectEnable = !chartView.fadeEffectEnable
//                chartView2.fadeEffectEnable = !chartView2.fadeEffectEnable
//                chartView3.fadeEffectEnable = !chartView3.fadeEffectEnable
//                chartView4.fadeEffectEnable = !chartView4.fadeEffectEnable
            }
        }
    }
}