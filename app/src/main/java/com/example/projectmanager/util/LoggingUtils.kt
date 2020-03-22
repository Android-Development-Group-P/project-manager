package com.example.projectmanager.util

import android.app.Activity
import android.util.Log
import com.example.projectmanager.BuildConfig

fun Activity.logd(message: String){
    if (BuildConfig.DEBUG) Log.d(this::class.java.simpleName, message)

    TODO("Lägg till detta istället \n " +
            "https://stackoverflow.com/questions/34416869/idiomatic-way-of-logging-in-kotlin")

    TODO("error handling : https://rongi.github.io/kotlin-blog/rxjava/rx/2017/08/01/error-handling-in-rxjava.html")
}

