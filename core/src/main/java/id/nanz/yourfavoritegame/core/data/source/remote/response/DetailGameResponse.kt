package id.nanz.yourfavoritegame.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DetailGameResponse(
    val id: Int = 1,
    val name: String = "",
    val released: String = "",
    @SerializedName("background_image")
    val backgroundImage: String = "",
    val rating: Double = 0.0,
    @SerializedName("description")
    val descriptionHtml: String = "",
    @SerializedName("description_raw")
    val descriptionRaw: String = ""
)
