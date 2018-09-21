package com.rahmanarif.footballclubschedule.presenter

import com.google.gson.Gson
import com.rahmanarif.footballclubschedule.api.ApiRepository
import com.rahmanarif.footballclubschedule.api.TheSportDBApi
import com.rahmanarif.footballclubschedule.main.DetailView
import com.rahmanarif.footballclubschedule.model.TeamResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DetailPresenter(private val view: DetailView,
                      private val apiRepository: ApiRepository,
                      private val gson: Gson) {
    fun getTeam(idTeam: String){
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getTeam(idTeam)),
                    TeamResponse::class.java
            )

            uiThread {
                view.showTeam(data.teams.first())
            }
        }
    }
}