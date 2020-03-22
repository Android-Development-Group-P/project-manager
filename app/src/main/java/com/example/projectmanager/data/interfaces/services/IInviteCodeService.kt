package com.example.projectmanager.data.interfaces.services

import com.example.projectmanager.data.entities.InviteCodeEntity
import io.reactivex.Completable
import io.reactivex.Single

interface IInviteCodeService {
    fun create(code: InviteCodeEntity): Single<String>
    fun delete(id: String): Completable
    fun getById(id: String): Single<InviteCodeEntity>
    fun getByProjectId(projectId: String): Single<InviteCodeEntity>
}