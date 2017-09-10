package xyz.fullstackahead.where2go.ui.adapter

import android.support.v7.widget.RecyclerView
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import xyz.fullstackahead.where2go.R
import xyz.fullstackahead.where2go.Where2GoApp
import xyz.fullstackahead.where2go.network.ApiClient
import xyz.fullstackahead.where2go.network.RequestManager
import xyz.fullstackahead.where2go.pojo.RateRequest
import xyz.fullstackahead.where2go.pojo.Recommendation
import xyz.fullstackahead.where2go.ui.viewholder.RecommendationViewHolder
import xyz.fullstackahead.where2go.utils.loadImage
import javax.inject.Inject

class RecommendationsAdapter(
        private val recyclerView: RecyclerView
) : RecyclerView.Adapter<RecommendationViewHolder>() {

    var data: List<Recommendation> = emptyList()

    @Inject
    lateinit var apiClient: ApiClient

    private var expandedPosition = -1

    init {
        Where2GoApp.instance.component.inject(this)
    }


    fun update(data: List<Recommendation>) {
        this.data = data.sortedByDescending { it.predictedRating }
        expandedPosition = -1
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecommendationViewHolder?, position: Int) {
        if (holder == null) return
        val recommendation = data[position]
        holder.placeTitle?.text = recommendation.name
        holder.placeCategories?.text = recommendation.categories.joinToString(separator = ", ")
        if (recommendation.imageUrl != null && recommendation.imageUrl.isNotEmpty()) {
            loadImage(holder.placeImage!!, recommendation.imageUrl)
        } else {
            loadImage(holder.placeImage!!, R.drawable.place_placeholder)
        }

        holder.setRating(recommendation.userRating.toInt())
        holder.setPredictedRating(recommendation.predictedRating)
        if (recommendation.price != null) {
            holder.price?.text = holder.itemView?.context?.getString(R.string.place_price, recommendation.price)
            holder.price?.visibility = VISIBLE
        } else {
            holder.price?.visibility = GONE
        }

        val isExpanded = position == expandedPosition
        holder.expandedHolder?.visibility = if (isExpanded) VISIBLE else GONE
        holder.itemView?.isActivated = isExpanded
        holder.showNumberPicker(isExpanded, recommendation.userRating.toInt())
        holder.cardView?.setOnClickListener {
            expandedPosition = if (isExpanded) -1 else position
            TransitionManager.beginDelayedTransition(recyclerView)
            notifyItemChanged(position)
        }

        holder.confirmRateBtn?.setOnClickListener {
            val value = holder.ratingPicker!!.value
            recommendation.userRating = value.toFloat()
            holder.cardView?.performClick()
            RequestManager.enqueue(apiClient.rate(RateRequest(placeId = recommendation.id, rating = value)))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecommendationViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.viewtag_recommendation, parent, false)
        return RecommendationViewHolder(view)
    }

}
