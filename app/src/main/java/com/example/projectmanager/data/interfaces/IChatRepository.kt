package com.example.projectmanager.data.interfaces

import android.util.Log
import com.example.projectmanager.Models.ChatMessage
import com.example.projectmanager.data.entities.ChatMessageEntity
import com.google.firebase.firestore.ListenerRegistration
import io.reactivex.Completable
import io.reactivex.Single

interface IChatRepository {

    /**
     * Creates a new chat message in the database
     *
     * @param chatMessage The content of the message
     * @return A "reactivex" "Single" object
     */
    fun create(channelId: String, chatMessage: ChatMessageEntity) : Single<Boolean>

    /**
     * Deletes a specific message in the database
     *
     * @param messageId The ID of the message to be deleted
     * @return A "reactivex" "Single" object
     */
    fun delete(channelId: String, messageId: String) : Single<Boolean>


    //fun getChannel(issueId: String?, projectId: String?) : Single<Boolean>


    /**
     * Adds a message listener for a specific issue or project ID
     *
     * @param issueId Optional issue ID related to the channel
     * @param projectId Optional project ID related to the channel
     * @return A ListenerRegistration
     */
    fun addMessageListener(channelId: String, onListen: (List<ChatMessageEntity>) -> Unit) : ListenerRegistration
}
