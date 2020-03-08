package com.example.projectmanager.data.interfaces.repositories

import com.example.projectmanager.data.entities.UserEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface IUserRepository {

    val listeners: IListener

    fun create(user: UserEntity) : Single<String>
    fun update(user: UserEntity) : Completable
    fun delete(id: String) : Completable
    fun getById(id: String) : Single<UserEntity>
    fun getAll(): Single<List<UserEntity>>

    fun getAllById(ids: List<String>) : Single<List<UserEntity>>

    interface IListener {
        fun getById(id: String): Observable<UserEntity>
    }
}