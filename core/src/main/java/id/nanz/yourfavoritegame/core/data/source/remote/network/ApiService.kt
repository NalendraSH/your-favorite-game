package id.nanz.yourfavoritegame.core.data.source.remote.network

import id.nanz.yourfavoritegame.core.BuildConfig
import id.nanz.yourfavoritegame.core.data.source.remote.response.DetailGameResponse
import id.nanz.yourfavoritegame.core.data.source.remote.response.ListGameResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("games")
    suspend fun getGames(
        @Query("key") accessToken: String = BuildConfig.ACCESS_TOKEN,
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 10
    ): ListGameResponse

    @GET("games")
    suspend fun searchGame(
        @Query("key") accessToken: String = BuildConfig.ACCESS_TOKEN,
        @Query("search") query: String,
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 10
    ): ListGameResponse

    @GET("games/{gameId}")
    suspend fun getDetailGame(
        @Path("gameId") gameId: Int,
        @Query("key") accessToken: String = BuildConfig.ACCESS_TOKEN
    ): DetailGameResponse

}