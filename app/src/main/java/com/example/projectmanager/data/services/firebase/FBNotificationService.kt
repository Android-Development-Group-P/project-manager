package com.example.projectmanager.data.services.firebase

import android.util.Log
import com.example.projectmanager.data.entities.IssueEntity
import com.example.projectmanager.data.entities.NotificationEntity
import com.example.projectmanager.data.entities.ProjectEntity
import com.example.projectmanager.data.interfaces.repositories.IIssueRepository
import com.example.projectmanager.data.interfaces.repositories.IProjectRepository
import com.example.projectmanager.data.interfaces.services.INotificationService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FBNotificationService(
    private val projectRepository: IProjectRepository,
    private val issueRepository: IIssueRepository
) : INotificationService {
    override fun getNotificationsByUser(user: String): Single<List<NotificationEntity>> {

        var issueList = mutableListOf<IssueEntity>()

        return issueRepository.getAllIssuesByAssignedUser(user)
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { issues ->
                    var projectIdList = mutableListOf<String>()
                    for (issue in issues) {
                        projectIdList.add(issue.project!!)
                    }
                    issueList.addAll(issues)
                    projectRepository.getByIdList(projectIdList)
                }
                .map { projects ->
                    mapNotifications(issueList, projects)
                }
    }

    private fun mapNotifications(issues: List<IssueEntity>, projects: List<ProjectEntity>) : List<NotificationEntity> {
        var notifications = mutableListOf<NotificationEntity>()
        for (issue in issues) {
            var project = projects.find { it.id == issue.project }
            notifications.add(NotificationEntity(issue.title, project!!.title))
        }
        return notifications
    }
}

