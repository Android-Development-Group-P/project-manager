package com.example.projectmanager.data.interfaces

import com.example.projectmanager.data.entities.UserEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface IUserRepository {

    fun create(user: UserEntity) : Single<String>

    fun update(user: UserEntity) : Completable

    fun delete(id: String) : Completable

    fun getById(id: String) : Single<UserEntity>

    fun getAll() : Single<List<UserEntity>>

    fun subscribe(id: String) : Observable<UserEntity>

    fun subscribe(id: String, field: Fields) : Observable<Any>

    enum class Fields {
        Name,
        Projects,
    }
}