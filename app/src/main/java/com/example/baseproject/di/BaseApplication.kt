package com.example.baseproject.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.baseproject.model.local.SCHOOLS_DB
import com.example.baseproject.model.local.SchoolsDao
import com.example.baseproject.model.local.SchoolsDatabase
import com.example.baseproject.model.local.SchoolsLocalDataSource
import com.example.baseproject.model.local.SchoolsLocalDataSourceImpl
import com.example.baseproject.model.local.SchoolsSatDao
import com.example.baseproject.model.remote.ApiService
import com.example.baseproject.model.remote.SchoolsRemoteDataSourceImpl
import com.example.baseproject.network.Repository
import com.example.baseproject.network.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Base application class
 */
@HiltAndroidApp
class BaseApplication : Application()

/**
 * App Module to provide dependencies
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(ApiService.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideRepository(
        schoolsLocalDataSourceImpl: SchoolsLocalDataSourceImpl,
        remoteDataSource: SchoolsRemoteDataSourceImpl
    ): Repository =
        RepositoryImpl(schoolsLocalDataSourceImpl, remoteDataSource)

    @Provides
    @Singleton
    fun createDatabaseClient(@ApplicationContext appContext: Context): SchoolsDatabase =
        Room.databaseBuilder(
            appContext,
            SchoolsDatabase::class.java,
            SCHOOLS_DB
        ).build()


    @Provides
    @Singleton
    fun createSchoolsDaoClient(database: SchoolsDatabase): SchoolsDao = database.schoolsDao()

    @Provides
    @Singleton
    fun createSchoolsSatDaoClient(database: SchoolsDatabase): SchoolsSatDao =
        database.schoolsSatDao()

    @Provides
    @Singleton
    fun provideSchoolsLocalDataSource(schoolsDao: SchoolsDao, schoolsSatDao: SchoolsSatDao): SchoolsLocalDataSource =
        SchoolsLocalDataSourceImpl(schoolsDao, schoolsSatDao)

    @Provides
    @Singleton
    fun provideRemoteDataSource(apiService: ApiService): SchoolsRemoteDataSourceImpl =
        SchoolsRemoteDataSourceImpl(apiService = apiService)

}