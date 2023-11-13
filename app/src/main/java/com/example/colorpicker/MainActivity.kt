package com.example.colorpicker

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.Button
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var colorSquare: View
    private lateinit var roundedDrawable: GradientDrawable
    private var RGB: String = ""
    private var red = 0
    private var green = 0
    private var blue = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        roundedDrawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 50f // Set the radius for rounded corners
        }


        colorSquare = findViewById(R.id.colorSquare)

        val redSeekBar = findViewById<SeekBar>(R.id.redSeekBar)
        val greenSeekBar = findViewById<SeekBar>(R.id.greenSeekBar)
        val blueSeekBar = findViewById<SeekBar>(R.id.blueSeekBar)


        val seekBarChangeListener = object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                when (seekBar?.id) {
                    R.id.redSeekBar -> red = progress
                    R.id.greenSeekBar -> green = progress
                    R.id.blueSeekBar -> blue = progress
                }
                updateColor()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Not used
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Not used
            }
        }

        val button = findViewById<Button>(R.id.colorMixer)
        button.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

        redSeekBar.setOnSeekBarChangeListener(seekBarChangeListener)
        greenSeekBar.setOnSeekBarChangeListener(seekBarChangeListener)
        blueSeekBar.setOnSeekBarChangeListener(seekBarChangeListener)
    }

    private fun updateColor() {
        val color = Color.rgb(red, green, blue)
        roundedDrawable.setColor(color)
        colorSquare.background = roundedDrawable

        val rgbText = "RGB: $red, $green, $blue"
        findViewById<TextView>(R.id.RGB).text = rgbText

        val hexText = "HEX: #${Integer.toHexString(color).substring(2).uppercase()}"
        findViewById<TextView>(R.id.HEX).text = hexText

        val cmyk = rgbToCmyk(red, green, blue)
        val cmykText = "CMYK: ${cmyk[0]}, ${cmyk[1]}, ${cmyk[2]}, ${cmyk[3]}"
        findViewById<TextView>(R.id.CMYK).text = cmykText

        val hsv = FloatArray(3)
        Color.RGBToHSV(red, green, blue, hsv)
        val hsvText = "HSV: ${"%.2f".format(hsv[0])}, ${"%.2f".format(hsv[1])}, ${"%.2f".format(hsv[2])}"
        findViewById<TextView>(R.id.HSV).text = hsvText

    }

    private fun rgbToCmyk(r: Int, g: Int, b: Int): IntArray {
        val c = 1 - r / 255.0
        val m = 1 - g / 255.0
        val y = 1 - b / 255.0
        val k = minOf(c, m, y)

        val c1 = ((c - k) / (1 - k) * 100).toInt()
        val m1 = ((m - k) / (1 - k) * 100).toInt()
        val y1 = ((y - k) / (1 - k) * 100).toInt()
        val k1 = (k * 100).toInt()

        return intArrayOf(c1, m1, y1, k1)
    }
}
