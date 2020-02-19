package com.example.projectmanager.data.repositories.firebase

import com.example.projectmanager.data.entities.UserEntity
import com.example.projectmanager.data.interfaces.IUserRepository
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Completable
import io.reactivex.Single

class FBUserRepository : IUserRepository {

    private val auth = FirebaseAuth.getInstance()

    override fun login(email: String, password: String) = Completable.create { emitter ->

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (!emitter.isDisposed) {
                if (it.isSuccessful)
                    emitter.onComplete()
                else
                    emitter.onError(it.exception!!)
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

    override fun getCurrentUser(): Single<UserEntity> {
        return Single.create { emitter ->

            if (auth.currentUser != null) {
                val user = UserEntity(
                    uid = auth.currentUser!!.uid,
                    name = auth.currentUser!!.displayName!!,
                    email = auth.currentUser!!.email!!
                )

                emitter.onSuccess(user)

            } else {
                emitter.onError(throw Exception("auth.getCurrentUser: currentUser is null."))
            }
        }
    }
}