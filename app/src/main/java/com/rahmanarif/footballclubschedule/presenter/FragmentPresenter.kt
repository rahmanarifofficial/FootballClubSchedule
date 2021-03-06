package com.rahmanarif.footballclubschedule.presenter

import com.google.gson.Gson
import com.rahmanarif.footballclubschedule.api.ApiRepository
import com.rahmanarif.footballclubschedule.api.TheSportDBApi
import com.rahmanarif.footballclubschedule.main.FragmentView
import com.rahmanarif.footballclubschedule.model.EventsResponse
import com.rahmanarif.footballclubschedule.util.CoroutineContextProvider
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class FragmentPresenter(private val view: FragmentView,
                        private val apiRepository: ApiRepository,
                        private val gson: Gson,
                        private val context: CoroutineContextProvider = CoroutineContextProvider()) {
    fun getPastEventsList(){
        view.showLoading()
        async(context.main){
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getPastEvents()),
                        EventsResponse::class.java
                )
            }
            view.showPastEvents(data.await().events)
            view.hideLoading()
        }
//        doAsync {
//            val data = gson.fromJson(apiRepository
//                    .doRequest(TheSportDBApi.getPastEvents()),
//                    EventsResponse::class.java
//            )
//
//            uiThread {
//                view.hideLoading()
//                view.showPastEvents(data.events)
//            }
//        }
    }

    fun getNextEventsList(){
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getNextEvents()),
                    EventsResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showNextEvents(data.events)
            }
        }
    }
}