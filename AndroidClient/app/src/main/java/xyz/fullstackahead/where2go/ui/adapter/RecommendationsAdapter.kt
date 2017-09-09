package xyz.fullstackahead.where2go.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import xyz.fullstackahead.where2go.R
import xyz.fullstackahead.where2go.pojo.Recommendation
import xyz.fullstackahead.where2go.ui.viewholder.RecommendationViewHolder

class RecommendationsAdapter : RecyclerView.Adapter<RecommendationViewHolder>() {

    var data: List<Recommendation> = emptyList()


    fun update(data: List<Recommendation>) {
        this.data = data
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecommendationViewHolder?, position: Int) {
        val recommendation = data[position]
        holder?.placeTitle?.text = recommendation.title
        holder?.placeDescription?.text = recommendation.description
        // TODO holder?.placeImage
        holder?.setRating(recommendation.rating)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecommendationViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.viewtag_recommendation, parent, false)
        return RecommendationViewHolder(view)
    }

}
