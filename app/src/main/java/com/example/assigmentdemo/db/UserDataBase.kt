package com.example.assigmentdemo.db

import android.content.Context
import androidx.room.*
import com.example.assigmentdemo.utils.Converters

@Database(entities = [UserEnitity::class],version = 1)
@TypeConverters(Converters::class)
abstract class UserDataBase : RoomDatabase() {
    abstract val userDao:UserDao

    companion object {

        @Volatile
        private var INSTANCE: UserDataBase? = null

        fun getInstance(context: Context): UserDataBase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserDataBase::class.java,
                        "user_database"
                    )
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}