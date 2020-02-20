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

class FBProjectRepository : IProjectRepository {

    private val COLLECTION_PATH = "projects"

    val db = FirebaseFirestore.getInstance()

    override fun create(project: ProjectEntity): Single<String> {
        return Single.create {emitter ->
            db.collection(COLLECTION_PATH).add(project).addOnSuccessListener {
                emitter.onSuccess(it.id)
            }.addOnFailureListener {
                emitter.onError(it)
            }
        }
    }

    override fun getAll() : Single<List<ProjectEntity>> {
        return Single.create { emitter ->
            db.collection(COLLECTION_PATH)
                .get()
                .addOnSuccessListener { result ->

                    val projects = mutableListOf<ProjectEntity>()

                    for (document in result) {
                        projects.add(document.toObject(ProjectEntity::class.java))
                    }

                    emitter.onSuccess(projects)
                }
                .addOnFailureListener { exception ->
                    emitter.onError(exception)
                }
        }
    }

    override fun getProjectsByIds(ids: List<String>): Single<List<ProjectEntity>> {
        return Observable.fromIterable(ids)
            .flatMapSingle { id -> getById(id) }
            .toList()
            .doOnError {
                Log.d("test", "test nåååå $it")
            }
    }

    override fun getById(id: String): Single<ProjectEntity> {
        return Single.create {emitter ->
            db.collection(COLLECTION_PATH).document(id)
                .get()
                .addOnSuccessListener { document ->
                    Log.d("test", "test jjjjåååå")
                    if (document.exists()) {
                        val project = document.toObject(ProjectEntity::class.java)!!
                        project.id = id
                        emitter.onSuccess(project)
                    } else {
                        Log.d("test", "test nåååå")
                        emitter.onError(Exception("test: hello"))
                    }
                }
                .addOnFailureListener { exception ->
                    emitter.onError(exception)
                }
        }
    }

    override fun addUserToProject(projectId: String, userId: String) = Completable.create { emitter ->
        db.collection(COLLECTION_PATH).document(projectId)
            .update("members", FieldValue.arrayUnion(userId))
            .addOnSuccessListener {
                emitter.onComplete()
            }
            .addOnFailureListener { e ->
                emitter.onError(Exception("project.addUserToProject: ${e.message}"))
            }
    }

    override fun removerUserFromProject(projectId: String, userId: String) = Completable.create { emitter ->

        db.collection(COLLECTION_PATH).document(projectId)
            .update("members", FieldValue.arrayRemove(userId))
            .addOnSuccessListener {
                emitter.onComplete()
            }
            .addOnFailureListener { e ->
                emitter.onError(Exception("project.removeUserFromProject: ${e.message}"))
            }
    }

    override fun changeStatusOnProject(status: Boolean, projectId: String) = Completable.create { emitter ->

        db.collection(COLLECTION_PATH).document(projectId)
            .update("archived", status)
            .addOnSuccessListener {
                emitter.onComplete()
            }
            .addOnFailureListener { e ->
                emitter.onError(Exception("project.changeStatusOnProject: ${e.message}"))
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