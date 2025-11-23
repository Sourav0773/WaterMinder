package com.example.waterminder.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.waterminder.db.dao.UserDAO
import com.example.waterminder.db.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDAO
}
