package com.example.projectmanager

import android.app.Application
import com.example.projectmanager.data.factories.*
import com.example.projectmanager.data.providers.firebase.FBSessionImpl

import com.example.projectmanager.data.interfaces.*
import com.example.projectmanager.data.repositories.firebase.*
import com.example.projectmanager.data.interfaces.repositories.IProjectRepository
import com.example.projectmanager.data.interfaces.repositories.IUserRepository
import com.example.projectmanager.data.interfaces.repositories.*
import com.example.projectmanager.data.interfaces.services.IInviteCodeService
import com.example.projectmanager.data.interfaces.services.INotificationService
import com.example.projectmanager.data.interfaces.services.IProjectService
import com.example.projectmanager.data.interfaces.services.IUserService
import com.example.projectmanager.data.repositories.firebase.FBChatRepoImpl
import com.example.projectmanager.data.repositories.firebase.FBIssueRepoImpl
import com.example.projectmanager.data.repositories.firebase.FBProjectRepoImpl
import com.example.projectmanager.data.repositories.firebase.FBUserRepoImpl
import com.example.projectmanager.data.services.firebase.FBInviteCodeService
import com.example.projectmanager.data.services.firebase.FBNotificationService
import com.example.projectmanager.data.services.firebase.FBProjectService
import com.example.projectmanager.data.services.firebase.FBUserService
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

        /** Repository layer initialization */
        bind<IAccountRepository>() with singleton { FBAccountRepoImpl() }
        bind<IUserRepository>() with singleton { FBUserRepoImpl() }
        bind<IProjectRepository>() with singleton { FBProjectRepoImpl() }
        bind<IProjectRefRepository>() with singleton { FBProjectRefRepoImpl() }
        bind<IInviteCodeRepository>() with singleton { FBInviteCodeRepoImpl() }
        bind<IIssueRepository>() with singleton { FBIssueRepoImpl() }
        bind<IChatRepository>() with singleton { FBChatRepoImpl() }

        /** Service layer initialization */
        bind<IProjectService>() with singleton { FBProjectService(instance(), instance()) }
        bind<IInviteCodeService>() with singleton { FBInviteCodeService(instance()) }
        bind<IUserService>() with singleton { FBUserService(instance()) }
        bind<INotificationService>() with singleton { FBNotificationService(instance(), instance()) }

        /** Storage layer initialization */
        bind<IImageStorage>() with singleton { FBImageStorage() }

        /** Session layer initialization */
        bind<SessionProvider>() with singleton { FBSessionImpl(instance(), instance()) }

        /** ViewModelFactories initialization */
        bind() from provider { MainViewModelFactory(instance()) }
        bind() from provider { CreateProjectViewModelFactory(instance(), instance(), instance()) }
        bind() from provider { JoinProjectViewModelFactory(instance(), instance(), instance()) }
        bind() from provider { CreateIssueViewModelFactory(instance(), instance()) }
        bind() from provider { IssueInfoViewModelFactory(instance()) }
        bind() from provider { IssuesViewModelFactory(instance(), instance()) }
        bind() from provider { StartProjectsViewModelFactory(instance(), instance()) }
        bind() from provider { UpdateIssueViewModelFactory(instance()) }
        bind() from provider { RegisterViewModelFactory(instance(), instance(), instance(), instance()) }
        bind() from provider { LoginViewModelFactory(instance(), instance(), instance()) }
        bind() from provider { UserCreationViewModelFactory(instance(), instance()) }
        bind() from provider { ChatViewModelFactory(instance(), instance()) }
        bind() from provider { StartNotificationViewModelFactory(instance(), instance ())}
        bind() from provider { ProjectViewModelFactory(instance()) }
    }
}