package com.easycash.footballleague.di

import android.app.Application
import androidx.room.Room
import com.easycash.footballleague.data.local.CompetitionsDao
import com.easycash.footballleague.data.local.CompetitionsInfoDao
import com.easycash.footballleague.data.local.FootballDatabase
import com.easycash.footballleague.data.local.TeamDao
import com.easycash.footballleague.ui.teem.model.TeamsResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application, callback: FootballDatabase.Callback): FootballDatabase{
        return Room.databaseBuilder(application, FootballDatabase::class.java, "news_database")
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()
    }

    @Provides
    fun provideCompetitionsDao(db: FootballDatabase): CompetitionsDao {
        return db.competitionsDao()
    }

    @Provides
    fun provideCompetitionsInfoDao(db: FootballDatabase): CompetitionsInfoDao {
        return db.competitionsInfoDao()
    }
    @Provides
    fun provideTeamDao(db: FootballDatabase): TeamDao {
        return db.teamDao()
    }
}