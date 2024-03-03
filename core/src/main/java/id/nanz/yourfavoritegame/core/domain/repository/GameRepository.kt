package id.nanz.yourfavoritegame.core.domain.repository

import id.nanz.yourfavoritegame.core.data.Resource
import id.nanz.yourfavoritegame.core.domain.model.DetailGame
import id.nanz.yourfavoritegame.core.domain.model.Game
import kotlinx.coroutines.flow.Flow

interface GameRepository {

    fun getGames(): Flow<Resource<List<Game>>>

    fun searchGame(query: String): Flow<Resource<List<Game>>>

    fun getDetailGame(gameId: Int): Flow<Resource<DetailGame>>

    fun getFavoriteGame(): Flow<List<Game>>

    fun setFavoriteGame(game: Game, state: Boolean)

    fun getIsSetFavorite(gameId: Int): Flow<Boolean>

}