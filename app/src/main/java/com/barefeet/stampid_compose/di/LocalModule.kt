package com.barefeet.stampid_compose.di

import android.content.Context
import com.barefeet.stampid_compose.screens.onboard.OnboardingManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    fun provideOnboardManager(@ApplicationContext context: Context): OnboardingManager {
        return OnboardingManager(context)
    }
}