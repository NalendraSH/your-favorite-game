package id.nanz.yourfavoritegame.core.data.source.remote.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GameResponse(
    val id: Int = 1,
    val name: String = "",
    val released: String? = "",
    @SerializedName("background_image")
    val backgroundImage: String? = "",
    val rating: Double = 0.0
)
