package com.example.aquatrack.data

import TanksRepository
import com.example.aquatrack.data.tank.Tank
import com.example.aquatrack.data.tank.TankDao
import kotlinx.coroutines.flow.Flow

class OfflineTanksRepository(private val itemDao: TankDao) : TanksRepository {
    override fun getAllItemsStream(): Flow<List<Tank>> = itemDao.getAllTanks()

    override fun getItemStream(id: Int): Flow<Tank?> = itemDao.getTank(id)

    override suspend fun insertItem(item: Tank) = itemDao.insert(item)

    override suspend fun deleteItem(item: Tank) = itemDao.delete(item)

    override suspend fun updateItem(item: Tank) = itemDao.update(item)

}
