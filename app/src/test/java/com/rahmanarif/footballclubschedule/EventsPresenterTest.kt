package com.rahmanarif.footballclubschedule

import com.google.gson.Gson
import com.rahmanarif.footballclubschedule.api.ApiRepository
import com.rahmanarif.footballclubschedule.api.TheSportDBApi
import com.rahmanarif.footballclubschedule.main.FragmentView
import com.rahmanarif.footballclubschedule.model.Events
import com.rahmanarif.footballclubschedule.model.EventsResponse
import com.rahmanarif.footballclubschedule.presenter.FragmentPresenter
import com.rahmanarif.footballclubschedule.util.TestContextProvider
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class EventsPresenterTest{
    @Mock
    private
    lateinit var view: FragmentView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: FragmentPresenter


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = FragmentPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun testPastEventsList() {
        val event = listOf<Events>(Events("576548"))
        val response = EventsResponse(event)

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getPastEvents()),
                EventsResponse::class.java
        )).thenReturn(response)

        presenter.getPastEventsList()

        verify(view).showLoading()
        verify(view).showPastEvents(event)
        verify(view).hideLoading()

    }

    @Test
    fun testAPIDoRequest(){
        val url = "https://www.thesportsdb.com/api/v1/json/1/lookupteam.php?id=133600"
        apiRepository.doRequest(TheSportDBApi.getTeam("133600"))
        verify(apiRepository).doRequest(url)
    }
}