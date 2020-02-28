package com.example.projectmanager.data.repositories.firebase

import com.example.projectmanager.data.interfaces.repositories.IProjectRefRepository
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class FBProjectRefRepoImpl :
    IProjectRefRepository {

    override val listener: IProjectRefRepository.IListener = Listener()

    companion object {
        const val COLLECTION_ROOT = "project_references"
        const val COLLECTION_REFERENCES = "references"
    }

    private val db = FirebaseFirestore.getInstance()

    override fun create(uid: String): Single<String> {
        return Single.create { emitter ->
            db.collection(COLLECTION_ROOT)
                .document(uid)
                .set(mapOf<String, Any>())
                .addOnSuccessListener {
                    emitter.onSuccess(uid)
                }.addOnFailureListener {
                    emitter.onError(it)
                }
        }
    }

    override fun add(uid: String, reference: String) = Completable.create { emitter ->
        db.collection(COLLECTION_ROOT).document(uid).collection(COLLECTION_REFERENCES)
            .document(reference)
            .set(mapOf<String, Any>())
            .addOnSuccessListener {
                emitter.onComplete()
            }.addOnFailureListener {
                emitter.onError(it)
            }
    }

    override fun remove(uid: String, reference: String) = Completable.create { emitter ->
        db.collection(COLLECTION_ROOT).document(uid).collection(COLLECTION_REFERENCES)
            .document(reference)
            .delete()
            .addOnSuccessListener {
                emitter.onComplete()
            }.addOnFailureListener {
                emitter.onError(it)
            }
    }

    override fun getById(uid: String) : Single<List<String>> {
        return Single.create { emitter ->
            db.collection(COLLECTION_ROOT)
                .document(uid).collection(COLLECTION_REFERENCES)
                .get()
                .addOnSuccessListener{ documents ->
                    var keys = mutableListOf<String>()

                    for (document in documents)
                        keys.add(document.id)

                    emitter.onSuccess(keys)
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }
    }

    inner class Listener : IProjectRefRepository.IListener {

        override fun getById(uid: String): Observable<List<String>> {
            return Observable.create { emitter ->
                val registration = db.collection(COLLECTION_ROOT)
                    .document(uid).collection(COLLECTION_REFERENCES)
                    .addSnapshotListener { snapshot, exception ->
                        if (exception != null)
                            emitter.onError(exception)

                        if (snapshot != null && !snapshot.isEmpty) {

                            var keys = mutableListOf<String>()

                            for (document in snapshot.documents)
                                keys.add(document.id)

                            emitter.onNext(keys)
                        }
                    }

                emitter.setCancellable { registration.remove() }
            }
        }
    }

}