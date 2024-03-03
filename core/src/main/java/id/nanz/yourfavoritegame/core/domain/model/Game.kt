package id.nanz.yourfavoritegame.core.domain.model

data class Game(
    val gameId: Int = 1,
    val name: String = "",
    val released: String = "",
    val backgroundImage: String = "",
    val rating: Double = 0.0,
    val isFavorite: Boolean = false
)
