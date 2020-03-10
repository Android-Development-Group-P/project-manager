package com.example.projectmanager.data.services.firebase

import android.util.Log
import com.example.projectmanager.data.entities.IssueEntity
import com.example.projectmanager.data.entities.NotificationEntity
import com.example.projectmanager.data.interfaces.repositories.IIssueRepository
import com.example.projectmanager.data.interfaces.repositories.IProjectRepository
import com.example.projectmanager.data.interfaces.services.INotificationService
import io.reactivex.Single

class FBNotificationService(
    private val projectRepository: IProjectRepository,
    private val issueRepository: IIssueRepository
) : INotificationService {
    override fun getNotificationsByUser(user: String): Single<List<NotificationEntity>> {
        return Single.create { emitter ->
            issueRepository.getAllIssuesByAssignedUser(user)
                .doOnSuccess { issues ->
                    lateinit var notifications: MutableList<NotificationEntity>
                    var issueCounter = 0
                    for (issue in issues) {
                        projectRepository.getById(issue.project!!)
                            .doOnSuccess { project ->
                                notifications.add(NotificationEntity(issue.title, project.title))
                                issueCounter++
                            }
                            .doOnError { error ->
                                emitter.onError(error)
                            }

                        if (issueCounter == issues.size) {
                            emitter.onSuccess(notifications)
                        }
                    }
                }
        }
    }
}

