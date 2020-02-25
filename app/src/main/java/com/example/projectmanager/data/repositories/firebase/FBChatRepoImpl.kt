package com.example.projectmanager.data.repositories.firebase

import android.util.Log
import com.example.projectmanager.Models.ChatMessage
import com.example.projectmanager.Models.Project
import com.example.projectmanager.data.entities.ChatMessageEntity
import com.example.projectmanager.data.interfaces.IChatRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class FBChatRepoImpl : IChatRepository {

    private val CHANNELS_PATH = "chatChannels"
    private val MESSAGES_PATH = "chatMessages"

    private val db = FirebaseFirestore.getInstance()

    override fun create(channelId: String, chatMessage: ChatMessageEntity): Single<Boolean> {
        return Single.create { emitter ->

            db.collection(CHANNELS_PATH).document(channelId).collection(MESSAGES_PATH)
                .add(chatMessage)
                .addOnSuccessListener {
                    emitter.onSuccess(true)
                }
                .addOnFailureListener { e ->
                    emitter.onError(e)
                }
        }
    }

    override fun delete(channelId: String, messageId: String): Single<Boolean> {
        return Single.create { emitter ->

            db.collection(CHANNELS_PATH).document(channelId).collection(MESSAGES_PATH).document(messageId)
                .delete()
                .addOnSuccessListener {
                    emitter.onSuccess(true)
                }
                .addOnFailureListener { e ->
                    emitter.onError(e)
                }
        }
    }

    /*
    override fun getChannel(issueId: String?, projectId: String?): Single<Boolean> {
        return Single.create { emitter ->

            lateinit var source: String
            lateinit var sourceId: String

            when (issueId != null) {
                true -> source = "issue"; sourceId = issueId
                false -> source = "project"; sourceId = projectId
            }


        }
    }
    */

    /*
    override fun addMessageListener(channelId: String, onListen: (List<ChatMessageEntity>) -> Unit ): ListenerRegistration {

        return db.collection(CHANNELS_PATH).document(channelId).collection(MESSAGES_PATH)
            .orderBy("createdAt").limitToLast(20)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    //emitter.onError(Exception("chatMessage.getChannel: ${e.message}"))
                    return@addSnapshotListener
                }
                val messages = mutableListOf<ChatMessageEntity>()
                if (!snapshot!!.isEmpty) {
                    snapshot!!.documents.forEach { message ->
                        messages.add(message.toObject(ChatMessageEntity::class.java)!!)

                    }
                }
                onListen(messages)
            }
    }
    */


    override fun getAll(channelId: String) : Single<List<ChatMessageEntity>> {
        return Single.create { emitter ->
            db.collection(CHANNELS_PATH).document(channelId).collection(MESSAGES_PATH)
                .get()
                .addOnSuccessListener {
                    val messages = it.toObjects(ChatMessageEntity::class.java)
                    emitter.onSuccess(messages)
                }
                .addOnFailureListener { exception ->
                    emitter.onError(exception)
                }
        }
    }

    override fun getAllBySection(channelId: String, limit: Int, offset: Int) : Single<List<ChatMessageEntity>> {
        return Single.create { emitter ->
            db.collection(CHANNELS_PATH).document(channelId).collection(MESSAGES_PATH)
                .orderBy("createdAt")
                .limit(limit as Long * offset)
                .get()
                .addOnSuccessListener {
                    val messages = it.toObjects(ChatMessageEntity::class.java)
                    emitter.onSuccess(messages)
                }
                .addOnFailureListener { exception ->
                    emitter.onError(exception)
                }
        }
    }

    override fun addMessageListener(channelId: String): Observable<ChatMessageEntity> {
        return Observable.create { emitter ->
            val registration = db.collection(CHANNELS_PATH).document(channelId).collection(MESSAGES_PATH)
                .orderBy("createdAt").limit(1)
                .addSnapshotListener { snapshot, exception ->
                    if (exception != null) {
                        emitter.onError(exception)
                    } else if (snapshot != null && !snapshot.isEmpty) {

                        val message = snapshot.documents[0].toObject(ChatMessageEntity::class.java)
                        emitter.onNext(message!!)
                        }
                    }
            emitter.setCancellable { registration.remove() }
        }
    }
        /*db.collection("chatMessages").document(source)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    emitter.onError(Exception("chatMessage.getChannel: ${e.message}"))
                    return@addSnapshotListener
                }
                if (snapshot!!.exists()) {

                    Log.d("testlog", "Current data: ${snapshot.data}")
                } else {
                    Log.d("testlog", "Current data: null")
                }
                emitter.onComplete()
            }
        */

}
