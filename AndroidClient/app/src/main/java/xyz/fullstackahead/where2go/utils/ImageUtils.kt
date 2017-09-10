package xyz.fullstackahead.where2go.utils

import android.support.annotation.DrawableRes
import android.widget.ImageView
import com.bumptech.glide.Glide

fun loadImage(imageView: ImageView, @DrawableRes image: Int) {
    Glide.with(imageView).load(image).into(imageView)
}


fun loadImage(imageView: ImageView, url: String) {
    Glide.with(imageView).load(url).into(imageView)
}
