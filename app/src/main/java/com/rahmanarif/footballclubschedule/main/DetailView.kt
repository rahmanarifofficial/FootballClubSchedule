package com.rahmanarif.footballclubschedule.main

import com.rahmanarif.footballclubschedule.model.DetailEvent
import com.rahmanarif.footballclubschedule.model.Team

interface DetailView {
    fun showTeam(data: Team)
    fun getDetailEvent(data: DetailEvent)
}