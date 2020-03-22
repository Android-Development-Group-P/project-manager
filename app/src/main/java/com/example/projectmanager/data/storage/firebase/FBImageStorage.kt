package com.example.projectmanager.data.storage.firebase

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.example.projectmanager.data.interfaces.IImageStorage
import com.google.firebase.storage.FirebaseStorage
import io.reactivex.Completable
import io.reactivex.Single
import java.io.File

class FBImageStorage : IImageStorage {

    private val storage = FirebaseStorage.getInstance()

    override fun upload(path: String, name: String, data: ByteArray) = Completable.create { emitter ->
        val ref = storage.reference.child("$path/$name")
        val task = ref.putBytes(data)

        task.addOnSuccessListener {
            emitter.onComplete()
        }.addOnFailureListener {
            emitter.onError(it)
        }
    }

    override fun upload(path: String, name: String, file: File) = Completable.create { emitter ->
        val ref = storage.reference.child("$path/$name")
        val task = ref.putFile(Uri.fromFile(file))

        task.addOnSuccessListener {
            emitter.onComplete()
        }.addOnFailureListener {
            emitter.onError(it)
        }
    }

    override fun retrieve(path: String, name: String): Single<Bitmap> {
        return Single.create { emitter ->
            val ref = storage.reference.child("$path+$name")
            val task = ref.getBytes(Long.MAX_VALUE)

            task.addOnSuccessListener { bytes ->
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                emitter.onSuccess(bitmap)
            }.addOnFailureListener {
                emitter.onError(it)
            }
        }
    }


}