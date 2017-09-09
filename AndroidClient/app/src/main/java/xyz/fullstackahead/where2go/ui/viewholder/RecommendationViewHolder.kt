package xyz.fullstackahead.where2go.ui.viewholder

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import kotlinx.android.synthetic.main.viewtag_recommendation.view.*
import xyz.fullstackahead.where2go.R
import xyz.fullstackahead.where2go.Where2GoApp

class RecommendationViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    val placeImage = itemView?.placeImage
    val placeTitle = itemView?.placeTitle
    val placeDescription = itemView?.placeDescription
    private val rateMeBtn = itemView?.btnRate
    private val predictedRating = itemView?.predictedRating
    private val ratingContainer = itemView?.ratingContainer

    fun setRating(rating: Int) {
        cleanRatingStars()
        if (rating == 0) {
            rateMeBtn?.visibility = VISIBLE
        } else {
            rateMeBtn?.visibility = GONE
            for (i in 1..rating) {
                val starView = ImageView(itemView.context)
                starView.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_star_black_24dp))
                starView.setColorFilter(ContextCompat.getColor(itemView.context, R.color.colorPrimaryDark))
                val size = itemView.context.resources.getDimensionPixelSize(R.dimen.keyline_2)
                val params = ViewGroup.LayoutParams(size, size)
                starView.layoutParams = params
                ratingContainer?.addView(starView)
            }
        }
    }


    private fun cleanRatingStars() {
        val childCount = ratingContainer?.childCount ?: 0
        val toRemove = (0..childCount)
                .map { ratingContainer?.getChildAt(it) }
                .filterIsInstance<ImageView>()
        for (view in toRemove) {
            ratingContainer?.removeView(view)
        }
    }


    fun setPredictedRating(rating: Float) {
        val text = "%.1f".format(rating)
        predictedRating?.text = Where2GoApp.instance.getString(R.string.place_predicted_rating, text)
    }

}
