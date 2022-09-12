package com.idrisfallout.colorconverter

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        val title_string = findViewById<TextView>(R.id.lblTitle)
        val red = findViewById<TextView>(R.id.textView2)
        val green = findViewById<TextView>(R.id.textView3)
        val blue = findViewById<TextView>(R.id.textView4)

        val red_input = findViewById<EditText>(R.id.red_input)
        val green_input = findViewById<EditText>(R.id.green_input)
        val blue_input = findViewById<EditText>(R.id.blue_input)
        val hex_input = findViewById<EditText>(R.id.hex_input)

        val color_display = findViewById<View>(R.id.view)

        var color_output = findViewById<TextView>(R.id.output_color)

        val convert = findViewById<Button>(R.id.cmdconvert)

        val toggle: ToggleButton = findViewById(R.id.toggleButton)

        toggle.setOnCheckedChangeListener { _, isChecked ->
//            Toast.makeText(this, if(isChecked) "Convert To RGB" else "Convert To HEX", Toast.LENGTH_SHORT).show()
//            check if the toggle button is ON
//            True -> conversion to RGB, False -> conversion to HEX
            if (isChecked){
                // change labels
                title_string.setText("Convertion to RGB")
                red.setText("")
                green.setText("Enter Hex")
                blue.setText("")

                red_input.setVisibility(View.INVISIBLE)
                green_input.setVisibility(View.INVISIBLE)
                hex_input.setVisibility(View.VISIBLE)
                hex_input.setText(hex_input.text.toString())
//                set input text color to matrix-green
                hex_input.setTextColor(Color.parseColor("#2955C4"))
                blue_input.setVisibility(View.INVISIBLE)

//              Validation - the input box accept not move than 7 characters(the total length of a hex color code)
                val maxLength = 7
                hex_input.setFilters(arrayOf<InputFilter>(LengthFilter(maxLength)))


            }else{
                title_string.setText("Convertion to Hexadecimal")
                red.setText("Red")
                green.setText("Green")
                blue.setText("Blue")
                // change inputBoxes
                hex_input.setVisibility(View.INVISIBLE)
                red_input.setVisibility(View.VISIBLE)
                red_input.setTextColor(Color.RED)
                green_input.setVisibility(View.VISIBLE)
                green_input.setTextColor(Color.GREEN)
                blue_input.setVisibility(View.VISIBLE)
                blue_input.setTextColor(Color.BLUE)

            }
        }
