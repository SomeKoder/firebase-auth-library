package com.somekoder.library.firebase_auth.di

import com.somekoder.library.firebase_auth.usecase.*
import com.somekoder.library.firebase_auth.usecase.CreateUserEmailPasswordUseCaseImpl
import com.somekoder.library.firebase_auth.usecase.ForgotPasswordUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {

    @Provides
    @Singleton
    fun provideCreateUserEmailPasswordUseCase() : CreateUserEmailPasswordUseCase {
        return CreateUserEmailPasswordUseCaseImpl()
    }

    @Provides
    @Singleton
    fun provideForgotPasswordUseCase() : ForgotPasswordUseCase {
        return ForgotPasswordUseCaseImpl()
    }

    @Provides
    @Singleton
    fun provideLoginEmailPasswordUseCase() : LoginEmailPasswordUseCase {
        return LoginEmailPasswordUseCaseImpl()
    }

}