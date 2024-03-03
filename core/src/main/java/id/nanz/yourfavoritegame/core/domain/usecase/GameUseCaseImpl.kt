package id.nanz.yourfavoritegame.core.domain.usecase

import id.nanz.yourfavoritegame.core.data.Resource
import id.nanz.yourfavoritegame.core.domain.model.DetailGame
import id.nanz.yourfavoritegame.core.domain.model.Game
import id.nanz.yourfavoritegame.core.domain.repository.GameRepository
import kotlinx.coroutines.flow.Flow

class GameUseCaseImpl(private val gameRepository: GameRepository): GameUseCase {

    override fun getGames(): Flow<Resource<List<Game>>> = gameRepository.getGames()

    override fun searchGame(query: String): Flow<Resource<List<Game>>> = gameRepository.searchGame(query)

    override fun getDetailGame(gameId: Int): Flow<Resource<DetailGame>> = gameRepository.getDetailGame(gameId)

    override fun getFavoriteGame(): Flow<List<Game>> = gameRepository.getFavoriteGame()

    override fun setFavoriteGame(game: Game, state: Boolean) = gameRepository.setFavoriteGame(game, state)

    override fun getIsSetFavorite(gameId: Int): Flow<Boolean> = gameRepository.getIsSetFavorite(gameId)

}