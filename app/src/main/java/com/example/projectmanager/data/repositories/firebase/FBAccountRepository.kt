package com.example.projectmanager.data.repositories.firebase

import com.example.projectmanager.data.entities.UserEntity
import com.example.projectmanager.data.interfaces.IAccountRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable
import io.reactivex.Single

class FBAccountRepository : IAccountRepository {

    private val auth = FirebaseAuth.getInstance()

    override fun login(email: String, password: String): Single<String> {
        return Single.create { emitter ->
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (!emitter.isDisposed) {
                    if (it.isSuccessful)
                        emitter.onSuccess(auth.currentUser!!.uid)
                    else
                        emitter.onError(it.exception!!)
                }
            }
        }
    }

    override fun register(email: String, password: String) = Completable.create { emitter ->
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (!emitter.isDisposed) {
                if (it.isSuccessful)
                    emitter.onComplete()
                else
                    emitter.onError(it.exception!!)
            }
        }
    }

    override fun logout() = Completable.create { emitter ->
        auth.signOut()

        if (!emitter.isDisposed) {
            if (auth.currentUser == null)
                emitter.onComplete()
            else
                emitter.onError(throw Exception("auth.signout: currentUser not null."))
        }
    }
}