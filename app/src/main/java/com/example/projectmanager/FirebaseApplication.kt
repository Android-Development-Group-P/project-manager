package com.example.projectmanager

import android.app.Application
import com.example.projectmanager.data.factories.*
import com.example.projectmanager.data.providers.firebase.FBSessionImpl

import com.example.projectmanager.data.interfaces.*
import com.example.projectmanager.data.repositories.firebase.*
import com.example.projectmanager.data.interfaces.IChatRepository
import com.example.projectmanager.data.interfaces.IIssueRepository
import com.example.projectmanager.data.interfaces.IProjectRepository
import com.example.projectmanager.data.interfaces.IUserRepository
import com.example.projectmanager.data.repositories.firebase.FBChatRepoImpl
import com.example.projectmanager.data.repositories.firebase.FBIssueRepoImpl
import com.example.projectmanager.data.repositories.firebase.FBProjectRepoImpl
import com.example.projectmanager.data.repositories.firebase.FBUserRepoImpl
import com.example.projectmanager.data.storage.firebase.FBImageStorage
import com.jakewharton.threetenabp.AndroidThreeTen

import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class FirebaseApplication : Application(), KodeinAware {

    override fun onCreate() {
        AndroidThreeTen.init(this)
        super.onCreate()
    }
    override val kodein = Kodein.lazy {
        import(androidXModule(this@FirebaseApplication))

        bind<IAccountRepository>() with singleton { FBAccountRepoImpl() }
        bind<IUserRepository>() with singleton { FBUserRepoImpl() }
        bind<IProjectRepository>() with singleton { FBProjectRepoImpl() }
        bind<IIssueRepository>() with singleton { FBIssueRepoImpl() }
        bind<IChatRepository>() with singleton { FBChatRepoImpl() }

        bind<IImageStorage>() with singleton { FBImageStorage() }

        bind<SessionProvider>() with singleton { FBSessionImpl(instance(), instance()) }

        bind() from provider { CreateProjectViewModelFactory(instance(), instance(), instance()) }
        bind() from provider { CreateIssueViewModelFactory(instance(), instance()) }
        bind() from provider { IssueInfoViewModelFactory(instance()) }
        bind() from provider { IssuesViewModelFactory(instance()) }
        bind() from provider { ProjectViewModelFactory(instance()) }
        bind() from provider { StartProjectsViewModelFactory(instance(), instance()) }
        bind() from provider { UpdateIssueViewModelFactory(instance()) }
        bind() from provider { RegisterViewModelFactory(instance(), instance(), instance()) }
        bind() from provider { LoginViewModelFactory(instance(), instance(), instance()) }
        bind() from provider { UserCreationViewModelFactory(instance(), instance()) }
        bind() from provider { ChatViewModelFactory(instance(), instance()) }
    }
}