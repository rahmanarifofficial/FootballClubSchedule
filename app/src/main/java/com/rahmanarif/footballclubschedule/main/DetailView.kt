package com.rahmanarif.footballclubschedule.main

import com.rahmanarif.footballclubschedule.model.Team

interface DetailView {
    fun showTeam(data: Team)
}