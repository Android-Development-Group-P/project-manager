package com.example.projectmanager.data.interfaces.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface IProjectRefRepository {

    val listener: IListener

    fun create(uid: String) : Single<String>
    fun add(uid: String, reference: String) : Completable
    fun remove(uid: String, reference: String) : Completable
    fun getById(uid: String) : Single<List<String>>

    interface IListener {
        fun getById(uid: String): Observable<List<String>>
    }
}