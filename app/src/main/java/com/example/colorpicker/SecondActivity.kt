package com.example.colorpicker

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {
    private var red = 0
    private var blue = 0
    private var green = 0
    private var yellow = 0
    private var purple = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val colorSquare = findViewById<View>(R.id.colorSquare)
        val redTextView = findViewById<TextView>(R.id.textView)
        val blueTextView = findViewById<TextView>(R.id.textViewblue)
        val greenTextView = findViewById<TextView>(R.id.textViewgreen)

        setupButton(R.id.buttonIncreasered, true, ::increaseRed, redTextView)
        setupButton(R.id.buttonDecreasered, false, ::increaseRed, redTextView)
        setupButton(R.id.buttonIncreaseblue, true, ::increaseBlue, blueTextView)
        setupButton(R.id.buttonDecreaseblue, false, ::increaseBlue, blueTextView)
        setupButton(R.id.buttonIncreasegreen, true, ::increaseGreen, greenTextView)
        setupButton(R.id.buttonDecreasegreen, false, ::increaseGreen, greenTextView)
        setupButton(R.id.buttonIncreaseyellow, true, ::increaseYellow, greenTextView)
        setupButton(R.id.buttonDecreaseyellow, false, ::increaseYellow, greenTextView)
        setupButton(R.id.buttonIncreasepurple, true, ::increasePurple, greenTextView)
        setupButton(R.id.buttonDecreasepurple, false, ::increasePurple, greenTextView)
        updateColorSquare(colorSquare)

        val button = findViewById<Button>(R.id.colorPicker)
        button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupButton(buttonId: Int, isIncrease: Boolean, colorFunction: (Boolean) -> Unit, textView: TextView) {
        findViewById<Button>(buttonId).setOnClickListener {
            colorFunction(isIncrease)
            updateColorText(textView, isIncrease)
        }
    }

    private fun increaseRed(isIncrease: Boolean) {
        red = changeColorValue(red, isIncrease)
        updateColorSquare(findViewById(R.id.colorSquare))
        updateColorText(findViewById<TextView>(R.id.textView), isIncrease)
    }

    private fun increaseBlue(isIncrease: Boolean) {
        blue = changeColorValue(blue, isIncrease)
        updateColorSquare(findViewById(R.id.colorSquare))
        updateColorText(findViewById<TextView>(R.id.textViewblue), isIncrease)
    }

    private fun increaseGreen(isIncrease: Boolean) {
        green = changeColorValue(green, isIncrease)
        updateColorSquare(findViewById(R.id.colorSquare))
        updateColorText(findViewById<TextView>(R.id.textViewgreen), isIncrease)
    }
    private fun increaseYellow(isIncrease: Boolean) {
        yellow = changeColorValue(yellow, isIncrease)
        updateColorSquare(findViewById(R.id.colorSquare))
        updateColorText(findViewById<TextView>(R.id.textViewyellow), isIncrease)
    }
    private fun increasePurple(isIncrease: Boolean) {
        purple = changeColorValue(purple, isIncrease)
        updateColorSquare(findViewById(R.id.colorSquare))
        updateColorText(findViewById<TextView>(R.id.textViewpurple), isIncrease)
    }

    private fun changeColorValue(colorValue: Int, isIncrease: Boolean): Int {
        return (colorValue + if (isIncrease) 10 else -10).coerceIn(0, 255)
    }

    private fun updateColorText(textView: TextView, isIncrease: Boolean) {
        val colorValue = when (textView.id) {
            R.id.textView -> (red/2.55).toInt()
            R.id.textViewblue -> (blue/2.55).toInt()
            R.id.textViewgreen -> (green/2.55).toInt()
            R.id.textViewyellow -> (yellow/2.55).toInt()
            R.id.textViewpurple -> (purple/2.55).toInt()
            else -> 0
        }
        textView.text = "${getColorName(textView.id)} - ${colorValue}%"
    }

    private fun getColorName(textViewId: Int): String {
        return when (textViewId) {
            R.id.textView -> "Red"
            R.id.textViewblue -> "Blue"
            R.id.textViewgreen -> "Green"
            R.id.textViewyellow -> "Yellow"
            R.id.textViewpurple -> "Purple"
            else -> ""
        }
    }

    private fun updateColorSquare(colorSquare: View) {
        val mixedColor = Color.rgb(minOf(red+yellow+purple,255), minOf(green+yellow,255), minOf(blue+purple,255))
        (colorSquare.background as? GradientDrawable)?.setColor(mixedColor)
    }
}
