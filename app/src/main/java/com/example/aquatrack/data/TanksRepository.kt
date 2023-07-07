import com.example.aquatrack.data.tank.Tank
import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, update, delete, and retrieve of [Item] from a given data source.
 */
interface TanksRepository {
    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllItemsStream(): Flow<List<Tank>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getItemStream(id: Int): Flow<Tank?>

    /**
     * Insert item in the data source
     */
    suspend fun insertItem(item: Tank)

    /**
     * Delete item from the data source
     */
    suspend fun deleteItem(item: Tank)

    /**
     * Update item in the data source
     */
    suspend fun updateItem(item: Tank)
}

