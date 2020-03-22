package com.example.projectmanager.data.interfaces.repositories

import com.example.projectmanager.data.entities.ChatMessageEntity
import io.reactivex.Observable
import io.reactivex.Single

interface IChatRepository {

    val listener: IListener

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

    /**
     * Retrieve all messages in a specific channel
     *
     * @param channelId The ID of the channel
     * @return A "reactivex" "Single" object
     */
    fun getAll(channelId: String) : Single<List<ChatMessageEntity>>

    /**
     * Retreive all messages in a specific section (pagination)
     *
     * @param channelId The ID of the channel
     * @param limit Limit how many messages to fetch
     * @param offset The offset to use when fetching
     * @return A "reactivex" "Single" object
     */
    fun getAllBySection(channelId: String, limit: Long, offset: Long = 1) : Single<List<ChatMessageEntity>>


    interface IListener {

        /**
         * Adds a message listener for a specific channel
         *
         * @param channelId The ID of the specific chat channel
         * @return A "reactivex" "Observable" object
         */
        fun getMessageById(channelId: String) : Observable<ChatMessageEntity>
    }
}
