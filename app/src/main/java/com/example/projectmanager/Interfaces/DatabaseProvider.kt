package com.example.projectmanager.Interfaces

import com.example.projectmanager.Models.Chat
import com.example.projectmanager.Models.Issue
import com.example.projectmanager.Models.Project
import com.example.projectmanager.Models.UserFirebase
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentReference

interface DatabaseProvider {

    /*
    all
     */
    fun getDocument(collectionName: String, documentName: String, callback: (isSuccessful: Boolean, error: String?) -> Unit)

    /*
    User
     */
    fun createUser(uid: String, user: UserFirebase, callback: (isSuccessful: Boolean, error: String?) -> Unit)

    /*
    Project
     */
    fun getUserWithListener(uid: String, callback: (isSuccessful: Boolean, error: String?) -> Unit)

    fun createNewProject(project: Project, callback: (isSuccessful: Boolean, documentId: String?, error: String?) -> Unit)

    fun addUserToProject(project: String, user: String, callback: (isSuccessful: Boolean, error: String?) -> Unit)

    fun removerUserFromProject(projectRef: String, user: String, callback: (isSuccessful: Boolean, error: String?) -> Unit)

    fun changeStatusOnProject(status: Boolean, projectRef: String, callback: (isSuccessful: Boolean, error: String?) -> Unit)

    /*
    Issue
     */
    fun createIssue(issue: Issue, callback: (isSuccessful: Boolean, error: String?) -> Unit)

    fun getIssue(issueId: String, callback: (isSuccessful: Boolean, issue: Issue?, error: String?) -> Unit)

    fun getAllIssuesForProjectListener(projectId: String, callback: (isSuccessful: Boolean, issue: List<Issue>?, error: String?) -> Unit)

    fun updateIssue(issue: Issue, issueId: String, callback: (isSuccessful: Boolean, error: String?) -> Unit)

    fun deleteIssue(issueId: String, callback: (isSuccessful: Boolean, error: String?) -> Unit)

    /*
    Chat
     */
    fun createChatMessage(chat: Chat, callback: (isSuccessful: Boolean, error: String?) -> Unit)

    fun getChatMessageListener(issueId: String?, projectId: String?, callback: (isSuccessful: Boolean, error: String?) -> Unit)

    fun deleteChatMessage(messageId: String, callback: (isSuccessful: Boolean, error: String?) -> Unit)


}