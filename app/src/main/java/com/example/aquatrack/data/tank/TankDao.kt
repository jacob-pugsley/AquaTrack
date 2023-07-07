package com.example.aquatrack.data.tank

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


/**
 * A database access object, which contains methods to access the tank database.
 */
@Dao
interface TankDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(tank: Tank)

    @Update
    suspend fun update(tank: Tank)

    @Delete
    suspend fun delete(tank: Tank)


    @Query("select * from tanks where id = :tankId")
    fun getTank(tankId: Int): Flow<Tank>

    @Query("select * from tanks")
    fun getAllTanks(): Flow<List<Tank>>
}