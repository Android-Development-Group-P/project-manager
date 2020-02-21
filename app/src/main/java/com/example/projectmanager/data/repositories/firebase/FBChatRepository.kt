package com.example.projectmanager.data.repositories.firebase

import android.util.Log
import com.example.projectmanager.Models.ChatMessage
import com.example.projectmanager.data.entities.ChatMessageEntity
import com.example.projectmanager.data.interfaces.IChatRepository
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable
import io.reactivex.Single

class FBChatRepository : IChatRepository {

    private val COLLECTION_PATH = "chatMessages"

    val db = FirebaseFirestore.getInstance()

    override fun create(chatMessage: ChatMessageEntity): Single<Boolean> {
        return Single.create { emitter ->

            db.collection(COLLECTION_PATH)
                .add(chatMessage)
                .addOnSuccessListener {
                    emitter.onSuccess(true)
                }
                .addOnFailureListener { e ->
                    emitter.onError(e)
                }
        }
    }

    override fun delete(messageId: String): Single<Boolean> {
        return Single.create { emitter ->

            db.collection("chatMessages").document(messageId)
                .delete()
                .addOnSuccessListener {
                    emitter.onSuccess(true)
                }
                .addOnFailureListener { e ->
                    emitter.onError(e)
                }
        }
    }

    override fun getChannel(issueId: String?, projectId: String?) = Completable.create { emitter ->
        lateinit var source : String

        when (issueId != null) {
            true -> source = "issue"
            false -> source = "project"
        }
        db.collection("chatMessages").document(source)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    emitter.onError(Exception("chatMessage.getChannel: ${e.message}"))
                    return@addSnapshotListener
                }
                if (snapshot != null && snapshot.exists()) {
                    Log.d("testlog", "Current data: ${snapshot.data}")
                } else {
                    Log.d("testlog", "Current data: null")
                }
                emitter.onComplete()
            }
    }

}