package com.example.projectmanager.data.repositories.firebase

import com.example.projectmanager.data.entities.ProjectEntity
import com.example.projectmanager.data.interfaces.repositories.IProjectRepository
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class FBProjectRepoImpl :
    IProjectRepository {

    override val listeners: IProjectRepository.Listener = Listener()

    companion object {
        const val COLLECTION_ROOT = "projects"
    }

    private val db = FirebaseFirestore.getInstance()

    override fun create(project: ProjectEntity): Single<String> {
        return Single.create { emitter ->
            db.collection(COLLECTION_ROOT).add(project).addOnSuccessListener {
                emitter.onSuccess(it.id)
            }.addOnFailureListener {
                emitter.onError(it)
            }
        }
    }

    override fun update(project: ProjectEntity) = Completable.create { emitter ->
        db.collection(COLLECTION_ROOT).document(project.id!!)
            .set(project).addOnSuccessListener {
                emitter.onComplete()
            }.addOnFailureListener {
                emitter.onError(it)
            }
    }

    override fun delete(id: String) = Completable.create { emitter ->
        db.collection(COLLECTION_ROOT).document(id)
            .delete().addOnSuccessListener {
                emitter.onComplete()
            }.addOnFailureListener {
                emitter.onError(it)
            }
    }

    override fun getById(id: String): Single<ProjectEntity> {
        return Single.create {emitter ->
            db.collection(COLLECTION_ROOT).document(id)
                .get()
                .addOnSuccessListener { document ->
                    if (!document.exists()) {
                        emitter.onError(Exception("Entity not found"))
                    } else {
                        val project = document.toObject(ProjectEntity::class.java)!!
                        project.id = document.id
                        emitter.onSuccess(project)
                    }
                }
                .addOnFailureListener { exception ->
                    emitter.onError(exception)
                }
        }
    }

    override fun getAll() : Single<List<ProjectEntity>> {
        return Single.create { emitter ->
            db.collection(COLLECTION_ROOT)
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


    inner class Listener : IProjectRepository.Listener {
        override fun getById(id: String): Observable<ProjectEntity> {
            return Observable.create { emitter ->
                val registration = db.collection(COLLECTION_ROOT)
                    .document(id)
                    .addSnapshotListener { snapshot, exception ->
                        if (exception != null)
                            emitter.onError(exception)

                        if (snapshot != null && !snapshot.exists()) {
                            val project = snapshot.toObject(ProjectEntity::class.java)!!
                            project.id = id
                            emitter.onNext(project)
                        }
                    }

                emitter.setCancellable { registration.remove() }
            }
        }

    }
}