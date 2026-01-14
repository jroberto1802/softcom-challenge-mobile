package com.example.softcomchallengejunior.di

import android.content.Context
import androidx.room.Room
import com.example.softcomchallengejunior.data.local.AppDatabase
import com.example.softcomchallengejunior.data.local.dao.CartDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "softcom_petshop_db"
        )
        .fallbackToDestructiveMigration() // Adicionado para permitir migrações destrutivas durante o desenvolvimento
        .build()
    }

    @Provides
    @Singleton
    fun provideCartDao(db: AppDatabase): CartDao {
        return db.cartDao()
    }
}