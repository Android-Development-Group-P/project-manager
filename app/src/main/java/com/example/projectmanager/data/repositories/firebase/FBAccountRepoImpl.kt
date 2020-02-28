package com.example.projectmanager.data.repositories.firebase

import com.example.projectmanager.data.interfaces.repositories.IAccountRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import io.reactivex.Single

class FBAccountRepoImpl :
    IAccountRepository {

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

    override fun loginWithGoogleSignIn(account: GoogleSignInAccount): Single<String> {
        return Single.create { emitter ->
            val credentials = GoogleAuthProvider.getCredential(account.idToken, null)
            auth.signInWithCredential(credentials).addOnCompleteListener {
                if (it.isSuccessful)
                    emitter.onSuccess(account.id!!)
                else
                    emitter.onError(it.exception!!)
            }
        }
    }

    override fun register(email: String, password: String) : Single<String> {
        return Single.create { emitter ->
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (!emitter.isDisposed) {
                    if (it.isSuccessful)
                        emitter.onSuccess(auth.currentUser!!.uid)
                    else
                        emitter.onError(it.exception!!)
                }
            }
        }
    }
/*
    override fun logout() = Completable.create { emitter ->
        auth.signOut()

        if (!emitter.isDisposed) {
            if (auth.currentUser == null)
                emitter.onComplete()
            else
                emitter.onError(throw Exception("auth.signout: currentUser not null."))
        }
    }*/
}