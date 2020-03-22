package com.example.projectmanager.data.interfaces.repositories

import com.example.projectmanager.data.entities.IssueEntity
import io.reactivex.Single

interface IIssueRepository {

    /**
     * Create a new issue to the database
     *
     * @param code The issue to save to the database
     * @return A "reactivex" "Single" object
     */
    fun create(issue: IssueEntity) : Single<Boolean>

    /**
     * Retrieve an issue from the database
     *
     * @param issueId The ID of the issue to fetch
     * @return A "reactivex" "Single" object
     */
    fun getIssue(issueId: String) : Single<IssueEntity>

    /**
     * Retrieve all issues for a specific project from the database
     *
     * @param projectId The ID of the project to fetch issues from
     * @return A "reactivex" "Single" object
     */
    fun getAllIssuesForProject(projectId: String) : Single<List<IssueEntity>>

    /**
     * Retrieve all issues for a specific project from the database by a status
     *
     * @param projectId The ID of the project to fetch issues from
     * @param status The status
     * @return A "reactivex" "Single" object
     */
    fun getAllIssuesForProjectByStatus(projectId: String, status: String) : Single<List<IssueEntity>>

    /**
     * Retrieve all issues for a specific project from the database by an assigned user
     *
     * @param assignedUser The ID of the assignedUser to fetch issues via
     * @return A "reactivex" "Single" object
     */
    fun getAllIssuesByAssignedUser(assignedUser: String) : Single<List<IssueEntity>>

    /**
     * Update an issue from the database
     *
     * @param issue The new issue
     * @param issueId The ID of the specific issue to update
     * @return A "reactivex" "Single" object
     */
    fun updateIssue(issue: IssueEntity, issueId: String) : Single<Boolean>

    /**
     * Delete an issue from the database
     *
     * @param issueId The ID of the issue to fetch
     * @return A "reactivex" "Single" object
     */
    fun deleteIssue(issueId: String) : Single<Boolean>
}