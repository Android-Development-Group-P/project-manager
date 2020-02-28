package com.example.projectmanager.data.interfaces.repositories

import com.example.projectmanager.data.entities.InviteCodeEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface IInviteCodeRepository {
    fun create(code: InviteCodeEntity): Single<String>
    fun delete(id: String): Completable
    fun getById(id: String) : Single<InviteCodeEntity>
    fun getWhereEqual(field: String, value: String) : Single<InviteCodeEntity>
}