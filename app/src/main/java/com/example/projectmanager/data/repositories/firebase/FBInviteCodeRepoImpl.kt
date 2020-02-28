package com.example.projectmanager.data.repositories.firebase

import android.util.Log
import com.example.projectmanager.data.entities.InviteCodeEntity
import com.example.projectmanager.data.interfaces.repositories.IInviteCodeRepository
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable
import io.reactivex.Single

class FBInviteCodeRepoImpl :
    IInviteCodeRepository {

    companion object {
        const val COLLECTION_ROOT = "invite_codes"
    }

    private val db = FirebaseFirestore.getInstance()

    override fun create(code: InviteCodeEntity): Single<String> {
        return Single.create { emitter ->
            db.collection(COLLECTION_ROOT).add(code)
                .addOnSuccessListener {
                    Log.d("Invite", "Success")
                    emitter.onSuccess(it.id)
                }.addOnFailureListener {
                    emitter.onError(it)
                }
        }
    }


    override fun delete(id: String): Completable {
        return Completable.create { emitter ->
            db.collection(COLLECTION_ROOT)
                .document(id)
                .delete()
                .addOnSuccessListener {
                    emitter.onComplete()
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }
    }

    override fun getById(id: String): Single<InviteCodeEntity> {
        return Single.create { emitter ->
            db.collection(COLLECTION_ROOT)
                .document(id)
                .get()
                .addOnSuccessListener {
                    if (!it.exists()) {
                        emitter.onError(Exception("Entity not found."))
                    } else {
                        val code = it.toObject(InviteCodeEntity::class.java)!!
                        code.id = it.id
                        emitter.onSuccess(code)
                    }
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }
    }

    override fun getWhereEqual(field: String, value: String): Single<InviteCodeEntity> {
        return Single.create { emitter ->
            db.collection(COLLECTION_ROOT)
            .whereEqualTo(field, value)
            .get()
            .addOnSuccessListener {
                val code = it.documents[0].toObject(InviteCodeEntity::class.java)
                emitter.onSuccess(code!!)
            }
            .addOnFailureListener {
                emitter.onError(it)
            }
        }
    }
}