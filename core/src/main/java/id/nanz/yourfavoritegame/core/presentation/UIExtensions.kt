package id.nanz.yourfavoritegame.core.presentation

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun ImageView.load(imageUrl: String) {
    Glide.with(this.context)
        .load(imageUrl)
        .into(this)
}