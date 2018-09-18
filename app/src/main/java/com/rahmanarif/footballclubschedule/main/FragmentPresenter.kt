package com.rahmanarif.footballclubschedule.main

import com.google.gson.Gson
import com.rahmanarif.footballclubschedule.api.ApiRepository
import com.rahmanarif.footballclubschedule.api.TheSportDBApi
import com.rahmanarif.footballclubschedule.model.EventsResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class FragmentPresenter(private val view: FragmentView,
                        private val apiRepository: ApiRepository,
                        private val gson: Gson) {
    fun getPastEventsList(){
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getPastEvents()),
                    EventsResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showPastEvents(data.events)
            }
        }
    }

    fun getNextEventsList(){
//        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getNextEvents()),
                    EventsResponse::class.java
            )

            uiThread {
//                view.hideLoading()
                view.showNextEvents(data.events)
            }
        }
    }
}