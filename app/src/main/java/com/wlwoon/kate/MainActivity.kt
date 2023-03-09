package com.wlwoon.kate

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.drake.net.Get
import com.drake.net.Post
import com.drake.net.utils.scopeNetLife
import com.google.gson.Gson
import kotlinx.coroutines.*
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import java.io.IOException

class MainActivity : AppCompatActivity() {

    var tvTelno: TextView? = null
    var tvAns: TextView? = null

    var editText: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn).setOnClickListener {
            getTelno()
        }

        tvTelno = findViewById<TextView>(R.id.tv_telno)

        findViewById<Button>(R.id.btn_1).setOnClickListener {
            gpt()
        }

        editText = findViewById<EditText>(R.id.edt)

        tvAns = findViewById<TextView>(R.id.tv_ans)


    }

    val client = OkHttpClient()
    val builder = "http://sl.shengmaiiot.com/Api/findpwd".toHttpUrl().newBuilder()
    var midNo = 1
    private fun getCorrectNum() {
        var cardno = "1311636810"
        var telNo = "18000006901"
        var midNoStr = "000".plus(midNo.toString())
        midNo++
        telNo = telNo.replaceRange(3, 7, midNoStr.substring(midNoStr.length - 4))

        println("响应体telno：${telNo}")

        builder.addQueryParameter("cardno", cardno)
        builder.addQueryParameter("telphone", telNo)

        val request = Request
            .Builder()
            .url(builder.build())
            .build()


//        Thread{
//            val response = client.newCall(request).execute()
//            if (response.isSuccessful) {
//                val string = response.body?.string()
//
//                println("响应体txt：${string}")
//
//                val bean = Gson().fromJson(string, BaseBean::class.java)
//
//                println("响应体code：${bean.code}")
//
//                if (bean.code == 0){
//                    runOnUiThread {
//                        tvTelno?.setTextColor(Color.RED)
//                        tvTelno?.text = telNo
//                    }
//                }else if (midNo<10000){
//                    getCorrectNum()
//                }
//            }
//        }.start()
//        if (midNo%200==0) Thread.sleep(5000)
//             client.newCall(request).enqueue(object : Callback{
//                 override fun onFailure(call: Call, e: IOException) {
//                     println("响应体fail：${e}")
//                 }
//
//                 override fun onResponse(call: Call, response: Response) {
//                     val string = response.body?.string()
//
//                     println("响应体txt：${string}")
//
//                     if (!response.isSuccessful) return
//
//                     val bean = Gson().fromJson(string, BaseBean::class.java)
//
//                     println("响应体code：${bean.code}")
//
//                     if (bean.code == 0){
//                         runOnUiThread {
//                             tvTelno?.setTextColor(Color.RED)
//                             tvTelno?.text = telNo
//                         }
//                     }else if (midNo<10000){
//                         getCorrectNum()
//                     }
//                 }
//
//             })


    }


    fun getTelno() {
        var cardno = "1311636813"
        var telNo = "13900004330"
        var midNoStr = "000".plus(midNo.toString())
        midNo++
        telNo = telNo.replaceRange(3, 7, midNoStr.substring(midNoStr.length - 4))

        println("响应体telno：${telNo}")

        scopeNetLife {
            val string = Get<String>("http://sl.shengmaiiot.com/Api/findpwd") {
                param("cardno", cardno)
                param("telphone", telNo)
            }.await()
            val bean = Gson().fromJson(string, BaseBean::class.java)
            tvTelno?.text = telNo
            if (bean.code == 0) {
                tvTelno?.setTextColor(Color.RED)
            } else if (midNo < 10000) {
                getTelno()
            }

        }
    }

    fun gpt(){

        tvAns!!.text = "正在作答，请耐心等待......"
        scopeNetLife {
            val content = Post<String>("https://gpt.baixing.com") {
                json(
                    mapOf(
                        "p" to editText!!.text.toString(),
                        "k" to "TNMAYMSI"
                    )
                )
            }.await()

            val bean = Gson().fromJson(content, GptBean::class.java)
            if (bean.code == 0&&bean.message == "success"){
                tvAns!!.text = bean.data
                tvAns!!.setTextColor(Color.RED)
            }
        }
    }


}