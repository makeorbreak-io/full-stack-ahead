package xyz.fullstackahead.where2go.ui.viewholder

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import kotlinx.android.synthetic.main.viewtag_recommendation.view.*
import xyz.fullstackahead.where2go.R

class RecommendationViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    val placeImage = itemView?.placeImage
    val placeTitle = itemView?.placeTitle
    val placeDescription = itemView?.placeDescription

    fun setRating(rating: Int) {
        itemView.ratingContainer?.removeAllViews()
        for (i in 0..rating) {
            val starView = ImageView(itemView.context)
            starView.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_star_black_24dp))
            starView.setColorFilter(ContextCompat.getColor(itemView.context, R.color.colorPrimaryDark))
            val size = itemView.context.resources.getDimensionPixelSize(R.dimen.keyline_2)
            val params = ViewGroup.LayoutParams(size, size)
            starView.layoutParams = params
            itemView.ratingContainer?.addView(starView)
        }
    }
}
