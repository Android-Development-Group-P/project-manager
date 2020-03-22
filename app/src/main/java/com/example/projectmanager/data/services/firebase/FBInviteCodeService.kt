package com.example.projectmanager.data.services.firebase

import com.example.projectmanager.data.entities.InviteCodeEntity
import com.example.projectmanager.data.interfaces.repositories.IInviteCodeRepository
import com.example.projectmanager.data.interfaces.services.IInviteCodeService
import io.reactivex.Completable

class FBInviteCodeService (
    private val repository: IInviteCodeRepository
) : IInviteCodeService {

    override fun create(code: InviteCodeEntity) = repository.create(code)

    override fun delete(id: String): Completable = repository.delete(id)

    override fun getById(id: String) = repository.getById(id)

    override fun getByProjectId(projectId: String) = repository.getWhereEqual("projectId", projectId)
}