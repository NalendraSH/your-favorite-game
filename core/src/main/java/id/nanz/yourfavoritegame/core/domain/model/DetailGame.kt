package id.nanz.yourfavoritegame.core.domain.model

data class DetailGame(
    val id: Int = 1,
    val name: String = "",
    val released: String = "",
    val backgroundImage: String = "",
    val rating: Double = 0.0,
    val descriptionHtml: String = "",
    val descriptionRaw: String = ""
)
