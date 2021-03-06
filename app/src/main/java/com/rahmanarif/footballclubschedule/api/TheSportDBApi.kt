package com.rahmanarif.footballclubschedule.api

import android.net.Uri
import com.rahmanarif.footballclubschedule.BuildConfig

object TheSportDBApi {
    fun getPastEvents(): String {
        return "https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id=4328"
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

    fun getTeam(idTeam: String?): String{
        return "https://www.thesportsdb.com/api/v1/json/1/lookupteam.php?id="+idTeam
    }

    fun getDetailEvent(idEvent: String?): String{
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("lookupevent.php")
                .appendQueryParameter("id", idEvent)
                .build()
                .toString()
    }
}