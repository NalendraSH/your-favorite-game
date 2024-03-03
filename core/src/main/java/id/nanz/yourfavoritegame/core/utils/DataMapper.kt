package id.nanz.yourfavoritegame.core.utils

import id.nanz.yourfavoritegame.core.data.source.local.entity.GameEntity
import id.nanz.yourfavoritegame.core.data.source.remote.response.DetailGameResponse
import id.nanz.yourfavoritegame.core.data.source.remote.response.GameResponse
import id.nanz.yourfavoritegame.core.domain.model.DetailGame
import id.nanz.yourfavoritegame.core.domain.model.Game

object DataMapper {

    fun mapResponsesToEntities(input: List<GameResponse>): List<GameEntity> {
        val gameList = ArrayList<GameEntity>()
        input.map {
            gameList.add(
                GameEntity(
                    gameId = it.id,
                    name = it.name,
                    released = it.released ?: "",
                    backgroundImage = it.backgroundImage ?: "",
                    rating = it.rating,
                    isFavorite = false
                )
            )
        }
        return gameList
    }

    fun mapResponsesToDomain(input: List<GameResponse>): List<Game> {
        val gameList = ArrayList<Game>()
        input.map {
            gameList.add(
                Game(
                    gameId = it.id,
                    name = it.name,
                    released = it.released ?: "",
                    backgroundImage = it.backgroundImage ?: "",
                    rating = it.rating,
                    isFavorite = false
                )
            )
        }
        return gameList
    }

    fun mapResponsesToDomain(input: DetailGameResponse) =
        DetailGame(
            input.id,
            input.name,
            input.released,
            input.backgroundImage,
            input.rating,
            input.descriptionHtml,
            input.descriptionRaw
        )

    fun mapEntitiesToDomain(input: List<GameEntity>): List<Game> =
        input.map {
            Game(
                gameId = it.gameId,
                name = it.name,
                released = it.released,
                backgroundImage = it.backgroundImage,
                rating = it.rating,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntity(input: Game) =
        GameEntity(
            gameId = input.gameId,
            name = input.name,
            released = input.released,
            backgroundImage = input.backgroundImage,
            rating = input.rating,
            isFavorite = input.isFavorite
        )

    fun mapDetailGameToGame(input: DetailGame) =
        Game(
            gameId = input.id,
            name = input.name,
            released = input.released,
            backgroundImage = input.backgroundImage,
            rating = input.rating,
            isFavorite = false
        )

}