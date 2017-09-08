package xyz.fullstackahead.where2go.utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import java.io.IOException

fun getAddressFromLocation(latitude: Double, longitude: Double, context: Context): Address? {
    val geocoder = Geocoder(context)
    return try {
        geocoder.getFromLocation(latitude, longitude, 1)[0]
    } catch (ex: IOException) {
        null
    }
}
