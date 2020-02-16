package com.example.projectmanager.Utilites

import android.util.Log
import com.example.projectmanager.Interfaces.DatabaseProvider
import com.example.projectmanager.Models.*
import com.google.firebase.firestore.*

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
            .addOnFailureListener { e ->
                Log.w(TAG, "Error writing document", e)
                callback(false, "error creating document")
            }
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

    /*
     * Project functions
     */
    override fun createNewProject(
        project: Project,
        callback: (isSuccessful: Boolean, documentId: String?, error: String?) -> Unit
    ) {
        db.collection("projects")
            .add(project)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
                callback(true, documentReference.id, null)
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
                callback(false, null, "error adding document")
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
                        callback(false, "error adding document")
                    }
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
                callback(false, "error adding document")
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
                callback(false, "error updating status")
            }
    }

    /**
     * Issue functions
     */
    override fun createIssue(
        issue: Issue,
        callback: (isSuccessful: Boolean, error: String?) -> Unit
    ) {
        db.collection("issues")
            .add(issue)
            .addOnSuccessListener {
                callback(true, null)
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
                callback(false, "Error creating issue")
            }
    }

    override fun getIssue(
        issueId: String,
        callback: (isSuccessful: Boolean, issue: Issue?, error: String?) -> Unit
    ) {
        db.collection("issue").document(issueId)
            .get()
            .addOnSuccessListener { issue ->
                callback(true,  issue.data as Issue, null)
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error fetching document", e)
                callback(false, null,"Error fetching issue")

            }


    }

    override fun getAllIssuesForProjectListener(
        projectId: String,
        callback: (isSuccessful: Boolean, issue: ArrayList<Issue>?, error: String?) -> Unit
    ) {
        db.collection("issues").whereEqualTo("project", projectId).addSnapshotListener { documents, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            } else {
                val issues = ArrayList<Issue>()
                for (document in documents!!) {
                    issues.add(document.data as Issue)
                }
                callback(true, issues, null)
            }
        }
    }

    override fun updateIssue(
        issue: Issue,
        issueId: String,
        callback: (isSuccessful: Boolean, error: String?) -> Unit
    ) {
        db.collection("issues").document(issueId)
            .set(issue)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully written!")
                callback(true, null)
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error writing document", e)
                callback(false, "error updating document")
            }
    }

    override fun deleteIssue(
        issueId: String,
        callback: (isSuccessful: Boolean, error: String?) -> Unit
    ) {
        db.collection("issues").document(issueId)
            .delete()
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully deleted!")
                callback(true, null)
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error deleting document", e)
                callback(false, "Error deleting issue")
            }
    }

    /*
     * Chat functions
     */
    override fun createChatMessage(
        chatMessage: ChatMessage,
        callback: (isSuccessful: Boolean, error: String?) -> Unit
    ) {
        db.collection( "chatMessages")
            .add(chatMessage)
            .addOnSuccessListener {
                Log.d(TAG, "Message posted!")
                callback(true, null)
            }
            .addOnFailureListener {e ->
                Log.w(TAG, "Error adding message", e)
                callback(false, "Error adding message")
            }
    }

    override fun deleteChatMessage(
        messageId: String,
        callback: (isSuccessful: Boolean, error: String?) -> Unit
    ) {

        db.collection("chatMessages").document(messageId)
            .delete()
            .addOnSuccessListener {
                Log.d(TAG, "message deleted")
                callback(true, null)
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error deleting document", e)
                callback(false, "Error deleting message")
            }

    }

    override fun getChatMessageWithListener(
        issueId: String?,
        projectId: String?,
        callback: (isSuccessful: Boolean, error: String?) -> Unit
    ) {

        lateinit var source : String

        when (issueId != null) {
            true -> source = "issue"
            false -> source = "project"
        }
        db.collection("chatMessages").document(source)
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


}