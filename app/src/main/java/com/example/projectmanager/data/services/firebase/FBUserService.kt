package com.example.projectmanager.data.services.firebase

import com.example.projectmanager.data.entities.UserEntity
import com.example.projectmanager.data.interfaces.repositories.IUserRepository
import com.example.projectmanager.data.interfaces.services.IUserService

class FBUserService (
    private val repository: IUserRepository
) : IUserService {

    override val listeners: IUserService.IListener = Listener()

    override fun create(user: UserEntity) = repository.create(user)

    override fun update(user: UserEntity) = repository.update(user)

    override fun delete(id: String) = repository.delete(id)

    override fun getById(id: String) = repository.getById(id)

    inner class Listener : IUserService.IListener {
        override fun getById(id: String) = repository.listeners.getById(id)
    }
}