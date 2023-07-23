package com.app.riststock.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import com.app.riststock.R
import com.app.riststock.RootApplication
import com.app.riststock.utils.NumberHandler
import java.util.*


object MapHandler {

    fun getGPSData(gps: String?): DoubleArray {
        val data = DoubleArray(2)
        if (gps != null) {
            val gpsSplit = gps.split(",".toRegex()).toTypedArray()
            for (i in gpsSplit.indices) data[i] = NumberHandler.getDouble(gpsSplit[i])
        }
        return data
    }

//    fun getGpsAddress(c: Context, latitude: Double?, longitude: Double?): String {
//        return try {
//            val addresses: List<Address>
//            val geocoder = Geocoder(c, Locale(UtilityApp.language))
//            addresses = geocoder.getFromLocation(
//                latitude!!,
//                longitude!!,
//                1
//            ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5
//            val address: String
//            //            if (addresses.get(0) != null)
//            address =
//                addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//            //            else
//            //                address = c.getResources().getString(R.string.no_address);
//            address
//        } catch (e: Exception) {
//            e.printStackTrace()
//            c.resources.getString(R.string.no_address)
//        }
//        //        return c.getResources().getString(R.string.no_address);
//    }

    fun GetMapImage(size: String, latitude: Double, longitude: Double): String {
//        markers=icon:https://mytable.sa/static/media/pin_32.png|" + latitude + "," + longitude
        return ("https://maps.googleapis.com/maps/api/staticmap?center=" + latitude + "," + longitude + "&size=" + size + "&sensor=false&zoom=17&scale=2&style=feature:poi|visibility:off"
                + "&key=" + RootApplication.instance!!.getString(R.string.mapKey))
    }

    fun getDistance(
        srcLat: Double?,
        srcLng: Double?,
        destLat: Double?,
        destLng: Double?
    ): Float {

        try {
            val loc1 = Location("")
            loc1.latitude = srcLat!!
            loc1.longitude = srcLng!!

            val loc2 = Location("")
            loc2.latitude = destLat!!
            loc2.longitude = destLng!!

            return loc1.distanceTo(loc2)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return 0F
    }

    fun OpenGoogleMapIntent(activity: Activity, title: String, lat: Double, lng: Double) {
        val uriBegin = "geo:$lat,$lng"
        val query = "$lat,$lng($title)"
        val encodedQuery = Uri.encode(query)
        val uriString = "$uriBegin?q=$encodedQuery&z=16"
        val uri = Uri.parse(uriString)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        activity.startActivity(intent)
    }
}
