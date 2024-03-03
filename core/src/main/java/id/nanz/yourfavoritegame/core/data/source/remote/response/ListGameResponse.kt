package id.nanz.yourfavoritegame.core.data.source.remote.response

import androidx.annotation.Keep

@Keep
data class ListGameResponse(
    val results: List<GameResponse> = listOf()
)