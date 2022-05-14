package com.easycash.footballleague.data.local

import androidx.room.*
import com.easycash.footballleague.di.ApplicationScope
import com.easycash.footballleague.ui.competitions.model.CompetitionsResponse
import com.easycash.footballleague.ui.competitionsInfo.model.CompetitionsInfoResponse
import com.easycash.footballleague.ui.teem.model.TeamsResponse
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Provider


@Database(entities = [CompetitionsResponse::class, CompetitionsInfoResponse::class, TeamsResponse::class], version = 1, exportSchema = false)
@TypeConverters(CompetitionListConverter::class,CurrentSeasonConverter::class,SeasonListConverter::class,WinnerConverter::class,AreaConverter::class,TeamListConverter::class)
abstract class FootballDatabase : RoomDatabase() {

    abstract fun competitionsDao(): CompetitionsDao
    abstract fun competitionsInfoDao(): CompetitionsInfoDao
    abstract fun teamDao(): TeamDao

    class Callback @Inject constructor(
        private val database: Provider<FootballDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback()

}