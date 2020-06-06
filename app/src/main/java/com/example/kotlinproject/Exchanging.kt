package com.example.kotlinproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View.OnTouchListener
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_exchanging.*
import java.math.BigDecimal
import java.math.RoundingMode


class Exchanging : AppCompatActivity() {
    fun getPriceToRUB(value:String):Float{
        var price:Float = 1F
        when(value){
            //ИНФОРМАЦИЯ ВЗЯТА С САЙТА https://cash.rbc.ru/converter.html 06.06.2020 12:30
            "USD" -> price = 68.6319F
            "EUR" -> price = 77.9658F
            "GBP" -> price = 86.8468F
            "UAH" -> price = 2.5797F
            "KZT" -> price = 0.1714F
            "AMD" -> price = 0.1418F
            "BYN" -> price = 28.8248F
            "PLN" -> price = 17.5283F
        }
        return price
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchanging)
        val mSettings = getSharedPreferences("strings.xml", Context.MODE_PRIVATE)
        val1.text = mSettings.getString("value1", "")!!.substringBefore(' ')
        val2.text = mSettings.getString("value2", "")!!.substringBefore(' ')
        var EditingFst:Boolean = false
        var EditingScd:Boolean = false
        val pr1 = Math.round((getPriceToRUB(val1.text.toString()) / getPriceToRUB(val2.text.toString())) * 10000.0F) / 10000.0F
        val pr2 = Math.round((getPriceToRUB(val2.text.toString()) / getPriceToRUB(val1.text.toString())) * 10000.0F) / 10000.0F
        price1.text = "1 ${val1.text} = ${pr1} ${val2.text}"
        price2.text = "1 ${val2.text} = ${pr2} ${val1.text}"
        arg1.setOnTouchListener(OnTouchListener { v, event ->
            EditingFst = true
            EditingScd = false
            false
        })
        arg2.setOnTouchListener(OnTouchListener { v, event ->
            EditingFst = false
            EditingScd = true
            false
        })
        arg1.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if(EditingFst){
                    if(arg1.text.isEmpty())
                        arg2.setText("")
                    if(arg1.text.isEmpty()){
                        error.setText("Поле не может быть пустым")
                    } else {
                        var price: Float
                        if (arg1.text.isNotEmpty() && arg1.text.toString().toFloatOrNull() != null) {
                            price = arg1.text.toString().toFloat()
                        } else
                            price = 1F
                        price *= getPriceToRUB(val1.text.toString())
                        price /= getPriceToRUB(val2.text.toString())
                        val newPrice = BigDecimal(price.toDouble()).setScale(4, RoundingMode.HALF_EVEN)
                        arg2.setText(newPrice.toString())
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {error.setText("")}
        })
        arg2.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if(EditingScd){
                    if(arg2.text.isEmpty())
                        arg1.setText("")
                    if(arg2.text.isEmpty()){
                        error.text = "Поле не может быть пустым"
                    } else {
                        var price:Float
                        if (arg2.text.isNotEmpty() && arg2.text.toString().toFloatOrNull() != null) {
                            price = arg2.text.toString().toFloat()
                        } else
                            price = 1F
                        price *= getPriceToRUB(val2.text.toString())
                        price /= getPriceToRUB(val1.text.toString())
                        val newPrice = BigDecimal(price.toDouble()).setScale(4, RoundingMode.HALF_EVEN)
                        arg1.setText(newPrice.toString())
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {if(arg2?.text != null && arg2.text.isNotEmpty()){
            }}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        back.setOnClickListener{
            val myIntent = Intent(this, MainActivity::class.java)
            startActivity(myIntent)
        }

    }
}
