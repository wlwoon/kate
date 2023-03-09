package com.wlwoon.kate

import android.app.Application
import com.drake.net.NetConfig
import com.drake.net.interceptor.LogRecordInterceptor
import com.drake.net.okhttp.setConverter
import com.drake.net.okhttp.setDebug
import com.drake.net.okhttp.trustSSLCertificate
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class App: Application() {

    override fun onCreate() {
        super.onCreate()


        val okHttpClientBuilder = OkHttpClient.Builder()
            .setDebug(BuildConfig.DEBUG)
            .trustSSLCertificate()
            .readTimeout(300,TimeUnit.SECONDS)
            .connectTimeout(300,TimeUnit.SECONDS)
            .writeTimeout(300,TimeUnit.SECONDS)
            .addInterceptor(LogRecordInterceptor(BuildConfig.DEBUG))


        NetConfig.initialize("https://github.com/liangjingkanji/Net/", this,okHttpClientBuilder)
    }
}