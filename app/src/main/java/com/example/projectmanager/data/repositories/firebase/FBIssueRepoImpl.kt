package com.example.projectmanager.data.repositories.firebase

import androidx.lifecycle.LiveData
import com.example.projectmanager.data.entities.IssueEntity
import com.example.projectmanager.data.interfaces.repositories.IIssueRepository
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Single

class FBIssueRepoImpl :
    IIssueRepository {

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
                    issue?.id = issueId
                    emitter.onSuccess(issue!!)
                }
                .addOnFailureListener { e ->
                    emitter.onError(e)
                }
        }
    }

    override fun getAllIssuesForProject(projectId: String): Single<List<IssueEntity>> {
        return Single.create {emitter ->
            db.collection(COLLECTION_PATH).whereEqualTo("project", projectId).get()
                .addOnSuccessListener {documents ->
                    val issuesList = mutableListOf<IssueEntity>()

                    for (document in documents) {
                        val issue = document.toObject(IssueEntity::class.java)
                        issue.id = document.id
                        issuesList.add(issue)
                    }

                    emitter.onSuccess(issuesList)

                    //emitter.onSuccess(documents.toObjects(IssueEntity::class.java))
                }
                .addOnFailureListener {
                    emitter.onError(Exception("error with db"))
                }
        }
    }

    override fun getAllIssuesForProjectByStatus(
        projectId: String,
        status: String
    ): Single<List<IssueEntity>> {
        return Single.create {emitter ->
            db.collection(COLLECTION_PATH)
                .whereEqualTo("project", projectId)
                .whereEqualTo("status", status)
                .get()
                .addOnSuccessListener {documents ->
                    val issuesList = mutableListOf<IssueEntity>()

                    for (document in documents) {
                        val issue = document.toObject(IssueEntity::class.java)
                        issue.id = document.id
                        issuesList.add(issue)
                    }

                    emitter.onSuccess(issuesList)

                    //emitter.onSuccess(documents.toObjects(IssueEntity::class.java))
                }
                .addOnFailureListener {
                    emitter.onError(Exception("error with db"))
                }
        }
    }

    override fun getAllIssuesByAssignedUser(assignedUser: String) : Single<List<IssueEntity>> {
        return Single.create {emitter ->
            db.collection(COLLECTION_PATH)
                .whereEqualTo("assigned_user", assignedUser).get()
                .addOnSuccessListener { documents ->
                    val issueList = mutableListOf<IssueEntity>()
                    for (document in documents) {
                        val issue = document.toObject(IssueEntity::class.java)
                        issue.id = document.id
                        issueList.add(issue)
                    }
                    emitter.onSuccess(issueList)
                }
                .addOnFailureListener {
                    emitter.onError(Exception("error with db"))
                }
        }
    }

    override fun updateIssue(issue: IssueEntity, issueId: String): Single<Boolean> {

        return Single.create {emitter ->
            db.collection(COLLECTION_PATH).document(issueId)
                .set(issue)
                .addOnSuccessListener {
                    emitter.onSuccess(true)
                }
                .addOnFailureListener { e ->
                    emitter.onError(Exception("error updating document"))
                }
        }
    }

    override fun deleteIssue(issueId: String): Single<Boolean> {
        return Single.create {emitter ->
            db.collection(COLLECTION_PATH).document(issueId)
                .delete()
                .addOnSuccessListener {
                    emitter.onSuccess(true)
                }
                .addOnFailureListener { e ->
                    emitter.onError(Exception("error deleting issue"))
                }
        }
    }
}