package com.example.projectmanager.Interfaces

import com.example.projectmanager.Models.Issue
import com.example.projectmanager.Models.Project
import com.example.projectmanager.Models.UserFirebase
import com.google.firebase.firestore.DocumentReference

interface DatabaseProvider {

    fun getDocument(collectionName: String, documentName: String, callback: (isSuccessful: Boolean, error: String?) -> Unit)

    fun createUser(uid: String, user: UserFirebase, callback: (isSuccessful: Boolean, error: String?) -> Unit)

    fun getUserWithListener(uid: String, callback: (isSuccessful: Boolean, error: String?) -> Unit)

    fun createNewProject(project: Project, callback: (isSuccessful: Boolean, document:DocumentReference?, error: String?) -> Unit)

    fun addUserToProject(project: String, user: String, callback: (isSuccessful: Boolean, error: String?) -> Unit)

    fun removerUserFromProject(projectRef: String, user: String, callback: (isSuccessful: Boolean, error: String?) -> Unit)

    fun changeStatusOnProject(status: Boolean, projectRef: String, callback: (isSuccessful: Boolean, error: String?) -> Unit)

    fun createIssue(projectRef: String, issue: Issue, user: String, callback: (isSuccessful: Boolean, error: String?) -> Unit)

    fun deleteIssue(projectRef: String, issueRef: DocumentReference, callback: (isSuccessful: Boolean, error: String?) -> Unit)

}