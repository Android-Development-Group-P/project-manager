package com.example.projectmanager

import android.app.Application
import com.example.projectmanager.data.factories.*
import com.example.projectmanager.data.providers.firebase.FBSession

import com.example.projectmanager.data.interfaces.*
import com.example.projectmanager.data.repositories.firebase.*
import com.example.projectmanager.data.interfaces.IChatRepository
import com.example.projectmanager.data.interfaces.IIssueRepository
import com.example.projectmanager.data.interfaces.IProjectRepository
import com.example.projectmanager.data.interfaces.IUserRepository
import com.example.projectmanager.data.repositories.firebase.FBChatRepository
import com.example.projectmanager.data.repositories.firebase.FBIssueRepository
import com.example.projectmanager.data.repositories.firebase.FBProjectRepository
import com.example.projectmanager.data.repositories.firebase.FBUserRepository
import com.example.projectmanager.data.storage.firebase.FBImageStorage

import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class FirebaseApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@FirebaseApplication))

        bind<IAccountRepository>() with singleton { FBAccountRepository() }
        bind<IUserRepository>() with singleton { FBUserRepository() }
        bind<IProjectRepository>() with singleton { FBProjectRepository() }
        bind<IIssueRepository>() with singleton { FBIssueRepository() }
        bind<IChatRepository>() with singleton { FBChatRepository() }

        bind<IImageStorage>() with singleton { FBImageStorage() }

        bind<SessionProvider>() with singleton { FBSession(instance()) }

        bind() from provider { CreateProjectViewModelFactory(instance()) }
        bind() from provider { CreateIssueViewModelFactory(instance()) }
        bind() from provider { IssueInfoViewModelFactory(instance()) }
        bind() from provider { ProjectViewModelFactory(instance()) }
        bind() from provider { AuthViewModelFactory(instance(), instance(), instance()) }
        bind() from provider { UserCreationViewModelFactory(instance(), instance()) }
    }
}