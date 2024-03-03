package id.nanz.yourfavoritegame.core.data.source.remote

import id.nanz.yourfavoritegame.core.data.source.remote.network.ApiResponse
import id.nanz.yourfavoritegame.core.data.source.remote.network.ApiService
import id.nanz.yourfavoritegame.core.data.source.remote.response.DetailGameResponse
import id.nanz.yourfavoritegame.core.data.source.remote.response.GameResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

class RemoteDataSource(private val apiService: ApiService, private val defaultDispatcher: CoroutineDispatcher) {

    suspend fun getGames(): Flow<ApiResponse<List<GameResponse>>> {
        return flow {
            try {
                val response = apiService.getGames()
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Timber.e("RemoteDataSource return exception: $e")
            }
        }.flowOn(defaultDispatcher)
    }

    suspend fun searchGame(query: String): Flow<ApiResponse<List<GameResponse>>> {
        return flow {
            try {
                val response = apiService.searchGame(query = query)
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Timber.e("RemoteDataSource return exception: $e")
            }
        }.flowOn(defaultDispatcher)
    }

    suspend fun getDetailGame(gameId: Int): Flow<ApiResponse<DetailGameResponse>> {
        return flow {
            try {
                val response = apiService.getDetailGame(gameId = gameId)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Timber.e("RemoteDataSource return exception: $e")
            }
        }.flowOn(defaultDispatcher)
    }

}