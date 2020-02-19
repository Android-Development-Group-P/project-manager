package com.example.projectmanager.data.repositories.firebase

import com.example.projectmanager.data.entities.IssueEntity
import com.example.projectmanager.data.interfaces.IIssueRepository
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Single

class FBIssueRepository : IIssueRepository {

    private val COLLECTION_PATH = "issues"

    val db = FirebaseFirestore.getInstance()

    override fun create(issue: IssueEntity): Single<Boolean> {
        return Single.create {emitter ->

            db.collection(COLLECTION_PATH)
                .add(issue)
                .addOnSuccessListener {
                    emitter.onSuccess(true)
                }
                .addOnFailureListener { e ->
                    emitter.onError(e)
                }
        }
    }

    override fun getIssue(issueId: String): Single<IssueEntity> {
        return Single.create {emitter ->

            db.collection(COLLECTION_PATH).document(issueId)
                .get()
                .addOnSuccessListener { document ->
                    val issue = document.toObject(IssueEntity::class.java)
                    emitter.onSuccess(issue!!)
                }
                .addOnFailureListener { e ->
                    emitter.onError(e)
                }
        }
    }
}