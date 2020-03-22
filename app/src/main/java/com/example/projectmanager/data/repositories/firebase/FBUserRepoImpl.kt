package com.example.projectmanager.data.repositories.firebase

import android.annotation.SuppressLint
import android.util.Log
import com.example.projectmanager.data.entities.ProjectEntity
import com.example.projectmanager.data.entities.UserEntity
import com.example.projectmanager.data.interfaces.repositories.IUserRepository
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class FBUserRepoImpl :
    IUserRepository {

    override val listeners: IUserRepository.IListener = Listener()

    companion object {
        const val COLLECTION_ROOT = "users"
    }

    private val db = FirebaseFirestore.getInstance()

    override fun create(user: UserEntity) : Single<String> {
        return Single.create { emitter ->
            db.collection(COLLECTION_ROOT)
                .document(user.id!!)
                .set(user).addOnSuccessListener {
                    emitter.onSuccess(user.id!!)
                }.addOnFailureListener {
                    emitter.onError(it)
                }
        }
    }

    override fun update(user: UserEntity) = Completable.create { emitter ->
        db.collection(COLLECTION_ROOT)
            .document(user.id!!)
            .set(user).addOnSuccessListener {
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

    override fun getById(id: String): Single<UserEntity> {
        return Single.create { emitter ->
            db.collection(COLLECTION_ROOT)
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
            db.collection(COLLECTION_ROOT)
                .get().addOnSuccessListener {
                    val users = it.toObjects(UserEntity::class.java)
                    emitter.onSuccess(users)
                }.addOnFailureListener {
                    emitter.onError(it)
                }
        }
    }

    override fun getAllById(ids: List<String>): Single<List<UserEntity>> {
        return Observable.fromIterable(ids)
            .flatMapSingle { id -> getById(id) }
            .toList()
    }

    inner class Listener : IUserRepository.IListener {
        override fun getById(id: String): Observable<UserEntity> {
            return Observable.create { emitter ->
                val registration = db.collection(COLLECTION_ROOT)
                    .document(id)
                    .addSnapshotListener { snapshot, exception ->
                        if (exception != null)
                            emitter.onError(exception)

                        if (snapshot != null && snapshot.exists()) {
                            val user = snapshot.toObject(UserEntity::class.java)!!
                            user.id = id
                            emitter.onNext(user)
                        }
                    }

                emitter.setCancellable { registration.remove() }
            }
        }
    }
}