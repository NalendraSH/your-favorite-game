package id.nanz.yourfavoritegame.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.nanz.yourfavoritegame.core.data.source.local.entity.GameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {

    @Query("SELECT * FROM games")
    fun getGames(): Flow<List<GameEntity>>

    @Query("SELECT * FROM games WHERE isFavorite = 1")
    fun getFavoriteGame(): Flow<List<GameEntity>>

    @Query("SELECT isFavorite FROM games WHERE gameId = :gameId LIMIT 1")
    fun selectFavoriteById(gameId: Int): Flow<List<Boolean>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(game: List<GameEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateFavoriteGame(game: GameEntity)

}