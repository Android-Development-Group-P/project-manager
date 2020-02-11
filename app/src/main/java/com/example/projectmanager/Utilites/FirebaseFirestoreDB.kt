package com.example.projectmanager.Utilites

import android.util.Log
import com.example.projectmanager.Interfaces.DatabaseProvider
import com.example.projectmanager.Models.Issue
import com.example.projectmanager.Models.Project
import com.example.projectmanager.Models.UserFirebase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot

class FirebaseFirestoreDB : DatabaseProvider {

    private var db = FirebaseFirestore.getInstance()
    private var TAG = "test johan"

    override fun getDocument(
        collectionName: String,
        documentName: String,
        callback: (isSuccessful: Boolean, error: String?) -> Unit
    ) {
        db.collection(collectionName).document(documentName).get()
            .addOnSuccessListener { document ->
                Log.d(TAG, "hello ${document}")
                if (document.data != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    callback(true, null)
                } else {
                    Log.d(TAG, "No such document")
                    callback(false, "inget document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }

    override fun createUser(
        uid: String,
        user: UserFirebase,
        callback: (isSuccessful: Boolean, error: String?) -> Unit
    ) {
        db.collection("users").document(uid)
            .set(user)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully written!")
                callback(true, null)
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    override fun getUserWithListener(
        uid: String,
        callback: (isSuccessful: Boolean, error: String?) -> Unit
    ) {
        db.collection("users").document(uid)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    Log.d(TAG, "Current data: ${snapshot.data}")
                } else {
                    Log.d(TAG, "Current data: null")
                }
            }

    }

    override fun createNewProject(
        project: Project,
        callback: (isSuccessful: Boolean, document: DocumentReference?, error: String?) -> Unit
    ) {
        db.collection("projects")
            .add(project)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
                callback(true, documentReference, null)
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    override fun addUserToProject(
        project: String,
        user: String,
        callback: (isSuccessful: Boolean, error: String?) -> Unit
    ) {
        db.collection("projects").document(project)
            .update("members", FieldValue.arrayUnion(user))
            .addOnSuccessListener {
                db.collection("users").document(user)
                    .update("projects", FieldValue.arrayUnion(project))
                    .addOnSuccessListener {
                        callback(true, null)
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    override fun removerUserFromProject(
        projectRef: String,
        user: String,
        callback: (isSuccessful: Boolean, error: String?) -> Unit
    ) {
        db.collection("projects").document(projectRef)
            .update("members", FieldValue.arrayRemove(user))
            .addOnSuccessListener {
                db.collection("users").document(user)
                    .update("projects", FieldValue.arrayRemove(projectRef))
                    .addOnSuccessListener {
                        callback(true, null)
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    override fun changeStatusOnProject(
        status: Boolean,
        projectRef: String,
        callback: (isSuccessful: Boolean, error: String?) -> Unit
    ) {
        db.collection("projects").document(projectRef)
            .update("archived", status)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully updated!")
                callback(true, null)
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error updating document", e)
                callback(false, "error")
            }
    }

    override fun createIssue(
        projectRef: String,
        issue: Issue,
        user: String,
        callback: (isSuccessful: Boolean, error: String?) -> Unit
    ) {
        db.collection("issues")
            .add(issue)
            .addOnSuccessListener { documentReference ->
                db.collection("projects").document(projectRef)
                    .update("issues", FieldValue.arrayUnion(documentReference.id))
                    .addOnSuccessListener {
                        db.collection("users").document(user)
                            .update("issues", FieldValue.arrayUnion(documentReference.id))
                            .addOnSuccessListener {
                                callback(true, null)
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error adding document", e)
                            }
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }


                callback(true, null)
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    override fun deleteIssue(
        projectRef: String,
        issueRef: DocumentReference,
        callback: (isSuccessful: Boolean, error: String?) -> Unit
    ) {
        db.collection("projects").document(projectRef)
            .update("issues", FieldValue.arrayRemove(issueRef))
            .addOnSuccessListener {

                db.collection("users")
                    .whereArrayContains("created_issues", issueRef)
                    .get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {

                            deleteIssueFromUser(document, issueRef)

                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.w(TAG, "Error getting documents: ", exception)
                    }

                issueRef.get()
                    .addOnSuccessListener { document ->
                        if (document.data != null) {
                            Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                            callback(true, null)


                            // NOT FINISHED


                        } else {
                            Log.d(TAG, "No such document")
                            callback(false, "inget document")
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.d(TAG, "get failed with ", exception)
                    }
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    private fun deleteIssueFromUser(document: QueryDocumentSnapshot, issue: DocumentReference) {
        db.collection("users").document(document.id)
            .update("created_issues", FieldValue.arrayRemove(issue))
            .addOnSuccessListener {

            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

}