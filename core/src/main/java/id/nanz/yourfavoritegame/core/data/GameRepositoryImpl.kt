package id.nanz.yourfavoritegame.core.data

import id.nanz.yourfavoritegame.core.data.source.local.LocalDataSource
import id.nanz.yourfavoritegame.core.data.source.remote.RemoteDataSource
import id.nanz.yourfavoritegame.core.data.source.remote.network.ApiResponse
import id.nanz.yourfavoritegame.core.data.source.remote.response.DetailGameResponse
import id.nanz.yourfavoritegame.core.data.source.remote.response.GameResponse
import id.nanz.yourfavoritegame.core.domain.model.DetailGame
import id.nanz.yourfavoritegame.core.domain.model.Game
import id.nanz.yourfavoritegame.core.domain.repository.GameRepository
import id.nanz.yourfavoritegame.core.utils.AppExecutors
import id.nanz.yourfavoritegame.core.utils.DataMapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform

class GameRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): GameRepository {

    override fun getGames(): Flow<Resource<List<Game>>> =
        object : NetworkBoundResource<List<Game>, List<GameResponse>>() {
            override fun loadFromDB(): Flow<List<Game>> {
                return localDataSource.getGames().map { DataMapper.mapEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: List<Game>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<GameResponse>>> =
                remoteDataSource.getGames()

            override suspend fun saveCallResult(data: List<GameResponse>) {
                val gameList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertGame(gameList)
            }
        }.asFlow()

    override fun searchGame(query: String): Flow<Resource<List<Game>>> =
        flow {
            delay(1000)
            emit(Resource.Loading())
            when (val apiResponse = remoteDataSource.searchGame(query).first()) {
                is ApiResponse.Success -> {
                    emit(Resource.Success(DataMapper.mapResponsesToDomain(apiResponse.data)))
                }
                is ApiResponse.Empty -> {
                    emit(Resource.Success(DataMapper.mapResponsesToDomain(emptyList())))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(apiResponse.errorMessage))
                }
            }
        }

    override fun getDetailGame(gameId: Int): Flow<Resource<DetailGame>> =
        flow {
            emit(Resource.Loading())
            when (val apiResponse = remoteDataSource.getDetailGame(gameId).first()) {
                is ApiResponse.Success -> {
                    emit(Resource.Success(DataMapper.mapResponsesToDomain(apiResponse.data)))
                }
                is ApiResponse.Empty -> {
                    emit(Resource.Success(DataMapper.mapResponsesToDomain(DetailGameResponse())))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(apiResponse.errorMessage))
                }
            }
        }

    override fun getFavoriteGame(): Flow<List<Game>> {
        return localDataSource.getFavoriteGame().map { DataMapper.mapEntitiesToDomain(it) }
    }

    override fun setFavoriteGame(game: Game, state: Boolean) {
        val gameEntity = DataMapper.mapDomainToEntity(game)
        appExecutors.diskIO().execute { localDataSource.setFavoriteGame(gameEntity, state) }
    }

    override fun getIsSetFavorite(gameId: Int): Flow<Boolean> {
        return localDataSource.getIsSetFavorite(gameId).map { if (it.isNotEmpty()) it[0] else false }
    }

}