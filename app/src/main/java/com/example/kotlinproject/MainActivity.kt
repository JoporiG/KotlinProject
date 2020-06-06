package com.example.kotlinproject

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_exchanging.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var Values:Array<CheckBox> = arrayOf(usd, rub, gbp, eur, kzt, uah, pln, amd, byn)
        convert.setOnClickListener{

            var kol:Int = 0
            var values:Array<String> = arrayOf("", "")
            for(i in Values){
                if(i.isChecked){
                    if(kol == 0) {
                        val mSettings = getSharedPreferences("strings.xml", Context.MODE_PRIVATE)
                        val editor: SharedPreferences.Editor = mSettings.edit()
                        editor.putString("value1", i.text.toString());
                        editor.apply();
                    } else if(kol == 1){
                        val mSettings = getSharedPreferences("strings.xml", Context.MODE_PRIVATE)
                        val editor: SharedPreferences.Editor = mSettings.edit()
                        editor.putString("value2", i.text.toString());
                        editor.apply();
                    }
                    kol++
                }
            }
            if(kol == 2){
                val myIntent = Intent(this, Exchanging::class.java)
                startActivity(myIntent)
            } else {
                val toast = Toast.makeText(
                    applicationContext,
                    "Должно быть 2 элемента", Toast.LENGTH_SHORT
                )
                toast.show()
            }
        }
    }
}