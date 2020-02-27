package com.example.projectmanager.data.interfaces.services

import com.example.projectmanager.data.entities.UserEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface IUserService {

    val listeners: IListener

    fun create(user: UserEntity) : Single<String>
    fun update(user: UserEntity) : Completable
    fun delete(id: String) : Completable
    fun getById(id: String) : Single<UserEntity>

    interface IListener {
        fun getById(id: String): Observable<UserEntity>
    }
}