package com.example.softcomchallengejunior.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.softcomchallengejunior.data.local.dao.CartDao
import com.example.softcomchallengejunior.data.local.entities.CartItemEntity

@Database(
    entities = [CartItemEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    // O Room vai implementar essa função automaticamente
    abstract fun cartDao(): CartDao
}