package id.nanz.yourfavoritegame.core.data.source.local

import id.nanz.yourfavoritegame.core.data.source.local.entity.GameEntity
import id.nanz.yourfavoritegame.core.data.source.local.room.GameDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val gameDao: GameDao) {

    fun getGames(): Flow<List<GameEntity>> = gameDao.getGames()

    fun getFavoriteGame(): Flow<List<GameEntity>> = gameDao.getFavoriteGame()

    suspend fun insertGame(gameList: List<GameEntity>) = gameDao.insertGame(gameList)

    fun setFavoriteGame(game: GameEntity, newState: Boolean) {
        game.isFavorite = newState
        gameDao.updateFavoriteGame(game)
    }

    fun getIsSetFavorite(gameId: Int) = gameDao.selectFavoriteById(gameId)

}