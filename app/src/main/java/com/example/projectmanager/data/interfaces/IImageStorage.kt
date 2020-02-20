package com.example.projectmanager.data.interfaces

import android.graphics.Bitmap
import io.reactivex.Completable
import io.reactivex.Single
import java.io.File

interface IImageStorage {
    fun upload(path: String, name: String, data: ByteArray) : Completable
    fun upload(path: String, name: String, file: File) : Completable
    fun retrieve(path: String, name: String) : Single<Bitmap>
}