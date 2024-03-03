package id.nanz.yourfavoritegame.core.presentation

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.load(imageUrl: String) {
    Glide.with(this.context)
        .load(imageUrl)
        .into(this)
}