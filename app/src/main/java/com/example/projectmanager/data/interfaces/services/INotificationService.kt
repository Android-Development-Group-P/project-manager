package com.example.projectmanager.data.interfaces.services

import com.example.projectmanager.data.entities.NotificationEntity
import io.reactivex.Single

interface INotificationService {
    fun getNotificationsByUser(user: String) : Single<List<NotificationEntity>>
}