package com.example.projectmanager.data.repositories.firebase

import com.example.projectmanager.data.entities.UserEntity
import com.example.projectmanager.data.interfaces.IUserRepository
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable
import io.reactivex.Single

class FBUserRepository : IUserRepository {

    private val COLLECTION_PATH = "users"

    private val db = FirebaseFirestore.getInstance()

    override fun create(user: UserEntity) = Completable.create { emitter ->
        db.collection(COLLECTION_PATH)
            .document(user.id)
            .set(user)
            .addOnSuccessListener {
                emitter.onComplete()
            }.addOnFailureListener {
                emitter.onError(it)
            }
    }

    override fun getById(id: String): Single<UserEntity> {
        return Single.create { emitter ->
            db.collection(COLLECTION_PATH)
                .document(id)
                .get()
                .addOnSuccessListener {
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
                .get()
                .addOnSuccessListener {
                    val users = it.toObjects(UserEntity::class.java)
                    emitter.onSuccess(users!!)
                }.addOnFailureListener {
                    emitter.onError(it)
                }
        }
    }
}