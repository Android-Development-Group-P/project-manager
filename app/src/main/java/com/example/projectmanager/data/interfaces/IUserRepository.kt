package com.example.projectmanager.data.interfaces

import com.example.projectmanager.data.entities.UserEntity
import com.google.firebase.firestore.auth.User
import io.reactivex.Completable
import io.reactivex.Single

interface IUserRepository {

    fun create(user: UserEntity) : Completable

    fun getById(id: String) : Single<UserEntity>

    fun getAll() : Single<List<UserEntity>>
}