//        On convert button pressed
        convert.setOnClickListener {
//            If the toggle button is checked --> conversion to rgb else conversion to HEX
            if (toggle.isChecked){
                Toast.makeText(this, "converting to rgb", Toast.LENGTH_SHORT).show()
                try {
                    val r = hex_input.text.toString().substring(1,3)
                    val g = hex_input.text.toString().substring(3,5)
                    val b = hex_input.text.toString().substring(5,7)
                    color_output.setText(Integer.decode("0x" + r.toString()).toString() + ", " + Integer.decode("0x" + g.toString().toString()) + ", " + Integer.decode("0x" + b.toString()).toString())
                    convert_to_rgb(Integer.decode("0x" + r.toString()).toString(), Integer.decode("0x" + g.toString()).toString(), Integer.decode("0x" + b.toString()).toString())

                }catch (e: Exception){
                    Toast.makeText(this, "converting to rgb", Toast.LENGTH_SHORT).show()
                }

            }else{
                if (findViewById<EditText>(R.id.red_input).text.toString() == "" || findViewById<EditText>(R.id.green_input).text.toString() == "" || findViewById<EditText>(R.id.blue_input).text.toString() == ""){
                    return@setOnClickListener
                }

                var r = red_input.text.toString().toInt()
                var g = green_input.text.toString().toInt()
                var b = blue_input.text.toString().toInt()

                if (r <= 255){
                    do_convertion(r, g, b)
                }else{
                    red_input.setText("255")
                    do_convertion(255, g, b)
                }

                if (g <= 255){
                    do_convertion(r, g, b)
                }else{
                    green_input.setText("255")
                    do_convertion(r, 255, b)
                }

                if (b <= 255){
                    do_convertion(r, g, b)
                }else{
                    blue_input.setText("255")
                    do_convertion(r, g, 255)
                }
            }
        }
    }

    private fun convert_to_rgb(r: String, g: String, b: String) {
        try {
            val rangi = findViewById<TextView>(R.id.output_color)
            findViewById<View>(R.id.view).setBackgroundColor(Color.rgb(r.toInt(), g.toInt(), b.toInt()))

            if ((r.toInt() > 200 && g.toInt() > 200) || (r.toInt() > 200 && b.toInt() > 200) || (g.toInt() > 200 && b.toInt() > 200)) {
                rangi.setTextColor(Color.BLACK)
            }else{
                rangi.setTextColor(Color.WHITE)
            }

        }catch (e: Exception){
            Toast.makeText(this, "Error Occurred", Toast.LENGTH_SHORT).show()
        }

    }

    private fun do_convertion(r: Int, g: Int, b: Int) {
        val rangi = findViewById<TextView>(R.id.output_color)
//        Toast.makeText(this, "converting to hex", Toast.LENGTH_SHORT).show()
        findViewById<View>(R.id.view).setBackgroundColor(Color.rgb(r, g, b))

        if ((r > 200 && g > 200) || (r > 200 && b > 200) || (g > 200 && b > 200)) {
            rangi.setTextColor(Color.BLACK)
        }else{
            rangi.setTextColor(Color.WHITE)
        }
        val rr = java.lang.Integer.toHexString(findViewById<EditText>(R.id.red_input).text.toString().toInt()).length
        val gg = java.lang.Integer.toHexString(findViewById<EditText>(R.id.green_input).text.toString().toInt()).length
        val bb = java.lang.Integer.toHexString(findViewById<EditText>(R.id.blue_input).text.toString().toInt()).length
        if (rr == 1 && gg > 1 && bb > 1){
            rangi.setText("#" + "0" + java.lang.Integer.toHexString(findViewById<EditText>(R.id.red_input).text.toString().toInt()) + java.lang.Integer.toHexString(findViewById<EditText>(R.id.green_input).text.toString().toInt()).toString() + java.lang.Integer.toHexString(findViewById<EditText>(R.id.blue_input).text.toString().toInt()).toString())
        }
        if (rr > 1 && gg == 1 && bb > 1){
            rangi.setText("#" + java.lang.Integer.toHexString(findViewById<EditText>(R.id.red_input).text.toString().toInt()).toString() + "0" + java.lang.Integer.toHexString(findViewById<EditText>(R.id.green_input).text.toString().toInt()) + java.lang.Integer.toHexString(findViewById<EditText>(R.id.blue_input).text.toString().toInt()).toString())
        }
        if (rr > 1 && gg > 1 && bb == 1){
            rangi.setText("#" + java.lang.Integer.toHexString(findViewById<EditText>(R.id.red_input).text.toString().toInt()).toString() + java.lang.Integer.toHexString(findViewById<EditText>(R.id.green_input).text.toString().toInt()).toString() + "0" + java.lang.Integer.toHexString(findViewById<EditText>(R.id.blue_input).text.toString().toInt()))
        }
        if (rr == 1 && gg == 1 && bb > 1){
            rangi.setText("#" + "0" + java.lang.Integer.toHexString(findViewById<EditText>(R.id.red_input).text.toString().toInt()) + "0" + java.lang.Integer.toHexString(findViewById<EditText>(R.id.green_input).text.toString().toInt()) + java.lang.Integer.toHexString(findViewById<EditText>(R.id.blue_input).text.toString().toInt()).toString())
        }
        if (rr == 1 && gg > 1 && bb == 1){
            rangi.setText("#" + "0" + java.lang.Integer.toHexString(findViewById<EditText>(R.id.red_input).text.toString().toInt()) + java.lang.Integer.toHexString(findViewById<EditText>(R.id.green_input).text.toString().toInt()).toString() + "0" + java.lang.Integer.toHexString(findViewById<EditText>(R.id.blue_input).text.toString().toInt()))
        }
        if (rr > 1 && gg == 1 && bb == 1){
            rangi.setText("#" + java.lang.Integer.toHexString(findViewById<EditText>(R.id.red_input).text.toString().toInt()).toString() + "0" + java.lang.Integer.toHexString(findViewById<EditText>(R.id.green_input).text.toString().toInt()) + "0" + java.lang.Integer.toHexString(findViewById<EditText>(R.id.blue_input).text.toString().toInt()))
        }
        if (rr == 1 && gg == 1 && bb == 1){
            rangi.setText("#" + "0" + java.lang.Integer.toHexString(findViewById<EditText>(R.id.red_input).text.toString().toInt()) + "0" + java.lang.Integer.toHexString(findViewById<EditText>(R.id.green_input).text.toString().toInt()) + "0" + java.lang.Integer.toHexString(findViewById<EditText>(R.id.blue_input).text.toString().toInt()))
        }
        if (rr > 1 && gg > 1 && bb > 1){
            rangi.setText("#" + java.lang.Integer.toHexString(findViewById<EditText>(R.id.red_input).text.toString().toInt()) + java.lang.Integer.toHexString(findViewById<EditText>(R.id.green_input).text.toString().toInt()) + java.lang.Integer.toHexString(findViewById<EditText>(R.id.blue_input).text.toString().toInt()))
        }
        if (rr == 1 && gg > 1 && bb > 1){
            rangi.setText("#" + "0" + java.lang.Integer.toHexString(findViewById<EditText>(R.id.red_input).text.toString().toInt()) + java.lang.Integer.toHexString(findViewById<EditText>(R.id.green_input).text.toString().toInt()).toString() + java.lang.Integer.toHexString(findViewById<EditText>(R.id.blue_input).text.toString().toInt()))
        }
        if (rr > 1 && gg == 1 && bb > 1){
            rangi.setText("#" + java.lang.Integer.toHexString(findViewById<EditText>(R.id.red_input).text.toString().toInt()) + "0" +java.lang.Integer.toHexString(findViewById<EditText>(R.id.green_input).text.toString().toInt()).toString() + java.lang.Integer.toHexString(findViewById<EditText>(R.id.blue_input).text.toString().toInt()))
        }


    }
}