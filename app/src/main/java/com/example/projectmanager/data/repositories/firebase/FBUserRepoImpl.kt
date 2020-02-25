package com.example.projectmanager.data.repositories.firebase

import android.util.Log
import com.example.projectmanager.data.entities.UserEntity
import com.example.projectmanager.data.interfaces.IUserRepository
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.Single

class FBUserRepoImpl : IUserRepository {

    companion object {
        const val COLLECTION_PATH = "users"
    }

    private val db = FirebaseFirestore.getInstance()

    override fun create(user: UserEntity) : Single<String> {
        return Single.create { emitter ->
            db.collection(COLLECTION_PATH)
                .document(user.id!!)
                .set(user).addOnSuccessListener {
                    emitter.onSuccess(user.id!!)
                }.addOnFailureListener {
                    emitter.onError(it)
                }
        }
    }

    override fun update(user: UserEntity) = Completable.create { emitter ->
        db.collection(COLLECTION_PATH)
            .document(user.id!!)
            .set(user).addOnSuccessListener {
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

    override fun getById(id: String): Single<UserEntity> {
        return Single.create { emitter ->
            db.collection(COLLECTION_PATH)
                .document(id)
                .get().addOnSuccessListener {
                    val user = it.toObject(UserEntity::class.java)
                    emitter.onSuccess(user!!)
                }.addOnFailureListener {
                    emitter.onError(it)
                }
        }
    }

    override fun getAll(): Single<List<UserEntity>> {
        return Single.create { emitter ->
            db.collection(COLLECTION_PATH)
                .get().addOnSuccessListener {
                    val users = it.toObjects(UserEntity::class.java)
                    emitter.onSuccess(users!!)
                }.addOnFailureListener {
                    emitter.onError(it)
                }
        }
    }

    override fun subscribe(id: String): Observable<UserEntity> {
        return Observable.create { emitter ->
            val registration = db.collection(COLLECTION_PATH).document(id)
                .addSnapshotListener { snapshot, exception ->
                    if (exception != null)
                        emitter.onError(exception)

                    if (snapshot != null && snapshot.exists()) {
                        val user = snapshot.toObject(UserEntity::class.java)
                        emitter.onNext(user!!)
                    }
                }


            emitter.setCancellable { registration.remove() }
        }
    }

    override fun subscribe(id: String, field: IUserRepository.Fields): Observable<Any> {
        var fieldPath: String? = null

        fieldPath = when (field) {
            IUserRepository.Fields.Name -> {
                "displayName"
            }
            IUserRepository.Fields.Projects -> {
                "projects"
            }
        }

        return Observable.create { emitter ->
            val registration = db.collection(COLLECTION_PATH).document("$id/$fieldPath")
                .addSnapshotListener { snapshot, exception ->
                    if (exception != null)
                        emitter.onError(exception)

                    if (snapshot != null && snapshot.exists()) {
                        val any = snapshot.toObject(Any::class.java)
                        emitter.onNext(any!!)
                    }
                }


            emitter.setCancellable { registration.remove() }
        }
    }
}