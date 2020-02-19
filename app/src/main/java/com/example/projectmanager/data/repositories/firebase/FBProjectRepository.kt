package com.example.projectmanager.data.repositories.firebase

import com.example.projectmanager.data.entities.ProjectEntity
import com.example.projectmanager.data.interfaces.IProjectRepository
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable
import io.reactivex.Single

class FBProjectRepository : IProjectRepository {

    private val COLLECTION_PATH = "projects"

    val db = FirebaseFirestore.getInstance()

    override fun create(project: ProjectEntity) = Completable.create { emitter ->
        db.collection(COLLECTION_PATH).add(project).addOnSuccessListener {
            emitter.onComplete()
        }.addOnFailureListener {
            emitter.onError(it)
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
}