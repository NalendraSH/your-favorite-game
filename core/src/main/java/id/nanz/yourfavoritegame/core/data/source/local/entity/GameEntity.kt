package id.nanz.yourfavoritegame.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class GameEntity(
    @PrimaryKey
    @ColumnInfo(name = "gameId")
    val gameId: Int = 1,

    @ColumnInfo(name = "name")
    val name: String = "",

    @ColumnInfo(name = "released")
    val released: String = "",

    @ColumnInfo(name = "backgroundImage")
    val backgroundImage: String = "",

    @ColumnInfo(name = "rating")
    val rating: Double = 0.0,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
)
