package com.example.projectmanager.data.interfaces

import com.example.projectmanager.data.entities.IssueEntity
import com.example.projectmanager.data.entities.ProjectEntity
import io.reactivex.Completable
import io.reactivex.Single

interface IIssueRepository {

    fun create(issue: IssueEntity) : Single<Boolean>

    fun getIssue(issueId: String) : Single<IssueEntity>

    //fun getAllIssuesForProject(projectId: String) : Single<List<IssueEntity>>

    //fun updateIssue(issue: IssueEntity, issueId: String) : Single<Boolean>

    //fun deleteIssue(issueId: String) : Single<Boolean>
}