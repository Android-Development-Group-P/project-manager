package com.example.projectmanager.data.repositories.firebase

import com.example.projectmanager.data.entities.ProjectEntity
import com.example.projectmanager.data.interfaces.IProjectRepository
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable
import io.reactivex.Single

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

    override fun addUserToProject(projectId: String, userId: String) = Completable.create { emitter ->
        db.collection(COLLECTION_PATH).document(projectId)
            .update("members", FieldValue.arrayUnion(userId))
            .addOnSuccessListener {
                emitter.onComplete()
            }
            .addOnFailureListener { e ->
                emitter.onError(throw Exception("project.addUserToProject: ${e.message}"))
            }
    }

    override fun removerUserFromProject(projectId: String, userId: String) = Completable.create { emitter ->

        db.collection(COLLECTION_PATH).document(projectId)
            .update("members", FieldValue.arrayRemove(userId))
            .addOnSuccessListener {
                emitter.onComplete()
            }
            .addOnFailureListener { e ->
                emitter.onError(throw Exception("project.removeUserFromProject: ${e.message}"))
            }
    }

    override fun changeStatusOnProject(status: Boolean, projectId: String) = Completable.create { emitter ->

        db.collection(COLLECTION_PATH).document(projectId)
            .update("archived", status)
            .addOnSuccessListener {
                emitter.onComplete()
            }
            .addOnFailureListener { e ->
                emitter.onError(throw Exception("project.changeStatusOnProject: ${e.message}"))
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