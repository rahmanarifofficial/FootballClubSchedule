package com.rahmanarif.footballclubschedule.api

import android.net.Uri
import com.rahmanarif.footballclubschedule.BuildConfig

object TheSportDBApi {
    fun getPastEvents(): String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("eventspastleague.php")
                .appendQueryParameter("id","4328")
                .build()
                .toString()
    }

    fun getNextEvents(): String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("eventsnextleague.php")
                .appendQueryParameter("id", "4328")
                .build()
                .toString()
    }
}