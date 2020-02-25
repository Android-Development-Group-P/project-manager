package com.example.projectmanager.data.repositories.firebase

import android.util.Log
import com.example.projectmanager.data.entities.ProjectEntity
import com.example.projectmanager.data.interfaces.IProjectRepository
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import kotlinx.coroutines.*

class FBProjectRepoImpl : IProjectRepository {

    companion object {
        const val COLLECTION_PATH = "projects"
    }

    private val db = FirebaseFirestore.getInstance()

    override fun create(project: ProjectEntity): Single<String> {
        return Single.create { emitter ->
            db.collection(COLLECTION_PATH).add(project).addOnSuccessListener {
                emitter.onSuccess(it.id)
            }.addOnFailureListener {
                emitter.onError(it)
            }
        }
    }

    override fun update(project: ProjectEntity) = Completable.create { emitter ->
        db.collection(COLLECTION_PATH).document(project.id!!)
            .set(project).addOnSuccessListener {
                emitter.onComplete()
            }.addOnFailureListener {
                emitter.onError(it)
            }
    }

    override fun delete(id: String) = Completable.create { emitter ->
        db.collection(COLLECTION_PATH).document(id)
            .delete().addOnSuccessListener {
                emitter.onComplete()
            }.addOnFailureListener {
                emitter.onError(it)
            }
    }

    override fun getById(id: String): Single<ProjectEntity> {
        return Single.create {emitter ->
            db.collection(COLLECTION_PATH).document(id)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val project = document.toObject(ProjectEntity::class.java)!!
                        project.id = document.id
                        emitter.onSuccess(project)
                    } else {
                        emitter.onError(Exception("Could not fetch project with id {$id}"))
                    }
                }
                .addOnFailureListener { exception ->
                    emitter.onError(exception)
                }
        }
    }

    override fun getAll() : Single<List<ProjectEntity>> {
        return Single.create { emitter ->
            db.collection(COLLECTION_PATH)
                .get()
                .addOnSuccessListener { documents ->
                    val projectsList = mutableListOf<ProjectEntity>()

                    for (documet in documents) {
                        val project = documet.toObject(ProjectEntity::class.java)
                        project.id = documet.id
                        projectsList.add(project)
                    }
                    emitter.onSuccess(projectsList)
                }
                .addOnFailureListener { exception ->
                    emitter.onError(exception)
                }
        }
    }


    override fun getSectionByIds(ids: List<String>): Single<List<ProjectEntity>> {
        return Observable.fromIterable(ids)
            .flatMapSingle { id -> getById(id) }
            .toList()
            .doOnError {
                Log.d("test", "$it")
            }
    }


    override fun checkIfCodeExists(code: String): Single<Boolean> {
        return Single.create {emitter ->

            db.collection(COLLECTION_PATH).whereEqualTo("code", code)
                .get()
                .addOnSuccessListener { documents ->
                    if (documents.size() == 0) {
                        emitter.onSuccess(false)
                    } else {
                        emitter.onSuccess(true)
                    }
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }
    }
}