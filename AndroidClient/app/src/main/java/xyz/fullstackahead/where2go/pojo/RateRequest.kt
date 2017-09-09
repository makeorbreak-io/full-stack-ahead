package xyz.fullstackahead.where2go.pojo

import com.google.gson.annotations.SerializedName

data class RateRequest(
        @SerializedName("place_id")
        val placeId: String = "",
        val rating: Int = 0
)
