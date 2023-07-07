package com.example.aquatrack.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.aquatrack.data.tank.Tank
import com.example.aquatrack.data.tank.TankDao

@Database(entities = [Tank::class], version = 1, exportSchema = false)
abstract class AquaTrackDatabase : RoomDatabase() {

    abstract fun tankDao(): TankDao

    companion object {
        @Volatile
        private var instance: AquaTrackDatabase? = null

        fun getDatabase(context: Context): AquaTrackDatabase {
            return instance?: synchronized(this) {
                Room
                    .databaseBuilder(context, AquaTrackDatabase::class.java, "tank_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also{ instance = it }
            }
        }

    }

}