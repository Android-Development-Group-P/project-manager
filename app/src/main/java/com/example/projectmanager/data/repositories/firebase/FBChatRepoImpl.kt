package com.example.projectmanager.data.repositories.firebase

import android.util.Log
import com.example.projectmanager.Models.ChatMessage
import com.example.projectmanager.Models.Project
import com.example.projectmanager.data.entities.ChatMessageEntity
import com.example.projectmanager.data.interfaces.IChatRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class FBChatRepoImpl : IChatRepository {
    override val listener: IChatRepository.IListener = Listener()

    companion object {
        const val CHANNELS_PATH = "chatChannels"
        const val MESSAGES_PATH = "chatMessages"
    }

    private val db = FirebaseFirestore.getInstance()

    override fun create(channelId: String, chatMessage: ChatMessageEntity): Single<Boolean> {
        return Single.create { emitter ->

            db.collection(CHANNELS_PATH).document(channelId).collection(MESSAGES_PATH)
                .add(chatMessage)
                .addOnSuccessListener {
                    Log.d("asd", it.toString())
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

    override fun getAllBySection(channelId: String, limit: Long, offset: Long) : Single<List<ChatMessageEntity>> {
        return Single.create { emitter ->
            db.collection(CHANNELS_PATH).document(channelId).collection(MESSAGES_PATH)
                .orderBy("createdAt")
                .limit(limit * offset)
                .get()
                .addOnSuccessListener {
                    if (it.isEmpty) {
                        emitter.onSuccess(listOf())
                    } else {
                        val messages = it.toObjects(ChatMessageEntity::class.java)
                        emitter.onSuccess(messages)
                    }
                }
                .addOnFailureListener { exception ->
                    emitter.onError(exception)
                }
        }
    }




    inner class Listener: IChatRepository.IListener {
        override fun getMessageById(channelId: String): Observable<ChatMessageEntity> {
            return Observable.create { emitter ->
                val registration = db.collection(CHANNELS_PATH).document(channelId).collection(MESSAGES_PATH)
                    .orderBy("createdAt", Query.Direction.DESCENDING)
                    .limit(1)
                    .addSnapshotListener { snapshot, exception ->
                        Log.d("123", "dffhf")
                        if (exception != null) {
                            emitter.onError(exception)
                        } else if (snapshot != null && !snapshot.isEmpty) {
                            Log.d("123", snapshot.size().toString())
                            val message = snapshot.documents.first().toObject(ChatMessageEntity::class.java)
                            emitter.onNext(message!!)
                        }
                    }
                emitter.setCancellable {
                    registration.remove()
                }
            }
        }

    }
}
